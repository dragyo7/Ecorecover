from fastapi import APIRouter, Depends, HTTPException, status
from app.api.v1.dependencies import get_current_user
from app.core.supabase import supabase

router = APIRouter()

@router.get("/")
def get_user_rewards(current_user=Depends(get_current_user)):
    try:
        # Fetch all appointments for the user
        response = (
            supabase
            .table("appointments")
            .select("*")
            .eq("user_id", current_user.id)
            .execute()
        )
        appointments = response.data or []
        
        # Calculate dynamic rewards based on completed pickups
        completed = [a for a in appointments if a.get("status", "").lower() == "completed"]
        pending = [a for a in appointments if a.get("status", "").lower() in ("pending", "confirmed", "scheduled")]
        
        total_devices_recycled = len(completed)
        money_earned = sum(float(a.get("estimated_price", 0)) for a in completed)
        
        # CO2 saved calculation: 1.4 kg for phones, 4.8 kg for laptops, 2.5 kg for others
        co2_saved = 0.0
        for a in completed:
            name = a.get("product_name", "").lower()
            if "phone" in name:
                co2_saved += 1.4
            elif "laptop" in name or "macbook" in name:
                co2_saved += 4.8
            else:
                co2_saved += 2.5
                
        # Trees equivalent: 1 tree absorbs ~20kg CO2 per year
        trees_equivalent = round(co2_saved / 20.0, 2)
        
        # Points calculation: 100 points per recycled device, plus 1 point per 10 INR earned
        eco_points = total_devices_recycled * 100 + int(money_earned / 10)
        
        # Base values for a new user so the dashboard is encouraging
        eco_points = max(eco_points, 150) # give a starting bonus
        
        # Level calculations
        level = int(eco_points / 1000) + 1
        points_in_level = eco_points % 1000
        level_progress = float(points_in_level) / 1000.0
        
        # Achievements
        achievements = [
            {
                "title": "Welcome Green Star",
                "description": "Signed up and joined EcoRecover",
                "icon": "🎉",
                "unlocked": True
            },
            {
                "title": "First Recycle",
                "description": "Complete your first e-waste recycle pickup",
                "icon": "♻️",
                "unlocked": total_devices_recycled >= 1
            },
            {
                "title": "Carbon Saver",
                "description": "Save at least 5 kg of CO2 emissions",
                "icon": "🌱",
                "unlocked": co2_saved >= 5.0
            },
            {
                "title": "Eco Champion",
                "description": "Recycle 5 or more electronic devices",
                "icon": "🏆",
                "unlocked": total_devices_recycled >= 5
            }
        ]
        
        # Get user full name
        user_metadata = getattr(current_user, "user_metadata", {}) or {}
        full_name = user_metadata.get("full_name") or current_user.email.split("@")[0]
        
        # Leaderboard with current user embedded
        leaderboard_data = [
            {"name": "Ananya Sharma", "points": 2450},
            {"name": "Rahul Verma", "points": 1800},
            {"name": "Priya Patel", "points": 1200},
            {"name": "Amit Singh", "points": 850},
            {"name": full_name, "points": eco_points, "is_current": True}
        ]
        
        # Sort leaderboard by points
        leaderboard_data = sorted(leaderboard_data, key=lambda x: x["points"], reverse=True)
        leaderboard = []
        for idx, entry in enumerate(leaderboard_data):
            leaderboard.append({
                "rank": idx + 1,
                "name": entry["name"],
                "points": entry["points"],
                "is_current": entry.get("is_current", False)
            })
            
        return {
            "success": True,
            "data": {
                "eco_points": eco_points,
                "total_devices_recycled": total_devices_recycled,
                "money_earned": money_earned,
                "co2_saved": round(co2_saved, 1),
                "trees_equivalent": trees_equivalent,
                "level": level,
                "level_progress": level_progress,
                "achievements": achievements,
                "leaderboard": leaderboard,
                "pending_pickups_count": len(pending),
                "pending_estimated_payout": sum(float(a.get("estimated_price", 0)) for a in pending)
            }
        }
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to calculate rewards: {str(e)}"
        )
