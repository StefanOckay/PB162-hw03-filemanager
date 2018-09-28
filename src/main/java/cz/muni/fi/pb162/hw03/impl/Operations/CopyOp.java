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
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        String destPath;
        File destFile;
        int fileNumber;
        for (File f : files) {
            if (f.isDirectory()) {
                copyRecursively(f);
            } else if (FilesHandler.hasExtension(f, extension)) {
                destPath = dest + File.separator + f.getName();
                destFile = new File(destPath);
                fileNumber = FilesHandler.getNextThreeDigitFileNumber(destFile, files, extension);
                try {
                    destFile.getParentFile().mkdirs();
                    if (!destFile.createNewFile()) {
                        System.err.println("The file already exists: " + destFile.getAbsolutePath());
                    }
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
