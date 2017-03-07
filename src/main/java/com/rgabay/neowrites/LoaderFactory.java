package com.rgabay.neowrites;

import com.rgabay.neowrites.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by rossgabay on 3/3/17.
 */
public class LoaderFactory {
    private final static Map<String, Supplier<NodeLoader>> loaderMap = new HashMap<>();

   Optional<NodeLoader> getNodeLoader(String nodeLoaderType, int threadsNum, int nodesNum, String neoUrl){

       loaderMap.put("LabeledNodeLoader", () -> new LabeledNodeLoader(nodesNum, threadsNum, neoUrl));
       loaderMap.put("UnlabeledNodeLoader", () -> new UnlabeledNodeLoader(nodesNum, threadsNum, neoUrl));
       loaderMap.put("HttpUnlabeledNodeLoader",() -> new HttpUnlabeledNodeLoader(nodesNum, threadsNum, neoUrl));

        Supplier<NodeLoader> nodeLoader = loaderMap.get(nodeLoaderType);
        return nodeLoader == null ? Optional.empty() : Optional.of(nodeLoader.get());
    }
}

