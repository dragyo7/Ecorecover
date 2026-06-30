from app.engines.cost_fetcher import CostFetcher
from app.core.paths import DATASET_PATH, LATEST_PRICE_FILE


class EstimateService:

    def __init__(self):
        self.engine = CostFetcher(
            dataset_path=str(DATASET_PATH),
            price_json=str(LATEST_PRICE_FILE)
        )

    def estimate(self, product: str):

        result = self.engine.calculate(product)

        if "error" in result:

            return {
                "success": False,
                "message": result["error"],
                "data": None
            }

        return {
            "success": True,
            "message": "Estimate generated successfully",
            "data": result
        }