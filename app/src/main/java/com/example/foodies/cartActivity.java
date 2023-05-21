package com.example.foodies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.Adapters.CartAdapter;

import java.text.DecimalFormat;

public class cartActivity extends AppCompatActivity {

    Double service_charges = 5.68, delivary_charges = 40.0, discount = 10.0, final_price;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);
        TextView total = findViewById(R.id.txt_cart_totalprice1);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllCart();

        sp = getSharedPreferences("order", MODE_PRIVATE);


        RecyclerView recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView txt_service_charges = findViewById(R.id.txt_cart_service_charges);
        TextView txt_delivary_charges = findViewById(R.id.txt_cart_delivary_charges);
        TextView txt_discounts = findViewById(R.id.txt_cart_dicount);
        TextView txt_final_price = findViewById(R.id.txt_cart_final_price);

        Button btn_clear_cart = findViewById(R.id.btn_clear_cart);
        Button btn_next = findViewById(R.id.btn_next);

        CartAdapter adapter = new CartAdapter(cursor);
        recyclerView.setAdapter(adapter);
        if (adapter.getItemCount() == 0) {
            Toast.makeText(cartActivity.this, "No items in cart", Toast.LENGTH_SHORT).show();
        }

        double totalPrice = CartAdapter.calculateTotalPrice();
        total.setText(String.valueOf(totalPrice));
        txt_service_charges.setText(String.valueOf(service_charges));
        txt_delivary_charges.setText(String.valueOf(delivary_charges));
        txt_discounts.setText("-" + String.valueOf(discount));

        double price = 0; // The original price
        price = totalPrice + delivary_charges + service_charges;

        double discountPercentage = 10.0; // The percentage to subtract

        double discountAmount = price * (discountPercentage / 100); // Calculate the discount amount
        double finaldiscountPrice = price - discountAmount; // Calculate the final price

        final_price = finaldiscountPrice;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedFinalPrice = decimalFormat.format(final_price);
        txt_final_price.setText("RS " + formattedFinalPrice);



        btn_clear_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getItemCount() == 0) {
                    Toast.makeText(cartActivity.this, "Cart is already empty", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(cartActivity.this);
                    builder.setTitle("Clear Cart");
                    builder.setMessage("Are you sure you want to clear the cart?");

                    // Set the positive button (Yes)
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean result = databaseHelper.ClearCart();
                            if (result) {
                                Toast.makeText(cartActivity.this, "Items Cleared", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), cartActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(cartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getItemCount() == 0) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cartActivity.this);
                    alertDialogBuilder.setMessage("Cart is empty Please add some items first");
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    return;
                }
                boolean isDelivered = sp.getBoolean("indelivery", false);
                 if (isDelivered == true) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cartActivity.this);
                    alertDialogBuilder.setMessage("An Order is Already in Delivery you can not order more");
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    return;
                } else {
                    Intent intent = new Intent(getApplicationContext(), paymentActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}