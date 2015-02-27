package es.tessier.mememaker.database;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static es.tessier.mememaker.database.MemeContract.AnnotationEntry;
import static es.tessier.mememaker.database.MemeContract.MemesEntry;


public class MemeSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "memes.db";
    private static final int DB_VERSION = 1;
    private static final String TAG = MemeSQLiteHelper.class.getName();

    private static final String CREATE_TABLE_MEMES =
            "CREATE TABLE " + MemesEntry.MEMES_TABLE + " ( " +
                    MemesEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MemesEntry.COLUMN_ASSET + " TEXT NOT NULL, " +
                    MemesEntry.COLUMN_NAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_ANNOTATIONS =
            "CREATE TABLE " + AnnotationEntry.ANNOTATION_TABLE + " ( " +
                    AnnotationEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AnnotationEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                    AnnotationEntry.COLUMN_X + " INTEGER NOT NULL, " +
                    AnnotationEntry.COLUMN_Y + " INTEGER NOT NULL, " +
                    AnnotationEntry.COLUMN_COLOR + " INTEGER NOT NULL, " +
                    AnnotationEntry.COLUMN_MEME_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + AnnotationEntry.COLUMN_MEME_ID + ") REFERENCES " +
                    MemesEntry.MEMES_TABLE + " (" + MemesEntry.COLUMN_ID + ") );";

    public MemeSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Meme Table functionality

    //Meme Table Annotations functionality

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_MEMES);
            db.execSQL(CREATE_TABLE_ANNOTATIONS);
        } catch(SQLException e) {
            Log.e(TAG, "Android SQLException caught " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);

    }


}
