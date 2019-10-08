package com.jiangtao.util;

import com.jiangtao.bean.Enviroment;

import java.util.List;

public interface BackUp extends WossModel {
    void save(List<Enviroment> list);

    List<Enviroment> load();
}
