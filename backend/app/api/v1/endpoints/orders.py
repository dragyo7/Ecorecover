from fastapi import APIRouter, Depends, HTTPException, status
from app.api.v1.dependencies import get_current_user
from app.core.supabase import supabase

router = APIRouter()

@router.get("/")
def get_user_orders(current_user=Depends(get_current_user)):
    try:
        response = (
            supabase
            .table("appointments")
            .select("*")
            .eq("user_id", current_user.id)
            .order("created_at", desc=True)
            .execute()
        )
        return {
            "success": True,
            "data": response.data
        }
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch orders: {str(e)}"
        )

@router.get("/{order_id}")
def get_order_details(order_id: str, current_user=Depends(get_current_user)):
    try:
        response = (
            supabase
            .table("appointments")
            .select("*")
            .eq("id", order_id)
            .execute()
        )
        if not response.data:
            raise HTTPException(
                status_code=status.HTTP_404_NOT_FOUND,
                detail="Order not found"
            )
        order = response.data[0]
        
        # Security check: Ensure order belongs to current user
        if order.get("user_id") != current_user.id:
            raise HTTPException(
                status_code=status.HTTP_403_FORBIDDEN,
                detail="You do not have permission to view this order"
            )
            
        return {
            "success": True,
            "data": order
        }
    except HTTPException:
        raise
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch order details: {str(e)}"
        )
