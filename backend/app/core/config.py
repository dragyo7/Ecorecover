from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    APP_NAME: str = "EcoRecover API"
    VERSION: str = "1.0.0"

    SUPABASE_URL: str = ""
    SUPABASE_KEY: str = ""

    class Config:
        env_file = ".env"


settings = Settings()