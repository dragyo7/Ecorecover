# EcoRecover AI Documentation

Welcome to the EcoRecover project.

This directory contains the complete AI engineering documentation used by Antigravity, Claude Code, Cursor, GitHub Copilot and other AI coding agents.

The purpose of these documents is to ensure every AI agent understands the project before generating code.

These files are the single source of truth for product vision, architecture, UI philosophy and engineering standards.

------------------------------------------------------------------------------

# Project Summary

EcoRecover is an Android application for intelligent e-waste recycling.

The application combines

• Blinkit-inspired speed
• Instamart-inspired discovery
• Uber-inspired pickup tracking
• Live metal valuation
• AI-assisted recommendations

Users do NOT buy products.

Users SELL old electronics.

The application generates transparent live quotes using recoverable metal composition and current market prices.

------------------------------------------------------------------------------

# Existing Project

The project already exists.

Do NOT rewrite it.

Do NOT regenerate the architecture.

Do NOT replace existing modules.

Always inspect the current codebase before making changes.

The goal is to evolve the project into a production-quality application.

------------------------------------------------------------------------------

# Technology

Android

Jetpack Compose

Material3

MVVM

Repository Pattern

Navigation Compose

Retrofit

Coroutines

StateFlow

Room

Backend

FastAPI

Python

SQLAlchemy

PostgreSQL

Supabase

JWT

------------------------------------------------------------------------------

# Existing Backend Engines

These engines already exist.

economic_fetcher.py

Retrieves live commodity prices.

----------------------------------

ewaste_lookup.py

Provides recoverable metal composition.

----------------------------------

cost_fetcher.py

Calculates live product valuation.

----------------------------------

metal_market_engine.py

Maintains market history and trends.

These engines are permanent.

Never duplicate them.

Always extend them.

------------------------------------------------------------------------------

# Documentation Reading Order

Read documents in this exact order.

1.

00_PRODUCT_CONSTITUTION.md

↓

2.

01_PROJECT.md

↓

3.

02_MEMORY.md

↓

4.

03_ARCHITECTURE.md

↓

5.

04_UI_SYSTEM.md

↓

6.

05_USER_FLOWS.md

↓

7.

06_FEATURES.md

↓

8.

07_ROADMAP.md

↓

9.

08_AGENT_RULES.md

↓

10.

09_AI_WORKFLOWS.md

Never skip this order.

------------------------------------------------------------------------------

# AI Workflow

Before implementing any feature

Inspect existing code.

↓

Understand architecture.

↓

Read documentation.

↓

Produce implementation plan.

↓

Implement.

↓

Test.

↓

Update documentation.

Never generate code blindly.

------------------------------------------------------------------------------

# Documentation Update Rules

If architecture changes

Update

03_ARCHITECTURE.md

----------------------------------

If product direction changes

Update

01_PROJECT.md

----------------------------------

If permanent knowledge changes

Update

02_MEMORY.md

----------------------------------

If UI changes

Update

04_UI_SYSTEM.md

----------------------------------

If feature behaviour changes

Update

06_FEATURES.md

----------------------------------

If roadmap changes

Update

07_ROADMAP.md

------------------------------------------------------------------------------

# Project Goals

The application should

Provide instant quotes.

Provide transparent pricing.

Support on-demand pickups.

Provide Uber-style tracking.

Provide market insights.

Reward sustainable behaviour.

Scale to production.

------------------------------------------------------------------------------

# Important Rules

Never duplicate backend logic.

Never calculate prices inside Android.

Never replace existing engines.

Never replace MVVM.

Never replace Material3.

Never replace Compose.

Always reuse repositories.

Always reuse ViewModels.

Always extend.

Never rebuild.

------------------------------------------------------------------------------

# Final Principle

Build EcoRecover as if it will eventually serve millions of users.

Every change should improve

Maintainability

Performance

Scalability

Reliability

User Experience

Consistency

Documentation