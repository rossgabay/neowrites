package com.rgabay.neowrites;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.v1.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.rgabay.neowrites.DaemonThreadFactory.daemon;
import static java.util.Objects.requireNonNull;
import static org.neo4j.driver.v1.Values.parameters;

/**
 * Created by rossgabay on 4/24/18.
 */
@Slf4j
public class TheDeadLock {

    private static Driver driver;
    private  ExecutorService executor;

    public static void main(String ... args){
        System.out.printf("Let's roll... \n");
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("", ""));

        TheDeadLock td = new TheDeadLock();

        try {
            td.transactionRunShouldFailOnDeadlocks();
        } catch (Exception e) {
            log.error("SOMETHING BAD HAPPENED");
            e.printStackTrace();
        }
    }

    public void transactionRunShouldFailOnDeadlocks() throws Exception
    {
        final int nodeId1 = 42;
        final int nodeId2 = 4242;
        final int newNodeId1 = 1;
        final int newNodeId2 = 2;

        createNodeWithId( nodeId1 );
        createNodeWithId( nodeId2 );

        final CountDownLatch latch1 = new CountDownLatch( 1 );
        final CountDownLatch latch2 = new CountDownLatch( 1 );

        Future<Void> result1 = executeInDifferentThread( () ->
        {
            try (Session session = driver.session();
                 Transaction tx = session.beginTransaction() )
            {
                // lock first node
                updateNodeId( tx, nodeId1, newNodeId1 ).consume();

                latch1.await();
                latch2.countDown();

                // lock second node
                updateNodeId( tx, nodeId2, newNodeId1 ).consume();

                tx.success();
            }
            return null;
        } );

        Future<Void> result2 = executeInDifferentThread( () ->
        {
            try ( Session session = driver.session();
                  Transaction tx = session.beginTransaction() )
            {
                // lock second node
                updateNodeId( tx, nodeId2, newNodeId2 ).consume();

                latch1.countDown();
                latch2.await();

                // lock first node
                updateNodeId( tx, nodeId1, newNodeId2 ).consume();

                tx.success();
            }
            return null;
        } );
        result1.get( 20, TimeUnit.SECONDS );
        result2.get( 20, TimeUnit.SECONDS );
    }


    private void createNodeWithId( int id )
    {
        try ( Session session = driver.session() )
        {
            session.run( "CREATE (n {id: {id}})", parameters( "id", id ) );
        }
    }

    private  <T> Future<T> executeInDifferentThread( Callable<T> callable )
    {
        if ( executor == null )
        {
            executor = Executors.newCachedThreadPool( daemon( getClass().getSimpleName() + "-thread-" ) );
        }
        return executor.submit( callable );
    }

    private static StatementResult updateNodeId( StatementRunner statementRunner, int currentId, int newId )
    {
        return statementRunner.run( "MATCH (n {id: {currentId}}) SET n.id = {newId}",
                parameters( "currentId", currentId, "newId", newId ) );
    }
}

class DaemonThreadFactory implements ThreadFactory
{
    private final String namePrefix;
    private final AtomicInteger threadId;

    public DaemonThreadFactory( String namePrefix )
    {
        this.namePrefix = requireNonNull( namePrefix );
        this.threadId = new AtomicInteger();
    }

    public static ThreadFactory daemon( String namePrefix )
    {
        return new DaemonThreadFactory( namePrefix );
    }

    @Override
    public Thread newThread( Runnable runnable )
    {
        Thread thread = new Thread( runnable );
        thread.setName( namePrefix + threadId.incrementAndGet() );
        thread.setDaemon( true );
        return thread;
    }

}