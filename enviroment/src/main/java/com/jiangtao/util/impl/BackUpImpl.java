package com.jiangtao.util.impl;

import com.jiangtao.bean.Enviroment;
import com.jiangtao.util.BackUp;
import com.jiangtao.util.Configuration;
import com.jiangtao.util.ConfigurationAware;
import com.jiangtao.util.Log;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class BackUpImpl implements BackUp, ConfigurationAware {

    private String path;
    private Configuration configuration;
    private Log logger;

    @Override
    public void save(List<Enviroment> list) {
        File file = new File(path);
        ObjectOutputStream oos = null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            oos = new ObjectOutputStream(new FileOutputStream(file,true));
            oos.writeObject(list);
            oos.flush();
            logger.info("backUp success ~");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Enviroment> load() {
        ObjectInputStream ois = null;
        Object o = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(path));
            o = ois.readObject();

            logger.info("load backUp ~");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (List<Enviroment>) o;
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        logger = configuration.getLog();
    }

    @Override
    public void init(Properties prop) {
        this.path = prop.getProperty("path");
    }
}
