package edu.lmu.xmlpipedb.xmlToObject;

/*
 * MyValidationEventHandler.java
 */

/**
 *
 * @author klichong
 */

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.ValidationEventHandler;


public class MyValidationEventHandler implements ValidationEventHandler{
     
            
        public boolean handleEvent(ValidationEvent ve) {            
                if (ve.getSeverity()==ve.FATAL_ERROR ||  ve .getSeverity()==ve.ERROR) {
                    ValidationEventLocator  locator = ve.getLocator();
                    //print message from valdation event
                    System.out.println("Message is " + ve.getMessage());
                    //output line and column number
                    System.out.println("Column is " + locator.getColumnNumber() + " at line number " + locator.getLineNumber());                               
                }
            return true;
        }
      
    
}
