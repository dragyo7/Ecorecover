# EcoRecover AI Workflows

Version: 3.0

Status: ACTIVE

Priority: HIGH

------------------------------------------------------------------------------

# Purpose

This document defines the standard workflows every AI coding agent must follow
when working on EcoRecover.

These workflows ensure that every implementation is consistent,
maintainable, reusable and aligned with the project architecture.

This document complements

README.md

00_PRODUCT_CONSTITUTION.md

08_AGENT_RULES.md

------------------------------------------------------------------------------

# Workflow Philosophy

Every task follows this sequence

Understand

↓

Inspect

↓

Plan

↓

Reuse

↓

Extend

↓

Implement

↓

Verify

↓

Document

Never skip a step.

Never jump directly into coding.

------------------------------------------------------------------------------

# Workflow 1

Project Initialization

Read

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

↓

Inspect Android project

↓

Inspect Backend project

↓

Produce implementation plan

Only then begin coding.

------------------------------------------------------------------------------

# Workflow 2

Architecture Audit

Inspect

Android structure

Backend structure

Navigation

Repositories

ViewModels

Compose Screens

Existing APIs

Existing Engines

Identify

Completed features

Incomplete features

Disconnected modules

Unused code

Duplicated logic

Technical debt

Never modify code before completing the audit.

------------------------------------------------------------------------------

# Workflow 3

Implement New Screen

Understand user flow.

↓

Locate navigation graph.

↓

Reuse existing components.

↓

Reuse theme.

↓

Create screen.

↓

Create ViewModel.

↓

Connect Repository.

↓

Connect API.

↓

Handle

Loading

Success

Empty

Error

↓

Test.

↓

Update documentation.

------------------------------------------------------------------------------

# Workflow 4

Implement Backend Feature

Inspect existing services.

↓

Inspect existing engines.

↓

Reuse schemas.

↓

Reuse models.

↓

Create service.

↓

Expose API.

↓

Repository integration.

↓

Android integration.

↓

Testing.

↓

Documentation.

Never duplicate existing business logic.

------------------------------------------------------------------------------

# Workflow 5

Create New Engine

Ask

Can an existing engine solve this?

YES

↓

Extend existing engine.

NO

↓

Create new engine.

Rules

Single responsibility.

Independent.

Reusable.

Documented.

Future-proof.

------------------------------------------------------------------------------

# Workflow 6

Implement API

Inspect similar endpoints.

↓

Reuse schemas.

↓

Validation.

↓

Business service.

↓

Response models.

↓

Swagger documentation.

↓

Repository integration.

↓

Compose integration.

------------------------------------------------------------------------------

# Workflow 7

Implement Compose UI

Material3.

↓

Responsive.

↓

Dark Mode.

↓

Accessibility.

↓

Reusable components.

↓

Animation.

↓

Performance review.

↓

Testing.

------------------------------------------------------------------------------

# Workflow 8

Implement ViewModel

One ViewModel.

↓

Repository only.

↓

Immutable StateFlow.

↓

No Retrofit.

↓

No business logic.

↓

Expose UI State.

------------------------------------------------------------------------------

# Workflow 9

Implement Repository

Repository becomes

Single Source of Truth.

Responsibilities

Network.

Cache.

Synchronization.

Fallback.

Offline support.

Never bypass repositories.

------------------------------------------------------------------------------

# Workflow 10

Implement Quote System

User selects device.

↓

Search API.

↓

ewaste_lookup.py

↓

economic_fetcher.py

↓

cost_fetcher.py

↓

Instant Quote API

↓

Repository

↓

ViewModel

↓

Compose UI

Android never calculates values.

------------------------------------------------------------------------------

# Workflow 11

Implement Pickup Flow

Search

↓

Quote

↓

Pickup Cart

↓

Checkout

↓

Order Created

↓

Tracking

↓

Inspection

↓

Payment

↓

Rewards

Never skip stages.

------------------------------------------------------------------------------

# Workflow 12

Implement Tracking

Order Created

↓

Partner Assigned

↓

Travelling

↓

Arriving

↓

Pickup Started

↓

Collected

↓

Inspection

↓

Payment Released

↓

Completed

Current implementation may simulate tracking.

Future implementation should support live GPS.

------------------------------------------------------------------------------

# Workflow 13

Market Refresh

Every 47 Minutes

↓

economic_fetcher.py

↓

Update metal prices

↓

cost_fetcher.py

↓

Recalculate device values

↓

Market Intelligence Engine

↓

Update Insights

↓

Send Notifications

------------------------------------------------------------------------------

# Workflow 14

Testing Workflow

Backend

↓

API Testing

↓

Repository Testing

↓

ViewModel Testing

↓

Compose Testing

↓

Navigation Testing

↓

Manual Testing

↓

Documentation Update

------------------------------------------------------------------------------

# Workflow 15

Refactoring Workflow

Identify duplication.

↓

Preserve behaviour.

↓

Improve readability.

↓

Improve architecture.

↓

Improve performance.

↓

Retest.

↓

Update documentation.

Never refactor solely for style preferences.

------------------------------------------------------------------------------

# Workflow 16

Documentation Workflow

Architecture changed?

↓

03_ARCHITECTURE.md

----------------------------------

Feature changed?

↓

06_FEATURES.md

----------------------------------

UI changed?

↓

04_UI_SYSTEM.md

----------------------------------

Product changed?

↓

01_PROJECT.md

----------------------------------

Permanent knowledge changed?

↓

02_MEMORY.md

----------------------------------

Roadmap changed?

↓

07_ROADMAP.md

Documentation must evolve with the codebase.

------------------------------------------------------------------------------

# Workflow 17

Definition of Done

Before completing any task verify

✓ Existing code reused

✓ Architecture respected

✓ Compose only

✓ Material3 only

✓ Backend integrated

✓ Existing engines reused

✓ No duplicated logic

✓ Loading handled

✓ Error handled

✓ Empty state handled

✓ Accessibility verified

✓ Dark mode verified

✓ Documentation updated

✓ Code formatted

✓ Ready for review

------------------------------------------------------------------------------

# AI Decision Matrix

If existing functionality exists

↓

Reuse it

----------------------------------

If functionality is incomplete

↓

Extend it

----------------------------------

If functionality does not exist

↓

Design first

↓

Implement second

----------------------------------

If uncertain

↓

Inspect project

↓

Ask for clarification

Never invent architecture.

Never assume APIs exist.

------------------------------------------------------------------------------

# Final Principle

Every workflow should move EcoRecover one step closer to a production-quality application.

The goal is not simply to generate code.

The goal is to build a scalable, maintainable and trustworthy platform that makes e-waste recycling as effortless as ordering groceries.