package com.jiangtao.bean;

import java.io.Serializable;

public class Enviroment implements Serializable {
    private int srcId;
    private int destId;
    private int devId;
    private int sersorAddress;
    private int count;
    private int cmd;
    private String Data;
    private int status;
    private long gather_date;
    private String name;

    @Override
    public String toString() {
        return "Enviroment{" +
                "srcId=" + srcId +
                ", destId=" + destId +
                ", devId=" + devId +
                ", sersorAddress=" + sersorAddress +
                ", count=" + count +
                ", cmd=" + cmd +
                ", Data='" + Data + '\'' +
                ", status=" + status +
                ", gather_date=" + gather_date +
                ", name='" + name + '\'' +
                '}';
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getDestId() {
        return destId;
    }

    public void setDestId(int destId) {
        this.destId = destId;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }

    public int getSersorAddress() {
        return sersorAddress;
    }

    public void setSersorAddress(int sersorAddress) {
        this.sersorAddress = sersorAddress;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getGather_date() {
        return gather_date;
    }

    public void setGather_date(long gather_date) {
        this.gather_date = gather_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enviroment(int srcId, int destId, int devId, int sersorAddress, int count, int cmd, String data, int status, long gather_date, String name) {
        this.srcId = srcId;
        this.destId = destId;
        this.devId = devId;
        this.sersorAddress = sersorAddress;
        this.count = count;
        this.cmd = cmd;
        Data = data;
        this.status = status;
        this.gather_date = gather_date;
        this.name = name;
    }

    public Enviroment() {
    }
}
