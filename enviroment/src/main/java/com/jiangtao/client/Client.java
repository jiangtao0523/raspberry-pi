package com.jiangtao.client;

import com.jiangtao.bean.Enviroment;
import com.jiangtao.util.WossModel;

import java.util.List;

public interface Client extends WossModel {
    void send(List<Enviroment> list);
}
