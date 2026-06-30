import json
from datetime import datetime

from app.existing.ewaste_lookup import EwasteLookup


class CostFetcher:

    IGNORE_METALS = [
        "Carbon (g)"
    ]

    PRICE_MAPPING = {

        "Gold (g)": "Gold",
        "Silver (g)": "Silver",
        "Platinum (g)": "Platinum",
        "Rhodium (g)": "Rhodium",
        "Nickel (g)": "Nickel",
        "Aluminum (g)": "Aluminum",
        "Tin (g)": "Tin",
        "Lithium (g)": "Lithium"

    }

    # ----------------------------------------------------

    def __init__(self,
                 dataset_path,
                 price_json):

        self.lookup = EwasteLookup(
            dataset_path
        )

        with open(
            price_json,
            "r",
            encoding="utf-8"
        ) as f:

            self.market = json.load(f)

    # ----------------------------------------------------

    def get_price(
            self,
            dataset_column):

        metal = self.PRICE_MAPPING.get(
            dataset_column
        )

        if metal is None:
            return None

        return self.market.get(
            metal
        )

    # ----------------------------------------------------

    def calculate(
            self,
            product):

        # Uses MINIMUM metal content
        lookup = self.lookup.search_product(
            product,
            method="minimum"
        )

        if lookup["entries_found"] == 0:

            return {
                "error":
                "Product not found."
            }

        valuation = {}

        total = 0

        for metal, qty in lookup[
            "metal_content"
        ].items():

            qty = float(qty)

            if metal in self.IGNORE_METALS:

                valuation[metal] = {

                    "quantity_g":
                    round(qty, 4),

                    "status":
                    "Ignored (Not traded commodity)"

                }

                continue

            market = self.get_price(
                metal
            )

            if market is None:

                valuation[metal] = {

                    "quantity_g":
                    round(qty, 4),

                    "status":
                    "Market data unavailable"

                }

                continue

            value = qty * market[
                "price_inr_per_g"
            ]

            valuation[metal] = {

                "quantity_g":
                round(qty, 4),

                "live_price":
                round(
                    market["price_inr_per_g"],
                    4
                ),

                "unit":
                "INR / gram",

                "market_unit":
                market["unit"],

                "trend":
                market["trend"],

                "daily_percent":
                market["daily_percent"],

                "weekly_percent":
                market["weekly_percent"],

                "monthly_percent":
                market["monthly_percent"],

                "ytd_percent":
                market["ytd_percent"],

                "estimated_value":
                round(
                    value,
                    2
                )

            }

            total += value

        contribution = {}

        if total > 0:

            for metal, info in valuation.items():

                if "estimated_value" in info:

                    contribution[
                        metal
                    ] = round(

                        info[
                            "estimated_value"
                        ]
                        / total
                        * 100,

                        2

                    )

        contribution = dict(

            sorted(

                contribution.items(),

                key=lambda x: x[1],

                reverse=True

            )

        )

        return {

            "timestamp":

            datetime.now().strftime(
                "%d-%m-%Y %H:%M:%S"
            ),

            "product":

            lookup["product_name"],

            "entries_found":

            lookup["entries_found"],

            "calculation_method":

            lookup["calculation_method"],

            "metal_content_used":

            lookup["metal_content"],

            "metal_valuation":

            valuation,

            "metal_contribution_percent":

            contribution,

            "estimated_total_value_inr":

            round(
                total,
                2
            )

        }


# ======================================================

if __name__ == "__main__":

    engine = CostFetcher(

        dataset_path="e_waste_dataset (1).csv",

        price_json="economic_latest.json"

    )

    while True:

        product = input(
            "\nEnter Product Name (quit): "
        )

        if product.lower() == "quit":
            break

        result = engine.calculate(
            product
        )

        print("\n")
        print("=" * 100)
        print(
            json.dumps(
                result,
                indent=4
            )
        )
        print("=" * 100)