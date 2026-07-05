# EcoRecover Product Constitution
Version: 2.0

Status: ACTIVE

This document is the highest authority for the EcoRecover project.

Every AI coding agent, contributor, or developer MUST read this document before making any architectural, UI, backend, or feature decision.

If another markdown file conflicts with this document, THIS DOCUMENT ALWAYS WINS.

------------------------------------------------------------------------------

# Product Identity

EcoRecover is NOT an e-commerce application.

EcoRecover is NOT a metal price tracker.

EcoRecover is NOT a marketplace.

EcoRecover is an on-demand e-waste pickup platform inspired by the simplicity of Blinkit & Instamart and the live service experience of Uber.

Users do NOT buy products.

Users SELL electronic waste.

The application makes selling old electronics as simple as ordering groceries.

------------------------------------------------------------------------------

# Mission

Reduce friction in e-waste recycling.

Provide instant transparent valuation.

Allow users to schedule pickups within minutes.

Educate users about recoverable materials.

Build trust through complete pricing transparency.

------------------------------------------------------------------------------

# Vision

Become the easiest way to recycle electronics.

Every household should be able to sell unwanted electronics within minutes.

The experience should feel familiar to anyone who has used Blinkit, Instamart or Uber.

------------------------------------------------------------------------------

# Product Pillars

Every feature MUST strengthen at least ONE of these pillars.

1.

Instant Selling

Search.

Quote.

Pickup.

Done.

2.

Transparent Valuation

Every quote must be explainable.

Never hide calculations.

Users should understand WHY a product is worth a certain amount.

3.

Reliable Pickup

Scheduling.

ETA.

Tracking.

Order Management.

Notifications.

4.

Market Intelligence

Real-time metal prices.

Historical pricing.

Product rankings.

Insights.

Recommendations.

5.

Sustainability

Carbon savings.

Environmental impact.

Green rewards.

User achievements.

------------------------------------------------------------------------------

# User Experience Philosophy

The UI should feel like:

Blinkit

+

Instamart

+

Uber

NOT

Government Portal

NOT

Dashboard Software

NOT

Admin Panel

NOT

Engineering Tool

------------------------------------------------------------------------------

# UI Principles

Fast.

Minimal.

Modern.

Friendly.

Rounded.

Large touch targets.

High contrast.

Low cognitive load.

One primary action per screen.

No clutter.

Every important action should be reachable within two taps.

------------------------------------------------------------------------------

# Design Inspiration

Blinkit

• Home layout
• Search experience
• Category cards
• Product cards
• Checkout flow

Instamart

• Discovery
• Offers
• Categories
• Home organization

Uber

• Live tracking
• ETA
• Progress timeline
• Driver status
• Order lifecycle

Material 3

• Components
• Typography
• Accessibility
• Motion
• Adaptive layouts

Never copy branding.

Never clone UI.

Only adopt interaction principles.

------------------------------------------------------------------------------

# Core User Journey

Launch App

↓

Search Device

↓

Select Device

↓

View Instant Quote

↓

Review Metal Breakdown

↓

Add To Pickup

↓

Choose Pickup Slot

↓

Confirm Pickup

↓

Driver Assigned

↓

Live Tracking

↓

Pickup Completed

↓

Inspection

↓

Payment Released

↓

Rewards Updated

Every feature must support this journey.

------------------------------------------------------------------------------

# Product Language

Never use technical language when user-friendly alternatives exist.

Use

Pickup Order

Instead of

Appointment

----------------------------------

Use

Instant Quote

Instead of

Estimate

----------------------------------

Use

Orders

Instead of

History

----------------------------------

Use

EcoHub

Instead of

Recycler

----------------------------------

Use

Insights

Instead of

Market

----------------------------------

Use

Smart Detection

Instead of

Scan

------------------------------------------------------------------------------

# Backend Philosophy

Business logic belongs ONLY inside the backend.

Android must NEVER calculate:

Product value

Metal prices

Recovery %

Business rules

Price predictions

Rankings

Recommendations

Android displays backend results.

Backend owns business logic.

------------------------------------------------------------------------------

# Existing Backend Engines

These engines already exist.

They are core intellectual property.

Never replace them.

Always extend them.

------------------------------------------------

economic_fetcher.py

Responsibilities

Fetch live market prices.

Currency conversion.

Market changes.

Historical values.

------------------------------------------------

ewaste_lookup.py

Responsibilities

Search products.

Retrieve recoverable metal content.

Provide product dataset.

------------------------------------------------

cost_fetcher.py

Responsibilities

Calculate live product valuation.

Apply recovery rates.

Business rules.

Metal contribution.

Final quote.

------------------------------------------------

metal_market_engine.py

Responsibilities

Market history.

Trend generation.

Snapshot storage.

Price reporting.

------------------------------------------------

Future engines should orchestrate these.

Never duplicate their work.

------------------------------------------------------------------------------

# Future Engines

Market Intelligence Engine

Automatically rank every product using current market data.

------------------------------------------------

Recommendation Engine

Suggest best products to sell.

Generate "Best Time To Sell".

------------------------------------------------

Tracking Engine

Manage live order lifecycle.

ETA.

Status.

Notifications.

------------------------------------------------

Rewards Engine

Green points.

Badges.

Carbon savings.

------------------------------------------------------------------------------

# Android Philosophy

Existing architecture must be evolved.

Never rewritten.

Use

Jetpack Compose

Material 3

MVVM

Repository Pattern

Retrofit

Navigation Compose

Coroutines

StateFlow

ViewModels

Do not migrate to another architecture.

------------------------------------------------------------------------------

# Screen Philosophy

Each screen must have ONE primary purpose.

Home

Discover.

Search.

Sell.

----------------------------

Product

Understand value.

----------------------------

Cart

Review pickup.

----------------------------

Checkout

Schedule.

----------------------------

Orders

Track pickup.

----------------------------

Insights

Market intelligence.

----------------------------

Profile

Account.

Rewards.

Settings.

------------------------------------------------------------------------------

# Navigation Philosophy

Keep navigation shallow.

Avoid deep navigation trees.

Maximum:

Home

↓

Product

↓

Cart

↓

Checkout

↓

Tracking

Never exceed this complexity.

------------------------------------------------------------------------------

# Performance

Always prefer:

Caching

Pagination

Lazy loading

Background refresh

Skeleton loading

Optimistic UI

Avoid duplicate API calls.

------------------------------------------------------------------------------

# AI Philosophy

AI should assist.

AI should NEVER replace deterministic calculations.

Allowed

Classification

Recommendations

Search

OCR

Condition detection

Future predictions

Not Allowed

Price calculation

Business rules

Market calculations

Recovery calculations

------------------------------------------------------------------------------

# Security

Never expose secrets.

Never expose API keys.

Use HTTPS.

Validate everything.

Authenticate requests.

Least privilege.

------------------------------------------------------------------------------

# Engineering Rules

Prefer extension over replacement.

Prefer composition over duplication.

Reuse existing components.

Reuse existing repositories.

Reuse existing services.

Reuse existing models.

Never duplicate business logic.

------------------------------------------------------------------------------

# Feature Priority

Tier 1

Search

Instant Quote

Pickup

Orders

Tracking

Insights

Tier 2

Rewards

Wallet

Notifications

EcoHub

Tier 3

Market Intelligence

Recommendations

Leaderboards

Analytics

------------------------------------------------------------------------------

# Success Criteria

A first-time user should understand the app within 30 seconds.

Searching a device should require less than three interactions.

Booking a pickup should require less than one minute.

Every quote must be explainable.

Every order must be trackable.

Every screen must provide value.

------------------------------------------------------------------------------

# Non-Negotiable Rules

Never redesign the project from scratch.

Never ignore existing backend engines.

Never duplicate backend calculations.

Never calculate prices on Android.

Never replace MVVM.

Never abandon Material 3.

Never create inconsistent UI.

Never introduce multiple sources of truth.

------------------------------------------------------------------------------

# Final Principle

EcoRecover is not a college project.

Build every feature as if it will eventually serve real users.