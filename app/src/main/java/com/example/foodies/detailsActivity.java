package com.example.foodies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class detailsActivity extends AppCompatActivity {

    int items = 1;
    int tmp_image;
    String tmp_price, tmp_name, tmp_disc, tmp_quantity;
    String tmp_email;
    int cal_price;
    int tmp_total_Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DatabaseHelper DB = new DatabaseHelper(this);
        SharedPreferences sp = getSharedPreferences("login_user_pref", MODE_PRIVATE);
        tmp_email = sp.getString("user_email", "null");

        ImageView prod_image = findViewById(R.id.prod_image);
        ImageView btn_add_item = findViewById(R.id.btn_add_item);
        ImageView btn_remove_item = findViewById(R.id.btn_remove_item);
        TextView txt_prod_name = findViewById(R.id.txt_details_prod_name);
        TextView txt_prod_price = findViewById(R.id.prod_price);
        TextView txt_prod_description = findViewById(R.id.prod_discription);
        TextView txt_prod_quantity = findViewById(R.id.txt_quantity);
        TextView txt_prod_total_price=findViewById(R.id.prod_total_price);

        Button btn_addto_cart = findViewById(R.id.btn_addto_cart);


        tmp_image = getIntent().getIntExtra("image", 0);
        tmp_price = getIntent().getStringExtra("price");
        tmp_name = getIntent().getStringExtra("name");
        tmp_disc = getIntent().getStringExtra("description");

        prod_image.setImageResource(tmp_image);
        txt_prod_price.setText(tmp_price);
        txt_prod_name.setText(tmp_name);
        txt_prod_description.setText(tmp_disc);
        txt_prod_quantity.setText(Integer.toString(items));

        cal_price = Integer.parseInt(tmp_price.trim());
        tmp_total_Price = items * cal_price;
        txt_prod_total_price.setText(Integer.toString(tmp_total_Price));

        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items == 7) {
                    Toast.makeText(detailsActivity.this, "You can not add more then 7 at single order", Toast.LENGTH_SHORT).show();
                    return;
                }
                items++;
                txt_prod_quantity.setText(Integer.toString(items));
                cal_price = Integer.parseInt(tmp_price.trim());
                tmp_total_Price = items * cal_price;
                txt_prod_total_price.setText(Integer.toString(tmp_total_Price));



            }
        });

        btn_remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items == 1) {
                    return;
                }
                items--;
                txt_prod_quantity.setText(Integer.toString(items));
                cal_price = Integer.parseInt(tmp_price.trim());
                tmp_total_Price = items * cal_price;
                txt_prod_total_price.setText(Integer.toString(tmp_total_Price));


            }
        });






        btn_addto_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = String.valueOf(items);

                boolean isInsertedCart = DB.insertDataCart(tmp_name, tmp_price, quantity, String.valueOf(tmp_total_Price));
                boolean isInsertedHistory =  DB.insertDataHistory(tmp_email, tmp_name, tmp_price, quantity, String.valueOf(tmp_total_Price));

                if (isInsertedCart && isInsertedHistory) {
                    Toast.makeText(detailsActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(detailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}