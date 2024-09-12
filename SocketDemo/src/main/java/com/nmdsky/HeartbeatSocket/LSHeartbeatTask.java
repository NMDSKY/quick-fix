package com.nmdsky.HeartbeatSocket;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

//长连接阻塞解决方案
public class LSHeartbeatTask implements Runnable{
    public Socket socket;
    private volatile long lastActivityTime; // 记录最后一次收到数据的时间
    private static final long TIMEOUT = 30 * 1000; // 超时时间：60秒
    BufferedReader in=null;
    PrintWriter out=null;
    public LSHeartbeatTask(Socket socket) {
        this.socket = socket;
        this.lastActivityTime = System.currentTimeMillis(); // 初始化为当前时间,作为线程起始时间
    }
    @Override
    public void run() {
        String request=null;
        try {
            // 线程嵌套=>启动一个独立的线程来检测心跳超时
            new Thread(this::checkHeartbeat).start();
            while (true){
                out= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //服务器默认读取流数据，因为客户端是主动连的会发送消息，因此为了避免读阻塞需要先读
                //接收客户端发送数据，若客户端发送exit指令则断开连接并结束IO和线程
                request = in.readLine();
                // 更新最后一次活动时间,若客户端啥也不发则in.readLine()阻塞无法更新lastActivityTime
                lastActivityTime = System.currentTimeMillis();
                if(request==null){
                    //客户端断开连接时,客户端会发送FIN给服务器，readLine()检测到EOF(由FIN包通知)会返回null
                    break;
                }
                System.out.println("来自客户端的请求: " + request);
                if ("exit".equalsIgnoreCase(request)) {
                    System.out.println("客户端断开连接");
                    //注意这里不必做任何操作，只需要客户端主动断开连接即可
                    break;
                }
                //回复客户端收到，确保双方保持连接
                out.println("服务器收到:"+request);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("====bye====");
        }
    }

    /**
     * 心跳检查
     */
    private void checkHeartbeat() {
        while (true) {
            // 如果超过超时时间没有收到客户端的消息
            if (System.currentTimeMillis() - lastActivityTime > TIMEOUT) {
                System.out.println("客户端不够活跃，强制下线");
                try {
                    socket.close();
                    out.close();

                } catch (IOException e) {
                    System.err.println("超时关闭socket_err:" + e.getMessage());
                }
                System.out.println("下线成功");
                break; // 退出循环，结束线程
            }

            // 每隔一秒检查一次,保证当前线程是秒级处理
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
