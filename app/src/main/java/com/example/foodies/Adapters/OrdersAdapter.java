package com.example.foodies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.Models.MainModel;
import com.example.foodies.Models.OrdersModel;
import com.example.foodies.R;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.viewholder1>{

    ArrayList<OrdersModel> list;
    Context context;

    public OrdersAdapter(ArrayList<OrdersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_order, parent, false);
        return new viewholder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.viewholder1 holder, int position) {
        final OrdersModel model = list.get(position);
        holder.prod_image.setImageResource(model.getOrder_image());
        holder.txt_prod_price.setText(model.getOrder_price());
        holder.txt_prod_name.setText(model.getOrder_name());
        holder.txt_product_id.setText(model.getOrder_number());

    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder1 extends RecyclerView.ViewHolder {



        ImageView prod_image;
        TextView txt_prod_name, txt_prod_price, txt_product_id;


        public viewholder1(@NonNull View itemView) {
            super(itemView);

            prod_image = itemView.findViewById(R.id.prod_image);
            txt_prod_name = itemView.findViewById(R.id.txt_product_title);
            txt_product_id = itemView.findViewById(R.id.txt_product_id);
            txt_prod_price = itemView.findViewById(R.id.txt_product_price);

        }
    }
}
