package mhowat1.nait.ca.dmit2504lab02;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import java.util.concurrent.DelayQueue;

/**
 * Created by Matthew on 2018-03-12.
 */

public class ToDoListViewCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;



    //Use this class to insert values into a custom to-do list object on the first page

    public ToDoListViewCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.to_do_item_row, viewGroup, false);
    }

    @Override
    public void bindView(View row, Context context, Cursor cursor) {
        TextView textViewItemName = (TextView) row.findViewById(R.id.todo_item_name);
        TextView textViewDescription = (TextView) row.findViewById(R.id.todo_item_description);
        TextView textViewDate = (TextView) row.findViewById(R.id.todo_item_date);
        String itemName = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMNAME));
        String description = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMDESCRIPTION));
        String date = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMDATE));

        textViewItemName.setText(itemName);
        textViewDescription.setText(description);
        textViewDate.setText(date);
    }


}