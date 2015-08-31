package com.pluspow.service;

import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Email;
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
        if(client == null || !client.getPassword().equals(password)) {
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
     */
    public static Client add(String name, String email, String password, SupportLang lang) throws UnsuitableException {
        
        if(StringUtil.isEmpty(email)
                || StringUtil.isEmpty(password)
                || StringUtil.isEmpty(name)) {
            throw new UnsuitableException("登録情報が不十分です");
        }
        
        // 既に存在しているかチェック
        if(get(email) != null) {
            throw new UnsuitableException("このメールアドレスは既に登録されています。");
        }
        
        Client model = new Client();
        model.setName(name);
        model.setEmail(new Email(email));
        model.setPassword(password);
        model.setLang(lang);
        
        return put(model);
    }
    
    /**
     * パスワードの変更
     * @param model
     * @param password
     * @return
     */
    public static void updatePassword(Client model, String password) {
        model.setPassword(password);
        
        dao.put(model);
    }

}
