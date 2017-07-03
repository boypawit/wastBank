package com.example.boyvi.wastbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText UserOrAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         UserOrAdmin = (EditText)this.findViewById(R.id.editText);



        Button buttonCountry = (Button) findViewById(R.id.Loginbutton);
        buttonCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ยังไม่ได้ส่งอะไรไป
                //User = UserOrAdmin.getText().toString();
               if(UserOrAdmin.getText().length()==4){
               // if(User==" user "){
                Intent intent = new Intent(Login.this,User_Main.class);
                startActivity(intent);
               }else if (UserOrAdmin.getText().length()==1){
                   String fake = "Invalid Username or Password";
                   Toast.makeText(Login.this,fake,Toast.LENGTH_LONG).show();

               }

               else{ Intent intent = new Intent(Login.this,Admin_Main.class);
                startActivity(intent);}

            }
        });
    }
}
