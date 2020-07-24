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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * @author cdwuchunzhi@jd.com
 * @version 1.0
 * @since 2020/4/21 20:50
 */

public class GetLoginTokenController extends Application {

    private TextField userTextField;

    private String timLineToken = "";

    private String nonce = "";

    private BufferedReader recvReader;

    private PrintWriter sendWriter;

    private TextArea authRequestReceive;

    private String loginResultString = "";

    private Button getloginTokenButton = null;

    private Button loginButton = null;

    private boolean loginResult = false;

    private String wholeAid = "";

    private String userId = "J8A9xJ_wHTk8Eb9GX9KJL";

    private Stage tempStage = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        try {
            //设置窗体的标题
            stage.setTitle("登录");
            //网格布局方式,创建一个GridPane面板
            GridPane grid = new GridPane();
            //改变grid的默认位置，默认情况下，grid的位置是在其父容器的左上方，此处父容器是Scene，现在将grid移至Scene的中间
            grid.setAlignment(Pos.CENTER);
            //是用来设置该网格每行和每列之间的水平间距和垂直间距的
            grid.setHgap(10);
            grid.setVgap(10);
            //设置了环绕在该网格面板上的填充距离，这里网格默认被设为在场景容器中居中，这里的填充距离是表示网格边缘距离里面内容的距离。
            // 设置内边距,传入的是一个Insets对象，该insets对象的参数是：上，左，下，右
            grid.setPadding(new Insets(25, 25, 25, 25));

            //<span style="font-size:14px">
            Text sceneTitle = new Text("模拟移动端登录");
            sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            sceneTitle.setFill(Color.ROYALBLUE);

            //设置倒影
            Reflection r = new Reflection();
            r.setFraction(0.7);
            sceneTitle.setEffect(r);

            //grid.add()方法，该方法，有五个参数，该参数不是每个都必填的，分别是要放入的组件，以及第几列，第几行，跨几列和跨几行。
            // 因此我们将Text组件放在第一行，第一列并且跨两列和跨一行；将‘UserName’的Label组件放在第二行第一列，TextField放在第二行第二列，
            // 将‘Password’的Lable组件放在第三行第一列，PasswordField放在第三行第二列。在grid中行和列是从0开始算起的。
            grid.add(sceneTitle, 1, 0, 4, 1);

            //用户名
            Label userName = new Label("用户名(如果不填默认是wuchunzhi):");
            //userName.set
            grid.add(userName, 0, 3);

            //用户名输入文本框
            userTextField = new TextField("J8A9xJ_wHTk8Eb9GX9KJL");
            grid.add(userTextField, 1, 3);

            //密码
            Label password = new Label("teamID(如果不填默认是00046419):");
            grid.add(password, 0, 4);

            //密码输入文本框
            final PasswordField passwordField = new PasswordField();
            grid.add(passwordField, 1, 4);
            //</span>

            //获取loginToken 按钮
            getloginTokenButton = new Button("获取loginToken");
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            hBox.getChildren().add(getloginTokenButton);
            grid.add(hBox, 1, 6);

            //添加一个文本框，用于显示信息的控制

            final TextArea actionTarget = new TextArea();
            actionTarget.setWrapText(true);
            actionTarget.setId("actionTarget");
            actionTarget.setEditable(false);
            grid.add(actionTarget, 1, 8);


            //添加一个文本框，用于显示信息的控制

            final TextArea authRequestSend = new TextArea();
            authRequestSend.setWrapText(true);
            authRequestSend.setId("authRequestSend");
            authRequestSend.setEditable(false);
            grid.add(authRequestSend, 2, 3,1,4);


            //添加一个文本框，用于显示信息的控制

            authRequestReceive = new TextArea();
            authRequestReceive.setWrapText(true);
            authRequestReceive.setEditable(false);
            authRequestReceive.setId("authRequestReceive");
            grid.add(authRequestReceive, 2, 7,1,4);


            //登录 按钮
            loginButton = new Button("使用loginToken登录");
            HBox h2Box = new HBox(10);
            h2Box.setAlignment(Pos.BOTTOM_RIGHT);
            loginButton.setVisible(false);
            h2Box.getChildren().add(loginButton);
            grid.add(h2Box, 1, 10);

            //获取loginToken
            getloginTokenButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(StringUtils.isNotEmpty(userTextField.getText())){
                        userId = userTextField.getText();
                    }


                    String teamId = passwordField.getText();
                    String getLoginTokenResult = JDMeLoginTest.timLineLoginHaveToken(userId,teamId);
                    actionTarget.setText(ForMatJSONStr.format(getLoginTokenResult));
                    boolean loginResult = false;
                    try{
                        JSONObject responseJson = JSON.parseObject(getLoginTokenResult);
                        if(responseJson == null){

                            System.out.println("responseJson IS null for responseResult:"+getLoginTokenResult);
                        }else{
                            String errorMsg = responseJson.getString("errorMsg");
                            if(StringUtils.isEmpty(errorMsg)){
                                System.out.println("errorMsg IS isEmpty for responseResult:"+getLoginTokenResult);
                            }
                            System.out.println("errorMsg IS :"+errorMsg);
                            if("success".equals(errorMsg)){
                                loginResult = true;
                                JSONObject contentJson = responseJson.getJSONObject("content");
                                if(contentJson != null){
                                    timLineToken = contentJson.getString("timLineToken");
                                    nonce = contentJson.getString("nonce");
                                }
                            }
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    //如果获取成功，再用loginToken来进行登录
                    if (loginResult) {
                        getloginTokenButton.setVisible(false);
                        loginButton.setVisible(true);
                    }
                }
            });


            //登录
            loginButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(StringUtils.isNotEmpty(userTextField.getText())){
                        userId = userTextField.getText();
                    }

                    String authSendStr = "{\"body\":" +
                            "{\"clientKind\":\"enterprise\"," +
                            "\"clientVersion\":\"6.3.0.1.20190729\"," +
                            "\"cr\":\""+nonce+"\"," +
                            "\"dvc\":\"1080303a53934d5b861819e15aeb8ac7\"," +
                            "\"ext\":{\"sdkName\":\"sdk_e\",\"sdkVersion\":\"1.5.0\"}," +
                            "\"netType\":\"internet\",\"presence\":\"chat\"," +
                            "\"token\":\""+timLineToken+"\"}," +
                            "\"from\":{\"app\":\"ee\",\"clientType\":\"android\",\"pin\":\""+userId+"\"}," +
                            "\"lang\":\"zh_CN\",\"len\":0,\"mid\":0,\"timestamp\":"+ChatTools.getTimeStampCurrent()+"," +
                            "\"to\":{\"app\":\"ee\",\"pin\":\"@im.jd.com\"},\"ver\":\"4.3\"," +
                            "\"id\":\""+ChatTools.getUUid()+"\",\"type\":\"auth\"}";
                    authRequestSend.setText(ForMatJSONStr.format(authSendStr));
                    // 10.172.234.0
                    // 11.42.68.80
                    JDMeLoginTest.setScoketConnection("10.172.234.0");
                    recvReader = JDMeLoginTest.getReader();
                    sendWriter = JDMeLoginTest.getWriter();
                    JDMeLoginTest.sendMessage(authSendStr);
                    Thread readerThread = null;
                    if (recvReader != null) { 			//如果可以开启聊天
                        readerThread = new Thread(new ReceMsgReader());
                        readerThread.start(); 		//开启新的线程，负责接收来自服务器端的消息
                    }

//                    while(!loginResult){
//                        switchToChat();
//                        //如果登录成功，并且切到聊天窗口成功，那么就关闭该线程。
////                        if( readerThread != null ){
////                            readerThread.interrupt();
////                        }
//                    }

                }
            });

            //表示为该舞台创建一个场景，上面的网格就是被安置在这个场景中的，该场景的大小为300和275个像素，oracle官网推荐，
            // 在创建场景的时候，好的做法是给该场景设置大小，如果不设置大小则会默认自动更具场景中的内容调整场景的大小，
            // 此外该场景的大小也直接决定于外围舞台的大小。
            Scene scene = new Scene(grid, 1400, 700);
            //场景引入css文件
            //scene.getStylesheets().add(LoginController.class.getResource("LoginController.css").toExternalForm());
            //将场景加入舞台中
            stage.setScene(scene);
            //让窗体显示
            stage.show();
            tempStage = stage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接收处理来自服务器端的消息
    public class ReceMsgReader implements Runnable {
        public void run() {
            String message;
            try {
                System.out.println("inner ReceMsgReader....");

                while ((message = recvReader.readLine()) != null) {
                    loginResultString = message;
                    System.out.println("message....is:"+message);
                    authRequestReceive.appendText(ForMatJSONStr.format(message) + "\r\n");


                    if(!loginResult && StringUtils.isNotEmpty(loginResultString)){
                        JSONObject loginResultJson = JSON.parseObject(loginResultString);
                        if(loginResultJson != null ){
                            String loginResultHas = loginResultJson.getString("type");
                            String aid = loginResultJson.getString("aid");
                            if(StringUtils.isNotEmpty(loginResultHas) && "auth_result".equals(loginResultHas)){
                                loginResult = true;
                                wholeAid = aid;
                                System.out.println("------------loginResult:"+loginResult);
                                switchToChat(tempStage);
                                return ;
                            }
                        }
                    }

                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Yes,I am interruted,but I am still running");
                        return;
                    }else{
                        System.out.println("not yet interrupted");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean switchToChat(Stage stage){
        //System.out.println("inner switchToChat  loginResult is:"+loginResult);

        //如果获取成功，再用loginToken来进行登录
        if (loginResult) {
            System.out.println("inner switchToChat  loginResult is:"+loginResult);

            //登录成功
            //actionTarget.setFill(Color.GREENYELLOW);
            //actionTarget.setText("欢迎登录...");
            //Stage newStage = new Stage();
            Platform.runLater(new Runnable() {
                public void run() {
                    try {
                        if(getloginTokenButton != null){
                            getloginTokenButton.setVisible(false);
                        }
                        if(loginButton != null){
                            loginButton.setVisible(false);
                        }

                        if(stage != null){
                            stage.close();
                        }

                        new LoginController(wholeAid,userId).start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return loginResult;
    }
}
