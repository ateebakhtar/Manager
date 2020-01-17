package com.example.admin.manager;

import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.admin.manager.app.CHANNEL_1_ID;

public class inventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        input();
    }

    public ArrayList<String> item_name=new ArrayList<>();
    public ArrayList<String> quantity=new ArrayList<>();
    public ArrayList<String> price=new ArrayList<>();




     public  void refresh(View view)
     {
         Intent x = new Intent(this,inventory.class);
         startActivity(x);
     }
    public void input()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Inventory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        String x3 = null, x1 = null, x2 = null,x4 = null;
                        ArrayList<String> pic= new ArrayList<>();
                        ArrayList<String> names= new ArrayList<>();
                        ArrayList<String> qty= new ArrayList<>();
                        ArrayList<String> price= new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot x : task.getResult())
                            {
                                x1 = x.getString("name");
                                x2 = x.getString("price");
                                x3 = x.getString("quantity");

                                System.out.println("Name" + x2);


                                int temp = Integer.parseInt(x3);
                                if(temp<10)
                                {
                                    System.out.println("something"+temp);
                                    NotificationManagerCompat notificationManager;
                                    notificationManager= NotificationManagerCompat.from(inventory.this);
                                    Notification notification = new NotificationCompat.Builder(inventory.this, CHANNEL_1_ID)
                                            .setSmallIcon(R.drawable.bbq)
                                            .setContentTitle("Alert")
                                            .setContentText("Inventory is running Low on "+x1)
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                            .build();

                                    notificationManager.notify(1, notification);
                                }






                                names.add(x2);
                                qty.add(x3);
                                price.add(x1);


                            }

                            inventoryadapter adapter = new inventoryadapter( inventory.this,names,price,qty);
                            RecyclerView recyclerView=findViewById(R.id.recyclerView);

                            if(adapter != null || recyclerView !=null)
                            {
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(inventory.this));
                            }

                            else
                            {
                                System.out.println("Eorrrosr");
                            }
                        }
                        else
                        {
                            System.out.println("Eorrrosr");
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Inventory").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    DocumentSnapshot documentSnapshot = dc.getDocument();
                    String id = documentSnapshot.getId();
                    int oldIndex = dc.getOldIndex();
                    int newIndex = dc.getNewIndex();

                    switch (dc.getType()) {
                        case ADDED:
                            input();
                            break;
                        case MODIFIED:
                            input();
                            NotificationManagerCompat notificationManager;
                            notificationManager= NotificationManagerCompat.from(inventory.this);
                            Notification notification = new NotificationCompat.Builder(inventory.this, CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.bbq)
                                    .setContentTitle("Alert")
                                    .setContentText("Inventory is running Low on ")
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();

                            notificationManager.notify(1, notification);
                            break;
                        case REMOVED:
                            //textViewData.append("\nRemoved: " + id +
                            //      "\nOld Index: " + oldIndex + "New Index: " + newIndex);
                            break;
                    }
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {

        Intent setIntent ;
         if(data.manager == 1)
         {
              setIntent = new Intent(this,managermenu.class);
         }
         else
         {
              setIntent = new Intent(this,mainmenu.class);
         }

        startActivity(setIntent);
    }


}
