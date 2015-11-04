/**
 * Created by VS on 11/4/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class sqlite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDB.db";
    public static final String CONTACTS_TABLE_NAME = "events";
    public static final String CONTACTS_COLUMN_DATE = "date";
    public static final String CONTACTS_COLUMN_DESCRIPTION = "description";

    public sqlite(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table events " +
                        "(date text primary key, description text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }

    public boolean insertEvent  (String date, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("description", description);
        db.insert("events", null, contentValues);
        return true;
    }

    public Cursor getData(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from events where date = "+date+"", null );
        return res;
    }

    public void deleteContact (String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("events",
                "date = ? ",
                new String[] { date });

    }



}
