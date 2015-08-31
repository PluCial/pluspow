package com.pluspow.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.pluspow.App;


public class EMailService {
    
    /**
     * 登録メール
     * @param recipientAddress
     * @param recipientName
     * @throws MessagingException 
     * @throws UnsupportedEncodingException 
     */
    public static void register(
            String recipientAddress, 
            String recipientName, 
            String registerUrl,
            boolean isLocal) throws UnsupportedEncodingException, MessagingException {
        
        // タイトル
        String subject = "[" + App.APP_DISPLAY_NAME + "] お申し込み完了のご案内";
        
        // メッセージ
        StringBuilder message = new StringBuilder();
        message.append(recipientName + " 様");
        message.append("\n\n");
        message.append("このたびは、" + App.APP_DISPLAY_NAME + "をお申し込みいただき、誠にありがとうございます。");
        message.append("\n\n");
        message.append("以下の内容でお申し込みを承りました。");
        message.append("\n");
        message.append("◆ お申し込み内容");
        message.append("\n");
        message.append("-------------------------------------------------");
        message.append("\n");
        message.append("◇ 利用プラン --- フリープラン");
        message.append("\n");
        message.append("◇ 名前 --- " + recipientName);
        message.append("\n");
        message.append("◇ メールアドレス --- " + recipientAddress);
        message.append("\n");
        message.append("-------------------------------------------------");
        message.append("\n\n\n");
        message.append("◆ ご利用開始方法");
        message.append("\n");
        message.append("下記URLをクリックし登録を完了してください。");
        message.append("\n");
        message.append(registerUrl);
        message.append("\n\n");
        message.append("今後とも" + App.APP_DISPLAY_NAME + "をどうぞよろしくお願いいたします。");
        
        send(recipientAddress, recipientName, subject, message.toString(), isLocal);
        
    }
    
    /**
     * パスワード再設定メール
     * @param recipientAddress
     * @param recipientName
     * @throws MessagingException 
     * @throws UnsupportedEncodingException 
     */
    public static void resetPassword(
            String mailAddress, 
            String userName, 
            String entryUrl,
            boolean isLocal) throws UnsupportedEncodingException, MessagingException {
        
        // タイトル
        String subject = "[" + App.APP_DISPLAY_NAME + "] パスワード再設定のご案内";
        
        // メッセージ
        StringBuilder message = new StringBuilder();
        message.append(userName + " 様");
        message.append("\n\n");
        message.append("いつも" + App.APP_DISPLAY_NAME + "をご利用頂き、誠にありがとうございます。");
        message.append("\n\n");
        message.append("パスワードの再設定方法についてご案内致します。");
        message.append("\n");
        message.append("◆ パスワード再設定方法");
        message.append("下記URLをクリックし、新しいパスワードの設定を行ってください。");
        message.append("\n");
        message.append(entryUrl);
        message.append("\n\n");
        message.append("今後とも" + App.APP_DISPLAY_NAME + "をどうぞよろしくお願いいたします。");
        
        send(mailAddress, userName, subject, message.toString(), isLocal);
    }
    
    /**
     * メール送信
     * @param recipientAddress
     * @param recipientName
     * @param subject
     * @param message
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    private static void send(
            String recipientAddress, 
            String recipientName, 
            String subject, 
            String message,
            boolean isLocal) throws UnsupportedEncodingException, MessagingException {
        
        if(isLocal) {
            System.out.println(message.toString());
            return;
        }
        
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage msg = new MimeMessage(session);

        //発信元情報：メールアドレス、名前
        msg.setFrom(new InternetAddress(App.EMAIL_FROM_ADDRESS, App.APP_DISPLAY_NAME, "ISO-2022-JP"));

        //送信先情報
        msg.addRecipient(Message.RecipientType.TO,
            new InternetAddress(recipientAddress, recipientName));

        msg.setSubject(subject, "ISO-2022-JP");
        msg.setText(message);

        Transport.send(msg);
        
    }

}
