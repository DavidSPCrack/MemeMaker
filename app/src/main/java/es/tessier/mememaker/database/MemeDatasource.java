package es.tessier.mememaker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import es.tessier.mememaker.models.Meme;
import es.tessier.mememaker.models.MemeAnnotation;

import static es.tessier.mememaker.database.MemeContract.AnnotationEntry;
import static es.tessier.mememaker.database.MemeContract.MemesEntry;


public class MemeDatasource {

    private Context mContext;
    private MemeSQLiteHelper mMemeSQLiteHelper;

    public MemeDatasource(Context context) {
        mContext = context;
        mMemeSQLiteHelper = new MemeSQLiteHelper(mContext);
    }

    public ArrayList<Meme> read() {
        ArrayList<Meme> memes = new ArrayList<>();
        SQLiteDatabase db = openReadable();
        Cursor cursorMeme = db.query(MemesEntry.MEMES_TABLE, MemesEntry.ALL_COLUMNS, null, null, null, null, null);
        if (cursorMeme.moveToFirst()) {
            do {
                Meme meme = new Meme(cursorMeme);
                ArrayList<MemeAnnotation> annotations = new ArrayList<>();
                String sqlAnnot = getSqlAnnotations(meme.getId());
                Cursor cursorAnnotation = db.rawQuery(sqlAnnot, null);
                if (cursorAnnotation.moveToFirst()) {
                    do {
                        MemeAnnotation annotation = new MemeAnnotation(cursorAnnotation);
                        annotations.add(annotation);
                    } while (cursorAnnotation.moveToNext());
                }
                meme.setAnnotations(annotations);
                memes.add(meme);
            } while (cursorMeme.moveToNext());
        }

        close(db);

        return memes;
    }

    public void create(Meme meme) {
        SQLiteDatabase db = openWritable();
        db.beginTransaction();

        ContentValues memeValues = meme.getContentValues();
        long id = db.insert(MemesEntry.MEMES_TABLE, null, memeValues);
        if (id > 0) {
            meme.setId(id);
            ArrayList<MemeAnnotation> annotations = meme.getAnnotations();
            for (MemeAnnotation annotation : annotations) {
                ContentValues memeAnnotationValues = annotation.getContentValues();
                long idAnnot = db.insert(AnnotationEntry.ANNOTATION_TABLE, null, memeAnnotationValues);
                annotation.setId(idAnnot);
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
    }

    public SQLiteDatabase openReadable() {
        SQLiteDatabase db = mMemeSQLiteHelper.getReadableDatabase();
        return db;
    }

    public SQLiteDatabase openWritable() {
        SQLiteDatabase db = mMemeSQLiteHelper.getWritableDatabase();
        return db;
    }

    public void close(SQLiteDatabase db) {
        db.close();
    }

    private static final String getSqlAnnotations(int id) {
        String sql = "SELECT * FROM " + AnnotationEntry.ANNOTATION_TABLE +
                " WHERE " + AnnotationEntry.COLUMN_MEME_ID + " = " + id + ";";
        return sql;
    }
}
