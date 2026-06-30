from pydantic import BaseModel


class EstimateRequest(BaseModel):
    product: str


class ApiResponse(BaseModel):
    success: bool
    message: str
    data: dict | None = None