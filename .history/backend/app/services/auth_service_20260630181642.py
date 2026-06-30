from app.core.supabase import supabase


class AuthService:

    @staticmethod
    def signup(full_name: str, email: str, password: str):

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

        return response

    @staticmethod
    def login(email: str, password: str):

        response = supabase.auth.sign_in_with_password(
            {
                "email": email,
                "password": password
            }
        )

        return response