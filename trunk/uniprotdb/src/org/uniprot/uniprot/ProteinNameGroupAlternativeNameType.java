//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.07 at 10:24:02 PM PST 
//


package org.uniprot.uniprot;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/dondi/Documents/Main/Eclipse/research/xmlpipedb-uniprotdb/xsd//uniprot.xsd line 682)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fullName" type="{http://uniprot.org/uniprot}evidencedStringType" minOccurs="0"/>
 *         &lt;element name="shortName" type="{http://uniprot.org/uniprot}evidencedStringType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ProteinNameGroupAlternativeNameType {


    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getRef();

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setRef(java.lang.String value);

    /**
     * Gets the value of the fullName property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.EvidencedStringType}
     */
    org.uniprot.uniprot.EvidencedStringType getFullName();

    /**
     * Sets the value of the fullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.EvidencedStringType}
     */
    void setFullName(org.uniprot.uniprot.EvidencedStringType value);

    /**
     * Gets the value of the ShortName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ShortName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShortName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.EvidencedStringType}
     * 
     */
    java.util.List getShortName();

    /**
     * Gets the value of the hjid property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Long}
     */
    java.lang.Long getHjid();

    /**
     * Sets the value of the hjid property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Long}
     */
    void setHjid(java.lang.Long value);

    /**
     * Gets the value of the hjversion property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Long}
     */
    java.lang.Long getHjversion();

    /**
     * Sets the value of the hjversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Long}
     */
    void setHjversion(java.lang.Long value);

}
