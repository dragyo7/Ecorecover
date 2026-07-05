import unittest
from unittest.mock import patch, MagicMock
from supabase_auth.errors import AuthApiError
from app.services.auth_service import AuthService


class TestAuthServiceUnit(unittest.TestCase):

    @patch("app.services.auth_service.supabase")
    def test_signup_success(self, mock_supabase):
        # Mock successful sign up
        mock_user = MagicMock()
        mock_user.model_dump.return_value = {"id": "123", "email": "test@example.com"}
        mock_response = MagicMock()
        mock_response.user = mock_user
        mock_supabase.auth.sign_up.return_value = mock_response

        res = AuthService.signup("Test User", "test@example.com", "password")
        self.assertTrue(res["success"])
        self.assertEqual(res["status"], "VERIFICATION_REQUIRED")
        self.assertEqual(res["data"]["id"], "123")

    @patch("app.services.auth_service.supabase")
    def test_signup_duplicate_error(self, mock_supabase):
        # Mock AuthApiError for duplicate signup
        mock_supabase.auth.sign_up.side_effect = AuthApiError(
            "For security purposes, you can only request this after 57 seconds.", 
            429,
            "rate_limit"
        )

        res = AuthService.signup("Test User", "test@example.com", "password")
        self.assertFalse(res["success"])
        self.assertEqual(res["status"], "ERROR")
        self.assertIn("after 57 seconds", res["message"])

    @patch("app.services.auth_service.supabase")
    def test_login_success(self, mock_supabase):
        # Mock successful login
        mock_user = MagicMock()
        mock_user.model_dump.return_value = {"id": "123", "email": "test@example.com"}
        mock_session = MagicMock()
        mock_session.access_token = "access-token-xyz"
        mock_response = MagicMock()
        mock_response.user = mock_user
        mock_response.session = mock_session
        mock_supabase.auth.sign_in_with_password.return_value = mock_response

        res = AuthService.login("test@example.com", "password")
        self.assertTrue(res["success"])
        self.assertEqual(res["status"], "SUCCESS")
        self.assertEqual(res["data"]["access_token"], "access-token-xyz")

    @patch("app.services.auth_service.supabase")
    def test_login_unconfirmed_error(self, mock_supabase):
        # Mock unconfirmed email error
        mock_supabase.auth.sign_in_with_password.side_effect = AuthApiError(
            "Email not confirmed", 
            400,
            "email_not_confirmed"
        )

        res = AuthService.login("test@example.com", "password")
        self.assertTrue(res["success"])
        self.assertEqual(res["status"], "VERIFICATION_REQUIRED")
        self.assertIsNone(res["data"])

    @patch("app.services.auth_service.supabase")
    def test_login_invalid_credentials(self, mock_supabase):
        # Mock invalid credentials error
        mock_supabase.auth.sign_in_with_password.side_effect = AuthApiError(
            "Invalid login credentials", 
            400,
            "invalid_credentials"
        )

        res = AuthService.login("test@example.com", "password")
        self.assertFalse(res["success"])
        self.assertEqual(res["status"], "ERROR")

    @patch("app.services.auth_service.supabase")
    def test_resend_success(self, mock_supabase):
        mock_supabase.auth.resend.return_value = MagicMock()

        res = AuthService.resend_verification("test@example.com")
        self.assertTrue(res["success"])
        self.assertEqual(res["message"], "Verification email resent successfully")


if __name__ == "__main__":
    unittest.main()
