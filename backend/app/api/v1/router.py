from fastapi import APIRouter

from app.api.v1.endpoints import prices
from app.api.v1.endpoints import prices, estimate

api_router = APIRouter()

api_router.include_router(
    prices.router,
    prefix="/prices",
    tags=["Metal Prices"]
)
api_router.include_router(
    estimate.router,
    prefix="/estimate",
    tags=["Estimate"]
)
