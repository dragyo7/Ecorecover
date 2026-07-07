package com.ecorecover.app.data.model

import com.google.gson.annotations.SerializedName

data class RewardsResponse(
    val success: Boolean,
    val data: RewardsData?
)

data class RewardsData(
    @SerializedName("eco_points") val ecoPoints: Int,
    @SerializedName("total_devices_recycled") val totalDevicesRecycled: Int,
    @SerializedName("money_earned") val moneyEarned: Double,
    @SerializedName("co2_saved") val co2Saved: Double,
    @SerializedName("trees_equivalent") val treesEquivalent: Double,
    val level: Int,
    @SerializedName("level_progress") val levelProgress: Float,
    val achievements: List<Achievement>,
    val leaderboard: List<LeaderboardEntry>,
    @SerializedName("pending_pickups_count") val pendingPickupsCount: Int,
    @SerializedName("pending_estimated_payout") val pendingEstimatedPayout: Double
)

data class Achievement(
    val title: String,
    val description: String,
    val icon: String,
    val unlocked: Boolean
)

data class LeaderboardEntry(
    val rank: Int,
    val name: String,
    val points: Int,
    @SerializedName("is_current") val isCurrent: Boolean
)
