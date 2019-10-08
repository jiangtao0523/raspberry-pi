package com.jiangtao.client.impl;

import com.jiangtao.bean.Enviroment;
import com.jiangtao.client.Client;
import com.jiangtao.client.Gather;
import com.jiangtao.util.Configuration;
import com.jiangtao.util.ConfigurationAware;
import com.jiangtao.util.Log;
import com.jiangtao.util.impl.ConfigurationImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class ClientImpl implements Client, ConfigurationAware {

    private Configuration configuration;
    private String host;
    private int port;
    private Log logger;

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        this.logger = configuration.getLog();
    }

    @Override
    public void init(Properties prop) {
         this.host = prop.getProperty("host");
         this.port = Integer.parseInt(prop.getProperty("port"));
    }

    @Override
    public void send(List<Enviroment> list) {
        try {
            Socket socket = new Socket(host,port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(list);
            oos.flush();
            oos.close();
            socket.close();

        } catch (IOException e) {
            logger.error("send list failed~");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Configuration configuration = new ConfigurationImpl();
        Gather gather = configuration.getGather();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Enviroment> list = gather.gather();
                configuration.getClient().send(list);
            }
        },1 * 60 * 1000,30 * 1000);
    }

}
