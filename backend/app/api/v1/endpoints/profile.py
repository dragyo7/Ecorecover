from fastapi import APIRouter, Depends, HTTPException, status
from pydantic import BaseModel
from app.api.v1.dependencies import get_current_user, security
from fastapi.security import HTTPAuthorizationCredentials
from supabase import create_client
from supabase_auth import Session
from app.core.settings import settings

router = APIRouter()

class ProfileUpdate(BaseModel):
    full_name: str

@router.get("/")
def get_profile(current_user=Depends(get_current_user)):
    user_metadata = getattr(current_user, "user_metadata", {}) or {}
    full_name = user_metadata.get("full_name") or current_user.email.split("@")[0]
    
    return {
        "success": True,
        "data": {
            "id": current_user.id,
            "full_name": full_name,
            "email": current_user.email
        }
    }

@router.put("/")
def update_profile(
    request: ProfileUpdate,
    credentials: HTTPAuthorizationCredentials = Depends(security),
    current_user=Depends(get_current_user)
):
    try:
        token = credentials.credentials
        
        # Initialize a new Supabase client and set the user's session in the auth instance
        user_client = create_client(settings.SUPABASE_URL, settings.SUPABASE_ANON_KEY)
        session = Session(
            access_token=token,
            refresh_token="dummy",
            expires_in=3600,
            token_type="bearer",
            user=current_user
        )
        user_client.auth._save_session(session)
        
        response = user_client.auth.update_user(
            attributes={
                "data": {
                    "full_name": request.full_name
                }
            }
        )
        
        if not response or not response.user:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail="Failed to update profile metadata"
            )
            
        updated_user = response.user
        user_metadata = getattr(updated_user, "user_metadata", {}) or {}
        full_name = user_metadata.get("full_name") or updated_user.email.split("@")[0]
        
        return {
            "success": True,
            "message": "Profile updated successfully",
            "data": {
                "id": updated_user.id,
                "full_name": full_name,
                "email": updated_user.email
            }
        }
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to update profile: {str(e)}"
        )
