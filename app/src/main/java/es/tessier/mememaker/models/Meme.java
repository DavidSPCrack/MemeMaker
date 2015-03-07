package es.tessier.mememaker.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import static es.tessier.mememaker.database.MemeContract.MemesEntry;

/**
 * Created by Carlos Tessier on 30/12/14.
 *
 * @author david.sancho
 */
public class Meme implements Serializable {
    private int mId;
    private String mAssetLocation;
    private ArrayList<MemeAnnotation> mAnnotations;
    private String mName;

    public Meme() {
        this(-1, "", "", new ArrayList<MemeAnnotation>());
    }

    public Meme(int id, String assetLocation, String name, ArrayList<MemeAnnotation> annotations) {
        mId = id;
        mAssetLocation = assetLocation;
        mAnnotations = annotations == null ? new ArrayList<MemeAnnotation>() : annotations;
        mName = name;
    }

    public Meme(Cursor cursor) {
        mId = getIntegerValue(cursor, MemesEntry.COLUMN_ID);
        mAssetLocation = getStringValue(cursor, MemesEntry.COLUMN_ASSET);
        mName = getStringValue(cursor, MemesEntry.COLUMN_NAME);
        mAnnotations = new ArrayList<MemeAnnotation>();
    }

    public int getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = (int) id;
        ArrayList<MemeAnnotation> annotations = getAnnotations();
        for (MemeAnnotation annotation : annotations) {
            annotation.setMemeId(this.mId);
        }
    }

    public String getAssetLocation() {
        return mAssetLocation;
    }

    public void setAssetLocation(String assetLocation) {
        mAssetLocation = assetLocation;
    }

    public ArrayList<MemeAnnotation> getAnnotations() {
        return mAnnotations;
    }

    public void setAnnotations(ArrayList<MemeAnnotation> annotations) {
        mAnnotations = annotations == null ? new ArrayList<MemeAnnotation>() : annotations;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Bitmap getBitmap() {
        File file = new File(mAssetLocation);
        if (!file.exists()) {
            Log.e("FILE IS MISSING", mAssetLocation);
        }
        return BitmapFactory.decodeFile(mAssetLocation);
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        long id = getId();
        if (id > 0)
            cv.put(MemesEntry.COLUMN_ID, id);
        cv.put(MemesEntry.COLUMN_NAME, getName());
        cv.put(MemesEntry.COLUMN_ASSET, getAssetLocation());
        return cv;
    }

    public static String getStringValue(Cursor cursor, String colName) {
        int colIndex = cursor.getColumnIndex(colName);
        return colIndex >= 0 ? cursor.getString(colIndex) : "";
    }

    public static int getIntegerValue(Cursor cursor, String colName) {
        int colIndex = cursor.getColumnIndex(colName);
        return colIndex >= 0 ? cursor.getInt(colIndex) : -1;
    }

    public boolean hasBeenSaved() {
        return getId() != -1;
    }
}
