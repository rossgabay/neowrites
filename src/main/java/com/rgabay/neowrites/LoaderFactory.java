package com.rgabay.neowrites;

import com.rgabay.neowrites.impl.HttpUnlabeledNodeLoader;
import com.rgabay.neowrites.impl.LabeledNodeLoader;
import com.rgabay.neowrites.impl.UnlabeledNodeLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by rossgabay on 3/3/17.
 */
public class LoaderFactory {
    private final static Map<String, Supplier<NodeLoader>> loaderMap = new HashMap<>();

   Optional<NodeLoader> getNodeLoader(String nodeLoaderType, int threadNum, int nodesNum){

       loaderMap.put("LabeledNodeLoader", () -> new LabeledNodeLoader(threadNum, nodesNum));
       loaderMap.put("UnlabeledNodeLoader", () -> new UnlabeledNodeLoader(threadNum, nodesNum));
       loaderMap.put("HttpUnlabeledNodeLoader",() -> new HttpUnlabeledNodeLoader(threadNum, nodesNum));

        Supplier<NodeLoader> nodeLoader = loaderMap.get(nodeLoaderType);
        return nodeLoader == null ? Optional.empty() : Optional.of(nodeLoader.get());
    }
}

