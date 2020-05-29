package com.xsic.wechat.webapp.webServer;

import com.xsic.wechat.webapp.config.Config;
import com.xsic.wechat.webapp.httpServer.HttpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {


    public void startWebServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(Config.port);
            while (true){
                Socket socket = serverSocket.accept();
                new HttpServer(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
