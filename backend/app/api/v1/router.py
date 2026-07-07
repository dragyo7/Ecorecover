from fastapi import APIRouter

from app.api.v1.endpoints import search

from app.api.v1.endpoints import (
    prices,
    estimate,
    auth,
    appointments,
    history,
    orders,
    rewards,
    profile,
)

api_router = APIRouter()

api_router.include_router(
    prices.router,
    prefix="/prices",
    tags=["Market"]
)

api_router.include_router(
    estimate.router,
    prefix="/estimate",
    tags=["Estimate"]
)

api_router.include_router(
    auth.router,
    prefix="/auth",
    tags=["Authentication"]
)

api_router.include_router(
    appointments.router,
    prefix="/appointments",
    tags=["Appointments"]
)

api_router.include_router(
    history.router,
    prefix="/history",
    tags=["History"]
)

api_router.include_router(
    search.router,
    prefix="/search",
    tags=["Search"]
)

api_router.include_router(
    orders.router,
    prefix="/orders",
    tags=["Orders"]
)

api_router.include_router(
    rewards.router,
    prefix="/rewards",
    tags=["Rewards"]
)

api_router.include_router(
    profile.router,
    prefix="/profile",
    tags=["Profile"]
)