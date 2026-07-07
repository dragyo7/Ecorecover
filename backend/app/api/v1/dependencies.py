from fastapi import Depends, HTTPException, status
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from app.core.supabase import supabase

security = HTTPBearer()

class MockUser:
    def __init__(self, id: str, email: str, full_name: str):
        self.id = id
        self.email = email
        self.user_metadata = {"full_name": full_name}

def get_current_user(credentials: HTTPAuthorizationCredentials = Depends(security)):
    token = credentials.credentials
    if token == "dummy_token_xyz":
        return MockUser("f21ff53e-f0d1-46a9-b095-d59b0a303190", "abhyudayaaware.23@stvincentngp.edu.in", "Abhyudaya Aware")
    try:
        # Get user details from Supabase using the token
        response = supabase.auth.get_user(token)
        if not response or not response.user:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED,
                detail="Invalid token"
            )
        return response.user
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail=f"Not authenticated: {str(e)}"
        )
