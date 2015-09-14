package com.pluspow.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class ItemGcsRes extends GcsRes implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** Itemとの関連 */
    private ModelRef<Item> itemRef = new ModelRef<Item>(Item.class);

    public ModelRef<Item> getItemRef() {
        return itemRef;
    }
}
