//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.02.04 at 11:58:01 PST 
//


package org.uniprot.uniprot;


/**
 * Stores all information of the reference block in SPTr (RN, RP, RC, RX, RA, RT and RL line).
 * Java content class for referenceType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/XMLPipeDB/schema/hacked_uniprot.xsd line 343)
 * <p>
 * <pre>
 * &lt;complexType name="referenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="citation" type="{http://uniprot.org/uniprot}citationType"/>
 *         &lt;group ref="{http://uniprot.org/uniprot}sptrCitationGroup"/>
 *       &lt;/sequence>
 *       &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ReferenceType {


    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getKey();

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setKey(java.lang.String value);

    /**
     * Gets the value of the citation property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.CitationType}
     */
    org.uniprot.uniprot.CitationType getCitation();

    /**
     * Sets the value of the citation property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.CitationType}
     */
    void setCitation(org.uniprot.uniprot.CitationType value);

    /**
     * Contains a scope regarding a citation. There is no classification yet. (RP lines).Gets the value of the Scope property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Scope property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScope().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getScope();

    /**
     * Gets the value of the evidence property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getEvidence();

    /**
     * Sets the value of the evidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setEvidence(java.lang.String value);

    /**
     * Contains all information about the source this citation is referring to (RC line).
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.SourceDataType}
     */
    org.uniprot.uniprot.SourceDataType getSource();

    /**
     * Contains all information about the source this citation is referring to (RC line).
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.SourceDataType}
     */
    void setSource(org.uniprot.uniprot.SourceDataType value);

    /**
     * Contains a scope regarding a citation. There is no classification yet. (RP lines).Gets the value of the Scope property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Scope property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScope().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    public java.util.List getScopeInternal();

    public void setScopeInternal(java.util.List theScopeInternal);

    /**
     * 
     */
    public java.lang.String getIdInternal();

    public void setIdInternal(java.lang.String anId);

}
