package com.receipt_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.receipt_app.R;
import com.receipt_app.models.Ingredient;
import com.receipt_app.network.api.IngredientData;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private List<IngredientData> ingredientList;
    private Context mContext;
    private IngredientListener ingredientListener;

    public IngredientsAdapter(Context context, List<IngredientData> ingredientList) {
        mContext = context;
        this.ingredientList = ingredientList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item_row,
                        parent, false);
        return new IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final IngredientViewHolder holder, int position) {
        IngredientData ingredient = ingredientList.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientText;
        ImageView wasteBin;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            ingredientText = itemView.findViewById(R.id.ingredientText);
            wasteBin = itemView.findViewById(R.id.wasteBin);
            wasteBin.setOnClickListener(v -> {
                if (ingredientListener != null)
                    ingredientListener.onEditIngredient(getAdapterPosition());
            });
        }

        public void bind(IngredientData ingredient) {
            ingredientText.setText(ingredient.getName());
        }
    }

    public void setIngredientListener(IngredientListener ingredientListener) {
        this.ingredientListener = ingredientListener;
    }

    public interface IngredientListener {
        void onEditIngredient(int id);
    }
}
