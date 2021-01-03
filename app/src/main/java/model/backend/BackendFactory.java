package model.backend;

import android.content.Context;

public final class BackendFactory {
    static IBackend instance = null;

    public static String mode = "firebase";

    /**
     * this function return the single insatnce of the datasource class
     * @param context
     * @return
     */
    public final static IBackend getInstance(Context context) {
        if (mode == "lists") {
            if (instance == null)
                instance = new model.datasource.DatabaseList();
            return instance;
        }
       if (mode == "firebase") {
            if (instance == null)
                instance = new model.datasource.DatabaseFB();
            return instance;
        }
        else {
            return null;
        }
    }
}
