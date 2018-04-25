package com.rgabay.neowrites.util;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.v1.*;

import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by rossgabay on 3/7/17.
 */
@Slf4j
public  class NeoBoltCommand implements Runnable {
    private  Driver driver;

    private final int _nodesNum;

    public NeoBoltCommand(int _nodesNum, String _url, String _query) {
        this._nodesNum = _nodesNum;
        this._query = _query;

        try{
            this.driver = GraphDatabase.driver( _url, AuthTokens.basic( "", "" ),
                    Config.build().withMaxTransactionRetryTime( 15, SECONDS ).toConfig());
        }catch(Exception e){
            log.error("Boom!" + e );
            this.driver = GraphDatabase.driver( _url, AuthTokens.basic( "", "" ),
                    Config.build().withMaxTransactionRetryTime( 15, SECONDS ).toConfig());
        }

    }

    private final String _query;

    public void run() {
      try{
            Session session = driver.session();

        IntStream.range(0, _nodesNum)
                .forEach(i -> {
                    session.run(_query, Values.parameters("i", i) );
                });

        session.close();
        } catch (Exception e){
            log.error("WAWAWEEWA", e);
        }

        //driver.close();
    }
}
