package ru.netology.task2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Server {
    public static final int PORT = 45_470;
    public static final String NAME_SERVER = "localhost";

    public static void main(String[] args) {
        try (final ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            serverChannel.bind(new InetSocketAddress(NAME_SERVER, PORT));

            while (true) {
                try (SocketChannel socketChannel = serverChannel.accept()) {
                    System.out.println("Client connected: " + socketChannel);
                    final ByteBuffer inputBuffer = ByteBuffer.allocate(1 << 11);

                    while (socketChannel.isConnected()) {
                        int bytesCount = socketChannel.read(inputBuffer);
                        if (bytesCount == -1) { break; }

                        final String msg = new String(inputBuffer.array(), 0, bytesCount,
                                StandardCharsets.UTF_8);
                        inputBuffer.clear();
                        String msgWithoutSpace = msg.replace(" ", "");
                        socketChannel.write(ByteBuffer.wrap(("Ваше сообщение без пробелов: " +
                                msgWithoutSpace).getBytes(StandardCharsets.UTF_8)));
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
