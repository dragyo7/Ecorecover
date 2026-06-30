import pandas as pd

from app.core.paths import ECONOMIC_HISTORY_FILE


class HistoryService:

    @staticmethod
    def get_history():

        df = pd.read_csv(ECONOMIC_HISTORY_FILE)

        history = {}

        for metal in df["metal"].unique():

            metal_df = df[df["metal"] == metal]

            history[metal] = []

            for _, row in metal_df.iterrows():

                history[metal].append(
                    {
                        "timestamp": row["timestamp"],
                        "price": float(row["price_inr_per_g"]),
                        "trend": row["trend"],
                        "daily_percent": float(row["daily_percent"]),
                    }
                )

        return {
            "success": True,
            "data": history
        }