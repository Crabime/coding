package bundle.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {

    public static URL[] convertToURLArray(String... paths) throws MalformedURLException {
        if (paths == null) {
            return null;
        }
        URL[] u = new URL[paths.length];
        int i = 0;
        for (String p : paths) {
            File f = new File(p);
            u[i++] = f.toURI().toURL();
        }
        return u;
    }
}
