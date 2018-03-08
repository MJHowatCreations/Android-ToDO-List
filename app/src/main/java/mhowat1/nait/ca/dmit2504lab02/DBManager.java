package mhowat1.nait.ca.dmit2504lab02;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhowat1 on 2/28/2018.
 */

public class DBManager extends SQLiteOpenHelper {

    static final String TAG = "DBManager";
    static final String DB_NAME = "todolist.db";
    static private final int DB_VERSION = 1;

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
    static final String C_ITEMARCHIEVE = "archieve";


    public DBManager(Context context){super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase database) {
        String listSql = "create table " + LIST_TABLE + " (" + C_LISTID + " int primary key autoincrement, "
                + C_LISTNAME + " text)";

        String itemSql = "create table " + ITEM_TABLE +
                " (" + C_ITEMID + " int primary key autoincrement, " +
                       C_ITEMLISTFK + " int, " +
                       C_ITEMNAME + " text, " +
                       C_ITEMDESCRIPTION + " text, " +
                       C_ITEMDATE + " text, " +
                       C_ITEMDONE + " text, " +
                       C_ITEMARCHIEVE + " text)";

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

    public List<String> getAllLists(){
        List<String> lists = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + LIST_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                lists.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lists;
    }
}
