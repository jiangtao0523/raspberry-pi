package com.jiangtao.server;

import com.jiangtao.bean.Enviroment;
import com.jiangtao.util.WossModel;

import java.util.List;

public interface DBStore extends WossModel {
    void saveEnvs(List<Enviroment> list);
}
