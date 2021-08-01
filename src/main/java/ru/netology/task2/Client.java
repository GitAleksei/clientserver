package ru.netology.task2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress(Server.NAME_SERVER, Server.PORT);
        SocketChannel socketChannel = null;

        try (Scanner scanner = new Scanner(System.in)) {
            socketChannel = SocketChannel.open();
            socketChannel.connect(socketAddress);

            final ByteBuffer inputBuffer = ByteBuffer.allocate(1 << 11);

            String msg;
            while (true) {
                System.out.println("Введите сообщение для сервера: ");
                msg = scanner.nextLine();
                if ("end".equals(msg)) {
                    break;
                }

                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));

                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount,
                        StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }

        } catch (IOException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                if (socketChannel != null) {
                    socketChannel.close();
                }
            } catch (IOException ex) {
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }
        }
    }
}
