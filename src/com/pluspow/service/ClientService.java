package com.pluspow.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.pluspow.dao.ClientDao;
import com.pluspow.enums.SupportLang;
import com.pluspow.exception.UnsuitableException;
import com.pluspow.model.Client;


public class ClientService {
    
    /** DAO */
    private static final ClientDao dao = new ClientDao();
    
    /**
     * PUT
     * @param model
     * @return
     */
    public static Client put(Client model) {
        
        dao.put(model);
        
        return model;
    }
    
    /**
     * Get
     * @return
     */
    public static Client get(String email) {
        
        return dao.get(email);
    }
    
    /**
     * 承認
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public static Client login(String email, String password) throws Exception {
        Client client = get(email);
        
        // メールもしくはパスワードが違っている場合
        if(client == null || !client.getPassword().equals(getCipherPassword(client.getKey().getId(), password))) {
            return null;
        }
        
        return client;
    }
    
    /**
     * クライアント追加
     * @param email
     * @param password
     * @param name
     * @return
     * @throws UnsuitableException
     * @throws NoSuchAlgorithmException 
     */
    public static Client add(String name, String email, String password, SupportLang lang) throws UnsuitableException, NoSuchAlgorithmException {
        
        if(StringUtil.isEmpty(email)
                || StringUtil.isEmpty(password)
                || StringUtil.isEmpty(name)) {
            throw new UnsuitableException("登録情報が不十分です");
        }
        
        // 既に存在しているかチェック
        if(get(email) != null) {
            throw new UnsuitableException("このメールアドレスは既に登録されています。");
        }
        
        Key key = createKey();
        
        
        Client model = new Client();
        model.setKey(key);
        model.setName(name);
        model.setEmail(new Email(email));
        model.setPassword(getCipherPassword(key.getId(), password));
        model.setLang(lang);
        
        return put(model);
    }
    
    /**
     * パスワードの変更
     * @param model
     * @param password
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public static void updatePassword(Client model, String password) throws NoSuchAlgorithmException {
        model.setPassword(getCipherPassword(model.getKey().getId(), password));
        
        dao.put(model);
    }
    
    /**
     * パスワード暗号化
     * @return
     * @throws NoSuchAlgorithmException 
     */
    private static String getCipherPassword(long userId, String password) throws NoSuchAlgorithmException {
        StringBuilder buff = new StringBuilder();
        if (password != null && !password.isEmpty()) {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(String.valueOf(userId).getBytes());
            md.update(password.getBytes());
            byte[] digest = md.digest();
            for (byte d : digest) {
                buff.append((int)d&0xFF);
            }
        }
        return buff.toString();
    }
    
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    private static Key createKey() {
        return Datastore.allocateId(Client.class);
    }

}
