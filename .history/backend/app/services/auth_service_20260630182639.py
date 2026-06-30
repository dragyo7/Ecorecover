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
                "data": response.user.model_dump() if response.user else None
            }

        except AuthApiError as e:

            return {
                "success": False,
                "message": str(e),
                "data": None
            }

    @staticmethod
    def login(email: str, password: str):

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
                "data": {
                    "user": response.user.model_dump(),
                    "access_token": response.session.access_token
                }
            }

        except AuthApiError as e:

            return {
                "success": False,
                "message": str(e),
                "data": None
            }