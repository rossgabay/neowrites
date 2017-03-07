package com.rgabay.neowrites.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Created by rossgabay on 3/7/17.
 */
@AllArgsConstructor
@Slf4j
public class NeoHttpCommand implements Runnable {

    private final int _nodesNum;
    private final String _url;
    private final String _query;

    public void run() {
        JsonObject json = Json.createObjectBuilder()
                .add("statements", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("statement", _query))
                ).build();

        for (int i = 0; i < _nodesNum; i++) {
            try {
                HttpResponse<JsonNode> response = Unirest.post(_url)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(json.toString())
                        .asJson();
            } catch (UnirestException e) {
                log.error("Error communicating with the REST endpoint: " + e.getLocalizedMessage());
            }
        }
    }
}
