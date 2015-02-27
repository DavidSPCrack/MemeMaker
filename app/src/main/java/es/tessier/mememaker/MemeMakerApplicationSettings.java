package es.tessier.mememaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import es.tessier.mememaker.utils.StorageType;


public class MemeMakerApplicationSettings {

    private static final String KEY_STORAGE = "STORAGE";

    private SharedPreferences sPreferences;

    public MemeMakerApplicationSettings(Context context) {
        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getStoragePreference() {
        return sPreferences.getString(KEY_STORAGE, StorageType.INTERNAL);
    }

    public void setStoragePreference(String storage) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(KEY_STORAGE, storage);
        editor.apply();
    }

    public void resetDefaults() {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
