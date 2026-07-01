from fastapi import APIRouter
from fastapi.encoders import jsonable_encoder

from app.schemas.appointment import AppointmentCreate
from app.services.appointment_service import AppointmentService

router = APIRouter()


@router.post("/")
def create_appointment(request: AppointmentCreate):

    data = jsonable_encoder(
        request,
        exclude_none=True
    )

    # Swagger's default placeholder should never reach the DB
    if data.get("user_id") == "string":
        data.pop("user_id")

    return AppointmentService.create(data)


@router.get("/")
def get_appointments():

    return AppointmentService.list()