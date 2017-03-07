package com.rgabay.neowrites.impl;

import com.rgabay.neowrites.NodeLoader;
import com.rgabay.neowrites.NodeLoaderBase;
import lombok.extern.slf4j.Slf4j;



/**
 * Created by rossgabay on 3/5/17.
 */
@Slf4j
public class HttpUnlabeledNodeLoader extends NodeLoaderBase implements NodeLoader {
    private static final String LOAD_QUERY = "Create (x:TestHttp)";

    public HttpUnlabeledNodeLoader(int nodesNum, int threadsNum, String neoUrl) {

        super(nodesNum, threadsNum, neoUrl);
    }

    @Override
    public void loadNodes(String query, CommandType commandType) {
        super.loadNodes(query, commandType);
    }

    @Override
    public void loadNodes() {
        this.loadNodes(LOAD_QUERY, CommandType.HTTP);
    }
}
