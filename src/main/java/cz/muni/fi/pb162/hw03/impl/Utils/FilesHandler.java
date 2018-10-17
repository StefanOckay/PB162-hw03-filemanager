package cz.muni.fi.pb162.hw03.impl.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Stefan Ockay
 */
public class FilesHandler {

    /**
     * adds preceding digits - (number digits) 0s to String for of a number
     * @param number to be used
     * @param digits number of the characters in the result String
     * @return String number with preceding zeros added
     */
    private static String addZeros(int number, int digits) {
        String strNumber = Integer.toString(number);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digits - strNumber.length(); i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(strNumber);
        return stringBuilder.toString();
    }

    /**
     * gets the lowest free file number from the ordered files(by name) with specified extension
     * @param files to be examined
     * @param extension of the files
     * @return the lowest free file number of the files with specified extension
     */
    private static int getNextFileNumber(File[] files, File file, String extension) {
        ArrayList<File> orderedFiles = getOrderedFiles(files, file.getName(), extension);
        int nextNumber = 0;
        int fNumber;
        for (File f : orderedFiles) {
            fNumber = getFileNumber(f, file, extension);
            if (fNumber == nextNumber) {
                nextNumber += 1;
            } else {
                return nextNumber;
            }
        }
        return nextNumber;
    }

    /**
     * gets ordered(by name) ArrayList of the files array with specified extension and file name
     * @param files array to be ordered
     * @param extension of the files from the array to be ordered
     * @param fileName should be substring of the needed files
     * @return ordered ArrayList of the files with extension and fileName as a substring
     */
    private static ArrayList<File> getOrderedFiles(File[] files, String fileName, String extension) {
        if (files == null) {
            return new ArrayList<>();
        }
        Map<String, File> auxMap = new TreeMap<>();
        String fileNameNoExtension = fileName.substring(0, fileName.length() - extension.length() - 1);
        String substring;
        for (File f : files) {
            if (hasExtension(f, extension)) {
                try {
                    substring = f.getName().substring(0, fileNameNoExtension.length());
                    if (fileNameNoExtension.equals(substring) &&
                            (fileName.length() == f.getName().length() - 4 ||
                                    fileName.length() == f.getName().length())) {
                        auxMap.put(f.getName(), f);
                    }
                } catch (IndexOutOfBoundsException ex) {
                    continue;
                }
            }
        }
        return new ArrayList<>(auxMap.values());
    }

    /**
     * gets file number of a file with name in format 'name_abc'
     * @param file to be examined
     * @param baseFile its name dictates the basis of the file name without number
     * @param extension of the files
     * @return file number of the file
     */
    private static int getFileNumber(File file, File baseFile, String extension) {
        String fileName = file.getName();
        String baseFileName = baseFile.getName();
        String fileNameNoExtension = fileName.substring(0, fileName.length() - extension.length() - 1);
        String baseFileNameNoExtension = baseFileName.substring(0, baseFileName.length() - extension.length() - 1);
        try {
            String number = fileName.substring(baseFileNameNoExtension.length() + 1, fileNameNoExtension.length());
            if (fileNameNoExtension.charAt(fileNameNoExtension.length() - 4) != '_') {
                return 0;
            }
            return Integer.parseInt(number);
        } catch (NumberFormatException|StringIndexOutOfBoundsException ex) {
            return 0;
        }
    }

    /**
     * gets the new name for the file with number specifier in case of name collisions
     * @param file to be examined
     * @param files colliding with the file
     * @param extension of the file and files examined
     * @return new non-colliding file name for the file
     */
    public static String getNextFreeFileName(File file, File[] files, String extension) {
        int nextFreeFileNumber = getNextFileNumber(files, file, extension);
        if (nextFreeFileNumber == 0) {
            return file.getName();
        }
        String fileNameNoExtension = file.getName().substring(0, file.getName().length() - extension.length() - 1);
        return fileNameNoExtension + "_" + addZeros(nextFreeFileNumber, 3) + "." + extension;
    }

    /**
     *
     * @param file to be checked
     * @param extension of a file
     * @return true if file has the specified extension, false otherwise
     */
    public static boolean hasExtension(File file, String extension) {
        String fileName = file.getName();
        int lenExt = extension.length();
        int lenFile = fileName.length();
        char fileChar;
        char extChar;
        for (int i = 0; i < lenExt; i++) {
            extChar = extension.charAt(lenExt - 1 - i);
            try {
                fileChar = fileName.charAt(lenFile - 1 - i);
            } catch (IndexOutOfBoundsException ex) {
                return false;
            }
            if (extChar != fileChar) {
                return false;
            }
        }
        return true;
    }

}
