# EcoRecover Product Constitution

Version: 3.0

Status: ACTIVE

Priority: HIGHEST

------------------------------------------------------------------------------

# Authority

This document is the highest authority of the EcoRecover project.

Every AI agent must read this document before generating code.

If another markdown document conflicts with this one,

THIS DOCUMENT ALWAYS WINS.

------------------------------------------------------------------------------

# Product Identity

EcoRecover is an AI-powered on-demand e-waste recycling platform.

It combines

• Blinkit's simplicity

• Instamart's discovery

• Uber's live pickup experience

• Live commodity valuation

• Sustainable recycling

EcoRecover is NOT

❌ An e-commerce application

❌ A marketplace

❌ A dashboard

❌ A CRUD demo

❌ A college assignment

Build it like a production consumer application.

------------------------------------------------------------------------------

# Mission

Make selling electronic waste

Fast

Transparent

Trustworthy

Convenient

Educational

Sustainable

Users should recycle electronics with the same ease as ordering groceries.

------------------------------------------------------------------------------

# Product Vision

A user should be able to

Open the app

↓

Search a device

↓

Receive a transparent live valuation

↓

Book pickup

↓

Track Eco Partner

↓

Receive payment

↓

Earn rewards

↓

View environmental impact

Within a few minutes.

------------------------------------------------------------------------------

# Core Product Pillars

Every feature must improve at least one pillar.

1.

Instant Selling

2.

Transparent Valuation

3.

Reliable Pickup

4.

Market Intelligence

5.

Environmental Impact

If a feature improves none,

it should not exist.

------------------------------------------------------------------------------

# Product Philosophy

Technology exists to simplify recycling.

Never expose unnecessary technical information.

Never overwhelm users.

Every screen must answer

"What should I do next?"

------------------------------------------------------------------------------

# User Experience Philosophy

The application should feel

Modern

Fast

Minimal

Reliable

Premium

Human

The interface should never feel like

Government software

Enterprise software

Engineering software

------------------------------------------------------------------------------

# Inspiration

Study

Blinkit

Instamart

Uber

Google Maps

Material Design 3

Reuse

Interaction ideas

Navigation

Motion

User psychology

Never copy

Branding

Colors

Layouts

Assets

------------------------------------------------------------------------------

# Core User Journey

Launch

↓

Authentication

↓

Home

↓

Search

↓

Device

↓

Instant Quote

↓

Pickup Cart

↓

Checkout

↓

Order Created

↓

Eco Partner Assigned

↓

Tracking

↓

Inspection

↓

Payment

↓

Rewards

↓

Completed

Every feature should strengthen this journey.

------------------------------------------------------------------------------

# Existing Project Rule

This project already exists.

Do NOT rebuild it.

Do NOT regenerate it.

Do NOT restart architecture.

Do NOT replace existing modules.

Always evolve the project.

------------------------------------------------------------------------------

# Existing Backend Engines

The following files are core project assets.

economic_fetcher.py

Live metal prices

Market history

Currency conversion

----------------------------------

ewaste_lookup.py

Synthetic product dataset

Metal composition

Product search

----------------------------------

cost_fetcher.py

Business valuation

Recovery calculations

Final quote

----------------------------------

metal_market_engine.py

Market summaries

Snapshots

Historical insights

----------------------------------

These engines must remain independent.

Never duplicate their responsibilities.

Future engines must orchestrate them.

------------------------------------------------------------------------------

# Backend Philosophy

Backend owns

Pricing

Recovery

Business Rules

Market Logic

Tracking Logic

Recommendations

Rewards

Android owns

Presentation

Animations

Navigation

Rendering

Nothing else.

------------------------------------------------------------------------------

# Android Philosophy

Compose only.

Material3 only.

MVVM only.

Repository Pattern only.

One ViewModel per screen.

Never calculate business logic inside Android.

------------------------------------------------------------------------------

# Product Language

Always use

Instant Quote

Pickup Order

Eco Partner

Insights

Wallet

Green Coins

Eco Score

Never introduce inconsistent terminology.

------------------------------------------------------------------------------

# Design Laws

Large touch targets.

Minimal text.

One primary CTA.

Rounded UI.

Smooth animations.

Search-first.

Fast loading.

Skeleton states.

Accessible.

Responsive.

------------------------------------------------------------------------------

# Engineering Laws

Reuse first.

Extend second.

Create third.

Never duplicate.

Never hardcode.

Never bypass architecture.

Never bypass repositories.

Never bypass backend.

------------------------------------------------------------------------------

# Security

HTTPS

JWT

Validation

Least Privilege

Secure Storage

No API keys in repository.

------------------------------------------------------------------------------

# Performance

Lazy loading.

Pagination.

Caching.

Background refresh.

StateFlow.

Avoid unnecessary recomposition.

------------------------------------------------------------------------------

# Accessibility

Material3 accessibility.

Readable typography.

Dynamic font sizes.

Large touch targets.

High contrast.

Screen reader support.

------------------------------------------------------------------------------

# Scalability

Architecture must support

Multiple cities

Multiple Eco Partners

Corporate recycling

Government integration

Bulk pickup

Smart routing

AI recommendations

Without major redesign.

------------------------------------------------------------------------------

# Non Goals

Do NOT build

Marketplace

Social Media

Chat Application

Electronics Store

Repair Service

Refurbished Store

Gaming Features

Unrelated AI features

------------------------------------------------------------------------------

# Definition of Quality

Every implementation should

Reuse architecture.

Reuse engines.

Reuse repositories.

Support dark mode.

Handle loading.

Handle errors.

Handle empty states.

Be documented.

Be testable.

Improve maintainability.

------------------------------------------------------------------------------

# Final Principle

Every line of code should move EcoRecover closer to a real production application that users trust with valuable electronic devices.

Think like a product engineer.

Not a code generator.