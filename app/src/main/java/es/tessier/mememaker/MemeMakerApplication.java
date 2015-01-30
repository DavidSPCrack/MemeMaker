package es.tessier.mememaker;

import es.tessier.mememaker.utils.FileUtilities;


public class MemeMakerApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FileUtilities.saveAssetImage(this, "Funny_Face.jpg");
        FileUtilities.saveAssetImage(this, "Troll-face-Okay.jpg");
        FileUtilities.saveAssetImage(this, "Troll_meme.png");
        FileUtilities.saveAssetImage(this, "julio.png");
    }
}
