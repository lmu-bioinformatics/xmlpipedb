//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.02.12 at 05:43:41 PM PST 
//


package org.uniprot.uniprot;


/**
 * Contains a collection of SPTr entries.
 * Java content class for uniprot element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/XMLPipeDB/schema/uniprot.xsd line 789)
 * <p>
 * <pre>
 * &lt;element name="uniprot">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{http://uniprot.org/uniprot}entry" maxOccurs="unbounded"/>
 *           &lt;element ref="{http://uniprot.org/uniprot}copyright" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface Uniprot
    extends javax.xml.bind.Element, org.uniprot.uniprot.UniprotType
{


}
