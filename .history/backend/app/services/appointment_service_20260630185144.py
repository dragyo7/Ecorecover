from app.core.supabase import supabase


class AppointmentService:

    @staticmethod
    def create(data: dict):

        # Remove user_id during development if not available
        if not data.get("user_id"):
            data.pop("user_id", None)

        response = (
            supabase
            .table("appointments")
            .insert(data)
            .execute()
        )

        return {
            "success": True,
            "message": "Appointment booked successfully",
            "data": response.data
        }

    @staticmethod
    def list():

        response = (
            supabase
            .table("appointments")
            .select("*")
            .execute()
        )

        return {
            "success": True,
            "data": response.data
        }