from pathlib import Path
import pandas as pd

from app.core.paths import DATASET_PATH


class SearchService:

    def __init__(self):
        self.df = pd.read_csv(DATASET_PATH)

        self.df.columns = self.df.columns.str.strip()

        self.product_column = self.df.columns[0]

        self.products = (
            self.df[self.product_column]
            .dropna()
            .astype(str)
            .drop_duplicates()
            .tolist()
        )

    def search(self, query: str):

        query = query.lower().strip()

        results = []

        for product in self.products:

            if query in product.lower():

                results.append(product)

        results = sorted(results)

        return results[:20]