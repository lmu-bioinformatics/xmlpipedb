//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.29 at 11:27:53 PM PDT 
//


package org.uniprot.uniprot;


/**
 * Describes a collection of UniProtKB entries.
 * Java content class for uniprot element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/dondi/Downloads/xsd2db/db-gen/xsd//uniprot.xsd line 18)
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
