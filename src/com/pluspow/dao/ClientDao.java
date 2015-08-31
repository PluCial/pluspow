package com.pluspow.dao;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Email;
import com.pluspow.meta.ClientMeta;
import com.pluspow.model.Client;

public class ClientDao extends DaoBase<Client>{
    
    /** META */
    private static final ClientMeta meta = ClientMeta.get();
    
    public Client get(String email) {
        return Datastore.query(meta)
                .filter(
                    meta.email.equal(new Email(email))
                    )
                .asSingle();
    }

}
