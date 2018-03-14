package mhowat1.nait.ca.dmit2504lab02;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ItemViewActivity extends BaseActivity implements View.OnClickListener {

    int listIDFK;
    SQLiteDatabase db;
    DBManager dbManager;
    ListView listView;
    ToDoListViewCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        listIDFK = getIntent().getExtras().getInt("LISTFK");
        dbManager = new DBManager(this);


        Button addBtn = (Button) findViewById(R.id.item_activity_add_button);
        Button updateBtn = (Button) findViewById(R.id.item_activity_update_button);
        Button deleteBtn = (Button) findViewById(R.id.item_activity_delete_button);
        Button archiveBtn = (Button) findViewById(R.id.item_activity_archive_button);
        listView = (ListView) findViewById(R.id.item_activity_list);
        addBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        archiveBtn.setOnClickListener(this);

        refreshList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.item_activity_add_button:
            {
                addNewToDoItem();
                break;
            }
            case R.id.item_activity_update_button:
            {
                break;
            }
            case R.id.item_activity_delete_button:
            {
                break;

            }
            case R.id.item_activity_archive_button:
            {
                break;

            }
        }

    }

    private void addNewToDoItem() {
        EditText toDoNameEditText = (EditText)findViewById(R.id.item_activity_edit_name);
        EditText toDoDescriptionEditText = (EditText)findViewById(R.id.item_activity_edit_description);
        String toDoName = toDoNameEditText.getText().toString();
        String toDoDescription = toDoDescriptionEditText.getText().toString();

        if ((toDoName.trim().equals("")) || (toDoDescription.trim().equals("")))
        {
            Toast.makeText(this, "Error: Must enter a name and description", Toast.LENGTH_LONG).show();
        }
        else
        {
            //5.	There will be a view that allows the addition of a new item. Each item will contain a description, association with a title, created date (as string if desired) and completed flag.
            try {
                ContentValues values = new ContentValues();
                values.put(DBManager.C_ITEMLISTFK, listIDFK);
                values.put(DBManager.C_ITEMNAME, toDoName);
                values.put(DBManager.C_ITEMDESCRIPTION, toDoDescription);
                values.put(DBManager.C_ITEMDATE, String.valueOf(new Date().getTime()));
                db = dbManager.getWritableDatabase();
                db.insertOrThrow(DBManager.ITEM_TABLE, null, values);
                toDoNameEditText.setText("");
                toDoDescriptionEditText.setText("");
                db.close();


            }catch (SQLException e){

            }


        }
        refreshList();

    }

    //6.	There will be a view that displays all active items (within a list) and allows them to be selected for editing (update, delete and archive)
    private void refreshList() {
        db = dbManager.getReadableDatabase();
        try{

            Cursor cursor = db.query(DBManager.ITEM_TABLE,
                    null,
                    String.valueOf(listIDFK),
                    null,
                    null,
                    null,
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





    //7.	Archived items will be deleted from the local database and stored on the remote server. Each archived item will contain a username, password, content, list title, created date and completed flag (0 for false, 1 for true).  All will be posted as Strings.  The username and password will be unique on the remote server.  Creating a new username and/or password will effectively create a new account on the remote server.  The keys for the post parameters will be “LIST_TITLE, CONTENT, COMPLETED_FLAG, ALIAS, PASSWORD and CREATED_DATE”.

    //8.	There will be a view that displays all of the archived items.  Use a query string with the following format: http://www.youcode.ca/Lab02Get.jsp?ALIAS=username&PASSWORD=password.  The data will be returned as four strings per item in the order of “Posted Date, List Title, Content, and the Completed Flag as a 1 or 0.

    //9.	Implement preferences to store your username, password, two colors (radio list) and a font size. Be sure to provide a default values.

    //10.	Implement an interface and menu to provide an intuitive user experience.

}



