package mhowat1.nait.ca.dmit2504lab02;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import java.util.concurrent.DelayQueue;

/**
 * Created by Matthew on 2018-03-12.
 */

public class ToDoListViewCursorAdapter extends SimpleCursorAdapter {

    static final String[] FROM = {
            DBManager.C_ITEMNAME,
            DBManager.C_ITEMDESCRIPTION,
            DBManager.C_ITEMDATE};
    static final int[] TO ={
            R.id.todo_item_name,
            R.id.todo_item_description,
            R.id.todo_item_date
    };


    //Use this class to insert values into a custom to-do list object on the first page

    public ToDoListViewCursorAdapter(Context context, Cursor cursor) {
        super(context, R.layout.to_do_item_row, cursor, FROM, TO);
    }

    @Override
    public void bindView(View row, Context context, Cursor cursor) {
        super.bindView(row, context, cursor);
        /*
        String strDate = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMDATE));
        String strShort = strDate.substring(7,17);
      */  TextView textView = (TextView)row.findViewById(R.id.todo_item_date); //the position in the cell for date
       // textView.setText(strShort);

    }
}