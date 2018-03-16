package mhowat1.nait.ca.dmit2504lab02;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 * Created by mhowat1 on 2/28/2018.
 */

public class DBManager extends SQLiteOpenHelper {

    static final String TAG = "DBManager";
    static final String DB_NAME = "todolist.db";
    static private final int DB_VERSION = 2;


    //1.	The local SQLite database will contain a table of current ‘active’ items identified by ListName and a table or other method to maintain ListNames.
    static final String LIST_TABLE = "list";
    static final String C_LISTID = BaseColumns._ID;
    static final String C_LISTNAME = "name";

    static final String ITEM_TABLE = "item";
    static final String C_ITEMID = BaseColumns._ID;
    static final String C_ITEMLISTFK = "listID";
    static final String C_ITEMNAME = "name";
    static final String C_ITEMDESCRIPTION = "description";
    static final String C_ITEMDATE = "date";
    static final String C_ITEMDONE = "done";


    public DBManager(Context context){super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase database) {
        String listSql = "create table " + LIST_TABLE + " (" + C_LISTID + " integer primary key autoincrement, "
                + C_LISTNAME + " text)";

        String itemSql = "create table " + ITEM_TABLE +
                " (" + C_ITEMID + " integer primary key autoincrement, " +
                       C_ITEMLISTFK + " integer, " +
                       C_ITEMNAME + " text, " +
                       C_ITEMDESCRIPTION + " text, " +
                       C_ITEMDATE + " text, " +
                       C_ITEMDONE + " integer default 0)"  ;


        Log.d(TAG,listSql);
        Log.d(TAG,itemSql);

        database.execSQL(listSql);
        database.execSQL(itemSql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("drop table if exists " + LIST_TABLE); // drops the old database
        database.execSQL("drop table if exists " + ITEM_TABLE);
        Log.d(TAG, "onUpdated");
        onCreate(database);

    }

    public List<ToDoList> getAllLists() {
        List<ToDoList> lists = new ArrayList<ToDoList>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LIST_TABLE, null, null, null, null, null, C_LISTID + " ASC");
        if (cursor.moveToFirst()) {
            do {
                lists.add(new ToDoList(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("name"))));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lists;
    }
    //2.	The remote database will contain a table of historical archived items.
}
