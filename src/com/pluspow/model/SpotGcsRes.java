package com.pluspow.model;

import java.io.Serializable;

import org.slim3.datastore.Model;

import com.pluspow.enums.SpotGcsResRole;

@Model(schemaVersion = 1)
public class SpotGcsRes extends GcsResources implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 役割
     */
    private SpotGcsResRole role;
    
    

    public SpotGcsResRole getRole() {
        return role;
    }

    public void setRole(SpotGcsResRole role) {
        this.role = role;
    }
}
