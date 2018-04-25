package com.rgabay.neowrites;

import com.beust.jcommander.JCommander;
import com.rgabay.neowrites.util.JCommanderSetup;
import lombok.extern.slf4j.Slf4j;

import java.util.IllegalFormatException;

@Slf4j
public class NodeLoaderDriver
{
    private static final  String DEFAULT_LOADER_TYPE = "HttpUnlabeledNodeLoader";
    private static final  String DEFAULT_NEO_URL = "http://localhost:7474/db/data/transaction/commit";
    private static final  int DEFAULT_THREADS_NUM = 1;
    private static final  int DEFAULT_NODES_NUM = 1;

    public static void main( String... args ){

       JCommanderSetup jcommanderSetup = new JCommanderSetup();

       new JCommander(jcommanderSetup, args);

       String loaderType = (jcommanderSetup.getNodeLoaderType() == null) ?  DEFAULT_LOADER_TYPE : jcommanderSetup.getNodeLoaderType();
       int threadsNum = (jcommanderSetup.getThreadsNum() == null) ?  DEFAULT_THREADS_NUM : jcommanderSetup.getThreadsNum();
       int nodesNum = (jcommanderSetup.getNodesNum() == null) ?  DEFAULT_NODES_NUM : jcommanderSetup.getNodesNum();
       String neoUrl = (jcommanderSetup.getNeoUrl() == null) ?  DEFAULT_NEO_URL : jcommanderSetup.getNeoUrl();


        log.info("loaderType: {}", loaderType);
        log.info("threadsNum: {}", threadsNum);
        log.info("nodesNum: {}", nodesNum);

       LoaderFactory loaderFactory = new LoaderFactory();

       loaderFactory.getNodeLoader(loaderType, threadsNum, nodesNum, neoUrl).ifPresent(NodeLoader::loadNodes);

    }
}
