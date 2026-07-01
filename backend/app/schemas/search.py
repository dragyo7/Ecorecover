from pydantic import BaseModel


class SearchItem(BaseModel):
    id: int
    name: str


class SearchResponse(BaseModel):
    success: bool
    query: str
    count: int
    results: list[SearchItem]