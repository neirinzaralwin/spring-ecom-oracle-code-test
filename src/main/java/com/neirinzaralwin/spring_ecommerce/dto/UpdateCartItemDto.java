package com.neirinzaralwin.spring_ecommerce.dto;

public class UpdateCartItemDto {
    private int cartProductId;
    private int quantity;


    public int getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(int cartProductId) {
        this.cartProductId = cartProductId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "UpdateCartItemDto{" +
                "cartProductId=" + cartProductId +
                ", quantity=" + quantity +
                '}';
    }
}
