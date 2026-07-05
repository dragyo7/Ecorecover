# EcoRecover User Flows

Version: 3.0

Status: ACTIVE

------------------------------------------------------------------------------

# Purpose

This document defines every major user journey within EcoRecover.

It explains how users move through the application from launch to completing an action.

Every feature should strengthen one or more of these journeys.

------------------------------------------------------------------------------

# Product Philosophy

The application should require minimal thinking.

Every screen should naturally guide users toward the next action.

Users should never become lost.

Every journey should end with a meaningful outcome.

------------------------------------------------------------------------------

# Primary User Journey

Open App

↓

Authentication

↓

Home

↓

Search Device

↓

Device Details

↓

Instant Quote

↓

Pickup Cart

↓

Checkout

↓

Pickup Order Created

↓

Eco Partner Assigned

↓

Track Pickup

↓

Inspection

↓

Payment Released

↓

Rewards Updated

↓

Order Completed

This is the most important journey.

Nothing should interrupt it.

------------------------------------------------------------------------------

# Journey 1

First Time User

Launch App

↓

Onboarding

↓

Create Account

↓

Profile Setup

↓

Allow Location

↓

Home

Goal

User reaches Home within two minutes.

------------------------------------------------------------------------------

# Journey 2

Returning User

Launch App

↓

Session Restored

↓

Home

Goal

No login required if session is valid.

------------------------------------------------------------------------------

# Journey 3

Search Device

Home

↓

Search

↓

Suggestions

↓

Category

↓

Device Selected

↓

Device Details

Backend

Search API

↓

ewaste_lookup.py

Goal

Reach desired device within three interactions.

------------------------------------------------------------------------------

# Journey 4

Instant Quote

Device Details

↓

Fetch Metal Composition

↓

Fetch Live Market Prices

↓

Calculate Valuation

↓

Display Quote

Shows

Current Value

Recovery %

Metal Breakdown

Timestamp

Price Trend

Powered By

ewaste_lookup.py

economic_fetcher.py

cost_fetcher.py

Goal

Complete transparency.

------------------------------------------------------------------------------

# Journey 5

Book Pickup

Device Details

↓

Add To Pickup

↓

Pickup Cart

↓

Checkout

↓

Select Address

↓

Pickup Slot

↓

Confirm

↓

Order Created

Goal

Booking completed within one minute.

------------------------------------------------------------------------------

# Journey 6

Track Pickup

Orders

↓

Order Details

↓

Live Map

↓

Eco Partner

↓

ETA

↓

Timeline

↓

Pickup

↓

Inspection

↓

Payment

Inspired by Uber.

------------------------------------------------------------------------------

# Journey 7

Insights

Home

↓

Insights

↓

Market Overview

↓

Metal Prices

↓

Trending Devices

↓

Best Time To Sell

↓

Recovery Leaderboard

Backend

economic_fetcher.py

↓

metal_market_engine.py

↓

Market Intelligence Engine

Goal

Educate users.

------------------------------------------------------------------------------

# Journey 8

Rewards

Completed Pickup

↓

Rewards Engine

↓

Wallet

↓

Green Coins

↓

Achievements

↓

Profile

Goal

Encourage repeated recycling.

------------------------------------------------------------------------------

# Journey 9

Notifications

Market Change

↓

Push Notification

↓

Insights

----------------------------------

Pickup Update

↓

Push Notification

↓

Orders

----------------------------------

Rewards Earned

↓

Push Notification

↓

Wallet

Every notification should open the correct destination.

------------------------------------------------------------------------------

# Journey 10

Profile

Profile

↓

Wallet

↓

Orders

↓

Rewards

↓

Carbon Saved

↓

Settings

↓

Support

Goal

Everything personal in one place.

------------------------------------------------------------------------------

# Error Journey

Search Failed

↓

Retry

----------------------------------

Network Lost

↓

Offline UI

↓

Retry

----------------------------------

API Failure

↓

Friendly Message

↓

Retry

Users should always have a recovery path.

------------------------------------------------------------------------------

# Future Journeys

Smart Detection

Camera

↓

AI Detection

↓

Quote

----------------------------------

Bulk Pickup

Multiple Devices

↓

Single Pickup

----------------------------------

Corporate Pickup

Inventory

↓

Bulk Quote

↓

Schedule Pickup

----------------------------------

Price Alert

Notification

↓

Insights

↓

Book Pickup

------------------------------------------------------------------------------

# UX Rules

Search always accessible.

Booking under one minute.

Quote under two seconds.

Users always know the next action.

Every screen has one primary CTA.

Never trap users.

------------------------------------------------------------------------------

# Acceptance Criteria

Every journey must

Support loading.

Support empty states.

Support errors.

Use backend APIs.

Reuse existing engines.

Support dark mode.

Support accessibility.

Remain responsive.

------------------------------------------------------------------------------

# Final Principle

EcoRecover should make recycling electronics feel as simple and predictable as ordering groceries or booking a ride.