package com.rgabay.neowrites;

import com.beust.jcommander.JCommander;
import com.rgabay.neowrites.util.JCommanderSetup;
import lombok.extern.slf4j.Slf4j;

import java.util.IllegalFormatException;

@Slf4j
public class NodeLoaderDriver
{
    private static final  String DEFAULT_LOADER_TYPE = "UnlabeledNodeLoader";
    private static final  String DEFAULT_NEO_URL = "bolt://localhost:7687";
    private static final  int DEFAULT_THREADS_NUM = 10;
    private static final  int DEFAULT_NODES_NUM = 50;

    public static void main( String... args ){

       JCommanderSetup jcommanderSetup = new JCommanderSetup();

       new JCommander(jcommanderSetup, args);

       String loaderType = (jcommanderSetup.getNodeLoaderType() == null) ?  DEFAULT_LOADER_TYPE : jcommanderSetup.getNodeLoaderType();
       int threadsNum = (jcommanderSetup.getThreadsNum() == null) ?  DEFAULT_THREADS_NUM : jcommanderSetup.getThreadsNum();
       int nodesNum = (jcommanderSetup.getNodesNum() == null) ?  DEFAULT_NODES_NUM : jcommanderSetup.getNodesNum();
       String neoUrl = (jcommanderSetup.getNeoUrl() == null) ?  DEFAULT_NEO_URL : jcommanderSetup.getNeoUrl();

       log.info("loaderType: " + loaderType);

       LoaderFactory loaderFactory = new LoaderFactory();

       loaderFactory.getNodeLoader(loaderType, threadsNum, nodesNum, neoUrl).ifPresent(NodeLoader::loadNodes);

    }
}
