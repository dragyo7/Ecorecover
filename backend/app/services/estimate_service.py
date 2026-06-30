from pathlib import Path
from app.existing.engines.cost_fetcher import CostFetcher

BASE_DIR = Path(__file__).resolve().parent.parent

DATASET = BASE_DIR / "existing" / "data" / "ewaste_dataset.csv"
PRICE_JSON = BASE_DIR / "existing" / "data" / "economic_latest.json"


class EstimateService:

    def __init__(self):
        self.engine = CostFetcher(
            dataset_path=str(DATASET),
            price_json=str(PRICE_JSON)
        )

    def estimate(self, product: str):
        return self.engine.calculate(product)