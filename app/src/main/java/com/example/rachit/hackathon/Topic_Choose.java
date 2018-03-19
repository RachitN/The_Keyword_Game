package com.example.rachit.hackathon;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Topic_Choose extends AppCompatActivity implements View.OnClickListener {
    TextView Profile,age;
    Intent i;
    Button LogOut, update,date;
    EditText Moileno,tdate;
    Spinner Gender;
    DatabaseReference ref;
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic__choose);
        ref = FirebaseDatabase.getInstance().getReference("Information");
        Profile = (TextView) findViewById(R.id.Profile);
        i = getIntent();
        name = i.getStringExtra("Name");
        Profile.setText("Hello " + name);
        LogOut = (Button) findViewById(R.id.LogOut);
        update.setOnClickListener(this);
        LogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)   //onclick for log out button
    {
        if (view == LogOut) {
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            fAuth.signOut();    //to get log out from profile page
            i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);  //to go back to login page after log out
            finish();
        }

    }
}



