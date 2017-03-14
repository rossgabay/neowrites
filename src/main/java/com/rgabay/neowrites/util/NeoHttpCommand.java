package com.rgabay.neowrites.util;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.stream.IntStream;

/**
 * Created by rossgabay on 3/7/17.
 */
@Slf4j
public class NeoHttpCommand implements Runnable {

    private final int _nodesNum;
    private final String _url;
    private JsonObject json;

    public NeoHttpCommand(int _nodesNum, String _url, String _query) {

        this._nodesNum = _nodesNum;
        this._url = _url;

        this.json = Json.createObjectBuilder()
                .add("statements", Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                .add("statement", _query))
                ).build();
    }

    public void run() {

        IntStream.range(0, _nodesNum)
                .forEach(i -> {
                    try {
                        post();
                    } catch (UnirestException e) {
                        log.error("Error communicating with the REST endpoint: " + e.getLocalizedMessage());
                    }
                });
    }

    private void post() throws UnirestException{

        Unirest.post(this._url)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(this.json.toString())
                .asJson();
    }
}
