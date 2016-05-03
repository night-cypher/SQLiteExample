package com.example.questdot.sqliteexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd=(Button)findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(this);

        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllCotacts();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        final ArrayList array_list2 = mydb.getid();

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub

                    int id_To_Search = Integer.parseInt(array_list2.get(arg2).toString());
                   // Toast.makeText(getApplicationContext(),""+id_To_Search,Toast.LENGTH_LONG).show();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", id_To_Search);

                    Intent intent = new Intent(getApplicationContext(),DisplayContact.class);

                    intent.putExtras(dataBundle);
                    startActivity(intent);



            }
        });
    }


     @Override
    public void onClick(View v) {
        if (v.equals(btnAdd)){
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", 0);

            Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
            intent.putExtras(dataBundle);

            startActivity(intent);
        }
    }

}