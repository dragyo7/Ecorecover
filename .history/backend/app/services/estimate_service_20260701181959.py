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
        
        if "estimated_total_value_inr" in result:
    result["estimated_total_value_inr"] = round(
        result["estimated_total_value_inr"], 2
    )

for metal in result.get("metal_valuation", {}).values():

    if "live_price" in metal:
        metal["live_price"] = round(
            metal["live_price"], 2
        )

    if "estimated_value" in metal:
        metal["estimated_value"] = round(
            metal["estimated_value"], 2
        )
        

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