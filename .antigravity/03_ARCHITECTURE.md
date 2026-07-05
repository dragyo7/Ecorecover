# EcoRecover Architecture

Version: 3.0

Status: TARGET ARCHITECTURE

------------------------------------------------------------------------------

# Purpose

Defines the architecture of EcoRecover.

This document describes how the existing project should evolve.

It is NOT a description of the current implementation.

Architecture should evolve without rewriting existing code.

------------------------------------------------------------------------------

# High-Level Architecture

Android Application

↓

REST API

↓

FastAPI Backend

↓

Business Services

↓

Valuation Engines

↓

Database

↓

Scheduler

↓

Analytics

Each layer has one responsibility.

Never bypass layers.

------------------------------------------------------------------------------

# Android Architecture

Presentation Layer

Responsibilities

• Compose UI

• Navigation

• Rendering

• User interaction

No business logic.

------------------------------------------------

ViewModel Layer

Responsibilities

• UI state

• Validation

• API calls through repositories

• Loading

• Errors

Never access Retrofit directly.

------------------------------------------------

Repository Layer

Responsibilities

• Single source of truth

• Cache

• Remote

• Local

• Synchronization

Repositories communicate with APIs.

------------------------------------------------------------------------------

# Android Folder Structure

presentation/

components/

navigation/

screens/

home/

search/

device/

cart/

checkout/

orders/

tracking/

insights/

profile/

authentication/

viewmodel/

theme/

data/

remote/

local/

repository/

domain/

usecase/

core/

network/

workers/

utils/

The existing structure should evolve naturally.

Avoid unnecessary restructuring.

------------------------------------------------------------------------------

# Backend Architecture

FastAPI

↓

API Routers

↓

Services

↓

Existing Engines

↓

Repositories

↓

Database

Business logic belongs only inside

Services

Existing Engines

Never inside API routers.

------------------------------------------------------------------------------

# Existing Engines

economic_fetcher.py

↓

Live Market Prices

Currency Conversion

Historical Market Data

------------------------------------------------

ewaste_lookup.py

↓

Dataset Lookup

Device Search

Metal Composition

------------------------------------------------

cost_fetcher.py

↓

Dynamic Valuation

Recovery %

Business Rules

Final Quote

------------------------------------------------

metal_market_engine.py

↓

Market Reports

Snapshots

Trend Analysis

These four files are permanent.

Never replace them.

------------------------------------------------------------------------------

# Future Engines

Market Intelligence Engine

↓

Ranks products

↓

Generates recommendations

↓

Updates Insights

------------------------------------------------

Tracking Engine

↓

Pickup lifecycle

↓

ETA

↓

Status

------------------------------------------------

Rewards Engine

↓

Green Coins

↓

Achievements

↓

Wallet

------------------------------------------------

Recommendation Engine

↓

Best Time To Sell

↓

Trending Devices

↓

Market Suggestions

Future engines extend existing engines.

------------------------------------------------------------------------------

# Data Flow

User

↓

Search

↓

Backend

↓

ewaste_lookup.py

↓

economic_fetcher.py

↓

cost_fetcher.py

↓

API Response

↓

Repository

↓

ViewModel

↓

Compose UI

Android never performs calculations.

------------------------------------------------------------------------------

# Primary APIs

Authentication

Search

Instant Quote

Orders

Tracking

Insights

Profile

Rewards

Wallet

Notifications

Version every endpoint.

------------------------------------------------------------------------------

# Navigation

Home

↓

Search

↓

Device Details

↓

Pickup Cart

↓

Checkout

↓

Orders

↓

Tracking

↓

Payment

↓

Profile

Keep navigation shallow.

------------------------------------------------------------------------------

# State Management

Idle

↓

Loading

↓

Success

↓

Empty

↓

Error

Every screen must support all five states.

------------------------------------------------------------------------------

# Caching

Android

Room

↓

Search History

↓

Profile

↓

Orders

↓

Insights

Backend

↓

Market Cache

↓

Valuation Cache

↓

Recommendation Cache

------------------------------------------------------------------------------

# Background Tasks

Every 47 Minutes

↓

Market Refresh

↓

Recalculate Device Values

↓

Update Insights

↓

Store Snapshot

↓

Send Notifications

Android should never perform scheduled market updates.

------------------------------------------------------------------------------

# Error Strategy

Backend

Catch

↓

Log

↓

Map

↓

Return Friendly Response

Android

Receive

↓

Repository

↓

ViewModel

↓

UI State

Never expose internal exceptions.

------------------------------------------------------------------------------

# Security

HTTPS

JWT

Secure Storage

Input Validation

Authorization

Least Privilege

No secrets inside source code.

------------------------------------------------------------------------------

# Scalability

Current

Single City

↓

Future

Multiple Cities

↓

Multiple EcoHubs

↓

Corporate Recycling

↓

Government Programs

↓

Production Infrastructure

Architecture should scale without redesign.

------------------------------------------------------------------------------

# Engineering Principles

One source of truth.

One navigation graph.

One valuation engine.

One repository per domain.

One ViewModel per screen.

One responsibility per class.

------------------------------------------------------------------------------

# Definition of Done

Every architectural change must

Respect MVVM.

Reuse repositories.

Reuse existing engines.

Avoid duplication.

Support testing.

Support scalability.

Update documentation.