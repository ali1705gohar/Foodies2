package com.example.foodies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.Models.MainModel;
import com.example.foodies.R;
import com.example.foodies.detailsActivity;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewholder> {

    ArrayList<MainModel> list;
    Context context;

    public MainAdapter(ArrayList<MainModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_mainfood, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final MainModel model = list.get(position);

     holder.prod_image.setImageResource(model.getImage());
     holder.txt_prod_price.setText(model.getProd_price());
     holder.txt_prod_name.setText(model.getProd_name());
     holder.txt_prod_description.setText(model.getProd_description());

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Intent intent=new Intent(context, detailsActivity.class);
             intent.putExtra("image",model.getImage());
             intent.putExtra("name",model.getProd_name());
             intent.putExtra("price",model.getProd_price());
             intent.putExtra("description",model.getProd_description());
             context.startActivity(intent);


         }
     });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView prod_image;
        TextView txt_prod_name, txt_prod_price, txt_prod_description;


        public viewholder(@NonNull View itemView) {
            super(itemView);

            prod_image = itemView.findViewById(R.id.prod_image);
            txt_prod_name = itemView.findViewById(R.id.txt_product_title);
            txt_prod_description = itemView.findViewById(R.id.txt_product_discription);
            txt_prod_price = itemView.findViewById(R.id.txt_product_price);

        }
    }
}
