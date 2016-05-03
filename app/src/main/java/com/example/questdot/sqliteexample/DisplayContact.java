package com.example.questdot.sqliteexample;

/**
 * Created by QuestDot on 2/5/2016.
 */
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    TextView name ;
    TextView phone;
    TextView email;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        name = (TextView) findViewById(R.id.editTextName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        email = (TextView) findViewById(R.id.editTextEmail);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));


                if (!rs.isClosed())
                {
                    rs.close();
                }
                name.setText((CharSequence)nam);
                phone.setText((CharSequence)phon);
                email.setText((CharSequence)emai);



            }
        }
         }


 public void edit(View view) {
     Bundle extras = getIntent().getExtras();
     if (extras != null) {
         int Value = extras.getInt("id");
         if (Value > 0) {
             if (mydb.updateContact(id_To_Update, name.getText().toString(), phone.getText().toString(), email.getText().toString())) {
                 Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                 startActivity(intent);
             } else {
                 Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
             }
         } else {
             if (mydb.insertContact(name.getText().toString(), phone.getText().toString(), email.getText().toString())) {
                 Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
             } else {
                 Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
             }
             Intent intent = new Intent(getApplicationContext(), MainActivity.class);
             startActivity(intent);
         }
     }
 }
    public void delete(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteContact)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      //  int Value = extras.getInt("id");
                        mydb.deleteContact(id_To_Update);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog d = builder.create();
        d.setTitle("Are you sure");
        d.show();
    }


}