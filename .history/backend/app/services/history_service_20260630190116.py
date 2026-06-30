import pandas as pd

from app.core.paths import HISTORY_CSV


class HistoryService:

    @staticmethod
    def get_history():

        df = pd.read_csv(HISTORY_CSV)

        history = {}

        for metal in df["metal"].unique():

            rows = df[df["metal"] == metal]

            history[metal] = [
                {
                    "timestamp": row["timestamp"],
                    "price": row["price_inr_per_g"]
                }
                for _, row in rows.iterrows()
            ]

        return {
            "success": True,
            "data": history
        }