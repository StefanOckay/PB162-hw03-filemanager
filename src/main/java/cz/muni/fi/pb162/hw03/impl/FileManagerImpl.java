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
        File job = new File(jobPath);
        if (!job.exists()) {
            throw new IOException("The job file doesn't exist.");
        }
        if (!(new File(logFilePath)).exists()) {
            Files.createFile(Paths.get(logFilePath));
        }
        PrintWriter pwr = new PrintWriter(logFilePath, JOB_FILE_CHARSET.toString());
        BufferedReader br = new BufferedReader(new FileReader(job));
        String line = br.readLine();
        String[] lineFields = line.split(";");
        if (lineFields.length != 2 || !lineFields[0].equals("root")) {
            throw new InvalidJobFileException("The first line of a job file is root;<src>");
        }
        File root = new File(lineFields[1]);
        Operation op = null;
        while ((line = br.readLine()) != null) {
            lineFields = line.split(";");
            if (lineFields[0].equals("CP")) {
                op = new CopyOp(lineFields[1], lineFields[2]);
            } else if (lineFields[0].equals("MV")) {
                op = new MoveOp(lineFields[1], lineFields[2]);
            } else if (lineFields[0].equals("DEL")) {
                op = new DeleteOp(lineFields[1]);
            } else if (lineFields.length != 1 || (!lineFields[0].equals("") && lineFields[0].charAt(0) != '#')) {
                throw new InvalidJobFileException("A line can be empty, with comment or with a command.");
            }
            if (op != null) {
                op.execute(root.toPath());
                pwr.write(line + '\n');
            }
        }
        br.close();
        pwr.close();
    }
}
