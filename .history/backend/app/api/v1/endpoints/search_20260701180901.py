from fastapi import APIRouter

from app.services.search_service import SearchService

router = APIRouter()

service = SearchService()


@router.get("/")
def search(q: str):

    return {
        "success": True,
        "query": q,
        "count": len(service.search(q)),
        "results": service.search(q)
    }