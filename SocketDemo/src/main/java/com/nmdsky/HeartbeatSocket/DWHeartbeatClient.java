package com.nmdsky.HeartbeatSocket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 维护一个socket,由频繁new Socket转移到频繁IO
 * socket.shutdownOutput();、pw.close();的行为都会导致socket is closed异常
 * 客户端打开一个输出流，如果不做约定，也不关闭它，那么服务端永远不知道客户端是否发送完消息，那么服务端会一直等待下去，直到读取超时。
 * 所以怎么告知服务端已经发送完消息就显得特别重要。
 * 正确关闭：结束时换行符或者println或者os.close()这种方法会关闭socket()
 */
public class DWHeartbeatClient {

    //监听服务端是否断开连接

    public static void main(String[] args) {
        //1.创建客户端Socket，指定服务器地址和端口
        Socket socket = null;

        try {
            socket = new Socket("localhost", 8888);Socket finalSocket = socket;
            //加入缓冲区概念，普通IO没有缓冲区
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //2.获取输出流，向服务器端发送信息
            // 定时发送心跳消息
            //todo 维修一下这个线程BUG，顺便将代码封装得更好
            ScheduledExecutorService heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
            heartbeatExecutor.scheduleAtFixedRate(() -> {
                        out.println("HEARTBEAT:"+ finalSocket);
                        System.out.println("==Sent heartbeat to server==");
            }, 0, 40, TimeUnit.SECONDS); // 每40秒发送一次心跳,可以调整数字测试是否被踢
            while (true) {
                if(in.readLine()==null){
                    // 关闭心跳任务
                    heartbeatExecutor.shutdown();
                    socket.close();
                    break;
                }
                //3.接收服务器数据
                System.out.println("=======已连接到服务器=>" + socket);
                System.out.println("=======向服务器发送消息======");
                Scanner sc = new Scanner(System.in);
                String k = sc.nextLine();
//                out.write("ClientSocket:{"+socket+"}=>"+k);
//                out.println();//输入换行符表示输出结束，否则只能out.close()这样会导致socket.close()
                out.println("ClientSocket:{" + socket + "}=>" + k);//优化
                //4. 如果用户输入 'exit'，退出客户端程序
                if ("exit".equalsIgnoreCase(k)) {
                    System.out.println("正在退出客户端...");
                    break;
                }
            }
            // 关闭Scanner以避免资源泄漏
            out.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            // 关闭Socket，确保资源释放
            if (socket != null) {
                try {
                    socket.close();
                    System.out.println("bye");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
