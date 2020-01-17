package com.example.admin.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class order extends AppCompatActivity {
    EditText x1;
    EditText x2;
    String qw;
    String qw1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Bundle p = getIntent().getExtras();
        x1 = findViewById(R.id.editText);
        x2 = findViewById(R.id.editText2);
        x1.setClickable(false);
        x1.setFocusable(false);
        x1.setText(p.getString("name"));
        qw = p.getString("name");
        qw1 = x2.getText().toString();
    }
    void ors(View view)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Inventory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        String x3 = null, x1 = null, x2 = null, x4 = null;

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot x : task.getResult()) {
                                System.out.println(qw+qw1);
                                EditText p = findViewById(R.id.editText2);
                                if(x.getString("name").equals(qw))
                                {
                                    int temp = Integer.parseInt(p.getText().toString());
                                    System.out.println(temp);
                                    x1 = x.getString("quantity");
                                    int temp2 = Integer.parseInt(x1);
                                    temp = temp + temp2;
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("Inventory").document(x.getId()).update("quantity",""+temp);

                                }
                            }
                        }
                    }
                });
        Intent p = new Intent(this,inventory.class);
        startActivity(p);
    }
}
