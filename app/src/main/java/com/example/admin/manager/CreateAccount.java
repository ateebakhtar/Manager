package com.example.admin.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener{



    private EditText name;
    private EditText position;
    private EditText age;
    private EditText num;
    private EditText resadd;
    private EditText email;
    private EditText pass;
    private TextView goToLogin;

    private FirebaseFirestore db;
    void open(View view)
    {
        Intent p = new Intent(this,Login.class);
        startActivity(p);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.name_reg);
        position = findViewById(R.id.position_reg);
        age = findViewById(R.id.age_reg);
        num = findViewById(R.id.contact_reg);
        resadd = findViewById(R.id.add_reg);
        email = findViewById(R.id.email_reg);
        pass = findViewById(R.id.password_reg);




    }
    public void goToLoginActivity(){
        Intent gTLP = new Intent(this, Login.class);
        startActivity(gTLP);
    }

    private boolean validateInputs(String rname, String rposition,String rage, String rnum, String rresadd, String remail, String rpass) {
        if (rname.isEmpty()) {
            name.setError("Name required");
            name.requestFocus();
            return true;
        }

        if (rposition.isEmpty()) {
            position.setError("Position required");
            position.requestFocus();
            return true;
        }

        if (rage.isEmpty()) {
            age.setError("Age required");
            age.requestFocus();
            return true;
        }

        if (rnum.isEmpty()) {
            num.setError("Contact Number required");
            num.requestFocus();
            return true;
        }

        if (rresadd.isEmpty()) {
            resadd.setError("Address required");
            resadd.requestFocus();
            return true;
        }

        if (remail.isEmpty()) {
            email.setError("Enter Email");
            email.requestFocus();
            return true;
        }

        if (rpass.isEmpty()) {
            pass.setError("Enter Password");
            pass.requestFocus();
            return true;
        }
        return false;
    }
    int temp = 1;
    void setu()
    {
        temp = 0;
    }
    String email1;
    public void onClick(View v) {

        System.out.println("something");
        String xname = name.getText().toString().trim();
        String xposition = position.getText().toString().trim();
        String xage = age.getText().toString().trim();
        String xnum = num.getText().toString().trim();
        String xresadd = resadd.getText().toString().trim();
        String xemail = email.getText().toString().trim();
        email1 = xemail;
        String xpass = pass.getText().toString().trim();

        if (!validateInputs(xname, xposition,xage, xnum, xresadd, xemail, xpass)) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Registered Employees")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(Task<QuerySnapshot> task) {
                            String x3 = null, x1 = null, x2 = null,x4 = null;
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot x : task.getResult())
                                {
                                    System.out.println("SOMEGING");
                                    if(x.getString("email").equals(email1))
                                    {
                                        setu();
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
            if(xage.length() > 3)
            {
                Toast.makeText(this, "Enter Valid age", Toast.LENGTH_LONG).show();
            }
            int p = Integer.parseInt(xage);
            if(xname.length() < 6)
            {
                Toast.makeText(this, "UserName is to Small", Toast.LENGTH_LONG).show();
            }
            else if( p > 80 || p < 18 )
            {
                Toast.makeText(this, "Enter Valid age", Toast.LENGTH_LONG).show();
            }
            else if(xresadd.length() < 8 )
            {
                Toast.makeText(this, "Address is to Small", Toast.LENGTH_LONG).show();
            }
            else if(xpass.length() < 8 )
            {
                Toast.makeText(this, "Password is to Small", Toast.LENGTH_LONG).show();
            }
            else if(xnum.length() < 8)
            {
                Toast.makeText(this, "Enter Valid Number", Toast.LENGTH_LONG).show();
            }
            else if(android.util.Patterns.EMAIL_ADDRESS.matcher(xemail).matches() == false)
            {
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_LONG).show();
            }
            else if(temp == 0 )
            {
                Toast.makeText(this, "Email Already Exists", Toast.LENGTH_LONG).show();
                temp = 1;
            }
            else {

                CollectionReference dbUser = db.collection("Registered Employees");
                RegisteredEmployee rE = new RegisteredEmployee(xname, xposition, xresadd, xemail, xpass, xage, xnum);
                dbUser.add(rE)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(CreateAccount.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                goToLoginActivity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateAccount.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


            }

        }


    }
}
