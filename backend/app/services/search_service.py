from pathlib import Path
import pandas as pd

from app.core.paths import DATASET_PATH


class SearchService:

    def __init__(self):
        self.df = pd.read_csv(DATASET_PATH)

        self.df.columns = self.df.columns.str.strip()

        print(self.df.columns.tolist())
        self.product_column = self.df.columns[0]

        self.products = (
            self.df[self.product_column]
            .dropna()
            .astype(str)
            .drop_duplicates()
            .tolist()
        )

    def search(self, query: str):

        matches = self.df[
        self.df["Item"].str.contains(
            query,
            case=False,
            na=False
        )
    ]

        return (
            matches["Item"]
            .drop_duplicates()
            .sort_values()
            .tolist()
        )[:20]