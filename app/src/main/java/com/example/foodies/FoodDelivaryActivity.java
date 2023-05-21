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

        ArrayList<MainModel> list =new ArrayList<>();
        list.add(new MainModel(R.drawable.tower,"Tower Burger","350 ","Tower Burger Cooked with Fresh Meat and cheese"));
        list.add(new MainModel(R.drawable.pizza,"Pizza","1000 ","Tower Burger Cooked with Fresh Meat and cheese"));
        list.add(new MainModel(R.drawable.fries,"Potato Fries","200 ","Tower Burger Cooked with Fresh Meat and cheese"));
        list.add(new MainModel(R.drawable.frieds,"Chicken Fried's","900 ","Tower Burger Cooked with Fresh Meat and cheese"));
        list.add(new MainModel(R.drawable.drinks,"Soft Drink","150 ","Tower Burger Cooked with Fresh Meat and cheese"));

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






