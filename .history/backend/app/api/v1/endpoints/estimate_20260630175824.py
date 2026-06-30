from fastapi import APIRouter

from app.schemas.estimate import EstimateRequest
from app.services.estimate_service import EstimateService

router = APIRouter()

service = EstimateService()


@router.post("/")
def estimate(request: EstimateRequest):

    return service.estimate(request.product)