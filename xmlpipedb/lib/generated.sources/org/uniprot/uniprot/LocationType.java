//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.02.04 at 11:58:01 PST 
//


package org.uniprot.uniprot;


/**
 * A location can be either a position or have
 * both a begin and end.
 * Java content class for locationType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/XMLPipeDB/schema/hacked_uniprot.xsd line 557)
 * <p>
 * <pre>
 * &lt;complexType name="locationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="begin" type="{http://uniprot.org/uniprot}positionType"/>
 *           &lt;element name="end" type="{http://uniprot.org/uniprot}positionType"/>
 *         &lt;/sequence>
 *         &lt;element name="position" type="{http://uniprot.org/uniprot}positionType"/>
 *       &lt;/choice>
 *       &lt;attribute name="sequence" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface LocationType {


    /**
     * Gets the value of the sequence property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSequence();

    /**
     * Sets the value of the sequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSequence(java.lang.String value);

    /**
     * Gets the value of the begin property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.PositionType}
     */
    org.uniprot.uniprot.PositionType getBegin();

    /**
     * Sets the value of the begin property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.PositionType}
     */
    void setBegin(org.uniprot.uniprot.PositionType value);

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.PositionType}
     */
    org.uniprot.uniprot.PositionType getPosition();

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.PositionType}
     */
    void setPosition(org.uniprot.uniprot.PositionType value);

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.PositionType}
     */
    org.uniprot.uniprot.PositionType getEnd();

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.PositionType}
     */
    void setEnd(org.uniprot.uniprot.PositionType value);

    /**
     * 
     */
    public java.lang.String getIdInternal();

    public void setIdInternal(java.lang.String anId);

}
