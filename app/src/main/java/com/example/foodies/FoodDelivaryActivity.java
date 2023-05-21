package com.example.foodies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodies.Adapters.MainAdapter;
import com.example.foodies.Models.MainModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FoodDelivaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_delivary);

        RecyclerView recyclerView=findViewById(R.id.recyclerview);

        ArrayList<MainModel> list = FoodItemsList.getFoodItems();

        MainAdapter adapter=new MainAdapter(list,this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton btn_cart=findViewById(R.id.btn_cart);

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getApplicationContext(), cartActivity.class);
                startActivity(intent);

            }
        });




    }
}






