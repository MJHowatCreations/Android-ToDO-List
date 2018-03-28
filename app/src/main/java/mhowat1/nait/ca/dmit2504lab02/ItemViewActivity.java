package mhowat1.nait.ca.dmit2504lab02;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ItemViewActivity extends BaseActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener, AdapterView.OnItemLongClickListener {

    TextView headerText;
    int listIDFK;
    SQLiteDatabase db;
    DBManager dbManager;
    ListView listView;
    ToDoListViewCursorAdapter adapter;
    String TAG = "Debugging";
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        listIDFK = getIntent().getExtras().getInt("LISTFK");
        dbManager = new DBManager(this);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        settings.registerOnSharedPreferenceChangeListener(this);

        headerText = (TextView) findViewById(R.id.item_activity_header);
        Button addBtn = (Button) findViewById(R.id.item_activity_add_button);
        Button completeBtn = (Button) findViewById(R.id.item_activity_complete_button);
        Button deleteBtn = (Button) findViewById(R.id.item_activity_delete_button);
        Button archiveBtn = (Button) findViewById(R.id.item_activity_archive_button);
        listView = (ListView) findViewById(R.id.item_activity_list);


        listView.setOnItemLongClickListener(this);
        addBtn.setOnClickListener(this);
        completeBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        archiveBtn.setOnClickListener(this);
        queryHeaderName();

        refreshList();
    }

    private void queryHeaderName() {
        db = dbManager.getReadableDatabase();
        try {
            Cursor cursor = db.query(DBManager.LIST_TABLE,
                    new String[] {DBManager.C_ITEMNAME},
                    DBManager.C_LISTID + " = " + listIDFK,
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null)
                cursor.moveToFirst();
            headerText.setText(cursor.getString(0));
        }
        finally {
            db.close();
        }

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
            case R.id.item_activity_complete_button:
            {
               markAsComplete();
                break;
            }
            case R.id.item_activity_delete_button:
            {
                deleteItems();
                break;

            }
            case R.id.item_activity_archive_button:
            {
                pd = ProgressDialog.show(this, "", "Archiving ToDo items to server...");
                new ToDoArchiver().execute();

                break;

            }
        }

    }
    private void deleteItems() {
        db = dbManager.getWritableDatabase();
        try {
            for (ToDoItem item : adapter.toDoItemsList) {
                if (item.isChecked() == true) {
                    db.delete(DBManager.ITEM_TABLE, DBManager.C_ITEMID + " = " + item.getItemID(), null);
                    item.setChecked(false);
                }
            }
            ;
        }
        catch (SQLException e) {
            Toast.makeText(this, "Error:" + e, Toast.LENGTH_LONG).show();
        }
        refreshList();

    }

    private void markAsComplete() {


        db = dbManager.getWritableDatabase();
        try {
            for (ToDoItem item: adapter.toDoItemsList) {
                if (item.isChecked() == true)
                {
                    ContentValues values = new ContentValues();
                    values.put(DBManager.C_ITEMDONE, 1);
                    db.update(DBManager.ITEM_TABLE, values, DBManager.C_ITEMID + " = " + item.getItemID(), null);
                    item.setChecked(false);
                }
            };
        }
        catch (SQLException e){
            Toast.makeText(this, "Error:" + e, Toast.LENGTH_LONG).show();
        }
        refreshList();

    }

    //7.	Archived items will be deleted from the local database and stored on the remote server. Each archived item will contain a username,
    // password, content, list title, created date and completed flag (0 for false, 1 for true).  All will be posted as Strings.  The username
    // and password will be unique on the remote server.  Creating a new username and/or password will effectively create a new account on the remote server.
    // The keys for the post parameters will be “LIST_TITLE, CONTENT, COMPLETED_FLAG, ALIAS, PASSWORD and CREATED_DATE”.

    private void addNewToDoItem() {
        EditText toDoNameEditText = (EditText)findViewById(R.id.item_activity_edit_name);
        EditText toDoDescriptionEditText = (EditText)findViewById(R.id.item_activity_edit_description);
        String toDoName = toDoNameEditText.getText().toString();
        String toDoDescription = toDoDescriptionEditText.getText().toString();
        Date datetime = Calendar.getInstance().getTime();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY");
        String toDoDate = formatDate.format(datetime);

        if ((toDoName.trim().equals("")) || (toDoDescription.trim().equals("")))
        {
            Toast.makeText(this, "Error: Must enter a name and description", Toast.LENGTH_LONG).show();
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put(DBManager.C_ITEMLISTFK, listIDFK);
            values.put(DBManager.C_ITEMNAME, toDoName);
            values.put(DBManager.C_ITEMDESCRIPTION, toDoDescription);
            values.put(DBManager.C_ITEMDATE, toDoDate);
            values.put(DBManager.C_ITEMDONE, 0);
            db = dbManager.getWritableDatabase();
            //5.	There will be a view that allows the addition of a new item. Each item will contain a description, association with a title,
            // created date (as string if desired) and completed flag.
            try {

                db.insertOrThrow(DBManager.ITEM_TABLE, null, values);

            }catch (SQLException e){
                Toast.makeText(this, "Error:" + e, Toast.LENGTH_LONG).show();

            }
                toDoNameEditText.setText("");
                toDoDescriptionEditText.setText("");
        }
        refreshList();

    }

    //6.	There will be a view that displays all active items (within a list) and allows them to be selected for editing (update, delete and archive)
    private void refreshList() {
            db = dbManager.getReadableDatabase();
            try{

                Cursor cursor = db.query(DBManager.ITEM_TABLE,
                        null,
                        DBManager.C_ITEMLISTFK + " = " + String.valueOf(listIDFK),
                        null,
                        null,
                        null,
                        DBManager.C_ITEMID + " DESC");
                    adapter = new ToDoListViewCursorAdapter(this, cursor, 0);
                    listView.setAdapter(adapter);

            }
            catch(SQLException e){
                Toast.makeText(this, "Error:" + e, Toast.LENGTH_LONG).show();

            }


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        refreshList();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        int itemPK = adapter.getToDoItem(i).itemID;
        Intent myIntent = new Intent(this, PopUpWindow.class);
        myIntent.putExtra("ITEMPK", itemPK);
        this.startActivity(myIntent);

        return false;
    }

    private class ToDoArchiver extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings) {



            String userName = settings.getString("username", "Matthew");
            String password = settings.getString("user_password", "password");
            String webServer = "http://www.youcode.ca/Lab02Post.jsp";
            try {
                for (ToDoItem item : adapter.toDoItemsList) {
                    if (item.isChecked()) {

                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(webServer);
                        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                        postParameters.add(new BasicNameValuePair("LIST_TITLE", item.getName()));
                        postParameters.add(new BasicNameValuePair("CONTENT", item.getDescription()));
                        postParameters.add(new BasicNameValuePair("COMPLETED_FLAG", String.valueOf(item.getCompleted())));
                        postParameters.add(new BasicNameValuePair("ALIAS", userName));
                        postParameters.add(new BasicNameValuePair("PASSWORD", password));
                        postParameters.add(new BasicNameValuePair("CREATED_DATE", item.getDate()));
                        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
                        post.setEntity(formEntity);
                        client.execute(post);
                    }
                }
                deleteItems();
            }
            catch(Exception e)
            {
                Toast.makeText(ItemViewActivity.this, "Error:" + e, Toast.LENGTH_LONG).show();
            }
            return null;
        }
        protected void onPostExecute(String s)
        {
            pd.dismiss();
        }
    }


    //8.	There will be a view that displays all of the archived items.  Use a query string with the following format: http://www.youcode.ca/Lab02Get.jsp?ALIAS=username&PASSWORD=password.  The data will be returned as four strings per item in the order of “Posted Date, List Title, Content, and the Completed Flag as a 1 or 0.

    //9.	Implement preferences to store your username, password, two colors (radio list) and a font size. Be sure to provide a default values.

    //10.	Implement an interface and menu to provide an intuitive user experience.

}



