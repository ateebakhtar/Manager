package com.example.admin.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class editaccount extends AppCompatActivity {

    TextView nameView; //= (TextView) findViewById(R.id.nameView);
    TextView contactView;// = (TextView) findViewById(R.id.passwordView);
    TextView positionView;
    TextView addressView;
    TextView emailView;
    TextView passView;
    TextView ageView;
    private String documentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);

        nameView = findViewById(R.id.name_reg);
        positionView = findViewById(R.id.position_reg);
        ageView = findViewById(R.id.age_reg);
        contactView = findViewById(R.id.contact_reg);
        addressView = findViewById(R.id.add_reg);
        emailView = findViewById(R.id.email_reg);
        passView = findViewById(R.id.password_reg);

        getdata();
    }

    public void getdata() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Registered Employees")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        String x1 = null, x2 = null, x3 = null, x4 = null, x5 = null,x6 = null,x7 = null;

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot x : task.getResult()) {
                                x1 = x.getString("name");
                                x2 = x.getString("position");
                                x3 = x.getString("age");
                                x4 = x.getString("number");
                                x5 = x.getString("address");
                                x6 = x.getString("email");
                                x7 = x.getString("password");
                                System.out.println(x4);
                                if(x1.equals(data.id))
                                {
                                    settingID(x.getId());
                                    printData(x1,x2,x3,x4,x5,x6,x7);

                                    break;
                                }
                            }
                        } else {
                            System.out.println("Eorrrosr");
                        }
                    }
                });

    }
    void update(View view) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Registered Employees").document(documentID).update("name", nameView.getText().toString());
        db.collection("Registered Employees").document(documentID).update("position", positionView.getText().toString());
        db.collection("Registered Employees").document(documentID).update("email", emailView.getText().toString());
        db.collection("Registered Employees").document(documentID).update("address", addressView.getText().toString());
        db.collection("Registered Employees").document(documentID).update("number", contactView.getText().toString());
        db.collection("Registered Employees").document(documentID).update("password", passView.getText().toString());
        db.collection("Registered Employees").document(documentID).update("age", ageView.getText().toString());


        Intent p = new Intent(this, viewworking.class);

        startActivity(p);
    }
    public void settingID(String ID)
    {
        this.documentID=ID;
    }
    public void printData(String x1, String x2, String x3, String x4, String x5, String x6, String x7)
    {
        nameView.setText(x1);
        contactView.setText(x4);
        positionView.setText(x2);
        addressView.setText(x5);
        emailView.setText(x6);
        passView.setText(x7);
        ageView.setText(x3);
    }
}
