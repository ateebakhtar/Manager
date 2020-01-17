package com.example.admin.manager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class inventoryadapter extends RecyclerView.Adapter<inventoryadapter.ViewHolder>
{
    private static final String TAG = "RecyclerViewAdapter_inventory";
    // private ArrayList<String> b=new ArrayList<>();
    private ArrayList<String> item_name =new ArrayList<>();
    private ArrayList<String> quantity =new ArrayList<>();
    private ArrayList<String> price=new ArrayList<>();
    private Context mContext;

    public inventoryadapter(Context mContext, ArrayList<String> item, ArrayList<String> qty, ArrayList<String> price) {
        this.item_name = item;
        this.quantity = qty;
        this.price=price;
        this.mContext = mContext;

    }



    @Override
    public inventoryadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inventoryitems,parent,false);
        inventoryadapter.ViewHolder holder=new inventoryadapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(inventoryadapter.ViewHolder holder, final int position) {

        holder.itemName.setText(item_name.get(position));
        holder.q.setText(quantity.get(position));
        holder.p.setText(price.get(position));
        String m1 = "yes";
        int m = data.manager;
        System.out.println(m);
        if(m == 1)
        {
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent p = new Intent(mContext,order.class);
                    Bundle x = new Bundle();
                    x.putString("name",quantity.get(position));
                    x.putString("quantity", price.get(position));
                    System.out.println("thing"+price.get(position));
                    p.putExtras(x);
                    mContext.startActivity(p);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return item_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView itemName;
        TextView q; //quantity
        TextView p; //price
        RelativeLayout parentLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            itemName=itemView.findViewById(R.id.name);
            q=itemView.findViewById(R.id.quantity);
            p=itemView.findViewById(R.id.price);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }

    }
}
