package ru.netology.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Server {
    public static final int PORT = 35_489;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in =
                             new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    System.out.println("Client connected: " + socket);
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.equals("end")) {
                            break;
                        }

                        int n = Integer.parseInt(line);

                        FibonacciFJP fibonacciFJP = new FibonacciFJP(n);
                        ForkJoinPool forkJoinPool = new ForkJoinPool();
                        n = forkJoinPool.invoke(fibonacciFJP);

                        out.println(n);
                    }
                } catch (IOException ex) {
                    System.out.println(Arrays.toString(ex.getStackTrace()));
                }
            }
        } catch (IOException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}