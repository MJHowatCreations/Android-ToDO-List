package mhowat1.nait.ca.dmit2504lab02;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private static final String TAG = "MainActivity";
    SQLiteDatabase db;
    List<String> lists;
    Spinner listSpinner;
    DBManager dbManager;
    ListView listView;
    ToDoListViewCursorAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listSpinner = (Spinner) findViewById(R.id.main_activity_spinner);
        listView = (ListView)findViewById(R.id.todo_item_list_view);

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
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lists);
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
                startActivity(new Intent(this, ListView.class));


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
                newListEditText.setText("");
                db.close();

            } catch (SQLException e) {
                Log.d(TAG, "duplicate record");

            }
        }
        loadSpinnerData();
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        db = dbManager.getReadableDatabase();
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);
        try{
            Cursor cursor = db.query(DBManager.ITEM_TABLE,
                    null,
                    null,
                    null,
                    DBManager.C_ITEMLISTFK,
                    DBManager.C_ITEMLISTFK + "=" + i,
                    DBManager.C_ITEMID + " DESC");
            startManagingCursor(cursor);
            adapter = new ToDoListViewCursorAdapter(this, cursor);
            listView.setAdapter(adapter);




            cursor.close();

        }
        catch(SQLException e){

        }


        db.close();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
