package com.jiangtao.util;

public interface Log extends WossModel{
    void debug(String msg);
    void info(String msg);
    void warn(String msg);
    void error(String msg);
}
