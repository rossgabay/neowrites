package com.rgabay.neowrites;

import com.beust.jcommander.JCommander;
import com.rgabay.neowrites.util.JCommanderSetup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NodeLoaderDriver
{
    private static final  String DEFAULT_LOADER_TYPE = "UnlabeledNodeLoader";
    private static final  int DEFAULT_THREAD_NUM = 10;
    private static final  int DEFAULT_NODES_NUM = 50;

    public static void main( String... args ){


       JCommanderSetup jcommanderSetup = new JCommanderSetup();

       new JCommander(jcommanderSetup, args);

       String loaderType = (jcommanderSetup.getNodeLoaderType() == null) ?  DEFAULT_LOADER_TYPE : jcommanderSetup.getNodeLoaderType();
       int threadNum = (jcommanderSetup.getNodeLoaderType() == null) ?  DEFAULT_THREAD_NUM : jcommanderSetup.getThreadNum();
       int nodesNum = (jcommanderSetup.getNodeLoaderType() == null) ?  DEFAULT_NODES_NUM : jcommanderSetup.getNodesNum();


        log.info("loaderType: " + loaderType);
        log.info("threadNum: " + threadNum);
        log.info("nodesNum: " + nodesNum);

       LoaderFactory loaderFactory = new LoaderFactory();

       loaderFactory.getNodeLoader(loaderType, threadNum, nodesNum).ifPresent(NodeLoader::loadNodes);

    }
}
