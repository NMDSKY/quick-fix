package com.nmdsky.HeartbeatSocket;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LongServerHeartbeatThreadPools {
    public static void main(String[] args) {
        //1.监听8080端口
        //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        try {
            ServerSocket serverSocket=new ServerSocket(8888);
            ExecutorService executorService= Executors.newCachedThreadPool();

            Socket socket=null;
            LSHeartbeatTask lsHeartbeatTask=null;
            //记录客户端的数量
            int count=0;
            System.out.println("***服务器即将启动，等待客户端的连接***");
            while (true){
                socket=serverSocket.accept();
                //将服务器和客户端的通信交给线程池处理
                lsHeartbeatTask=new LSHeartbeatTask(socket);
                executorService.execute(lsHeartbeatTask);
                count++;//统计客户端的数量
                System.out.println("客户端的数量："+count);
                InetAddress address=socket.getInetAddress();
                System.out.println("当前客户端的IP："+address.getHostAddress());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
