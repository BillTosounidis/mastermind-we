package we.software.gui;

import java.net.URL;

/**
 * Created by bill on 3/28/17.
 */
class LoadAssets {

    /**
     * Μας βοηθάει στην καλύτερη φόρτωση των εικόνων και στην συμπερίληψή τους
     * στο τελικό εκτελέσιμο αρχείο.
     */
    public static URL load(String path){
        return LoadAssets.class.getResource("/"+ path);
    }
}
