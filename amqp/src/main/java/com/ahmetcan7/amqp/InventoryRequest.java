package com.ahmetcan7.amqp;

import java.util.UUID;

public class InventoryRequest {
    private UUID productId;
    private Integer quantity;

    public InventoryRequest(UUID productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public InventoryRequest(){

    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
