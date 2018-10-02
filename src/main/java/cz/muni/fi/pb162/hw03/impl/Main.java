package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.FileManager;
import cz.muni.fi.pb162.hw03.impl.Utils.FilesHandler;

import java.io.File;

/**
 * @author Stefan Ockay
 */
public class Main {
    private static final boolean MYDEBUG = false;

    public static void main(String[] args) {
        if (MYDEBUG) {
            FileManager fm = new FileManagerImpl();
            try {
                fm.executeJob("my_test/jobfile.cmd", "my_test/logFiles/logFile.log");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
            File dest = new File("my_test/destFolder");
            File[] destFiles = dest.listFiles();
            File file = new File("my_test/testFiles/txt.xml");
            //System.out.println(FilesHandler.getNextFileNumber(destFiles, file, "xml"));
            //System.out.println(FilesHandler.getNextFreeFileName(file, destFiles, "xml"));
            return;
        }
        if (args.length != 2) {
            System.exit(1);
        }
        FileManager fm = new FileManagerImpl();
        try {
            fm.executeJob(args[0], args[1]);
        } catch (Exception ex) {
            System.exit(1);
        }
        System.out.println(args[1]);
    }
}
