from app.engines.economic_fetcher import fetch_prices


class PriceService:

    @staticmethod
    def get_live_prices():
        return fetch_prices()