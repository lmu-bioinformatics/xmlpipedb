//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.04.22 at 10:23:59 AM PDT 
//

package org.uniprot.uniprot.impl.runtime;


/**
 * Generated classes have to implement this interface for it
 * to be unmarshallable.
 * 
 * @author      Kohsuke KAWAGUCHI
 */
public interface UnmarshallableObject
{
    /**
     * Creates an unmarshaller that will unmarshall this object.
     */
    UnmarshallingEventHandler createUnmarshaller( UnmarshallingContext context );
}
