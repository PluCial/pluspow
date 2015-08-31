package com.pluspow.dao;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.datastore.Sort;

import com.google.appengine.api.datastore.Query.SortDirection;
import com.pluspow.meta.TransHistoryMeta;
import com.pluspow.model.Spot;
import com.pluspow.model.TransHistory;

public class TransHistoryDao extends DaoBase<TransHistory>{
    
    /** META */
    private static final TransHistoryMeta meta = TransHistoryMeta.get();
    
    /**
     * ヒストリ一覧を取得
     * @param spot
     * @return
     */
    public S3QueryResultList<TransHistory> getHistoryList(Spot spot, int limit) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey())
                        )
                .sort(
                    new Sort(meta.createDate, SortDirection.DESCENDING)
                    ).limit(limit)
                    .asQueryResultList();
    }

}
