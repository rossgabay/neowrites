package com.rgabay.neowrites.impl;

import com.rgabay.neowrites.NodeLoader;
import com.rgabay.neowrites.NodeLoaderBase;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by rossgabay on 3/3/17.
 */
@Slf4j
public class UnlabeledNodeLoader extends NodeLoaderBase implements NodeLoader{

    public UnlabeledNodeLoader(int nodesNum, int threadNum) {
        super(nodesNum, threadNum);
    }
    public void loadNodes() {
        log.info("generating unlabeled nodes over bolt with threadNum:" + threadNum + " and nodesNum:" + nodesNum);
    }
}
