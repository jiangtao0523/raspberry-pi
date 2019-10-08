package com.jiangtao.client.impl;

import com.jiangtao.bean.Enviroment;
import com.jiangtao.client.Gather;
import com.jiangtao.util.Configuration;
import com.jiangtao.util.ConfigurationAware;
import com.jiangtao.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class GatherImpl implements Gather, ConfigurationAware {

    private Configuration configuration;
    private String logsFile;
    private String skipFile;
    private Log logger;

    @Override
    public void init(Properties prop) {
        this.logsFile = prop.getProperty("logsFile");
        this.skipFile = prop.getProperty("skipFile");
    }


    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        this.logger = configuration.getLog();
    }

    @Override
    public List<Enviroment> gather() {
        List<Enviroment> list = new ArrayList<>();
        try {
            File logs = new File(logsFile);
            BufferedReader br = new BufferedReader(new FileReader(logs));
            File skips = new File(skipFile);
            if(skips.exists()){
                DataInputStream  dis = new DataInputStream(new FileInputStream(skips));
                long skip = dis.readLong();
                System.out.println(skip);
                br.skip(skip);
            }

            String line;
            while((line = br.readLine()) != null){
                if("".equals(line.trim())){
                    continue;
                }

                Enviroment enviroment = new Enviroment();
                String[] split = line.split("\\|");
                enviroment.setSrcId(Integer.parseInt(split[0]));
                enviroment.setDestId(Integer.parseInt(split[1]));
                enviroment.setDevId(Integer.parseInt(split[2]));
                enviroment.setSersorAddress(Integer.parseInt(split[3]));
                enviroment.setCount(Integer.parseInt(split[4]));
                enviroment.setCmd(Integer.parseInt(split[5]));
                enviroment.setStatus(Integer.parseInt(split[7]));
                enviroment.setGather_date(new Date(Long.parseLong(split[8])).getTime());

                int sensorAddress = Integer.parseInt(split[3]);
                String data = split[6];
                if(sensorAddress == 1280){
                    enviroment.setName("carbon dioxide");
                    String substring = data.substring(0, 4);
                    int carbonDioxide = Integer.parseInt(substring, 16);
                    enviroment.setData(carbonDioxide + "");
                    list.add(enviroment);
                }else if(sensorAddress == 256){
                    enviroment.setName("illumination intensity");
                    String substring = data.substring(0, 4);
                    int illuminationIntensity = Integer.parseInt(substring, 16);
                    enviroment.setData(illuminationIntensity + "");
                    list.add(enviroment);
                }else{
                    String substring1 = data.substring(0, 4);
                    String substring2 = data.substring(4, 8);
                    String temperature =  Integer.parseInt(substring1,16) * 0.00268127 - 46.85 + "";
                    String humidity = Integer.parseInt(substring2,16) * 0.00190735 - 6 + "";
                    enviroment.setName("temperature");
                    enviroment.setData(temperature);
                    Enviroment enviroment2 = new Enviroment(
                        enviroment.getSrcId(),enviroment.getDestId(),enviroment.getDevId(),enviroment.getSersorAddress(),
                            enviroment.getCount(),enviroment.getCmd(),humidity,enviroment.getStatus(),enviroment.getGather_date(),"humidity"
                    );

                    list.add(enviroment);
                    list.add(enviroment2);
                }
            }
            long length = logs.length();
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(skips));
            dos.writeLong(length);
            dos.flush();
            dos.close();

        } catch (Exception e) {
            logger.error("gather data failed ~");
            e.printStackTrace();
        }
        return list;
    }



}
