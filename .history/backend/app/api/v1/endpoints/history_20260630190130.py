from fastapi import APIRouter

from app.services.history_service import HistoryService

router = APIRouter()


@router.get("/")
def get_history():
    return HistoryService.get_history()