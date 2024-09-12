package com.nmdsky.LongSocket;

import java.io.*;
import java.net.Socket;

//长连接阻塞解决方案
public class LSTask implements Runnable{
    public Socket socket;
    public LSTask(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader in=null;
        PrintWriter out=null;
        String request=null;
        try {
            while (true){
                out= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //服务器默认读取流数据，因为客户端是主动连的会发送消息，因此为了避免读阻塞需要先读
                //接收客户端发送数据，若客户端发送exit指令则断开连接并结束IO和线程
                request = in.readLine();
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
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("====bye====");
        }

    }
}
