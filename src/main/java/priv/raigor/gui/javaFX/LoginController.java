/*
 * Copyright (c) 1998, 2020, Jd.com and/or its affiliates. All rights reserved.
 * JD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package priv.raigor.gui.javaFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * 音乐播放主页面
 * @author cdwuchunzhi@jd.com
 * @version 1.0
 * @since 2020/4/21 20:55
 */

public class LoginController extends Application {

    private BufferedReader recvReader = null;
    private PrintWriter sendWriter;
    private String aid = "";
    private String fromUserId = "";

    private boolean rollingFlag = true;
    public LoginController(String aid,String fromUserId){
        super();
        this.recvReader = JDMeLoginTest.getReader();
        this.sendWriter = JDMeLoginTest.getWriter();
        this.aid = aid;
        this.fromUserId = fromUserId;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        try {
            stage.setTitle("收发消息");
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(25, 25, 25, 25));

            //添加一个文本框，用于显示信息的控制

            final TextArea messageReceiveArea = new TextArea();
            messageReceiveArea.setWrapText(true);
            messageReceiveArea.setId("messageReceiveArea");
            messageReceiveArea.setEditable(false);
            messageReceiveArea.setPrefSize(500,700);
            gridPane.add(messageReceiveArea, 1, 1,6,4);

            final TextArea messageSendArea = new TextArea();
            messageSendArea.setWrapText(true);
            messageSendArea.setId("messageSendArea");
            messageSendArea.setEditable(true);
            gridPane.add(messageSendArea, 1, 5,6,4);


            Button sendMessageButton = new Button("发送协议消息");
            //playButton.setPadding(new Insets(5, 5, 5, 5));
            Button rollingButton = new Button("停止滚动");

            Button clearReceiveButton = new Button("清空接收框");

            Button clearSendButton = new Button("清空发送框");

            Button produceSingleMessageButton = new Button("生成单聊消息");
            Button produceGroupMessageButton = new Button("生成群聊消息");

            clearReceiveButton.setPrefSize(100,20);
            clearSendButton.setPrefSize(100,20);

            sendMessageButton.setPrefSize(100,20);
            rollingButton.setPrefSize(100,20);

            produceSingleMessageButton.setPrefSize(100,20);
            produceGroupMessageButton.setPrefSize(100,20);
            //exitButton.setPadding(new Insets(5, 5, 5, 5));
            HBox hBox = new HBox(10);
            hBox.setPadding(new Insets(15, 12, 15, 12));
            hBox.setAlignment(Pos.CENTER);
            //hBox.getChildren().add(playButton);
            hBox.getChildren().addAll(sendMessageButton, rollingButton, clearReceiveButton, clearSendButton,produceSingleMessageButton,produceGroupMessageButton);
            gridPane.add(sendMessageButton, 1, 9);
            gridPane.add(rollingButton, 2, 9);
            gridPane.add(clearReceiveButton, 3, 9);
            gridPane.add(clearSendButton, 4, 9);
            gridPane.add(produceSingleMessageButton, 5, 9);
            gridPane.add(produceGroupMessageButton, 6, 9);

            sendMessageButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String sendMessage = messageSendArea.getText();
                    System.out.println("sendMessage is:"+sendMessage);
                    if(StringUtils.isNotEmpty(sendMessage)){
                        JDMeLoginTest.sendMessage(sendMessage);
                    }
                }
            });

            rollingButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    rollingFlag = !rollingFlag;
                    if(rollingFlag){
                        rollingButton.setText("停止滚动");
                    }else{
                        rollingButton.setText("滚动收到的消息");
                    }
                }
            });


            clearReceiveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    messageReceiveArea.setText("");
                }
            });

            clearSendButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    messageSendArea.setText("");
                }
            });


            produceSingleMessageButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    messageSendArea.setText("{\"aid\":\""+aid+"\"," +
                            "\"body\":{\"content\":\"测试单聊消息"+ChatTools.getRandom()+"\"," +
                            "\"type\":\"text\"},\"datetime\":"+ChatTools.getTimeStampCurrent()+"," +
                            "\"from\":{\"app\":\"ee\",\"clientType\":\"android\",\"pin\":\""+fromUserId+"\"}," +
                            "\"id\":\""+ChatTools.getUUid()+"\",\"lang\":\"zh_CN\",\"len\":0,\"mid\":0,\"timestamp\":"+ChatTools.getTimeStampCurrent()+"," +
                            "\"to\":{\"app\":\"ee\",\"pin\":\"testuserid\"},\"type\":\"chat_message\",\"ver\":\"4.3\"}");
                }
            });


            produceGroupMessageButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    messageSendArea.setText("{\"timestamp\":"+ChatTools.getTimeStampCurrent()+",\"id\":\""+ChatTools.getUUid()+"\"," +
                            "\"body\":{\"content\":\"测试群聊消息"+ChatTools.getRandom()+"\",\"type\":\"text\"}," +
                            "\"gid\":\"174536381840752641\",\"ver\":\"4.3\",\"mid\":0," +
                            "\"from\":{\"app\":\"ee\",\"teamId\":\"00046419\",\"pin\":\""+fromUserId+"\",\"clientType\":\"android\"}," +
                            "\"aid\":\""+aid+"\",\"type\":\"chat_message\",\"lang\":\"zh_CN\"}");
                }
            });


            /*Button exitButton = new Button("注销");
            HBox exitButtonHBox = new HBox(10);
            exitButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
            exitButtonHBox.getChildren().add(exitButton);
            gridPane.add(exitButton, 6, 6);*/

            Scene scene = new Scene(gridPane, 700, 700);
            //scene.getStylesheets().add(MusicPlayerController.class.getResource("MusicPlayerController.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });
            if (recvReader != null) { 			//如果可以开启聊天
                Thread readerThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String message;
                        try {
                            System.out.println("inner ReceMsgReader....");
                            while ((message = recvReader.readLine()) != null) {
                                System.out.println("message....is:"+message);
                                if(rollingFlag){
                                    messageReceiveArea.appendText("-------------------------------------\r\n");
                                    messageReceiveArea.appendText(ForMatJSONStr.format(message) + "\r\n");
                                }
                            }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }});
                readerThread.start(); 		//开启新的线程，负责接收来自服务器端的消息
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}