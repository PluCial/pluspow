package com.pluspow.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class ResetPasswordEntry extends Entry implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** Userとの関連 */
    private ModelRef<Client> clientRef = new ModelRef<Client>(Client.class);

    

    public ModelRef<Client> getClientRef() {
        return clientRef;
    }
}
