from datetime import datetime

from app.core.supabase import supabase


class AppointmentService:

    @staticmethod
    def create(data: dict):

        # Remove empty user_id during development
        if not data.get("user_id"):
            data.pop("user_id", None)

        # Default values
        data.setdefault("status", "Pending")
        data.setdefault("created_at", datetime.now().isoformat())
        data.setdefault("notes", "")

        response = (
            supabase
            .table("appointments")
            .insert(data)
            .execute()
        )

        return {
            "success": True,
            "message": "Appointment booked successfully",
            "count": len(response.data),
            "data": response.data
        }

    @staticmethod
    def list():

        response = (
            supabase
            .table("appointments")
            .select("*")
            .order("created_at", desc=True)
            .execute()
        )

        return {
            "success": True,
            "count": len(response.data),
            "data": response.data
        }