package io.github.biezhi.compress.sevenz;

import java.io.File;
import java.io.IOException;

public class SevenZProgram {

    private static final String OUTPUT_DIRECTORY = new File("tmp").mkdir() ? "tmp" : "";
    private static final String TAR_GZIP_SUFFIX = ".7z";

    private static final String MULTIPLE_RESOURCES = "/example-multiple-resources";
    private static final String RECURSIVE_DIRECTORY = "/example-recursive-directory";

    private static final String MULTIPLE_RESOURCES_PATH = OUTPUT_DIRECTORY + MULTIPLE_RESOURCES + TAR_GZIP_SUFFIX;
    private static final String RECURSIVE_DIRECTORY_PATH = OUTPUT_DIRECTORY + RECURSIVE_DIRECTORY + TAR_GZIP_SUFFIX;


    public static void main(String... args) throws IOException {
        SevenZ.decompress("/Users/canh/Downloads/test.7z", new File("/Users/canh/Downloads"), "test.pdf");
//        SevenZ.decompress("/Users/canh/Documents/rs2-app-Sprint038.03.Prod/src/assets/innova.7z", new File("/Users/canh/Downloads"), "innova.db");

    }
}