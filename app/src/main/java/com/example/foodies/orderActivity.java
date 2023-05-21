package com.example.foodies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.foodies.Adapters.MainAdapter;
import com.example.foodies.Adapters.OrdersAdapter;
import com.example.foodies.Models.MainModel;
import com.example.foodies.Models.OrdersModel;

import java.util.ArrayList;

public class orderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        RecyclerView recyclerView=findViewById(R.id.recyclerview1);

        ArrayList<OrdersModel> list =new ArrayList<>();
        list.add(new OrdersModel(R.drawable.tower,"Tower Burger","350 ","226465"));
        list.add(new OrdersModel(R.drawable.pizza,"Pizza","1000 ","25554"));
        list.add(new OrdersModel(R.drawable.fries,"Potato Fries","200 ","226465"));
        list.add(new OrdersModel(R.drawable.frieds,"Chicken Fried's","900 ","226465"));
        list.add(new OrdersModel(R.drawable.drinks,"Soft Drink","150 ","226465"));

        OrdersAdapter adapter=new OrdersAdapter(list,this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


    }
}