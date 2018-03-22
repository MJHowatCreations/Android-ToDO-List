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
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 * Created by Matthew on 2018-03-12.
 */

public class ToDoListViewCursorAdapter extends CursorAdapter {

    public static List<ToDoItem> toDoItemsList = new ArrayList<>();
    private LayoutInflater cursorInflater;
    CheckBox checkBox;



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
/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        checkBox = (CheckBox)convertView.findViewById(R.id.todo_checkbox);
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(getToDoItem(position).isChecked());
        checkBox.setTag(Integer.valueOf(position));
        checkBox.setOnCheckedChangeListener(listener);
        Toast.makeText(convertView.getContext(), position + "box has been checked", Toast.LENGTH_SHORT).show();
        return convertView;
    }
*/

    @Override
    public void bindView(View row, Context context, final Cursor cursor) {
        checkBox = (CheckBox)row.findViewById(R.id.todo_checkbox);


        int itemID = cursor.getInt(cursor.getColumnIndex(DBManager.C_ITEMID));
        int listID_FK = cursor.getInt(cursor.getColumnIndex(DBManager.C_ITEMLISTFK));
        String name = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMNAME));
        String  description = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMDESCRIPTION));
        int completed = cursor.getInt(cursor.getColumnIndex(DBManager.C_ITEMDONE));
        String  date = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMDATE));
        ToDoItem item = new ToDoItem(itemID,listID_FK,name,description,date,completed);
        toDoItemsList.add(item);
        int index = toDoItemsList.indexOf(item);
        checkBox.setChecked(getToDoItem(index).isChecked());
        checkBox.setTag(Integer.valueOf(index));
        checkBox.setOnCheckedChangeListener(listener);




        TextView textViewItemName = (TextView) row.findViewById(R.id.todo_item_name);
        TextView textViewDescription = (TextView) row.findViewById(R.id.todo_item_description);
        TextView textViewDate = (TextView) row.findViewById(R.id.todo_item_date);
        if (completed == 1){
            row.setBackgroundColor(Color.GREEN);
        }

        textViewItemName.setText(name);
        textViewDescription.setText(description);
        textViewDate.setText(date);

/*        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                //toDoItemsList.get((Integer)compoundButton.getTag()).setChecked(b);
            }
        });

        final int position = row.getId();
        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), position + "box has been checked", Toast.LENGTH_LONG).show();

                return false;
            }
        });*/


    }

    public int getItemCount()
    {
        return toDoItemsList.size();
    }


    public ToDoItem getToDoItem(int pos)
    {
        return toDoItemsList.get(pos);
    }
    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b)
        {
            toDoItemsList.get((Integer)compoundButton.getTag()).setChecked(b);
        }
    };



}