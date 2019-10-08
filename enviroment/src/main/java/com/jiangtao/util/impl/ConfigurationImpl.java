package com.jiangtao.util.impl;

import com.jiangtao.client.Client;
import com.jiangtao.client.Gather;
import com.jiangtao.server.DBStore;
import com.jiangtao.server.Server;
import com.jiangtao.util.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConfigurationImpl implements Configuration {

    private Map<String, WossModel> map = new HashMap<>();

    public ConfigurationImpl(){
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read("D:\\ideaworkspace\\BD1904\\enviroment\\src\\main\\resources\\env-config.xml");
            Element rootElement = document.getRootElement();
            List<Element> secondElements = rootElement.elements();
            for(Element se : secondElements){
                String elementName = se.getName();
                String clazz = se.attributeValue("class");
                WossModel wossModel  = (WossModel) Class.forName(clazz).newInstance();
                if(wossModel instanceof ConfigurationAware){
                    ((ConfigurationAware) wossModel).setConfiguration(this);
                }

                List<Element> thirdElement = se.elements();
                Properties properties = new Properties();
                for(Element te : thirdElement){
                    String name = te.getName();
                    String value = te.getText();
                    properties.setProperty(name,value);
                }
                wossModel.init(properties);
                map.put(elementName,wossModel);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Gather getGather() {
        return (Gather) map.get("Gather");
    }

    @Override
    public Client getClient() {
        return (Client) map.get("Client");
    }

    @Override
    public Server getServer() {
        return (Server) map.get("Server");
    }

    @Override
    public DBStore getDBStore() {
        return (DBStore) map.get("DBStore");
    }

    @Override
    public Log getLog() {
        return (Log) map.get("Log");
    }

    @Override
    public BackUp getBackUP() {
        return (BackUp) map.get("backUp");
    }

    /*public static void main(String[] args) {
        ConfigurationImpl configuration = new ConfigurationImpl();
        Gather gather = configuration.getGather();
        System.out.println(gather);

    }*/
}
