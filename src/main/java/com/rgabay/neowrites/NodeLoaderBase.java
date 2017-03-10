package com.rgabay.neowrites;

import com.rgabay.neowrites.util.NeoBoltCommand;
import com.rgabay.neowrites.util.NeoHttpCommand;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rossgabay on 3/5/17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class NodeLoaderBase {
    private int nodesNum;
    private  int threadsNum;
    private  String neoUrl;

    public enum CommandType {
        BOLT,
        HTTP
    }

    public void loadNodes(String query, CommandType commandType) {
        log.info("generating "+ nodesNum * threadsNum + " nodes with threadsNum:" + threadsNum + " and nodesNum:" + nodesNum);

        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);
        DateTime dtStart = new DateTime();

        log.info("Start Time : {}",  dtStart.toString());

        DateTime dtEStart = new DateTime();

        for (int i = 0; i < threadsNum; i++) {

            Runnable command = commandType.equals(CommandType.BOLT)? new NeoBoltCommand(nodesNum, neoUrl, query) : new NeoHttpCommand(nodesNum, neoUrl, query);
            executor.execute(command);
        }

        DateTime dtEend = new DateTime();
        log.info("Threads created in : {}", new Period(dtEStart, dtEend));

        executor.shutdown();
        while (!executor.isTerminated());

        DateTime dtEnd = new DateTime();
        log.info("End Time : {}", dtEnd);
        log.info("Duration : {}", new Period(dtStart, dtEnd));

        System.exit(0); // status code here is the indicator of how much I care about all threads terminating properly
    }
}
