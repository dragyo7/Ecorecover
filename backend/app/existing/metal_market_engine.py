import requests
import json
from datetime import datetime


class MetalMarketEngine:

    SOURCE = "https://metalview.in/api/metals/mumbai"

    SUPPORTED_METALS = {
        "Gold (g)": "gold_1g",
        "Silver (g)": "silver_1g",
        "Copper (g)": "copper_1g"
    }

    UNSUPPORTED_METALS = [
        "Platinum (g)",
        "Rhodium (g)",
        "Nickel (g)",
        "Aluminum (g)",
        "Tin (g)",
        "Lithium (g)"
    ]

    def __init__(self):

        self.history_file = "price_history.json"

    # ----------------------------------

    def fetch_live_data(self):

        response = requests.get(
            self.SOURCE,
            timeout=15
        )

        response.raise_for_status()

        return response.json()

    # ----------------------------------

    def get_current_prices(self):

        data = self.fetch_live_data()

        prices = {}

        prices["Gold (g)"] = data.get(
            "gold_1g"
        )

        prices["Silver (g)"] = data.get(
            "silver_1g"
        )

        prices["Copper (g)"] = data.get(
            "copper_1g"
        )

        for metal in self.UNSUPPORTED_METALS:

            prices[metal] = None

        return prices

    # ----------------------------------

    def get_original_market_data(self):

        data = self.fetch_live_data()

        return {

            "Gold": {
                "1g":
                data.get("gold_1g"),

                "10g":
                data.get("gold_10g"),

                "18K_1g":
                data.get("gold_18k_1g"),

                "source":
                "MetalView Mumbai"
            },

            "Silver": {

                "1g":
                data.get("silver_1g"),

                "1kg":
                data.get("silver_1kg"),

                "source":
                "MetalView Mumbai"
            },

            "Copper": {

                "1g":
                data.get("copper_1g"),

                "10g":
                data.get("copper_10g"),

                "100g":
                data.get("copper_100g"),

                "1kg":
                data.get("copper_1kg"),

                "source":
                "MetalView Mumbai"
            }
        }

    # ----------------------------------

    def get_fluctuations(self):

        data = self.fetch_live_data()

        return {

            "Gold": {

                "change_percent":
                data.get(
                    "gold_24k_percentage"
                )
            },

            "Copper": {

                "change_percent":
                data.get(
                    "copperPercentageChange"
                )
            }
        }

    # ----------------------------------

    def get_trends(self):

        data = self.fetch_live_data()

        return {

            "Copper Trend":
            data.get(
                "copperTrend",
                []
            )
        }

    # ----------------------------------

    def save_snapshot(self):

        try:

            with open(
                self.history_file,
                "r"
            ) as f:

                history = json.load(f)

        except:

            history = []

        history.append({

            "timestamp":
            datetime.now().strftime(
                "%Y-%m-%d %H:%M:%S"
            ),

            "prices":
            self.get_current_prices()
        })

        with open(
            self.history_file,
            "w"
        ) as f:

            json.dump(
                history,
                f,
                indent=4
            )

    # ----------------------------------

    def get_saved_history(self):

        try:

            with open(
                self.history_file,
                "r"
            ) as f:

                return json.load(f)

        except:

            return []

    # ----------------------------------

    def get_complete_report(self):

        return {

            "source":
            self.SOURCE,

            "timestamp":
            datetime.now().strftime(
                "%Y-%m-%d %H:%M:%S"
            ),

            "current_prices":
            self.get_current_prices(),

            "market_data":
            self.get_original_market_data(),

            "fluctuations":
            self.get_fluctuations(),

            "trend_data":
            self.get_trends(),

            "unsupported_metals":
            self.UNSUPPORTED_METALS
        }


# -------------------------------------------------
# Standalone Testing
# -------------------------------------------------

if __name__ == "__main__":

    engine = MetalMarketEngine()

    report = engine.get_complete_report()

    print(
        json.dumps(
            report,
            indent=4
        )
    )

    engine.save_snapshot()

    print(
        "\nSnapshot saved successfully."
    )