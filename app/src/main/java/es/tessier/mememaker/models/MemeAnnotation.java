package es.tessier.mememaker.models;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

import static es.tessier.mememaker.database.MemeContract.AnnotationEntry;

/**
 * Created by Carlos Tessier on 30/12/14.
 */
public class MemeAnnotation implements Serializable {
    private int mId = -1;
    private int mMemeId = -1;
    private String mColor;
    private String mTitle;
    private int mLocationX;
    private int mLocationY;

    public MemeAnnotation() {
        this(-1, -1, "", "", 0, 0);
    }

    public MemeAnnotation(int id, int memeId, String color, String title, int locationX, int locationY) {
        mId = id;
        mMemeId = memeId;
        mColor = color;
        mTitle = title;
        mLocationX = locationX;
        mLocationY = locationY;
    }

    public MemeAnnotation(Cursor cursor) {
        mId = getIntegerValue(cursor, AnnotationEntry.COLUMN_ID);
        mMemeId = getIntegerValue(cursor, AnnotationEntry.COLUMN_MEME_ID);
        mColor = getStringValue(cursor, AnnotationEntry.COLUMN_COLOR);
        mTitle = getStringValue(cursor, AnnotationEntry.COLUMN_TITLE);
        mLocationX = getIntegerValue(cursor, AnnotationEntry.COLUMN_X);
        mLocationY = getIntegerValue(cursor, AnnotationEntry.COLUMN_Y);
    }

    public int getId() {
        return mId;
    }

    public int getMemeId() {
        return mMemeId;
    }

    public void setMemeId(int memeId) {
        this.mMemeId = memeId;
    }

    public boolean hasBeenSaved() {
        return (getId() != -1);
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String text) {
        mTitle = text;
    }

    public int getLocationX() {
        return mLocationX;
    }

    public void setLocationX(int x) {
        mLocationX = x;
    }

    public int getLocationY() {
        return mLocationY;
    }

    public void setLocationY(int y) {
        mLocationY = y;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        long id = getId();
        if (id > 0)
            cv.put(AnnotationEntry.COLUMN_ID, id);
        cv.put(AnnotationEntry.COLUMN_TITLE, getTitle());
        cv.put(AnnotationEntry.COLUMN_COLOR, getColor());
        cv.put(AnnotationEntry.COLUMN_X, getLocationX());
        cv.put(AnnotationEntry.COLUMN_Y, getLocationY());
        cv.put(AnnotationEntry.COLUMN_MEME_ID, getMemeId());
        return cv;
    }

    public void setId(long id) {
        this.mId = (int) id;
    }

    public static String getStringValue(Cursor cursor, String colName) {
        int colIndex = cursor.getColumnIndex(colName);
        return colIndex >= 0 ? cursor.getString(colIndex) : "";
    }

    public static int getIntegerValue(Cursor cursor, String colName) {
        int colIndex = cursor.getColumnIndex(colName);
        return colIndex >= 0 ? cursor.getInt(colIndex) : -1;
    }
}

