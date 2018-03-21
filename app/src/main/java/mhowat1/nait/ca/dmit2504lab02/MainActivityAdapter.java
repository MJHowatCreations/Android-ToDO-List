package mhowat1.nait.ca.dmit2504lab02;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Matthew on 2018-03-20.
 */

public class MainActivityAdapter extends ToDoListViewCursorAdapter {
    private LayoutInflater cursorInflater;

    public MainActivityAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.main_activity_row, viewGroup, false);
    }


    @Override
    public void bindView(View row, Context context, Cursor cursor) {

        String mainName = cursor.getString(cursor.getColumnIndex(DBManager.C_ITEMNAME));
        TextView textViewItemName = (TextView) row.findViewById(R.id.todo_item_name);

        textViewItemName.setText(mainName);

    }
}
