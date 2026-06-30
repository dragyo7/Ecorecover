from fastapi import APIRouter

from app.schemas.appointment import AppointmentCreate
from app.services.appointment_service import AppointmentService

router = APIRouter()


@router.post("/")
def create_appointment(request: AppointmentCreate):

    return AppointmentService.create(
        request.model_dump()
    )


@router.get("/")
def get_appointments():

    return AppointmentService.list()