package es.tessier.mememaker.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import es.tessier.mememaker.MemeMakerApplicationSettings;

public class FileUtilities {

    private static final String TAG = FileUtilities.class.getSimpleName();
    private static final String ALBUM_NAME = "mememaker";
    private static final int TAM_BUFFER = 1024;

    public static void saveAssetImage(Context context, String assetName) {
        File fileDirectory = getFileDirectory(context);
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        FileOutputStream out = null;
        try {
            File fileToWrite = new File(fileDirectory, assetName);
            if (!fileToWrite.exists())
                File.createTempFile(assetName, "", fileDirectory);
            in = assetManager.open(assetName);
            out = context.openFileOutput(
                    fileToWrite.getName(),
                    Context.MODE_PRIVATE);

            copyFile(in, out);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException caught ", e);
        } catch (IOException e) {
            Log.e(TAG, "IOException caught ", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean existFile(File directory, String file) {
        File fichero = new File(directory, file);
        return fichero.exists();
    }

    private static File getFileDirectory(Context context) {
        MemeMakerApplicationSettings settings = new MemeMakerApplicationSettings(context);
        String storageType = settings.getStoragePreference();

        if (storageType.equals(StorageType.INTERNAL)) {
            return context.getFilesDir();
        } else {
            if (isExternalStorageAvailable()) {
                if (storageType.equals(StorageType.PRIVATE_EXTERNAL)) {
                    return context.getExternalFilesDir(null);
                } else if (storageType.equals(StorageType.PUBLIC_EXTERNAL)) {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ALBUM_NAME);
                    if (!file.mkdirs()) {
                        Log.e(TAG, "Directory not created");
                    }
                    return file;
                }
            }
        }
        return null;
    }

    private static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private static void copyFile(InputStream in, FileOutputStream out) throws IOException {
        byte[] buffer = new byte[TAM_BUFFER];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static Uri saveImageForSharing(Context context, Bitmap bitmap, String assetName) {
        File fileToWrite = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), assetName);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return Uri.fromFile(fileToWrite);
        }
    }

    public static File[] getFiles(Context context) {
        File fileDirectory = getFileDirectory(context);
        return fileDirectory.listFiles(new FileFilter() {

            private String ACCEPTED_EXTENSIONS[] = {".jpg", ".png", ".gif", ".jpeg"};

            @Override
            public boolean accept(File pathname) {
                String path = pathname.getAbsolutePath();
                for (String acExt : ACCEPTED_EXTENSIONS)
                    if (path.endsWith(acExt))
                        return true;
                return false;
            }
        });
    }


    public static void saveImage(Context context, Bitmap bitmap, String name) {
        File fileDirectory = getFileDirectory(context);
        File fileToWrite = new File(fileDirectory, name);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
