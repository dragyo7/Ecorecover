from pathlib import Path
from backend.app.existing.engines.cost_fetcher import CostFetcher

BASE_DIR = Path(__file__).resolve().parent.parent

DATASET = BASE_DIR / "existing" / "e_waste_dataset (1).csv"
PRICE_JSON = BASE_DIR / "existing" / "economic_latest.json"


class EstimateService:

    def __init__(self):
        self.engine = CostFetcher(
            dataset_path=str(DATASET),
            price_json=str(PRICE_JSON)
        )

    def estimate(self, product: str):
        return self.engine.calculate(product)