package mhowat1.nait.ca.dmit2504lab02;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 * Created by Matthew on 2018-03-12.
 */

public class ToDoListViewCursorAdapter extends CursorAdapter {

    public List<ToDoItem> toDoItemsList = new ArrayList<>();
    private LayoutInflater cursorInflater;
    int itemID;
    int listID_FK;
    String name;
    String description;
    String date;
    int completed;



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
    public void bindView(View row, Context context, final Cursor cursor) {


        itemID = cursor.getInt(cursor.getColumnIndex(DBManager.C_ITEMID));
        listID_FK = cursor.getInt(cursor.getColumnIndex(DBManager.C_ITEMLISTFK));
            name = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMNAME));
            description = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMDESCRIPTION));
            completed = cursor.getInt(cursor.getColumnIndex(DBManager.C_ITEMDONE));
            date = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMDATE));
             toDoItemsList.add(new ToDoItem(itemID,listID_FK,name,description,date,completed));


        TextView textViewItemName = (TextView) row.findViewById(R.id.todo_item_name);
        TextView textViewDescription = (TextView) row.findViewById(R.id.todo_item_description);
        TextView textViewDate = (TextView) row.findViewById(R.id.todo_item_date);

        if (completed == 1){
            row.setBackgroundColor(Color.GREEN);
        }




        textViewItemName.setText(name);
        textViewDescription.setText(description);
        textViewDate.setText(date);

    }


}