package com.example.admin.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class workinglist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workinglist);



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
                                x2 = x.getString("position");


                                System.out.println("Name" + x2);
                                names.add(x1);
                                pos.add(x2);


                            }

                            position_adapter adapter = new position_adapter( workinglist.this,names,pos,1);
                            RecyclerView recyclerView=findViewById(R.id.recyclerview);

                            if(adapter != null || recyclerView !=null)
                            {
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(workinglist.this));
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
}
