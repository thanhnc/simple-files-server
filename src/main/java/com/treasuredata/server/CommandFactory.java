package com.treasuredata.server;

import java.io.File;
import java.io.PrintWriter;

public class CommandFactory {

    static FileCommand getCommand(String commandString, File rootFolder, PrintWriter writer) {
        String cmdName = commandString.split(" ")[0].toUpperCase();
        switch(cmdName)  {
            case "INDEX":
                return new IndexCommand(rootFolder, writer, commandString);
            case "GET":
                return new GetCommand(rootFolder, writer, commandString);
            default:
                return new UnknownCommand(rootFolder, writer, commandString);
        }
    }
}
