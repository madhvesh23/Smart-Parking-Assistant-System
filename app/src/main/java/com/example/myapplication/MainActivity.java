package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView Createnewaccount;

    EditText InputEmail,inputPassword;
    Button btnLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mauth;
    FirebaseUser muser;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Createnewaccount = findViewById(R.id.Createnewaccount);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InputEmail=findViewById(R.id.InputEmail);
        inputPassword=findViewById(R.id.Inputpassword);
        btnLogin=findViewById(R.id.btnlogin);
        progressDialog=new ProgressDialog(this);

        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();


        Createnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }

            private void performLogin() {
                String email=InputEmail.getText().toString();
                String password=inputPassword.getText().toString();

                if (!email.matches(emailPattern)){
                    InputEmail.setError("Enter Correct Email");
                }
                else if(password.isEmpty() || password.length()<6){
                    inputPassword.setError("Enter proper password");
                }

                else{
                    progressDialog.setMessage("Wait while login..");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           progressDialog.dismiss();
                           sendUserToNextActivity();

                           Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                       }
                       else{
                           progressDialog.dismiss();
                           Toast.makeText(MainActivity.this, "etask.getException()", Toast.LENGTH_SHORT).show();
                       }
                        }
                        });
                    }
            }
        });

    }
    private void sendUserToNextActivity() {

        Intent intent= new Intent(MainActivity.this,pkg.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        MainActivity.this.startActivity(intent);
    }
}