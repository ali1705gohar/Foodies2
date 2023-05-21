package com.example.foodies;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {


    View delivary_card_view;
    LinearLayout btn_history,btn_rateus;

    DatabaseHelper DB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        DB = new DatabaseHelper(getContext());
        delivary_card_view = view.findViewById(R.id.card_food_delivary);
        btn_history = view.findViewById(R.id.btn_previous_orders);
        btn_rateus=view.findViewById(R.id.rate_us);
        SharedPreferences sp = getActivity().getSharedPreferences("login_user_pref", MODE_PRIVATE);
        String tmp_email;
        tmp_email = sp.getString("user_email", "0");


        btn_rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(getContext(),Rate_us.class);
                    startActivity(intent);
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayOrderHistory(tmp_email);
            }
        });


        delivary_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FoodDelivaryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void displayOrderHistory(String email) {
        Cursor res = DB.getAllHistory(email);
        if (res.getCount() == 0) {
            showMessage("Error", "No Data");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Item Name: " + res.getString(1) + "\n");
            buffer.append("Quantity: " + res.getString(3) + "\n");
            buffer.append("Price per Item: " + res.getString(2) + "\n");
            buffer.append("Total Order Price: " + res.getString(4) + "\n\n");
        }

        showMessage("Previous Orders", buffer.toString());
    }


}