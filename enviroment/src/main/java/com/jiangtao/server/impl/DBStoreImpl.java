package com.jiangtao.server.impl;

import com.jiangtao.bean.Enviroment;
import com.jiangtao.server.DBStore;
import com.jiangtao.util.Configuration;
import com.jiangtao.util.ConfigurationAware;
import com.jiangtao.util.Log;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DBStoreImpl implements DBStore, ConfigurationAware {

    private String driver;
    private String url;
    private String user;
    private String password;
    private Configuration configuration;
    private Log logger;

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        this.logger = configuration.getLog();
    }

    @Override
    public void init(Properties prop) {
        this.driver = prop.getProperty("driver");
        this.url = prop.getProperty("url");
        this.user = prop.getProperty("user");
        this.password = prop.getProperty("password");
    }

    @Override
    public void saveEnvs(List<Enviroment> list) {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            String sql = "insert into enviroment values(?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            for(Enviroment en : list){
                ps.setInt(1,en.getSrcId());
                ps.setInt(2,en.getDestId());
                ps.setInt(3,en.getDevId());
                ps.setInt(4,en.getSersorAddress());
                ps.setInt(5,en.getCount());
                ps.setInt(6,en.getCmd());
                ps.setString(7,en.getData());
                ps.setInt(8,en.getStatus());
                ps.setTimestamp(9,new Timestamp(en.getGather_date()));
                ps.setString(10,en.getName());
                ps.execute();
            }
        } catch (Exception e) {
            logger.error("insert data failed ~");
            e.printStackTrace();
        } finally {
           try {
               if(ps != null) ps.close();
               if(connection != null) connection.close();
           }catch (SQLException e) {
               e.printStackTrace();
           }

        }
    }


}
