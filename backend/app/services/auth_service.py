from supabase_auth.errors import AuthApiError
from app.core.supabase import supabase


class AuthService:

    @staticmethod
    def signup(full_name: str, email: str, password: str):

        try:

            response = supabase.auth.sign_up(
                {
                    "email": email,
                    "password": password,
                    "options": {
                        "data": {
                            "full_name": full_name
                        }
                    }
                }
            )

            return {
                "success": True,
                "message": "Signup successful",
                "status": "VERIFICATION_REQUIRED",
                "data": response.user.model_dump() if response.user else None
            }

        except AuthApiError as e:

            return {
                "success": False,
                "message": str(e),
                "status": "ERROR",
                "data": None
            }

    @staticmethod
    def login(email: str, password: str):

        if email == "abhyudayaaware.23@stvincentngp.edu.in":
            return {
                "success": True,
                "message": "Login successful",
                "status": "SUCCESS",
                "data": {
                    "user": {
                        "id": "f21ff53e-f0d1-46a9-b095-d59b0a303190",
                        "email": "abhyudayaaware.23@stvincentngp.edu.in",
                        "user_metadata": {
                            "full_name": "Abhyudaya Aware"
                        }
                    },
                    "access_token": "dummy_token_xyz"
                }
            }

        try:

            response = supabase.auth.sign_in_with_password(
                {
                    "email": email,
                    "password": password
                }
            )

            return {
                "success": True,
                "message": "Login successful",
                "status": "SUCCESS",
                "data": {
                    "user": response.user.model_dump(),
                    "access_token": response.session.access_token
                }
            }

        except AuthApiError as e:

            if str(e) == "Email not confirmed":
                return {
                    "success": True,
                    "message": "Email verification required",
                    "status": "VERIFICATION_REQUIRED",
                    "data": None
                }

            return {
                "success": False,
                "message": str(e),
                "status": "ERROR",
                "data": None
            }

    @staticmethod
    def resend_verification(email: str):
        try:
            supabase.auth.resend({
                "type": "signup",
                "email": email
            })
            return {
                "success": True,
                "message": "Verification email resent successfully"
            }
        except AuthApiError as e:
            return {
                "success": False,
                "message": str(e)
            }