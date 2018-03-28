package mhowat1.nait.ca.dmit2504lab02;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PopUpWindow extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase db;
    DBManager dbManager;
    int itemID;
    int selectedObjectPK = 0;
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);
        itemID = getIntent().getExtras().getInt("ITEMPK");
        dbManager = new DBManager(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.7),(int)(height*0.4));
        Button editButton = (Button) findViewById(R.id.edit_activity_edit_button);
        headerText = (TextView) findViewById(R.id.edit_activity_header);
        editButton.setOnClickListener(this);
        queryHeaderName();
    }
    private void queryHeaderName() {
        db = dbManager.getReadableDatabase();
        try {
            Cursor cursor = db.query(DBManager.ITEM_TABLE,
                    new String[] {DBManager.C_ITEMNAME, DBManager.C_ITEMLISTFK},
                    DBManager.C_ITEMID + " = " + itemID,
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null)
                cursor.moveToFirst();
            headerText.setText(cursor.getString(0));
            selectedObjectPK = cursor.getInt(1);

        }
        catch (SQLException ex)
        {
            Toast.makeText(this, "Error" + ex, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        db = dbManager.getReadableDatabase();
        EditText toDoNameEditText = (EditText)findViewById(R.id.edit_activity_edit_name);
        EditText toDoDescriptionEditText = (EditText)findViewById(R.id.edit_activity_edit_description);
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
            values.put(DBManager.C_ITEMNAME, toDoName);
            values.put(DBManager.C_ITEMDESCRIPTION, toDoDescription);
            values.put(DBManager.C_ITEMDATE, toDoDate);
            db = dbManager.getWritableDatabase();

            try {

                db.update(DBManager.ITEM_TABLE, values, DBManager.C_ITEMID + " = " + itemID, null);



            }catch (SQLException e){
                Toast.makeText(this, "Error:" + e, Toast.LENGTH_LONG).show();

            }
            toDoNameEditText.setText("");
            toDoDescriptionEditText.setText("");

            Intent myIntent = new Intent(this, ItemViewActivity.class);
            myIntent.putExtra("LISTFK", selectedObjectPK);
            this.startActivity(myIntent);
        }

    }
}
