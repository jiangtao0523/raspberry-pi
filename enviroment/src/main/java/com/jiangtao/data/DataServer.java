package com.jiangtao.data;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class DataServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socketServer = new ServerSocket(10000);

        while(true){
            Socket socket = socketServer.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null){
                            sb.append(line);
                            if("</Message>".equals(line)) break;
                        }

                        SAXReader reader = new SAXReader();
                        Document document = DocumentHelper.parseText(sb.toString());
                        Element rootElement = document.getRootElement();
                        List<Element> secondElements = rootElement.elements();
                        String data = null;
                        for(Element se : secondElements){
                            if("SensorAddress".equals(se.getName())){
                                int sensorAddress = Integer.parseInt(se.getText());
                                if(sensorAddress == 16){
                                    String temperature = randomHexString();
                                    String humidity = randomHexString();
                                    String result = temperature + humidity + "02";
                                    data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                            "<Message>\n" +
                                            "    <SrcID>100</SrcID>\n" +
                                            "    <DstID>101</DstID>\n" +
                                            "    <DevID>2</DevID>\n" +
                                            "    <SensorAddress>"+sensorAddress+"</SensorAddress>\n" +
                                            "    <Counter>1</Counter>\n" +
                                            "    <Cmd>3</Cmd>\n" +
                                            "    <Data>"+result+"</Data>\n" +
                                            "    <Status>1</Status>\n" +
                                            "</Message>";
                                }else if(sensorAddress == 256){
                                    String result = randomHexString() + "03";
                                    data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                            "<Message>\n" +
                                            "   <SrcID>100</SrcID>\n" +
                                            "   <DstID>101</DstID>\n" +
                                            "   <DevID>2</DevID>\n" +
                                            "   <SensorAddress>"+sensorAddress+"</SensorAddress>\n" +
                                            "   <Counter>1</Counter>\n" +
                                            "   <Cmd>3</Cmd>\n" +
                                            "   <Data>"+result+"</Data>\n" +
                                            "   <Status>1</Status>\n" +
                                            "</Message>";
                                }else {
                                    String result = randomHexString() + "01";
                                    data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                            "<Message>\n" +
                                            "   <SrcID>100</SrcID>\n" +
                                            "   <DstID>101</DstID>\n" +
                                            "   <DevID>2</DevID>\n" +
                                            "   <SensorAddress>"+sensorAddress+"</SensorAddress>\n" +
                                            "   <Counter>1</Counter>\n" +
                                            "   <Cmd>3</Cmd>\n" +
                                            "   <Data>"+result+"</Data>\n" +
                                            "   <Status>1</Status>\n" +
                                            "</Message>";
                                }
                            }
                        }

                        PrintStream ps = new PrintStream(socket.getOutputStream(),true);
                        ps.println(data);


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static String randomHexString(){
        StringBuffer result = new StringBuffer();
        for(int i=0;i<4;i++) {
            result.append(Integer.toHexString(new Random().nextInt(16)));
        }
        return result.toString();
    }


}
