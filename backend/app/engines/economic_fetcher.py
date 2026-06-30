import requests
import pandas as pd
from io import StringIO
from datetime import datetime
import json
import csv
import os

# =====================================================
# CONFIG
# =====================================================

COMMODITIES_URL = "https://tradingeconomics.com/commodities"

LATEST_FILE = "economic_latest.json"
HISTORY_FILE = "economic_history.csv"

TROY_OUNCE_TO_GRAMS = 31.1034768
METRIC_TON_TO_GRAMS = 1_000_000

TARGET_METALS = [
    "Gold",
    "Silver",
    "Platinum",
    "Palladium",
    "Rhodium",
    "Nickel",
    "Aluminum",
    "Tin",
    "Lithium"
]


# =====================================================
# LIVE FX RATES
# =====================================================

def get_exchange_rates():

    try:

        r = requests.get(
            "https://open.er-api.com/v6/latest/USD",
            timeout=15
        )

        data = r.json()

        usd_to_inr = float(data["rates"]["INR"])
        usd_to_cny = float(data["rates"]["CNY"])

        cny_to_inr = usd_to_inr / usd_to_cny

        return {
            "USD_TO_INR": usd_to_inr,
            "CNY_TO_INR": cny_to_inr
        }

    except Exception as e:

        print("FX API failed.")
        print(e)

        return {
            "USD_TO_INR": 86.50,
            "CNY_TO_INR": 12.00
        }


# =====================================================
# UNIT CONVERSION
# =====================================================

def convert_to_inr_per_gram(
    price,
    unit,
    usd_to_inr,
    cny_to_inr
):

    unit = unit.lower()

    # Gold/Silver/Platinum/Palladium/Rhodium

    if "usd/t.oz" in unit or "usd/t oz" in unit:

        usd_per_gram = (
            price /
            TROY_OUNCE_TO_GRAMS
        )

        return usd_per_gram * usd_to_inr

    # Nickel/Tin/Aluminum

    elif "usd/t" in unit:

        usd_per_gram = (
            price /
            METRIC_TON_TO_GRAMS
        )

        return usd_per_gram * usd_to_inr

    # Lithium

    elif "cny/t" in unit:

        cny_per_gram = (
            price /
            METRIC_TON_TO_GRAMS
        )

        return cny_per_gram * cny_to_inr

    # Safety fallback

    return None


# =====================================================
# FETCH COMMODITIES
# =====================================================

def fetch_prices():

    fx = get_exchange_rates()

    usd_to_inr = fx["USD_TO_INR"]
    cny_to_inr = fx["CNY_TO_INR"]

    html = requests.get(
        COMMODITIES_URL,
        headers={
            "User-Agent":
            "Mozilla/5.0"
        },
        timeout=30
    ).text

    tables = pd.read_html(
        StringIO(html)
    )

    metals = {}

    for table in tables:

        first_col = table.columns[0]

        for _, row in table.iterrows():

            item = str(
                row[first_col]
            ).strip()

            for metal in TARGET_METALS:

                if item.startswith(metal):

                    parts = item.split()

                    unit = " ".join(
                        parts[1:]
                    )

                    try:

                        price = float(
                            row["Price"]
                        )

                        day_change = float(
                            row["Day"]
                        )

                        daily_percent = float(
                            str(row["%"])
                            .replace("%", "")
                        )

                        weekly_percent = float(
                            str(row["Weekly"])
                            .replace("%", "")
                        )

                        monthly_percent = float(
                            str(row["Monthly"])
                            .replace("%", "")
                        )

                        ytd_percent = float(
                            str(row["YTD"])
                            .replace("%", "")
                        )

                        yoy_percent = float(
                            str(row["YoY"])
                            .replace("%", "")
                        )

                        inr_per_g = (
                            convert_to_inr_per_gram(
                                price,
                                unit,
                                usd_to_inr,
                                cny_to_inr
                            )
                        )

                        trend = (
                            "Bullish"
                            if daily_percent > 0
                            else
                            "Bearish"
                            if daily_percent < 0
                            else
                            "Neutral"
                        )

                        metals[metal] = {

                            "price_original":
                                price,

                            "unit":
                                unit,

                            "price_inr_per_g":
                                round(
                                    inr_per_g,
                                    4
                                ),

                            "price_inr_per_10g":
                                round(
                                    inr_per_g * 10,
                                    2
                                ),

                            "price_inr_per_100g":
                                round(
                                    inr_per_g * 100,
                                    2
                                ),

                            "price_inr_per_kg":
                                round(
                                    inr_per_g * 1000,
                                    2
                                ),

                            "daily_change":
                                day_change,

                            "daily_percent":
                                daily_percent,

                            "weekly_percent":
                                weekly_percent,

                            "monthly_percent":
                                monthly_percent,

                            "ytd_percent":
                                ytd_percent,

                            "yoy_percent":
                                yoy_percent,

                            "volatility":
                                abs(
                                    daily_percent
                                ),

                            "trend":
                                trend,

                            "usd_to_inr":
                                usd_to_inr,

                            "cny_to_inr":
                                cny_to_inr,

                            "timestamp":
                                datetime.now()
                                .isoformat()
                        }

                    except Exception:
                        pass

    return metals


# =====================================================
# SAVE JSON
# =====================================================

def save_json(data):

    with open(
        LATEST_FILE,
        "w",
        encoding="utf-8"
    ) as f:

        json.dump(
            data,
            f,
            indent=4
        )


# =====================================================
# SAVE HISTORY
# =====================================================

def save_history(data):

    exists = os.path.exists(
        HISTORY_FILE
    )

    with open(
        HISTORY_FILE,
        "a",
        newline="",
        encoding="utf-8"
    ) as f:

        writer = csv.writer(f)

        if not exists:

            writer.writerow([
                "timestamp",
                "metal",
                "price_original",
                "unit",
                "price_inr_per_g",
                "price_inr_per_10g",
                "daily_change",
                "daily_percent",
                "weekly_percent",
                "monthly_percent",
                "ytd_percent",
                "yoy_percent",
                "volatility",
                "trend",
                "usd_to_inr",
                "cny_to_inr"
            ])

        for metal, info in data.items():

            writer.writerow([
                info["timestamp"],
                metal,
                info["price_original"],
                info["unit"],
                info["price_inr_per_g"],
                info["price_inr_per_10g"],
                info["daily_change"],
                info["daily_percent"],
                info["weekly_percent"],
                info["monthly_percent"],
                info["ytd_percent"],
                info["yoy_percent"],
                info["volatility"],
                info["trend"],
                info["usd_to_inr"],
                info["cny_to_inr"]
            ])


# =====================================================
# DISPLAY
# =====================================================

def display(data):

    print("\n")
    print("=" * 80)
    print("LIVE METAL MARKET")
    print("=" * 80)

    ranking = sorted(
        data.items(),
        key=lambda x:
        abs(
            x[1]["daily_percent"]
        ),
        reverse=True
    )

    for metal, info in ranking:

        arrow = (
            "▲"
            if info["daily_percent"] > 0
            else
            "▼"
            if info["daily_percent"] < 0
            else
            "-"
        )

        print(
            f"\n{metal}"
        )

        print(
            f"Original      : "
            f"{info['price_original']} "
            f"{info['unit']}"
        )

        print(
            f"INR / gram    : "
            f"₹{info['price_inr_per_g']}"
        )

        print(
            f"INR / 10 gram : "
            f"₹{info['price_inr_per_10g']}"
        )

        print(
            f"Trend         : "
            f"{arrow} "
            f"{info['daily_percent']}%"
        )

        print(
            f"Volatility    : "
            f"{info['volatility']}"
        )


# =====================================================
# MAIN
# =====================================================

def main():

    data = fetch_prices()

    display(data)

    save_json(data)

    save_history(data)

    print("\nSaved:")
    print(" economic_latest.json")
    print(" economic_history.csv")


if __name__ == "__main__":
    main()