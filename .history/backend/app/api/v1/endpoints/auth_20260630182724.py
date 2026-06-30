from fastapi import APIRouter

from app.schemas.auth import SignupRequest, LoginRequest
from app.services.auth_service import AuthService

router = APIRouter()


@router.post("/signup")
def signup(request: SignupRequest):

    return AuthService.signup(
        request.full_name,
        request.email,
        request.password
    )

@router.post("/login")
def login(request: LoginRequest):

    response = AuthService.login(
        request.email,
        request.password
    )

    return {
        "success": True,
        "message": "Login successful",
        "data": {
            "access_token": response.session.access_token,
            "refresh_token": response.session.refresh_token,
            "user": response.user
        }
    }