from fastapi import APIRouter

router = APIRouter()


@router.get("/")
def get_prices():

    return {
        "gold": 10500,
        "silver": 125,
        "copper": 0.95,
        "currency": "INR"
    }