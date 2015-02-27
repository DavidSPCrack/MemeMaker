package es.tessier.mememaker.database;

import android.provider.BaseColumns;

/**
 * Created by usuario.apellido on 27/02/2015.
 *
 * @author david.sancho
 */
public class MemeContract {

    public static abstract class MemesEntry implements BaseColumns {
        public static final String MEMES_TABLE = "MEMES";
        public static final String COLUMN_ASSET = "asset";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ID = BaseColumns._ID;

        public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_NAME, COLUMN_ASSET};
    }

    public static abstract class AnnotationEntry implements BaseColumns {
        public static final String ANNOTATION_TABLE = "ANNOTATIONS";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_MEME_ID = "meme_id";
        public static final String COLUMN_X = "x";
        public static final String COLUMN_Y = "y";
        public static final String COLUMN_COLOR = "color";
        public static final String COLUMN_ID = BaseColumns._ID;

        public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_MEME_ID, COLUMN_TITLE, COLUMN_COLOR, COLUMN_X, COLUMN_Y};
    }
}
