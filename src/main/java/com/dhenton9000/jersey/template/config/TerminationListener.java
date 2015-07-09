/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.config;

import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 *
 * @author dhenton
 */
public class TerminationListener
        implements ContainerLifecycleListener {

    static Logger LOG = LoggerFactory.getLogger(TerminationListener.class);

    @Override
    public void onStartup(Container container) {
        LOG.info("starting.....");

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    TerminationListener.this.stop();
                } catch (Exception e) {
                    LOG.error("Problem with shutdown", e);
                }
            }
        });
    }

    @Override
    public void onReload(Container container) {

    }

    public void stop() {
        LOG.info("service stopping");
    }

    @Override
    public void onShutdown(Container container) {
        stop();
    }
}
