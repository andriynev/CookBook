package com.receipt_app.network.api;

public class IngredientResponse {
    private IngredientData item;

    public IngredientResponse() {

    }

    public IngredientData getItem() {
        return item;
    }

    public void setItem(IngredientData item) {
        this.item = item;
    }
}
