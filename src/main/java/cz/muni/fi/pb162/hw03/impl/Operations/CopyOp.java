package cz.muni.fi.pb162.hw03.impl.Operations;

import cz.muni.fi.pb162.hw03.Operation;
import cz.muni.fi.pb162.hw03.impl.Utils.FilesHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Stefan Ockay
 */
public class CopyOp implements Operation {
    private String extension;
    private String dest;

    public CopyOp(String extension, String dest) {
        this.extension = extension;
        this.dest = dest;
    }

    /**
     * recursively copies all files with class extension
     * @param file to start from
     */
    private void copyRecursively(File file) {
        String destName;
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                copyRecursively(f);
            } else if (FilesHandler.hasExtension(f, extension)) {
                destName = dest + File.separator + f.getName();
                try {
                    Files.createFile(Paths.get(destName));
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
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
        copyRecursively(src);
        return true;
    }
}
