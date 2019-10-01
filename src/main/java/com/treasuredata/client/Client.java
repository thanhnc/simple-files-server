package com.treasuredata.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.treasuredata.server.Constant.PORT;
import static com.treasuredata.server.Constant.SERVER_ADDRESS;


public class Client implements Runnable {

    private final Socket socket;
    private final String cmd;

    private Client(String serverAddress, int serverPort, String cmd) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.cmd = cmd;
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();
            if (cmd == null || cmd.trim().isEmpty()) {
                System.out.println("The command must not be empty!");
                continue;
            }
            if ("quit".equalsIgnoreCase(cmd.trim())) {
                System.exit(0);
            }
            Client client = new Client(SERVER_ADDRESS, PORT, cmd.trim());
            System.out.println(String.format("Connected to files server %s:%s ", client.socket.getInetAddress(), PORT));
            client.run();
        }

//        testWithMultiThread(1000);

    }

    static void testWithMultiThread(int thead_num) throws Exception {
        for (int i = 0; i < thead_num; i++) {
            String cmd = "index";
            if (i%2 == 0) {
                cmd = "get abc.txt";
            }
            if (i%3 ==0) {
                cmd = "unknown";
            }
            Client client = new Client( SERVER_ADDRESS, PORT, cmd.trim());
            new Thread(client).start();
        }
    }

    static void printMenu() {
        System.out.println("=======================================");
        System.out.println("Please input command as below:\n" +
                " index - List files\n" +
                " get - Get file content\n" +
                " quit - Exit application\n");
    }


    @Override
    public void run() {
        try {
            try (PrintWriter printWriter = new PrintWriter(this.socket.getOutputStream(), true);
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                printWriter.println(this.cmd);
                String data;
                System.out.println("Server response: ");
                while ((data = bufferedReader.readLine()) != null) {
                    System.out.println(data);
                }
            } catch (IOException ex) {
                System.out.println("Error while read response data from server");
                ex.printStackTrace();
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown command");
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}