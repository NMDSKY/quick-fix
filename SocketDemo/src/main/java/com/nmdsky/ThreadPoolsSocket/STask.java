package com.nmdsky.ThreadPoolsSocket;

import java.io.*;
import java.net.Socket;

public class STask implements Runnable{
    public Socket socket;
    public STask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        OutputStream os=null;
        PrintWriter pw=null;
        try {
            //获取输入流，并读取客户端信息
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info=null;
            String info_history=null;
            while((info=br.readLine())!=null){//循环读取客户端的信息
                System.out.println("我是服务器，客户端说："+info);
                info_history=info;
            }
            socket.shutdownInput();//关闭输入流
            //获取输出流，响应客户端的请求
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.println("收到=>"+info_history);
//            pw.flush();//调用flush()方法将缓冲输出
            socket.shutdownOutput();//关闭输入流
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //关闭资源
            try {
                if(pw!=null)
                    pw.close();
                if(os!=null)
                    os.close();
                if(br!=null)
                    br.close();
                if(isr!=null)
                    isr.close();
                if(is!=null)
                    is.close();
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
