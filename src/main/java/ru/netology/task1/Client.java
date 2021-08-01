package ru.netology.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private static final String NAME_SERVER = "localhost";
    public static void main(String[] args) {
        try (Socket socket = new Socket(NAME_SERVER, Server.PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in =
                     new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Давайте посчитаем n-ный член ряда Фибоначчи.");
            while (true) {
                System.out.print("Введите номер(n) или end для выхода: ");
                String answer = scanner.nextLine();
                try {
                    if (answer.equals("end")) {
                        out.println(answer);
                        break;
                    }

                    int n = Integer.parseInt(answer);
                    if (n < 0) {
                        throw new IllegalStateException("For input string: \"" + answer + "\"");
                    }

                    out.println(n);
                    System.out.println(n + "-ый член ряда Фибоначчи равен " + in.readLine());
                } catch (IllegalStateException | IllegalArgumentException ex) {
                    System.out.println("Нужно вводить целое положительное число. " +
                            ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
