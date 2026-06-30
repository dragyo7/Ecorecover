from playwright.sync_api import sync_playwright
from datetime import datetime
import re
import csv
import json
import os

METALS = {
    "Gold": "https://metalview.in/gold",
    "Silver": "https://metalview.in/silver",
    "Copper": "https://metalview.in/copper",
    "Platinum": "https://metalview.in/platinum",
    "Palladium": "https://metalview.in/palladium"
}

CSV_FILE = "metal_price_history.csv"
JSON_FILE = "latest_prices.json"


def money_to_float(value):
    return float(value.replace("₹", "").replace(",", ""))


def extract_price_info(content, metal):
    matches = re.findall(r'₹[\d,]+(?:\.\d+)?', content)

    if not matches:
        return None

    prices = [money_to_float(x) for x in matches]

    # Deduplicate while preserving order
    unique_prices = []
    for p in prices:
        if p not in unique_prices:
            unique_prices.append(p)

    # MetalView consistently exposes:
    # Gold: gram first
    # Silver: kg first then gram
    # Platinum/Palladium: 10g first then gram

    if metal == "Gold":
        price_per_g = unique_prices[0]
        source_unit = "g"

    elif metal == "Silver":
        if len(unique_prices) >= 2:
            price_per_g = unique_prices[1]
        else:
            price_per_g = unique_prices[0] / 1000
        source_unit = "kg"

    elif metal == "Copper":
        price_per_g = unique_prices[0] / 1000
        source_unit = "kg"

    elif metal in ["Platinum", "Palladium"]:
        if len(unique_prices) >= 2:
            price_per_g = unique_prices[1]
        else:
            price_per_g = unique_prices[0] / 10
        source_unit = "10g"

    else:
        price_per_g = unique_prices[0]
        source_unit = "unknown"

    return {
        "metal": metal,
        "price_per_g": round(price_per_g, 4),
        "price_10g": round(price_per_g * 10, 2),
        "price_100g": round(price_per_g * 100, 2),
        "price_1kg": round(price_per_g * 1000, 2),
        "source_unit": source_unit,
        "scraped_at": datetime.now().isoformat()
    }


def save_history(records):
    file_exists = os.path.exists(CSV_FILE)

    with open(CSV_FILE, "a", newline="", encoding="utf-8") as f:

        writer = csv.DictWriter(
            f,
            fieldnames=[
                "scraped_at",
                "metal",
                "price_per_g",
                "price_10g",
                "price_100g",
                "price_1kg",
                "source_unit"
            ]
        )

        if not file_exists:
            writer.writeheader()

        for record in records.values():
            writer.writerow(record)


def save_latest(records):
    with open(JSON_FILE, "w", encoding="utf-8") as f:
        json.dump(records, f, indent=4)


def scrape_all():

    results = {}

    with sync_playwright() as p:

        browser = p.chromium.launch(headless=True)

        page = browser.new_page()

        for metal, url in METALS.items():

            print(f"Fetching {metal}...")

            try:
                page.goto(url, wait_until="networkidle")

                content = page.content()

                data = extract_price_info(content, metal)

                if data:
                    results[metal] = data

            except Exception as e:
                print(f"{metal}: {e}")

        browser.close()

    return results


if __name__ == "__main__":

    prices = scrape_all()

    save_history(prices)
    save_latest(prices)

    print("\n=== CURRENT PRICES ===\n")

    for metal, data in prices.items():

        print(
            f"{metal:<10}"
            f" ₹{data['price_per_g']}/g"
            f" | ₹{data['price_10g']}/10g"
            f" | ₹{data['price_1kg']}/kg"
        )

    print("\nSaved:")
    print(f"  {CSV_FILE}")
    print(f"  {JSON_FILE}")