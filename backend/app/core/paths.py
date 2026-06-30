from pathlib import Path

# app/core/paths.py

APP_DIR = Path(__file__).resolve().parent.parent

BASE_DIR = APP_DIR.parent

DATA_DIR = APP_DIR / "data"

ENGINE_DIR = APP_DIR / "engines"

DATASET_PATH = DATA_DIR / "ewaste_dataset.csv"

LATEST_PRICE_FILE = DATA_DIR / "economic_latest.json"

ECONOMIC_HISTORY_FILE = DATA_DIR / "economic_history.csv"

PRICE_HISTORY_FILE = DATA_DIR / "price_history.json"

METAL_PRICE_HISTORY_FILE = DATA_DIR / "metal_price_history.csv"