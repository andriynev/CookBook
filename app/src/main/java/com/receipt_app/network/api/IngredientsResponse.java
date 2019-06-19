package com.receipt_app.network.api;

import java.util.List;

public class IngredientsResponse {
    private List<IngredientData> list;

    public List<IngredientData> getList() {
        return list;
    }

    public void setList(List<IngredientData> list) {
        this.list = list;
    }
}
