from app.core.supabase import supabase


class AppointmentService:

    @staticmethod
    def create(data: dict):

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