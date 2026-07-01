from fastapi import APIRouter

from app.services.search_service import SearchService

router = APIRouter()

service = SearchService()


@router.get("/")
def search(q: str):

    results = service.search(q)

    return {
        "success": True,
        "query": q,
        "count": len(results),
        "results": [
            {
                "id": index + 1,
                "name": item
            }
            for index, item in enumerate(results)
        ]
    }