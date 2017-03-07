package com.rgabay.neowrites.impl;

import com.rgabay.neowrites.NodeLoader;
import com.rgabay.neowrites.NodeLoaderBase;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by rossgabay on 3/3/17.
 */
@Slf4j
public class UnlabeledNodeLoader extends NodeLoaderBase implements NodeLoader{

    private static final String LOAD_QUERY = "Create (x:TestUnlabeled)";

    public UnlabeledNodeLoader(int nodesNum, int threadNum, String neoUrl) {
        super(nodesNum, threadNum, neoUrl);
    }

    @Override
    public void loadNodes(String query, CommandType commandType) {
        super.loadNodes(query, commandType);
    }

    @Override
    public void loadNodes() {
        this.loadNodes(LOAD_QUERY, CommandType.BOLT);
    }
}
