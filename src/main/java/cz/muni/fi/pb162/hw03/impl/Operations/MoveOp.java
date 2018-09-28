package cz.muni.fi.pb162.hw03.impl.Operations;

import cz.muni.fi.pb162.hw03.Operation;
import cz.muni.fi.pb162.hw03.impl.Utils.FilesHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Stefan Ockay
 */
public class MoveOp implements Operation {
    private String extension;
    private String dest;

    public MoveOp(String extension, String dest) {
        this.extension = extension;
        this.dest = dest;
    }

    /**
     * recursively moves all files with class extension
     * @param file to start from
     */
    private void moveRecursively(File file) {
        String destName;
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                moveRecursively(f);
            } else if (FilesHandler.hasExtension(f, extension)) {
                destName = dest + File.separator + f.getName();
                if (!f.renameTo(new File(destName))) {
                    System.err.println("Moving " + f.getAbsolutePath() + " has failed.");
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
        moveRecursively(src);
        return true;
    }

}
