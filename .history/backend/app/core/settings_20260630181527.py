from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    APP_NAME: str = "EcoRecover API"
    VERSION: str = "1.0.0"
    DEBUG: bool = True

    SUPABASE_URL: str
    SUPABASE_ANON_KEY: str

    model_config = SettingsConfigDict(
        env_file=".env",
        extra="ignore"
    )


settings = Settings()