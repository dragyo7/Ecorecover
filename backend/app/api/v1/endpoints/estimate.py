from fastapi import APIRouter
from pydantic import BaseModel

from app.services.estimate_service import EstimateService

router = APIRouter()

service = EstimateService()


class EstimateRequest(BaseModel):
    product: str


@router.post("/")
def estimate(request: EstimateRequest):

    return service.estimate(request.product)