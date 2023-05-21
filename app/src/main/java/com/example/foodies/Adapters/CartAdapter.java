package com.example.foodies.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.DatabaseHelper;
import com.example.foodies.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private static Cursor cursor;

    public CartAdapter(Cursor cursor) {
        this.cursor = cursor;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.sample_cart, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cursor != null && cursor.moveToFirst()) {
            cursor.move(position); // Move to the desired position

            int columnIndexCartName = cursor.getColumnIndex(DatabaseHelper.cart_col_name);
            int columnIndexCartPrice = cursor.getColumnIndex(DatabaseHelper.cart_col_price);
            int columnIndexCartQuantity = cursor.getColumnIndex(DatabaseHelper.cart_col_quantity);
            int columnIndexCartTotalPrice = cursor.getColumnIndex(DatabaseHelper.cart_col_total_price);

            // Check if the column indexes are valid (-1 indicates column not found)
            if (columnIndexCartName != -1 && columnIndexCartPrice != -1 &&
                    columnIndexCartQuantity != -1 && columnIndexCartTotalPrice != -1) {

                String cartName = cursor.getString(columnIndexCartName);
                String cartPrice = cursor.getString(columnIndexCartPrice);
                String cartQuantity = cursor.getString(columnIndexCartQuantity);
                String cartTotalPrice = cursor.getString(columnIndexCartTotalPrice);

                // Bind the data to the ViewHolder views
                holder.cartNameTextView.setText(cartName);
                holder.cartPriceTextView.setText(cartPrice);
                holder.cartQuantityTextView.setText(cartQuantity);
                holder.cartTotalPriceTextView.setText(cartTotalPrice);
            }
        }
    }


    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cartNameTextView;
        public TextView cartPriceTextView;
        public TextView cartQuantityTextView;
        public TextView cartTotalPriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartNameTextView = itemView.findViewById(R.id.txt_cart_name);
            cartPriceTextView = itemView.findViewById(R.id.txt_cart_price);
            cartQuantityTextView = itemView.findViewById(R.id.txt_cart_quantity);
            cartTotalPriceTextView = itemView.findViewById(R.id.txt_cart_totalprice);

        }





    }
    public static double calculateTotalPrice() {
        double totalPrice = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int columnIndexCartPrice = cursor.getColumnIndex(DatabaseHelper.cart_col_total_price);
                if (columnIndexCartPrice != -1) {
                    double cartPrice = cursor.getDouble(columnIndexCartPrice);
                    totalPrice += cartPrice;
                }
            } while (cursor.moveToNext());
        }


        return totalPrice;
    }

}

