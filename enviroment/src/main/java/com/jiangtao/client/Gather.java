package com.jiangtao.client;

import com.jiangtao.bean.Enviroment;
import com.jiangtao.util.WossModel;

import java.util.List;

public interface Gather extends WossModel {
    List<Enviroment> gather();
}
