# EcoRecover Project Memory

Version: 3.0

Status: PERSISTENT

------------------------------------------------------------------------------

# Purpose

This document stores stable project knowledge.

It should only contain information that remains true over time.

Do NOT store temporary implementation details.

Do NOT store TODOs.

Do NOT store sprint information.

------------------------------------------------------------------------------

# Project

Name

EcoRecover

Platform

Android

Backend

FastAPI

Purpose

AI-powered e-waste recycling platform with real-time valuation and on-demand pickup.

------------------------------------------------------------------------------

# Current Project Status

Project Phase

MVP → Production Evolution

Backend

✔ FastAPI foundation

✔ Authentication endpoints

✔ Appointment endpoints

✔ Search endpoint

✔ Estimate endpoint

✔ Market endpoint

✔ Existing valuation engines

Android

✔ Jetpack Compose

✔ Material3

✔ MVVM

✔ Navigation

✔ Home

✔ Market

✔ History

✔ Profile

Current Goal

Transform the MVP into a Blinkit/Uber inspired production-quality application.

------------------------------------------------------------------------------

# Existing Backend Engines

These files are permanent assets.

Never replace them.

Always extend them.

------------------------------------------------------------------------------

economic_fetcher.py

Responsibilities

• Live metal prices

• Market updates

• Currency conversion

• Historical snapshots

------------------------------------------------------------------------------

ewaste_lookup.py

Responsibilities

• Product lookup

• Synthetic e-waste dataset

• Recoverable metals

• Search support

------------------------------------------------------------------------------

cost_fetcher.py

Responsibilities

• Product valuation

• Recovery calculations

• Business rules

• Final quote

------------------------------------------------------------------------------

metal_market_engine.py

Responsibilities

• Market summaries

• Historical trends

• Daily reports

• Market insights

------------------------------------------------------------------------------

These engines together form the Valuation Engine.

Future engines must orchestrate them.

Never duplicate calculations.

------------------------------------------------------------------------------

# Existing Backend APIs

Health

Authentication

Appointments

Estimate

Search

Market Prices

History

The backend already exposes API foundations.

Extend existing APIs before creating new ones.

------------------------------------------------------------------------------

# Existing Android Foundation

Navigation Compose

Material3

MVVM

Repository Pattern

Retrofit

StateFlow

Coroutines

Compose UI

Dark Theme

Always reuse these foundations.

------------------------------------------------------------------------------

# Existing Navigation

Home

Market

History

Profile

Future Navigation

Home

Orders

Insights

Profile

Tracking should remain a secondary screen.

------------------------------------------------------------------------------

# Stable Product Terminology

Always use

Instant Quote

Pickup Order

Eco Partner

Insights

Wallet

Green Coins

EcoHub

Smart Detection

Avoid inconsistent naming.

------------------------------------------------------------------------------

# Stable Design Philosophy

Modern

Minimal

Fast

Friendly

Premium

Consumer-first

Never build dashboard-style interfaces.

------------------------------------------------------------------------------

# Existing Assets

Synthetic product dataset

Metal recovery dataset

Live market pricing

Cost valuation engine

Compose components

Material theme

FastAPI backend

Supabase authentication

Never discard existing work.

------------------------------------------------------------------------------

# Backend Ownership

Backend owns

Business logic

Pricing

Recovery

Market

Tracking

Rewards

Recommendations

Android owns

Presentation

Navigation

Animations

User interaction

State rendering

------------------------------------------------------------------------------

# Existing Constraints

Pricing is always calculated in backend.

Android never calculates values.

Metal prices originate from economic_fetcher.py.

Device composition originates from ewaste_lookup.py.

Final valuation originates from cost_fetcher.py.

Market summaries originate from metal_market_engine.py.

------------------------------------------------------------------------------

# Future Engines

Market Intelligence Engine

Tracking Engine

Recommendation Engine

Rewards Engine

Notification Engine

Wallet Engine

These should integrate with existing engines.

Never replace existing functionality.

------------------------------------------------------------------------------

# Future Product Evolution

Blinkit-style Home

Uber-style Tracking

Wallet

Rewards

Market Intelligence

AI Smart Detection

Bulk Pickup

Corporate Recycling

Multiple EcoHubs

Multiple Cities

------------------------------------------------------------------------------

# Non-Negotiable Facts

The backend already exists.

The Android project already exists.

Compose is the UI framework.

Material3 is the design language.

FastAPI is the backend.

Existing engines are reusable.

Business logic belongs only in backend.

Architecture evolves.

It is never rewritten.

------------------------------------------------------------------------------

# Definition of Success

A new AI agent should understand

What EcoRecover is.

Which backend engines already exist.

Which Android architecture already exists.

Which APIs already exist.

What should never change.

Within five minutes of reading this document.