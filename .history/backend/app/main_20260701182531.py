from fastapi import FastAPI

from app.api.v1.router import api_router
from app.core.config import settings
from app.core.paths import DATASET_PATH, LATEST_PRICE_FILE

app = FastAPI(
    title=settings.APP_NAME,
    version=settings.VERSION,
    description="AI Powered E-Waste Recovery Platform"
)


@app.get("/")
def root():

    return {
        "application": settings.APP_NAME,
        "version": settings.VERSION,
        "status": "Running",
        "documentation": "/docs",
        "search": "/api/v1/search",
        "estimate": "/api/v1/estimate",
        "market": "/api/v1/prices"
    }


@app.get("/health")
def health():

    from app.core.paths import DATASET_PATH, LATEST_PRICE_FILE

    return {
        "status": "Healthy",
        "version": settings.VERSION,
        "dataset_loaded": DATASET_PATH.exists(),
        "price_cache_loaded": LATEST_PRICE_FILE.exists()
    }

app.include_router(
    api_router,
    prefix="/api/v1"
)