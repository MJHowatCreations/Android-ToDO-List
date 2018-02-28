package mhowat1.nait.ca.dmit2504lab02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by mhowat1 on 2/28/2018.
 */

public class DBManager extends SQLiteOpenHelper {

    static final String TAG = "DBManager";
    static final String DB_NAME = "chatter.db";
    static private final int DB_VERSION = 1;
    static final String LISTITEM_TABLE = "listitem";
    static final String C_ID = BaseColumns._ID;
    static final String C_DATE = "postDate";
    static final String C_SENDER = "sender";
    static final String C_DATA = "data";


    public DBManager(Context context){super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase database) {
        String sql = "create table " + TABLE + " (" + C_ID + " int primary key, "
                + C_DATE + " text, " + C_SENDER + " text, " + C_DATA + " text)";

        Log.d(TAG,sql);

        database.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("drop table if exists " + TABLE); // drops the old database
        Log.d(TAG, "onUpdated");
        onCreate(database);

    }
}
