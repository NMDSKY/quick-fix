package com.nmdsky.ThreadPoolsSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) {
        // 定义服务器地址和端口
        String serverAddress = "127.0.0.1"; // 替换为您的服务器地址
        int port = 8888;                  // 替换为您的服务器端口

        // 尝试连接到服务器
        try (Socket socket = new Socket(serverAddress, port);
             // 设置输入输出流
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("已连接到服务器 " + serverAddress + " 端口 " + port);

            String message;
            while (true) {
                // 从用户读取输入
                System.out.print("请输入消息 (输入 'exit' 退出): ");
                message = userInput.readLine();

                // 发送消息到服务器
                out.println(message);

                // 如果用户输入 'exit'，退出客户端程序
                if ("exit".equalsIgnoreCase(message)) {
                    System.out.println("正在退出客户端...");
                    break;
                }

                // 从服务器接收响应
//                String response = in.readLine();
//                System.out.println("来自服务器的响应: " + response);

                // 如果服务器返回 'exit'，退出客户端程序
//                if ("exit".equalsIgnoreCase(response)) {
//                    System.out.println("服务器请求断开连接。");
//                    break;
//                }
            }
        } catch (IOException e) {
            System.err.println("连接错误: " + e.getMessage());
        }

        System.out.println("客户端已关闭");
    }
}
