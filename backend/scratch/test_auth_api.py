import requests
import random
import sys

BASE_URL = "http://127.0.0.1:8000/api/v1/auth"

def run_tests():
    random_id = random.randint(1000, 9999)
    email = f"test_user_{random_id}@ecorecover.com"
    password = "securepassword123"
    full_name = f"Test User {random_id}"

    print(f"--- Running Auth Integration Tests for email: {email} ---")

    # TEST A: Successful Signup
    signup_payload = {
        "full_name": full_name,
        "email": email,
        "password": password
    }
    print("\n[A] Registering new user...")
    try:
        response = requests.post(f"{BASE_URL}/signup", json=signup_payload)
        print("Status Code:", response.status_code)
        data = response.json()
        print("Response payload:", data)
        assert response.status_code == 200, "Signup should return 200"
        assert data["success"] is True, "Signup should be successful"
        assert data["status"] == "VERIFICATION_REQUIRED", "Signup should return status='VERIFICATION_REQUIRED'"
        print("PASS: Signup successful, Verification Required state exposed")
    except Exception as e:
        print("FAIL: Signup failed:", str(e))
        sys.exit(1)

    # TEST B: Duplicate Email Handling
    print("\n[B] Attempting duplicate registration...")
    try:
        response = requests.post(f"{BASE_URL}/signup", json=signup_payload)
        print("Status Code:", response.status_code)
        data = response.json()
        print("Response payload:", data)
        assert data["success"] is False, "Duplicate signup should return success=False"
        print("PASS: Duplicate registration blocked with success=False")
    except Exception as e:
        print("FAIL: Duplicate registration test failed:", str(e))
        sys.exit(1)

    # TEST C: Login with Invalid Credentials
    print("\n[C] Logging in with incorrect password...")
    try:
        login_payload = {
            "email": email,
            "password": "wrongpassword"
        }
        response = requests.post(f"{BASE_URL}/login", json=login_payload)
        print("Status Code:", response.status_code)
        data = response.json()
        print("Response payload:", data)
        assert data["success"] is False, "Login with wrong password should fail"
        print("PASS: Invalid credentials rejected")
    except Exception as e:
        print("FAIL: Invalid credentials login test failed:", str(e))
        sys.exit(1)

    # TEST D: Login with Correct Credentials (expecting verification required status)
    print("\n[D] Logging in with correct credentials (expecting verification required status)...")
    try:
        login_payload = {
            "email": email,
            "password": password
        }
        response = requests.post(f"{BASE_URL}/login", json=login_payload)
        print("Status Code:", response.status_code)
        data = response.json()
        print("Response payload:", data)
        assert response.status_code == 200, "Login should return 200"
        assert data["success"] is True, "Login should return success=True"
        assert data["status"] == "VERIFICATION_REQUIRED", "Login status should be VERIFICATION_REQUIRED"
        assert data["data"] is None, "Login data must be null for unconfirmed accounts"
        print("PASS: VERIFICATION_REQUIRED state successfully returned!")
    except Exception as e:
        print("FAIL: Valid login verification test failed:", str(e))
        sys.exit(1)

    # TEST E: Resend Verification Email
    print("\n[E] Resending verification email...")
    try:
        resend_payload = {
            "email": email
        }
        response = requests.post(f"{BASE_URL}/resend", json=resend_payload)
        print("Status Code:", response.status_code)
        data = response.json()
        print("Response payload:", data)
        
        is_success = data.get("success") is True
        is_rate_limit = data.get("success") is False and "after" in data.get("message", "")
        
        assert is_success or is_rate_limit, "Resend call must either succeed or return rate limit error from Supabase"
        if is_rate_limit:
            print("PASS: Verification email resend correctly rate-limited by Supabase:", data.get("message"))
        else:
            print("PASS: Verification email resent successfully")
    except Exception as e:
        print("FAIL: Resend verification test failed:", str(e))
        sys.exit(1)

    print("\n--- ALL BACKEND INTEGRATION TESTS PASSED ---")

if __name__ == "__main__":
    run_tests()
