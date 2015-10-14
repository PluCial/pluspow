package com.pluspow.model;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Email;
import com.pluspow.enums.Lang;

/**
 * Client登録エントリー
 * @author takahara
 *
 */
@Model(schemaVersion = 1)
public class SignupEntry extends Entry implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 利用言語
     */
    @Attribute(unindexed = true)
    private Lang lang;
    
    /**
     * 名前
     */
    @Attribute(unindexed = true)
    private String name;
    
    /**
     * メールアドレス
     */
    @Attribute(unindexed = true)
    private Email email;
    
    /**
     * パスワード
     */
    @Attribute(unindexed = true)
    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }
}
