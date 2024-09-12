package com.nmdsky.doublesocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author:Xiao
 * @Date:2021/5/31
 **/
public class Server {

    public static void main(String[] args) {

        try {
            //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("***服务器即将启动，等待客户端的连接***");
            //2.调用accept()方法开始监听，等待客户端的连接
            Socket socket=serverSocket.accept();
            System.out.println("连接一台客户机");

            //3.获取输入流，并读取客户端信息
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            // 使用字符缓冲流
            BufferedReader br = new BufferedReader(isr);
            String info=null;
            while((info=br.readLine())!=null){//循环读取客户端的信息。一行一行的读取
                System.out.println("我是服务器，客户端说："+info);
            }
            socket.shutdownInput();//关闭输入流

            //4.获取输出流，响应客户端的请求
            OutputStream os = socket.getOutputStream();
            // 使用标准打印流
            PrintWriter pw = new PrintWriter(os);
            pw.write("欢迎您！");
            pw.flush();//调用flush()方法将缓冲输出
            socket.shutdownOutput();

            // 5.关闭资源
            br.close();
            is.close();
            isr.close();
            os.close();
            pw.close();
            socket.close();
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
