package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.FileManager;

/**
 * @author Stefan Ockay
 */
public class Main {

    public static void main(String[] args) throws Exception {
        FileManager fm = new FileManagerImpl();
        fm.executeJob("/my_test/jobfile.cmd", "/my_test/logFile.txt");
        /**
        if (args.length != 2) {
            System.err.println("Wrong number of arguments.");
            return;
        }
        FileManager fm = new FileManagerImpl();
        try {
            fm.executeJob(args[0], args[1]);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return;
        }
        System.out.println("Success. " + args[0]);
         **/
    }
}
