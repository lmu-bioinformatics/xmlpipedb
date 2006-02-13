//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.02.12 at 05:43:41 PM PST 
//


package org.uniprot.uniprot;


/**
 * The comment element stores all information found in the CC lines of the flatfile format. If there is a defined structure to the CC comment, the extracted is displayed in the various defined attributes and child-elements. See the documentation of these attributes/elements for more details.
 * Java content class for commentType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/XMLPipeDB/schema/uniprot.xsd line 381)
 * <p>
 * <pre>
 * &lt;complexType name="commentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;group ref="{http://uniprot.org/uniprot}bpcCommentGroup"/>
 *         &lt;choice minOccurs="0">
 *           &lt;sequence>
 *             &lt;element name="link" maxOccurs="unbounded" minOccurs="0">
 *               &lt;complexType>
 *                 &lt;complexContent>
 *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                     &lt;attribute name="uri" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *                   &lt;/restriction>
 *                 &lt;/complexContent>
 *               &lt;/complexType>
 *             &lt;/element>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="location" type="{http://uniprot.org/uniprot}locationType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="event" type="{http://uniprot.org/uniprot}eventType" maxOccurs="3"/>
 *             &lt;element name="isoform" type="{http://uniprot.org/uniprot}isoformType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="interactant" type="{http://uniprot.org/uniprot}interactantType" maxOccurs="2" minOccurs="2"/>
 *             &lt;element name="organismsDiffer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *             &lt;element name="experiments" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="error" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="locationType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mass" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="method" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="allergen"/>
 *             &lt;enumeration value="alternative products"/>
 *             &lt;enumeration value="biotechnology"/>
 *             &lt;enumeration value="biophysicochemical properties"/>
 *             &lt;enumeration value="catalytic activity"/>
 *             &lt;enumeration value="caution"/>
 *             &lt;enumeration value="cofactor"/>
 *             &lt;enumeration value="developmental stage"/>
 *             &lt;enumeration value="disease"/>
 *             &lt;enumeration value="domain"/>
 *             &lt;enumeration value="enzyme regulation"/>
 *             &lt;enumeration value="function"/>
 *             &lt;enumeration value="induction"/>
 *             &lt;enumeration value="miscellaneous"/>
 *             &lt;enumeration value="pathway"/>
 *             &lt;enumeration value="pharmaceutical"/>
 *             &lt;enumeration value="polymorphism"/>
 *             &lt;enumeration value="PTM"/>
 *             &lt;enumeration value="RNA editing"/>
 *             &lt;enumeration value="similarity"/>
 *             &lt;enumeration value="subcellular location"/>
 *             &lt;enumeration value="subunit"/>
 *             &lt;enumeration value="tissue specificity"/>
 *             &lt;enumeration value="toxic dose"/>
 *             &lt;enumeration value="online information"/>
 *             &lt;enumeration value="mass spectrometry"/>
 *             &lt;enumeration value="interaction"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface CommentType {


    /**
     * This stored the URIs defined in the WWW and FTP tokens of the database (online information in the XML format) CC comment type.Gets the value of the Link property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Link property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.CommentType.LinkType}
     * 
     */
    java.util.List getLink();

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getStatus();

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setStatus(java.lang.String value);

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
     * Gets the value of the experiments property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger}
     */
    java.math.BigInteger getExperiments();

    /**
     * Sets the value of the experiments property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger}
     */
    void setExperiments(java.math.BigInteger value);

    /**
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getMethod();

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setMethod(java.lang.String value);

    /**
     * If a CC line type does not have a defined structure, the text of this comment is stored in the element.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getText();

    /**
     * If a CC line type does not have a defined structure, the text of this comment is stored in the element.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setText(java.lang.String value);

    /**
     * Gets the value of the phDependence property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getPhDependence();

    /**
     * Sets the value of the phDependence property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setPhDependence(java.lang.String value);

    /**
     * Gets the value of the kinetics property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.BpcCommentGroupKineticsType}
     */
    org.uniprot.uniprot.BpcCommentGroupKineticsType getKinetics();

    /**
     * Sets the value of the kinetics property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.BpcCommentGroupKineticsType}
     */
    void setKinetics(org.uniprot.uniprot.BpcCommentGroupKineticsType value);

    /**
     * Gets the value of the locationType property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getLocationType();

    /**
     * Sets the value of the locationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setLocationType(java.lang.String value);

    /**
     * Gets the value of the Isoform property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Isoform property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIsoform().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.IsoformType}
     * 
     */
    java.util.List getIsoform();

    /**
     * Gets the value of the mass property.
     * 
     */
    float getMass();

    /**
     * Sets the value of the mass property.
     * 
     */
    void setMass(float value);

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getError();

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setError(java.lang.String value);

    /**
     * If a CC line type contains a "NOTE=", the text of that note is stored in this element.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getNote();

    /**
     * If a CC line type contains a "NOTE=", the text of that note is stored in this element.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setNote(java.lang.String value);

    /**
     * Gets the value of the Event property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Event property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.EventType}
     * 
     */
    java.util.List getEvent();

    /**
     * Gets the value of the temperatureDependence property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getTemperatureDependence();

    /**
     * Sets the value of the temperatureDependence property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setTemperatureDependence(java.lang.String value);

    /**
     * Gets the value of the organismsDiffer property.
     * 
     */
    boolean isOrganismsDiffer();

    /**
     * Sets the value of the organismsDiffer property.
     * 
     */
    void setOrganismsDiffer(boolean value);

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getName();

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setName(java.lang.String value);

    /**
     * Gets the value of the redoxPotential property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getRedoxPotential();

    /**
     * Sets the value of the redoxPotential property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setRedoxPotential(java.lang.String value);

    /**
     * The information of the mass spectrometry comment is stored in the attributes:
     * -molWeight (molecular weight)
     * -mwError (error of the molecular weight)
     * -msMethod (the method used for the mass spectrometry)
     * -range (which amino acids were messured. It's not mentioned if the complete sequence as shown in the entry was measured)Gets the value of the Location property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Location property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.LocationType}
     * 
     */
    java.util.List getLocation();

    /**
     * Gets the value of the Interactant property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Interactant property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInteractant().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.InteractantType}
     * 
     */
    java.util.List getInteractant();

    /**
     * Gets the value of the absorption property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.BpcCommentGroupAbsorptionType}
     */
    org.uniprot.uniprot.BpcCommentGroupAbsorptionType getAbsorption();

    /**
     * Sets the value of the absorption property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.BpcCommentGroupAbsorptionType}
     */
    void setAbsorption(org.uniprot.uniprot.BpcCommentGroupAbsorptionType value);

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
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Documents%20and%20Settings/Owner/My%20Documents/School/BIOL%20498/XMLPipeDB/schema/uniprot.xsd line 398)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="uri" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface LinkType {


        /**
         * Gets the value of the uri property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getUri();

        /**
         * Sets the value of the uri property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setUri(java.lang.String value);

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
