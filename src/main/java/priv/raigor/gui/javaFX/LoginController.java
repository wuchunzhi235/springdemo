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
    public LoginController(){
        super();
        this.recvReader = JDMeLoginTest.getReader();
        this.sendWriter = JDMeLoginTest.getWriter();
    }

    //private Button playButton;

    private MediaPlayer mp1;

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
            gridPane.add(messageReceiveArea, 2, 3,1,4);

            final TextArea messageSendArea = new TextArea();
            messageSendArea.setWrapText(true);
            messageSendArea.setId("messageSendArea");
            messageSendArea.setEditable(false);
            gridPane.add(messageSendArea, 2, 3,1,4);


            Button sendMessageButton = new Button("发送协议消息");
            //playButton.setPadding(new Insets(5, 5, 5, 5));
            Button rollingButton = new Button("滚动收到的消息");
            sendMessageButton.setPrefSize(100,20);
            rollingButton.setPrefSize(100,20);
            //exitButton.setPadding(new Insets(5, 5, 5, 5));
            HBox hBox = new HBox(10);
            hBox.setPadding(new Insets(15, 12, 15, 12));
            hBox.setAlignment(Pos.CENTER);
            //hBox.getChildren().add(playButton);
            hBox.getChildren().addAll(sendMessageButton, rollingButton);
            gridPane.add(sendMessageButton, 1, 6);
            gridPane.add(rollingButton, 12, 6);

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
                    mp1.stop();
                    stage.close();
                    GetLoginTokenController getLoginTokenController = new GetLoginTokenController();
                    Stage newStage = new Stage();
                    getLoginTokenController.start(newStage);
                }
            });


            /*Button exitButton = new Button("注销");
            HBox exitButtonHBox = new HBox(10);
            exitButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
            exitButtonHBox.getChildren().add(exitButton);
            gridPane.add(exitButton, 6, 6);*/

            Scene scene = new Scene(gridPane, 1500, 700);
            //scene.getStylesheets().add(MusicPlayerController.class.getResource("MusicPlayerController.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

            if (recvReader != null) { 			//如果可以开启聊天
                Thread readerThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String message;
                        try {
                            System.out.println("inner ReceMsgReader....");
                            while ((message = recvReader.readLine()) != null) {
                                System.out.println("message....is:"+message);
                                messageReceiveArea.appendText(ForMatJSONStr.format(message) + "\r\n");
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