package com.example.admin.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {



    EditText logEmail;
    EditText logPass;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        logEmail = findViewById(R.id.email_login);
        logPass = findViewById(R.id.password_login);
    }

    String getEmail(){
        return logEmail.getText().toString();
    }

    String getPass(){
        return logPass.getText().toString();
    }

    void open(View view)
    {
        Intent p = new Intent(this,mainmenu.class);
        startActivity(p);
    }
    void open1(View view)
    {
        Intent p = new Intent(this,CreateAccount.class);
        startActivity(p);
    }

    void confirmRet(int x){
        if (x == 1){
            Intent accV = new Intent(this, mainmenu.class);
            data.manager = 0;
            data.email = getEmail();
            Toast.makeText(this, "Logged IN!", Toast.LENGTH_LONG).show();
            startActivity(accV);
        }
        else {
            Toast.makeText(this, "Incorrect Email or Password", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateInputs(String lemail, String lpass) {
        if (lemail.isEmpty()) {
            logEmail.setError("Enter email");
            logEmail.requestFocus();
            return true;
        }

        if (lpass.isEmpty()) {
            logPass.setError("Enter password");
            logPass.requestFocus();
            return true;
        }
        return false;
    }

    public void loginActivity(View v){
        String em = logEmail.getText().toString();
        String ps = logPass.getText().toString();

        if(em.equals("manager") && ps.equals("lotus"))
        {
            Toast.makeText(this, "Logged IN!", Toast.LENGTH_LONG).show();
            Intent accV = new Intent(this, managermenu.class);
            data o = new data();
            o.setManager(1);
            startActivity(accV);
            return;
        }

        if(!validateInputs(em, ps)){
            db.collection("Registered Employees")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            String e1 = null, p1 = null, x1 = null;
                            x1 = getEmail();
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot logEmail : task.getResult()){
                                    e1 = logEmail.getString("email");
                                    p1 = logEmail.getString("password");

                                    if (e1.equals(x1)){
                                        break;
                                    }
                                    e1 = null;
                                }
                                if (p1.equals(getPass()) && e1 != null){
                                    LoginData logD = new LoginData();
                                    logD.setEmail(e1);
                                    confirmRet(1);

                                }
                                else {
                                    confirmRet(0);

                                }
                            }
                            else {
                                System.out.println("Eorror!");
                            }
                        }
                    });


        }
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

        Intent setIntent = new Intent(this,MainActivity.class);
        startActivity(setIntent);
    }

}
