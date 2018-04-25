package com.rgabay.neowrites.impl;

import com.rgabay.neowrites.NodeLoader;
import com.rgabay.neowrites.NodeLoaderBase;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by rossgabay on 3/3/17.
 */
@Slf4j
public class UnlabeledNodeLoader extends NodeLoaderBase implements NodeLoader{

    private static final String LOAD_QUERY = "Create (x)";

    //HACK to force locks on a REL update - only one rel in the graph
   //private static final String LOAD_QUERY = "match ()-[r]->() set r.t = 10";


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
