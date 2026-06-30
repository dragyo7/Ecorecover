import pandas as pd
import json


class EwasteLookup:

    def __init__(self, csv_path):

        self.df = pd.read_csv(csv_path)

        self.df.columns = self.df.columns.str.strip()

        self.metal_columns = [
            col for col in self.df.columns
            if col != "Item"
        ]

    # ----------------------------------------------------

    def search_product(self,
                       product_name,
                       method="minimum"):

        results = self.df[
            self.df["Item"]
            .astype(str)
            .str.contains(
                product_name,
                case=False,
                na=False
            )
        ]

        if results.empty:

            return {
                "entries_found": 0,
                "metal_content": {},
                "products": []
            }

        products = []

        for idx, row in results.iterrows():

            product = {
                "entry_id": int(idx),
                "product": row["Item"],
                "metal_content": {}
            }

            for metal in self.metal_columns:

                product["metal_content"][metal] = round(
                    float(row[metal]),
                    4
                )

            products.append(product)

        metal_content = {}

        for metal in self.metal_columns:

            if method.lower() == "minimum":

                value = results[metal].min()

            elif method.lower() == "maximum":

                value = results[metal].max()

            else:

                value = results[metal].mean()

            metal_content[metal] = round(
                float(value),
                4
            )

        return {

            "product_name":
            product_name,

            "entries_found":
            len(products),

            "calculation_method":
            method,

            "metal_content":
            metal_content,

            "products":
            products

        }


# ======================================================

if __name__ == "__main__":

    dataset = EwasteLookup(
        "e_waste_dataset (1).csv"
    )

    while True:

        product_name = input(
            "\nEnter Product Name (or quit): "
        )

        if product_name.lower() == "quit":
            break

        result = dataset.search_product(
            product_name,
            method="minimum"
        )

        print(
            json.dumps(
                result,
                indent=4
            )
        )