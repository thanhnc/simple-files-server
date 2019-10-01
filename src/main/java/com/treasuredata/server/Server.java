package com.treasuredata.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.treasuredata.server.Constant.DEFAULT_FOLDER;
import static com.treasuredata.server.Constant.PORT;

public class Server {

    static final ExecutorService executor = Executors.newFixedThreadPool(2);
    static  ServerSocket server;


    public static void main(String args[]) throws IOException {
        File fileFolder = new File(DEFAULT_FOLDER);
        if (args != null && args.length > 0 )
            fileFolder = new File(args[0]);
        if (!fileFolder.exists() || !fileFolder.isDirectory()) {
            System.out.println("Invalid files folder");
            System.exit(1);
        }

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("File folder: " + fileFolder.getAbsolutePath());
            while (true) {
                Socket socket = server.accept();
                executor.execute(new FileCommandHandler(fileFolder, socket));
            }
        } catch (IOException e) {
            System.out.println("Error while start server on port: " + PORT);
            e.printStackTrace();
            System.exit(1);
        } finally {
            server.close();
        }
    }
}
