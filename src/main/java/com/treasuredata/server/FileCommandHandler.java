package com.treasuredata.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class FileCommandHandler implements Runnable {

    private final Socket socket;
    private final File rootFolder;
    private FileCommand fileCommand;

    public FileCommandHandler(File rootFolder, Socket socket) {
        this.rootFolder = rootFolder;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream());
             Scanner inputScanner = new Scanner(socket.getInputStream())) {
            String commandString = null;
            if (inputScanner.hasNextLine()) {
                commandString = inputScanner.nextLine();
            }
            if (commandString == null || commandString.split(" ").length < 1) {
                throw new IllegalArgumentException("Cannot execute empty command");
            }

            fileCommand = CommandFactory.getCommand(commandString, rootFolder, writer);
            fileCommand.executeCommandWithLog();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!socket.isClosed()) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
