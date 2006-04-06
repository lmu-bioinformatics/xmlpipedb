//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.03.28 at 08:50:02 PM PST 
//


package org.uniprot.uniprot;


/**
 * Contains all information on a certain isoform including references to possible features defining the sequence.
 * Java content class for isoformType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/xsd2db/db-gen/xsd//uniprot.xsd line 516)
 * <p>
 * <pre>
 * &lt;complexType name="isoformType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="name" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="sequence">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="type" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="not described"/>
 *                       &lt;enumeration value="described"/>
 *                       &lt;enumeration value="displayed"/>
 *                       &lt;enumeration value="external"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="note" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface IsoformType {


    /**
     * Gets the value of the sequence property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.IsoformType.SequenceType}
     */
    org.uniprot.uniprot.IsoformType.SequenceType getSequence();

    /**
     * Sets the value of the sequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.IsoformType.SequenceType}
     */
    void setSequence(org.uniprot.uniprot.IsoformType.SequenceType value);

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.IsoformType.NoteType}
     */
    org.uniprot.uniprot.IsoformType.NoteType getNote();

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.IsoformType.NoteType}
     */
    void setNote(org.uniprot.uniprot.IsoformType.NoteType value);

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
     * {@link org.uniprot.uniprot.IsoformType.NameType}
     * 
     */
    java.util.List getName();

    /**
     * Gets the value of the Id property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Id property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getId();

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
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/xsd2db/db-gen/xsd//uniprot.xsd line 523)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface NameType {


        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getValue();

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setValue(java.lang.String value);

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


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/xsd2db/db-gen/xsd//uniprot.xsd line 547)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface NoteType {


        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getValue();

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setValue(java.lang.String value);

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


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/xsd2db/db-gen/xsd//uniprot.xsd line 532)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="type" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="not described"/>
     *             &lt;enumeration value="described"/>
     *             &lt;enumeration value="displayed"/>
     *             &lt;enumeration value="external"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface SequenceType {


        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getType();

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setType(java.lang.String value);

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