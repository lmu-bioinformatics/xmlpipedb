//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.10.31 at 01:32:53 AM PDT 
//


package org.uniprot.uniprot;


/**
 * The protein element stores all the information found in the DE line of a flatfile
 * entry.
 * 
 * Java content class for proteinType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/dondi/Downloads/xsd2db/uniprot/xsd//uniprot.xsd line 80)
 * <p>
 * <pre>
 * &lt;complexType name="proteinType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://uniprot.org/uniprot}proteinNameGroup"/>
 *         &lt;element name="domain" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;group ref="{http://uniprot.org/uniprot}proteinNameGroup"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="component" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;group ref="{http://uniprot.org/uniprot}proteinNameGroup"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ProteinType {


    /**
     * The domain list is equivalent to the INCLUDES section of the DE
     * line.
     * Gets the value of the Domain property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Domain property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomain().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.ProteinType.DomainType}
     * 
     */
    java.util.List getDomain();

    /**
     * Gets the value of the SubmittedName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the SubmittedName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubmittedName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.ProteinNameGroupSubmittedNameType}
     * 
     */
    java.util.List getSubmittedName();

    /**
     * Gets the value of the allergenName property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.EvidencedStringType}
     */
    org.uniprot.uniprot.EvidencedStringType getAllergenName();

    /**
     * Sets the value of the allergenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.EvidencedStringType}
     */
    void setAllergenName(org.uniprot.uniprot.EvidencedStringType value);

    /**
     * Gets the value of the AlternativeName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the AlternativeName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlternativeName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.ProteinNameGroupAlternativeNameType}
     * 
     */
    java.util.List getAlternativeName();

    /**
     * The component list is equivalent to the CONTAINS section of the DE
     * line.
     * Gets the value of the Component property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Component property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.ProteinType.ComponentType}
     * 
     */
    java.util.List getComponent();

    /**
     * Gets the value of the biotechName property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.EvidencedStringType}
     */
    org.uniprot.uniprot.EvidencedStringType getBiotechName();

    /**
     * Sets the value of the biotechName property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.EvidencedStringType}
     */
    void setBiotechName(org.uniprot.uniprot.EvidencedStringType value);

    /**
     * Gets the value of the recommendedName property.
     * 
     * @return
     *     possible object is
     *     {@link org.uniprot.uniprot.ProteinNameGroupRecommendedNameType}
     */
    org.uniprot.uniprot.ProteinNameGroupRecommendedNameType getRecommendedName();

    /**
     * Sets the value of the recommendedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.uniprot.uniprot.ProteinNameGroupRecommendedNameType}
     */
    void setRecommendedName(org.uniprot.uniprot.ProteinNameGroupRecommendedNameType value);

    /**
     * Gets the value of the CdAntigenName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the CdAntigenName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCdAntigenName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.EvidencedStringType}
     * 
     */
    java.util.List getCdAntigenName();

    /**
     * Gets the value of the InnName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the InnName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInnName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.uniprot.uniprot.EvidencedStringType}
     * 
     */
    java.util.List getInnName();

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
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/dondi/Downloads/xsd2db/uniprot/xsd//uniprot.xsd line 104)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;group ref="{http://uniprot.org/uniprot}proteinNameGroup"/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface ComponentType {


        /**
         * Gets the value of the SubmittedName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the SubmittedName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSubmittedName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.uniprot.uniprot.ProteinNameGroupSubmittedNameType}
         * 
         */
        java.util.List getSubmittedName();

        /**
         * Gets the value of the allergenName property.
         * 
         * @return
         *     possible object is
         *     {@link org.uniprot.uniprot.EvidencedStringType}
         */
        org.uniprot.uniprot.EvidencedStringType getAllergenName();

        /**
         * Sets the value of the allergenName property.
         * 
         * @param value
         *     allowed object is
         *     {@link org.uniprot.uniprot.EvidencedStringType}
         */
        void setAllergenName(org.uniprot.uniprot.EvidencedStringType value);

        /**
         * Gets the value of the AlternativeName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the AlternativeName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAlternativeName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.uniprot.uniprot.ProteinNameGroupAlternativeNameType}
         * 
         */
        java.util.List getAlternativeName();

        /**
         * Gets the value of the biotechName property.
         * 
         * @return
         *     possible object is
         *     {@link org.uniprot.uniprot.EvidencedStringType}
         */
        org.uniprot.uniprot.EvidencedStringType getBiotechName();

        /**
         * Sets the value of the biotechName property.
         * 
         * @param value
         *     allowed object is
         *     {@link org.uniprot.uniprot.EvidencedStringType}
         */
        void setBiotechName(org.uniprot.uniprot.EvidencedStringType value);

        /**
         * Gets the value of the recommendedName property.
         * 
         * @return
         *     possible object is
         *     {@link org.uniprot.uniprot.ProteinNameGroupRecommendedNameType}
         */
        org.uniprot.uniprot.ProteinNameGroupRecommendedNameType getRecommendedName();

        /**
         * Sets the value of the recommendedName property.
         * 
         * @param value
         *     allowed object is
         *     {@link org.uniprot.uniprot.ProteinNameGroupRecommendedNameType}
         */
        void setRecommendedName(org.uniprot.uniprot.ProteinNameGroupRecommendedNameType value);

        /**
         * Gets the value of the CdAntigenName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the CdAntigenName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCdAntigenName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.uniprot.uniprot.EvidencedStringType}
         * 
         */
        java.util.List getCdAntigenName();

        /**
         * Gets the value of the InnName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the InnName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInnName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.uniprot.uniprot.EvidencedStringType}
         * 
         */
        java.util.List getInnName();

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
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/dondi/Downloads/xsd2db/uniprot/xsd//uniprot.xsd line 94)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;group ref="{http://uniprot.org/uniprot}proteinNameGroup"/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface DomainType {


        /**
         * Gets the value of the SubmittedName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the SubmittedName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSubmittedName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.uniprot.uniprot.ProteinNameGroupSubmittedNameType}
         * 
         */
        java.util.List getSubmittedName();

        /**
         * Gets the value of the allergenName property.
         * 
         * @return
         *     possible object is
         *     {@link org.uniprot.uniprot.EvidencedStringType}
         */
        org.uniprot.uniprot.EvidencedStringType getAllergenName();

        /**
         * Sets the value of the allergenName property.
         * 
         * @param value
         *     allowed object is
         *     {@link org.uniprot.uniprot.EvidencedStringType}
         */
        void setAllergenName(org.uniprot.uniprot.EvidencedStringType value);

        /**
         * Gets the value of the AlternativeName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the AlternativeName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAlternativeName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.uniprot.uniprot.ProteinNameGroupAlternativeNameType}
         * 
         */
        java.util.List getAlternativeName();

        /**
         * Gets the value of the biotechName property.
         * 
         * @return
         *     possible object is
         *     {@link org.uniprot.uniprot.EvidencedStringType}
         */
        org.uniprot.uniprot.EvidencedStringType getBiotechName();

        /**
         * Sets the value of the biotechName property.
         * 
         * @param value
         *     allowed object is
         *     {@link org.uniprot.uniprot.EvidencedStringType}
         */
        void setBiotechName(org.uniprot.uniprot.EvidencedStringType value);

        /**
         * Gets the value of the recommendedName property.
         * 
         * @return
         *     possible object is
         *     {@link org.uniprot.uniprot.ProteinNameGroupRecommendedNameType}
         */
        org.uniprot.uniprot.ProteinNameGroupRecommendedNameType getRecommendedName();

        /**
         * Sets the value of the recommendedName property.
         * 
         * @param value
         *     allowed object is
         *     {@link org.uniprot.uniprot.ProteinNameGroupRecommendedNameType}
         */
        void setRecommendedName(org.uniprot.uniprot.ProteinNameGroupRecommendedNameType value);

        /**
         * Gets the value of the CdAntigenName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the CdAntigenName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCdAntigenName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.uniprot.uniprot.EvidencedStringType}
         * 
         */
        java.util.List getCdAntigenName();

        /**
         * Gets the value of the InnName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the InnName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInnName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.uniprot.uniprot.EvidencedStringType}
         * 
         */
        java.util.List getInnName();

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
