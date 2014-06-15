package ui.graphic;

import java.net.URL;

/**
 * Class used to import resources as images, etc.
*/
public class Resources {
    public static final URL getResourceFile(String name) {
        URL url = Resources.class.getResource(name);
        return url;
    }
}