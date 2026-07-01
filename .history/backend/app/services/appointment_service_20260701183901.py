from app.core.supabase import supabase


class AppointmentService:

    @staticmethod
    def create(data: dict):

        # Remove invalid user_id during development
        user_id = data.get("user_id")

        if (
            user_id is None
            or user_id == ""
            or user_id == "string"
        ):
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
            .order("created_at", desc=True)
            .execute()

        return {
            "success": True,
            "data": response.data
        }