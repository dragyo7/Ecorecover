from fastapi import FastAPI

app = FastAPI(
    title="EcoRecover API",
    version="1.0.0",
    description="Backend API for EcoRecover - AI Powered E-Waste Valuation Platform"
)

@app.get("/")
def root():
    return {
        "message": "Welcome to EcoRecover API 🚀",
        "status": "running"
    }

@app.get("/health")
def health():
    return {
        "status": "healthy"
    }