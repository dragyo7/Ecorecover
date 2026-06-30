from fastapi import APIRouter
from app.services.price_service import PriceService

router = APIRouter()


@router.get("/")
def get_prices():

    return PriceService.get_live_prices()