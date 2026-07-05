# EcoRecover AI Agent Rules

Version: 3.0

Status: MANDATORY

Priority: HIGH

------------------------------------------------------------------------------

# Purpose

This document defines the mandatory rules every AI coding agent must follow.

These rules apply to

• Antigravity

• Claude Code

• Cursor

• GitHub Copilot

• OpenAI Codex

• Future AI coding agents

These rules exist to keep EcoRecover maintainable, scalable and consistent.

------------------------------------------------------------------------------

# Primary Objective

Do not generate code.

Engineer the product.

Every implementation should improve

Maintainability

Scalability

Reliability

Performance

User Experience

Never optimize for speed over quality.

------------------------------------------------------------------------------

# Before Writing Any Code

Always perform these steps.

Step 1

Read documentation in order

README.md

↓

00_PRODUCT_CONSTITUTION.md

↓

01_PROJECT.md

↓

02_MEMORY.md

↓

03_ARCHITECTURE.md

↓

04_UI_SYSTEM.md

↓

05_USER_FLOWS.md

↓

06_FEATURES.md

↓

07_ROADMAP.md

↓

08_AGENT_RULES.md

Never skip documentation.

------------------------------------------------------------------------------

Step 2

Inspect existing project.

Understand

Android structure

Backend structure

Repositories

ViewModels

Navigation

Existing APIs

Existing Engines

Never assume.

------------------------------------------------------------------------------

Step 3

Reuse.

Always search for existing

Composable

Repository

ViewModel

Model

API

Service

Utility

Extension

Worker

before creating new files.

------------------------------------------------------------------------------

# Existing Project Rule

EcoRecover already exists.

Never rebuild it.

Never regenerate architecture.

Never replace working modules.

Never create duplicate implementations.

Prefer extension over replacement.

------------------------------------------------------------------------------

# Backend Rule

The backend owns

Business Logic

Pricing

Recovery

Recommendations

Tracking

Market Intelligence

Rewards

Wallet

Android owns

Presentation

Navigation

Animations

Rendering

Never calculate business logic inside Android.

------------------------------------------------------------------------------

# Existing Engine Rule

These files are permanent.

economic_fetcher.py

ewaste_lookup.py

cost_fetcher.py

metal_market_engine.py

Never modify their responsibilities.

Never duplicate their logic.

Future engines must orchestrate them.

------------------------------------------------------------------------------

# Architecture Rule

Respect

MVVM

Repository Pattern

Navigation Compose

Material3

Compose

Coroutines

StateFlow

Never introduce another architecture.

------------------------------------------------------------------------------

# UI Rule

One screen

↓

One responsibility

One ViewModel

↓

One Repository

↓

One API Source

Never overload screens.

------------------------------------------------------------------------------

# Compose Rules

Compose only.

Material3 only.

Prefer stateless composables.

State belongs in ViewModels.

Never place networking inside composables.

Never place business logic inside composables.

------------------------------------------------------------------------------

# ViewModel Rules

One ViewModel per screen.

Expose immutable StateFlow.

Repositories only.

Never call Retrofit directly.

Never calculate business logic.

------------------------------------------------------------------------------

# Repository Rules

Repositories are the single source of truth.

Repositories decide

Network

Cache

Fallback

Synchronization

Never bypass repositories.

------------------------------------------------------------------------------

# API Rules

Never hardcode URLs.

Never hardcode responses.

Always version endpoints.

Always validate inputs.

Always return friendly errors.

Support

Loading

Success

Empty

Error

Timeout

Offline

------------------------------------------------------------------------------

# Refactoring Rules

Refactor only if

Code duplication exists.

Performance improves.

Architecture improves.

Maintainability improves.

Readability improves.

Never refactor because of personal preference.

------------------------------------------------------------------------------

# Performance Rules

Prefer

LazyColumn

remember

derivedStateOf

Stable State

Background Work

Caching

Pagination

Avoid unnecessary recomposition.

------------------------------------------------------------------------------

# Error Handling

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

↓

Friendly Message

Never expose stack traces.

------------------------------------------------------------------------------

# Accessibility Rules

Support

TalkBack

Dynamic Fonts

Dark Mode

Large Touch Targets

High Contrast

Material3 accessibility guidelines.

------------------------------------------------------------------------------

# Documentation Rules

Architecture changed?

↓

Update

03_ARCHITECTURE.md

----------------------------------

Product changed?

↓

Update

01_PROJECT.md

----------------------------------

Stable knowledge changed?

↓

Update

02_MEMORY.md

----------------------------------

UI changed?

↓

Update

04_UI_SYSTEM.md

----------------------------------

Features changed?

↓

Update

06_FEATURES.md

----------------------------------

Roadmap changed?

↓

Update

07_ROADMAP.md

Documentation is part of the codebase.

------------------------------------------------------------------------------

# Code Quality Rules

Every implementation should be

Readable

Reusable

Maintainable

Predictable

Scalable

Consistent

Never optimize for fewer lines.

Optimize for clarity.

------------------------------------------------------------------------------

# Feature Evaluation

Before implementing a feature ask

Does it improve

Instant Selling

Transparent Valuation

Reliable Pickup

Market Intelligence

Sustainability

If the answer is no,

do not implement it.

------------------------------------------------------------------------------

# Security Rules

Use HTTPS.

Use JWT.

Validate all inputs.

Store secrets securely.

Never commit secrets.

Never expose API keys.

------------------------------------------------------------------------------

# Definition of Done

A task is complete only if

✓ Architecture respected

✓ Existing code reused

✓ Backend integrated

✓ UI completed

✓ Loading handled

✓ Empty state handled

✓ Error state handled

✓ Accessibility verified

✓ Dark mode verified

✓ Documentation updated

✓ No duplicated logic

✓ Code reviewed

------------------------------------------------------------------------------

# AI Behaviour

Think before coding.

Inspect before modifying.

Reuse before creating.

Extend before replacing.

Document before finishing.

Never guess.

Never hallucinate project structure.

Never invent APIs that do not exist.

If required functionality is missing,

design it first,

then implement it.

------------------------------------------------------------------------------

# Final Principle

Write code as if it will be maintained by another engineer five years from now.

Optimize for long-term quality rather than short-term convenience.

Every commit should leave EcoRecover better than it was before.