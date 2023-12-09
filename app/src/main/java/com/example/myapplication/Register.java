package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Register extends AppCompatActivity {

    TextView alreadyHaveaccount;
    EditText InputEmail,inputPassword,inputConfirmPassword;
    Button btnregister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mauth;
    FirebaseUser muser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        alreadyHaveaccount=findViewById(R.id.alreadyHaveaccount);
        InputEmail=findViewById(R.id.InputEmail);
        inputPassword=findViewById(R.id.Inputpassword);
        inputConfirmPassword=findViewById(R.id.inputconfirmpassword);
        btnregister=findViewById(R.id.btnregister);
        progressDialog=new ProgressDialog(this);

        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();




        alreadyHaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,MainActivity.class));
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }

            private void PerformAuth() {
                String email=InputEmail.getText().toString();
                String password=inputPassword.getText().toString();
                String confirmpassword=inputConfirmPassword.getText().toString();
                if (!email.matches(emailPattern)){
                    InputEmail.setError("Enter Correct Email");
                }
                else if(password.isEmpty() || password.length()<6){
                    inputPassword.setError("Enter proper password");
                }
                else if (!password.equals(confirmpassword)){
                    inputConfirmPassword.setError("Enter same password");
                }
                else{
                    progressDialog.setMessage("Wait while registration..");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                sendUserToNextActivity();
                                Toast.makeText(Register.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "etask.getException()", Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void sendUserToNextActivity() {

                            Intent intent= new Intent(Register.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });



                }

            }
        });


    }
}