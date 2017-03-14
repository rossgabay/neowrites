package com.rgabay.neowrites.util;

import lombok.extern.slf4j.Slf4j;

import javax.json.Json;
import javax.json.JsonObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import okhttp3.*;

/**
 * Created by rossgabay on 3/7/17.
 */
@Slf4j
public class NeoHttpCommand implements Runnable {

    private final int _nodesNum;
    private final CountDownLatch lock;
    private final Request request;
    private  OkHttpClient client;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NeoHttpCommand(int _nodesNum, String _url, String _query) {
        this._nodesNum = _nodesNum;
        this.lock = new CountDownLatch(_nodesNum);

        ExecutorService httpExecutor = Executors.newFixedThreadPool(1);

        Dispatcher dispatcher = new Dispatcher(httpExecutor);
        dispatcher.setMaxRequestsPerHost(100);
        dispatcher.setMaxRequests(100);

        this.client = new OkHttpClient().newBuilder().dispatcher(dispatcher).build();

        JsonObject json = Json.createObjectBuilder()
                .add("statements", Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                .add("statement", _query))
                ).build();

        RequestBody body = RequestBody.create(JSON, json.toString());
        this.request = new Request.Builder()
                .url(_url)
                .post(body)
                .build();
    }

    public void run() {

        IntStream.range(0, _nodesNum)
                .forEach(i -> {
                    try {
                        post();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

       try {
            lock.await();
        } catch (InterruptedException e) {
            log.error("unexpected interruption + " + e.getLocalizedMessage());
        }
    }

    private void post() throws IOException {

        client.newCall(this.request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                NeoHttpCommand.this.lock.countDown();
            }

        });
    }

    //keeping this for a quick switch to sync requests if ever needed for sanity checks
    //don't forget to remove lock.await()...
    /* private void post() throws IOException {
         client.newCall(request).execute();
    }*/
}
