package com.treasuredata.server;

import java.io.File;
import java.io.PrintWriter;

public class UnknownCommand extends FileCommand {

    public UnknownCommand(File rootFolder, PrintWriter writer, String commandString) {
        super(rootFolder, writer, commandString);
    }

    protected void preExecute() {
        System.out.println(String.format("Unknown command: [%s]", this.commandString));
    }

    protected void postExecute() {
//        this.writer.flush();
    }

    @Override
    public boolean execute() {
        this.writer.println("Unknown command");
        return this.writer.checkError();
    }
}
