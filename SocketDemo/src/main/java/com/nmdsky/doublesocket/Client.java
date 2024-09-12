package com.nmdsky.doublesocket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author:Xiao
 * @Date:2021/5/31
 **/
public class Client {
    public static void main(String[] args) {
        try {
            //1.创建客户端Socket，指定服务器地址和端口
            Socket socket=new Socket("localhost", 8888);

            //2.获取输出流，向服务器端发送信息
            OutputStream os=socket.getOutputStream();//字节输出流
            PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
            //4.自定义消息
            Scanner sc = new Scanner(System.in);
            String k = sc.nextLine();
            pw.write(k);
            pw.flush();
            socket.shutdownOutput();//关闭输出流

            //3.获取输入流，并读取服务器端的响应信息
            InputStream is=socket.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            String info=null;
            while((info=br.readLine())!=null){
                System.out.println("我是客户端，服务器说收到："+k);
            }
            socket.shutdownInput();

            //4.关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
