package com.treasuredata.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class FileCommand  implements Command {
    protected final File rootFolder;
    protected final PrintWriter writer;
    protected final String commandString;

    public FileCommand(File rootFolder, PrintWriter writer, String commandString) {
        this.rootFolder = rootFolder;
        this.writer = writer;
        this.commandString = commandString;
    }

    protected void preExecute() {
        System.out.println(String.format("Executing command: [%s]", this.commandString));
    }

    protected void postExecute() {
        this.writer.flush();
        if (this.writer.checkError()) {
            System.out.println(String.format("Error while executing command: [%s]", this.commandString));
        } else {
            System.out.println(String.format("Successful execute command: [%s]", this.commandString));
        }
    }

    public void executeCommandWithLog() throws IOException {
        preExecute();
        execute();
        postExecute();
    }
}
