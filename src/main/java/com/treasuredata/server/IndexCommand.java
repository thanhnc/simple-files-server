package com.treasuredata.server;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;

import static com.treasuredata.server.Constant.SUPPORTED_EXTENSION;

public class IndexCommand extends FileCommand {

    public IndexCommand(File rootFolder, PrintWriter writer, String commandString) {
        super(rootFolder, writer, commandString);
    }

    @Override
    public boolean execute() {
        Arrays.stream(this.rootFolder.listFiles())
                .filter(file -> !file.isDirectory() && file.getName().endsWith(SUPPORTED_EXTENSION))
                .map(File::getName)
                .forEach(this.writer::println);
        return this.writer.checkError();
    }
}
