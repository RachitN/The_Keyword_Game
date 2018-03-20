package com.example.rachit.hackathon;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class Topic_Choose extends AppCompatActivity implements View.OnClickListener {
    TextView Profile,keyword1;
    protected static final int RESULT_SPEECH = 1;
    Intent i;
    Button LogOut;
    DatabaseReference ref;
    String name;
    Spinner topic;
    ListView listview_keyword;
    List<Topic> keyword_list;
    ImageButton t1,t2,t3;
    String speech;
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic__choose);
        Profile =  findViewById(R.id.Profile);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        keyword_list = new ArrayList<>();
        topic = findViewById(R.id.topic);
        listview_keyword = findViewById(R.id.keyword);
        i = getIntent();
        name = i.getStringExtra("Name");
        Profile.setText("Hello " + name);
        LogOut =  findViewById(R.id.LogOut);
        LogOut.setOnClickListener(this);


      topic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
              String item = String.valueOf(parent.getItemAtPosition(pos));
              if(pos!=0) {
                  listview_keyword.setVisibility(View.VISIBLE);
                  Toast.makeText(getApplicationContext(), "It worked", Toast.LENGTH_LONG).show();
                  String res = "R.array."+item;
                  int resourseid = getResources().getIdentifier(item,"array",Topic_Choose.this.getPackageName());
                   items= getResources().getStringArray(resourseid);
                  @SuppressLint("ResourceType") ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                          android.R.layout.simple_list_item_1, items);
                  listview_keyword.setAdapter(adapter);
              }
              else
              {
                  listview_keyword.setVisibility(View.INVISIBLE);
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });

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


    public void speech(View view) {
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        try {
            startActivityForResult(intent, RESULT_SPEECH);
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Opps! Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                     speech = text.get(0);
                    String[] words= speech.split("\\s+");

                     for (int i = 0;i<items.length;i++)
                     {   int y=0;
                         String[] it = items[i].split("_");
                        for (int j=0;j<it.length;j++)
                        {
                            for(int k=0;k<words.length;k++) {
                               String word = words[k].substring(0,1)+words[k].substring(1);
                                if (it[j] == word ){
                                 y=y+1;

                                }
                            }
                        }
                        if(y==it.length)
                        {
                            t1.setVisibility(View.VISIBLE);
                        }

                     }
                }
                break;
            }

        }
    }
}





