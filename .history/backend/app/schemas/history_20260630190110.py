from pydantic import BaseModel
from typing import Dict, List


class PricePoint(BaseModel):
    timestamp: str
    price: float


class HistoryResponse(BaseModel):
    success: bool
    data: Dict[str, List[PricePoint]]