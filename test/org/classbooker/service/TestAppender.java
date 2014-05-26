/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author Antares
 */
class TestAppender extends AppenderSkeleton {
    private final List<LoggingEvent> log = new ArrayList<LoggingEvent>();

    @Override
    protected void append(final LoggingEvent le) {
        log.add(le);
    }

    @Override
    public void close() {
        
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
    
    public List<LoggingEvent> getLog(){
        return new ArrayList<LoggingEvent>(log);
    }
}
