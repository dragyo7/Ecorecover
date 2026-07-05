# EcoRecover UI System

Version: 3.0

Status: ACTIVE

------------------------------------------------------------------------------

# Purpose

This document defines the visual identity and user experience of EcoRecover.

It establishes the design language that every screen must follow.

The objective is not to imitate existing applications, but to provide a familiar, intuitive, and premium experience inspired by leading consumer apps.

------------------------------------------------------------------------------

# Design Philosophy

EcoRecover should feel

Fast

Friendly

Modern

Premium

Minimal

Transparent

Trustworthy

Every interaction should reduce user effort.

------------------------------------------------------------------------------

# Inspiration

Study interaction patterns from

• Blinkit
• Instamart
• Uber
• Google Maps
• Material Design 3

Never copy

Brand colors

Logos

Icons

Exact layouts

Reuse only UX principles.

------------------------------------------------------------------------------

# UX Principles

Search first.

One primary action per screen.

Minimal cognitive load.

Cards over tables.

Visual hierarchy over excessive text.

Fast interactions.

Large touch targets.

Immediate feedback.

Clear navigation.

------------------------------------------------------------------------------

# Visual Identity

Material Design 3

Rounded cards

Rounded buttons

Rounded search bars

Rounded bottom sheets

Subtle elevation

Soft animations

Modern spacing

Adaptive layouts

Dark mode support

------------------------------------------------------------------------------

# Color System

Use Material3 dynamic color roles.

Primary

Eco Green

Secondary

Blue

Accent

Amber

Success

Green

Warning

Orange

Error

Red

Follow Material3 color tokens.

Never hardcode colors.

------------------------------------------------------------------------------

# Typography

Material3 Typography only.

Readable.

Consistent hierarchy.

Maximum three font weights per screen.

Never overuse bold text.

------------------------------------------------------------------------------

# Spacing

Consistent spacing throughout the application.

Prefer whitespace over separators.

Avoid crowded layouts.

------------------------------------------------------------------------------

# Icons

Material Symbols.

Filled

Outlined

Rounded

Use consistently.

------------------------------------------------------------------------------

# Motion

Animations should communicate state.

Allowed

Fade

Slide

Scale

Crossfade

Shared transitions

Skeleton loading

Progress animations

Avoid decorative animations.

------------------------------------------------------------------------------

# Bottom Navigation

Maximum four destinations.

Home

Orders

Insights

Profile

Never exceed four tabs.

------------------------------------------------------------------------------

# Home Screen

Purpose

Help users start recycling immediately.

Must include

Current Location

Search Bar

Hero Banner

Categories

Trending Devices

Today's Best Prices

Quick Pickup

Nearby EcoHub

Recent Activity

Primary CTA

Search Device

------------------------------------------------------------------------------

# Search Experience

Search should always be accessible.

Features

Autocomplete

Recent Searches

Popular Searches

Categories

Voice Search (future)

Smart Detection Entry

Search results should appear instantly.

------------------------------------------------------------------------------

# Device Cards

Every card should display

Image

Device Name

Current Value

Today's Change

Pickup Availability

Primary CTA

Cards should remain compact and readable.

------------------------------------------------------------------------------

# Device Details

Must include

Large Image

Device Name

Current Value

Market Movement

Condition Selection

Metal Breakdown

Recovery Percentage

Price History

Book Pickup

Everything important should be visible without excessive scrolling.

------------------------------------------------------------------------------

# Pickup Cart

Inspired by Blinkit.

Displays

Selected Devices

Estimated Value

Pickup Fee

Coupons

Wallet

Continue Button

Running Total

------------------------------------------------------------------------------

# Checkout

Address

Pickup Slot

Instructions

Payment Method

Order Summary

Confirm Pickup

Keep the process under one minute.

------------------------------------------------------------------------------

# Orders

Inspired by Uber.

Contains

Live Map

Partner Details

ETA

Timeline

Status

Support

Every order should always display its current stage.

------------------------------------------------------------------------------

# Tracking Timeline

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

Tracking should be driven by backend state.

------------------------------------------------------------------------------

# Insights

Purpose

Help users make informed decisions.

Displays

Live Metal Prices

Historical Charts

Trending Devices

Top Gainers

Top Losers

Best Time To Sell

Recovery Leaderboard

Insights must originate from backend data.

------------------------------------------------------------------------------

# Profile

Contains

Wallet

Green Coins

Achievements

Carbon Saved

Orders

Settings

Support

About

------------------------------------------------------------------------------

# Notifications

Pickup Updates

Price Alerts

Reward Updates

Market Insights

Partner Assigned

Partner Arriving

Notifications should always lead to a meaningful action.

------------------------------------------------------------------------------

# Loading States

Use

Skeleton Loading

Shimmer

Progress Indicators

Never block the interface.

------------------------------------------------------------------------------

# Empty States

Every screen must define

No Orders

No Search Results

No Devices

No Notifications

No Insights

Always provide a recovery action.

------------------------------------------------------------------------------

# Error States

Friendly messages.

Retry option.

No technical jargon.

Never expose stack traces.

------------------------------------------------------------------------------

# Accessibility

Material3 accessibility.

Large touch targets.

High contrast.

Dynamic font sizes.

Screen reader support.

Responsive layouts.

------------------------------------------------------------------------------

# Performance

Use

LazyColumn

remember

derivedStateOf

Stable state

Efficient recomposition

Avoid unnecessary redraws.

------------------------------------------------------------------------------

# Component Rules

Prefer reusable composables.

Never duplicate UI.

Extract common components.

Maintain visual consistency.

------------------------------------------------------------------------------

# Screen Contract

Every screen must define

Purpose

Primary Action

Inputs

Outputs

Loading

Success

Empty

Error

Navigation

Definition of Done

------------------------------------------------------------------------------

# UX Laws

Search must always be one tap away.

Booking a pickup should take less than one minute.

Every quote must be transparent.

Every order must be trackable.

Every action must provide feedback.

Every screen should guide the user to the next step.

------------------------------------------------------------------------------

# Future UI Evolution

Voice Search

AI Smart Detection

Widgets

Wear OS

Foldable Support

Tablet Optimization

Offline Mode

Future features must integrate naturally with the existing design language.

------------------------------------------------------------------------------

# Final Principle

Users should feel they are using a polished consumer application that makes recycling electronics effortless, transparent and trustworthy.