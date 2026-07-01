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
        "message": "EcoRecover Backend Running 🚀"
    }


@app.get("/health")
def health():
    return {
        "status": "healthy"
    }


app.include_router(
    api_router,
    prefix="/api/v1"
)