package com.rgabay.neowrites.impl;

import com.rgabay.neowrites.NodeLoader;
import com.rgabay.neowrites.NodeLoaderBase;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by rossgabay on 3/5/17.
 */
@Slf4j
public class HttpUnlabeledNodeLoader extends NodeLoaderBase implements NodeLoader {
    public HttpUnlabeledNodeLoader(int nodesNum, int threadNum) {
        super(nodesNum, threadNum);
    }

    @Override
    public void loadNodes() {
        log.info("generating unlabeled nodes over http with threadNum:" + threadNum + " and nodesNum:" + nodesNum);
    }
}
