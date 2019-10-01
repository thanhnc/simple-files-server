package com.treasuredata.server;

import java.io.*;

public class GetCommand extends FileCommand {

    public GetCommand(File rootFolder, PrintWriter writer, String commandString) {
        super(rootFolder, writer, commandString);
    }

    @Override
    public boolean execute() throws IOException {
        if( commandString == null || commandString.split(" ").length != 2) {
            System.out.println("Invalid Get Command. It should follow format \"get file_name\".");
            return false;
        }

        final String fileName = commandString.split(" ")[1];
        File file = new File(rootFolder, fileName);

        if (!file.exists() || file.isDirectory()) {
            this.writer.println("Invalid file: " + fileName);
        } else {
            this.writer.println("ok");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    this.writer.println(line);
                }
            }
        }
        return this.writer.checkError();
    }
}
