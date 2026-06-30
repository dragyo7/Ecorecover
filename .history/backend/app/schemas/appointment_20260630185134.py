from typing import Optional
from pydantic import BaseModel
from datetime import date, time


class AppointmentCreate(BaseModel):
    user_id: Optional[str] = None

    product_name: str
    estimated_price: float

    service_type: str

    appointment_date: date
    appointment_time: time

    address: str
    city: str

    notes: str = ""


class AppointmentResponse(BaseModel):
    success: bool
    message: str
    data: dict | None = None