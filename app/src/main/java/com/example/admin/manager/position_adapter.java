package com.example.admin.manager;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class position_adapter extends RecyclerView.Adapter<position_adapter.ViewHolder> {
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> position = new ArrayList<>();
    private Context mContext;
    int temp;

    public position_adapter(Context c, ArrayList<String> n, ArrayList<String> p,int temp ) {
        this.mContext = c;
        this.name = n;
        this.position = p;
        this.temp = temp;
    }


    public position_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posititon_template, parent, false);
        position_adapter.ViewHolder holder = new position_adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int pos) {


        holder.name.setText(name.get(pos));
        holder.position.setText(position.get(pos));


        if(temp == 0)
        {
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    openDialog(pos);

                }
            });
        }
        else if(temp == 2)
        {
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    openDialog1(pos);

                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return name.size();
    }


    public void openDialog(final int pos1) {

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

        // Set Custom Title
        TextView title = new TextView(mContext);
        // Title Properties
        title.setText("Edit or Add to Working");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(mContext);
        // Message Properties
        msg.setText("Do you wish to edit or add");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Add to working", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    working a = new working();
                    a.insertdata(name.get(pos1),position.get(pos1));
                    db.collection("Workinglist").add(a);
            }
            });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Edit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent p = new Intent(mContext,editaccount.class);
                data.id = name.get(pos1);
                mContext.startActivity(p);
            }
        });

        new Dialog(mContext);
        alertDialog.show();

        // Set Properties for OK Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.BLACK);
        cancelBT.setLayoutParams(negBtnLP);
    }



    public void openDialog1(final int pos1) {

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

        // Set Custom Title
        TextView title = new TextView(mContext);
        // Title Properties
        title.setText("Modify Working List");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(mContext);
        // Message Properties
        msg.setText("Do you wish to remove this entry");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"remove", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Workinglist")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(Task<QuerySnapshot> task) {
                                String x3 = null, x1 = null, x2 = null,x4 = null;
                                ArrayList<String> pos= new ArrayList<>();
                                ArrayList<String> names= new ArrayList<>();

                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot x : task.getResult())
                                    {
                                        x1 = x.getString("name");
                                        if(x1.equals(name.get(pos1)))
                                        {
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            db.collection("Workinglist").document(x.getId()).delete();
                                            Intent p = new Intent(mContext,editworking.class);
                                            mContext.startActivity(p);
                                            break;
                                        }


                                    }


                                }
                                else
                                {
                                    System.out.println("Eorrrosr");
                                }
                            }
                        });

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        new Dialog(mContext);
        alertDialog.show();

        // Set Properties for OK Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.BLACK);
        cancelBT.setLayoutParams(negBtnLP);
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView name;
        TextView position;
        RelativeLayout parentLayout;
        public ViewHolder(View itemView)
        {
            super(itemView);
            //Price=itemView.findViewById(R.id.textView2);
            name=itemView.findViewById(R.id.EditText3);
            name.setFocusable(false);
            name.setClickable(false);
            position=itemView.findViewById(R.id.EditText4);
            position.setClickable(false);
            position.setFocusable(false);
            parentLayout=itemView.findViewById(R.id.relative);

        }

    }

}
