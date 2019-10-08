package com.jiangtao.util.impl;

import com.jiangtao.util.Log;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

public class LogImpl implements Log {

    private Logger logger = Logger.getLogger(LogImpl.class);

    public LogImpl() {
        PropertyConfigurator.configure("D:\\ideaworkspace\\BD1904\\enviroment\\src\\main\\resources\\log4j.properties");
    }

    @Override
    public void debug(String msg) {
        logger.debug(msg);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void warn(String msg) {
        logger.warn(msg);
    }

    @Override
    public void error(String msg) {
        logger.error(msg);
    }

    @Override
    public void init(Properties prop) {

    }
}
