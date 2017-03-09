package com.rgabay.neowrites.util;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.v1.*;

/**
 * Created by rossgabay on 3/7/17.
 */
@Slf4j
public  class NeoBoltCommand implements Runnable {
    private final Driver driver;

    private final int _nodesNum;

    public NeoBoltCommand(int _nodesNum, String _url, String _query) {
        this._nodesNum = _nodesNum;
        this._query = _query;
        this.driver = GraphDatabase.driver( _url, AuthTokens.basic( "", "" ) );
    }

    private final String _query;

    public void run() {
        Session session = driver.session();

        for (int i = 1; i < _nodesNum; i++) {
            session.run(_query, Values.parameters("i", i) );
        }

        session.close();
        driver.close();
    }
}
