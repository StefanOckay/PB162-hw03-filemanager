package cz.muni.fi.pb162.hw03.impl.Operations;

import cz.muni.fi.pb162.hw03.Operation;
import cz.muni.fi.pb162.hw03.impl.Utils.FilesHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

/**
 * @author Stefan Ockay
 */
public class DeleteOp implements Operation {
    private String extension;
    private PrintWriter pwr;

    public DeleteOp(String extension, PrintWriter pwr) {
        this.pwr = pwr;
        this.extension = extension;
    }

    /**
     * recursively deletes all files with class extension
     * @param file to start from
     */
    private void deleteRecursively(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                deleteRecursively(f);
            } else if (FilesHandler.hasExtension(f, extension)) {
                if (!f.delete()){
                    System.err.println("Couldn't delete: " + f.getAbsolutePath());
                } else {
                    pwr.write("DEL;");
                    pwr.write(f.getAbsolutePath());
                    pwr.write("\n");
                }
            }
        }
    }

    @Override
    public boolean execute(Path path) throws IOException {
        File src = path.toFile();
        if (!src.exists()) {
            throw new IOException("The source file doesn't exist.");
        }
        deleteRecursively(src);
        return true;
    }
}
