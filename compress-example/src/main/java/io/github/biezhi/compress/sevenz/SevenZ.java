package io.github.biezhi.compress.sevenz;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * SevenZ 操作
 *
 * @author biezhi
 * @date 2018/1/16
 */
public class SevenZ {

    private SevenZ() {

    }

    public static void compress(String name, File... files) throws IOException {
        try (SevenZOutputFile out = new SevenZOutputFile(new File(name))) {
            for (File file : files) {
                addToArchiveCompression(out, file, ".");
            }
        }
    }

    public static void decompress(String in, File destination, String outputFileName) throws IOException {
        SevenZFile sevenZFile = new SevenZFile(new File(in));
        SevenZArchiveEntry entry;
        while ((entry = sevenZFile.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                continue;
            }
            File curfile = new File(destination, entry.getName());
            File parent = curfile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            File outputDir = new File("/Users/canh/Downloads/");
            extractFileStream(sevenZFile, outputDir, outputFileName);
        }
    }

    public static void extractFileStream(SevenZFile sevenZFile, File outputDir, String outputFileName) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;

        File curFile = new File(outputDir, outputFileName);

        // Open the FileOutputStream in append mode
        FileOutputStream out = new FileOutputStream(curFile);

        while ((bytesRead = sevenZFile.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        out.close();
        sevenZFile.close();
    }

    private static void addToArchiveCompression(SevenZOutputFile out, File file, String dir) throws IOException {
        String name = dir + File.separator + file.getName();
        if (file.isFile()) {
            SevenZArchiveEntry entry = out.createArchiveEntry(file, name);
            out.putArchiveEntry(entry);

            FileInputStream in = new FileInputStream(file);
            byte[] b = new byte[1024];
            int count = 0;
            while ((count = in.read(b)) > 0) {
                out.write(b, 0, count);
            }
            out.closeArchiveEntry();

        } else if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addToArchiveCompression(out, child, name);
                }
            }
        } else {
            System.out.println(file.getName() + " is not supported");
        }
    }
}