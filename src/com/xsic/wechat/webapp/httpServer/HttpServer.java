package com.xsic.wechat.webapp.httpServer;

import com.xsic.wechat.webapp.config.Config;

import java.io.*;
import java.net.Socket;

public class HttpServer extends Thread {

    //输入流，接受客户端请求
    private InputStream inputStream;
    //输出流，返回给客户端
    private OutputStream outputStream;

    public HttpServer(Socket socket) {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    /**
     * 读取资源文件，返回给客户端
     * @param filePath
     */
    private void response(String filePath){
        File file = new File(Config.ResRootPath + filePath);
        if (file.exists()){
            //资源存在
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line).append("\r\n");
                }
                StringBuffer result = new StringBuffer();
                result.append("HTTP /1.1 200 ok /r/n");
                result.append("Content-Type:text/html /r/n");
                result.append("Content-Length:" + file.length() + "/r/n");
                result.append("\r\n:" + stringBuffer.toString());

                outputStream.write(result.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            // 2、资源不存在，提示 file not found
            StringBuffer error = new StringBuffer();
            error.append("HTTP /1.1 400 file not found /r/n");
            error.append("Content-Type:text/html \r\n");
            error.append("Content-Length:20 \r\n").append("\r\n");
            error.append("<h1 >File Not Found..</h1>");
            try {
                outputStream.write(error.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理数据，返回给客户端
     */
    private void response(){

    }
}
