package cz.muni.fi.pb162.hw03.impl.Utils;

import java.io.File;

/**
 * @author Stefan Ockay
 */
public class FilesHandler {

    /**
     *
     * @param file to be checked
     * @param extension of a file
     * @return true if file has specified extension, false otherwise
     */
    public static boolean hasExtension(File file, String extension) {
        String fileName = file.getName();
        int lenExt = extension.length();
        int lenFile = fileName.length();
        for (int i = 0; i < lenExt; i++) {
            if (extension.charAt(lenExt - 1 - i) != fileName.charAt(lenFile - 1 - i)) {
                return false;
            }
        }
        return true;
    }

}
