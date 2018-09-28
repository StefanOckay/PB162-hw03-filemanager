package cz.muni.fi.pb162.hw03.impl.Utils;

import java.io.File;

/**
 * @author Stefan Ockay
 */
public class FilesHandler {

    public static String addZeros(int number, int digits) {
        String strNumber = Integer.toString(number);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digits - strNumber.length(); i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(strNumber);
        return stringBuilder.toString();
    }

    public static int getNextThreeDigitFileNumber(File file, File[] files, String extension) {
        int max = 0;
        int fNumber;
        String pureFileName = getPureFileName(file, extension);
        String pureFName;
        for (File f : files) {
            pureFName = getPureFileName(f, extension);
            if (pureFileName.equals(pureFName.substring(0, pureFileName.length() - 1))) {
                fNumber = getThreeDigitFileNumber(f, extension);
                if (fNumber > max) {
                    max = fNumber;
                }
            }
        }
        return max + 1;
    }

    public static String getPureFileName(File file, String extension) {
        String fileName = file.getName();
        int endIndex = fileName.length() - extension.length() - 1;
        return fileName.substring(0, endIndex);
    }

    private static int getThreeDigitFileNumber(File file, String extension) {
        String fileName = file.getName();
        int endIndex = fileName.length() - extension.length() - 1;
        String number = fileName.substring(endIndex - 2, endIndex);
        try {
            return Integer.parseInt(number);
        } catch (Exception ex) {
            return 0;
        }
    }

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
