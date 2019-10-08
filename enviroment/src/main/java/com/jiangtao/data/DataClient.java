package com.jiangtao.data;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DataClient {
    public static void main(String[] args) throws IOException {

        Timer t1 = new Timer();
        t1.schedule(new MyTimerTask(16), 5000, 10000);

        Timer t2 = new Timer();
        t2.schedule(new MyTimerTask(256),6000,10000);

        Timer t3 = new Timer();
        t3.schedule(new MyTimerTask(1280),7000,10000);
    }
}

class MyTimerTask extends TimerTask{
    private int sensorAddress;

    public MyTimerTask(int sensorAddress){
        this.sensorAddress = sensorAddress;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1",10000);
            PrintStream ps = new PrintStream(socket.getOutputStream(), true);
            ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<Message>\n" +
                    "        <SrcID>100</SrcID>\n" +
                    "        <DstID>101</DstID>\n" +
                    "        <DevID>2</DevID>\n" +
                    "        <SensorAddress>"+sensorAddress+"</SensorAddress>\n" +
                    "        <Counter>1</Counter>\n" +
                    "        <Cmd>3</Cmd>\n" +
                    "        <Status>1</Status>\n" +
                    "</Message>");

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
                if("</Message>".equals(line)) break;
            }

            SAXReader reader = new SAXReader();
            Document document = DocumentHelper.parseText(sb.toString());
            Element rootElement = document.getRootElement();
            List<Element> secondElements = rootElement.elements();
            String log = "";
            for(Element se : secondElements){
                String value = se.getText();
                log += (value + "|");
            }
            log = log + new Date().getTime() + "\r\n";

            FileOutputStream fos = new FileOutputStream("D:\\ideaworkspace\\BD1904\\enviroment\\src\\main\\java\\com\\jiangtao\\data\\templogs",true);
            fos.write(log.getBytes());
            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}