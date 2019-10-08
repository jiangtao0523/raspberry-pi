package com.jiangtao.server.impl;

import com.jiangtao.bean.Enviroment;
import com.jiangtao.server.DBStore;
import com.jiangtao.server.Server;
import com.jiangtao.util.Configuration;
import com.jiangtao.util.ConfigurationAware;
import com.jiangtao.util.Log;
import com.jiangtao.util.impl.ConfigurationImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

public class ServerImpl implements Server, ConfigurationAware {

    private Configuration configuration;
    private int port;
    private Log logger;

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        this.logger = configuration.getLog();
    }

    @Override
    public void init(Properties prop) {
        this.port = Integer.parseInt(prop.getProperty("port"));
    }

    @Override
    public void receive() {
        try {
            ServerSocket serverSocket = new ServerSocket(10001);
            while(true){
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                List<Enviroment> list = (List<Enviroment>) ois.readObject();

                DBStore dbStore = configuration.getDBStore();
                dbStore.saveEnvs(list);

                ois.close();
                socket.close();
            }
        } catch (Exception e) {
            logger.error("receive data failed ~");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Configuration configuration = new ConfigurationImpl();
        Server server = configuration.getServer();
        server.receive();
    }

}
