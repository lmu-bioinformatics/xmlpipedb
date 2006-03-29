//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.03.28 at 08:50:02 PM PST 
//


package org.uniprot.uniprot;


/**
 * Java content class for organismType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/xsd2db/db-gen/xsd//uniprot.xsd line 687)
 * <p>
 * <pre>
 * &lt;complexType name="organismType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://uniprot.org/uniprot}organismNameType" maxOccurs="unbounded"/>
 *         &lt;element name="dbReference" type="{http://uniprot.org/uniprot}dbReferenceType" maxOccurs="unbounded"/>
 *         &lt;element name="lineage" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="taxon" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface OrganismType {


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
     * Gets the value of the DbReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the DbReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDbReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.DbReferenceType}
     * 
     */
    java.util.List getDbReference();

    /**
     * Gets the value of the Name property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Name property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.OrganismNameType}
     * 
     */
    java.util.List getName();

    /**
     * Gets the value of the lineage property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.OrganismType.LineageType}
     */
    org.uniprot.uniprot.OrganismType.LineageType getLineage();

    /**
     * Sets the value of the lineage property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.OrganismType.LineageType}
     */
    void setLineage(org.uniprot.uniprot.OrganismType.LineageType value);

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


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/xsd2db/db-gen/xsd//uniprot.xsd line 692)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="taxon" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface LineageType {


        /**
         * Gets the value of the Taxon property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the Taxon property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTaxon().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link java.lang.String}
         * 
         */
        java.util.List getTaxon();

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

}
