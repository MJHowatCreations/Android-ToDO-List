package mhowat1.nait.ca.dmit2504lab02;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private static final String TAG = "MainActivity";
    SQLiteDatabase db;
    List<String> lists;
    Spinner listSpinner;
    DBManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listSpinner = (Spinner) findViewById(R.id.main_activity_spinner);

        Button btnAdd = (Button) findViewById(R.id.main_activity_add_button);
        Button btnEdit = (Button) findViewById(R.id.main_activity_edit_button);

        dbManager = new DBManager(this);

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        listSpinner.setOnItemSelectedListener(this);
        loadSpinnerData();

    }



    private void loadSpinnerData() {
        try {
            lists = dbManager.getAllLists();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lists.);
            listSpinner.setAdapter(dataAdapter);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error:" + e, Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void onClick(View view){

        switch(view.getId())
        {
            case R.id.main_activity_add_button:
            {
                addNewListItem();
                break;
            }
            case R.id.main_activity_edit_button:
            {

                break;
            }
        }

    }


    private void addNewListItem() {
        EditText newListEditText = (EditText)findViewById(R.id.main_activity_edit_text);
        String newListName = newListEditText.getText().toString();
        if (newListName.trim() == "" )
        {
            Log.d(TAG, "new list name is empty");
            Toast.makeText(this, "Error: Must have a longer name for list", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                ContentValues values = new ContentValues();
                values.put(DBManager.C_LISTNAME, newListName);
                db = dbManager.getWritableDatabase();
                db.insertOrThrow(DBManager.LIST_TABLE, null, values);
                Log.d(TAG, "record inserted");
                db.close();

            } catch (SQLException e) {
                Log.d(TAG, "duplicate record");

            }
        }
        loadSpinnerData();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
