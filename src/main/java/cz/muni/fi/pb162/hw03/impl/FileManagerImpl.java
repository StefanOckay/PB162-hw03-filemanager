package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.FileManager;
import cz.muni.fi.pb162.hw03.Operation;
import cz.muni.fi.pb162.hw03.impl.Exceptions.InvalidJobFileException;
import cz.muni.fi.pb162.hw03.impl.Operations.CopyOp;
import cz.muni.fi.pb162.hw03.impl.Operations.DeleteOp;
import cz.muni.fi.pb162.hw03.impl.Operations.MoveOp;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Stefan Ockay
 */
public class FileManagerImpl implements FileManager {

    @Override
    public void executeJob(String jobPath, String logFilePath) throws Exception {
        File jobFile = new File(jobPath);
        if (!jobFile.exists()) {
            throw new IOException("The job file doesn't exist.");
        }
        File logFile = new File(logFilePath);
        logFile.getParentFile().mkdirs();
        if (!logFile.createNewFile()) {
            throw new IOException("The log file already exists");
        }
        PrintWriter pwr = new PrintWriter(logFilePath, JOB_FILE_CHARSET.toString());
        BufferedReader br = new BufferedReader(new FileReader(jobFile));
        String line = br.readLine();
        String[] lineFields = line.split(";");
        if (lineFields.length != 2 || !lineFields[0].equals("root")) {
            throw new InvalidJobFileException("The first line of a job file is root;<src>");
        }
        File src = new File(lineFields[1]);
        Operation op = null;
        while ((line = br.readLine()) != null) {
            lineFields = line.split(";");
            try {
                if (lineFields[0].equals("CP")) {
                    lineFields[2] = (new File(lineFields[2])).getAbsolutePath();
                    op = new CopyOp(lineFields[1], lineFields[2]);
                } else if (lineFields[0].equals("MV")) {
                    lineFields[2] = (new File(lineFields[2])).getAbsolutePath();
                    op = new MoveOp(lineFields[1], lineFields[2]);
                } else if (lineFields[0].equals("DEL")) {
                    op = new DeleteOp(lineFields[1]);
                } else if (lineFields.length != 1 || (!lineFields[0].equals("") && lineFields[0].charAt(0) != '#')) {
                    throw new InvalidJobFileException("A line can be empty, with comment or with a command.");
                }
            } catch (Exception ex) {
                throw new InvalidJobFileException(ex.getMessage(), ex);
            }
            if (op != null) {
                op.execute(src.toPath().toAbsolutePath());
                pwr.write(lineFields[0]);
                pwr.write(";" + lineFields[1]);
                if (lineFields.length == 3) {
                    pwr.write(";" + lineFields[2]);
                }
            }
        }
        br.close();
        pwr.close();
    }
}
