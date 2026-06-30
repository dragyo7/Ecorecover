from app.core.supabase import supabase


class AppointmentService:

    @staticmethod
    def create(data: dict):

        # Convert date/time objects to strings
        data["appointment_date"] = str(data["appointment_date"])
        data["appointment_time"] = str(data["appointment_time"])

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