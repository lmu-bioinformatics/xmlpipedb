//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.03.04 at 04:02:45 PM PST 
//


package org.uniprot.uniprot.impl;

public class SequenceTypeImpl implements org.uniprot.uniprot.SequenceType, com.sun.xml.bind.JAXBObject, org.uniprot.uniprot.impl.runtime.UnmarshallableObject, org.uniprot.uniprot.impl.runtime.XMLSerializable, org.uniprot.uniprot.impl.runtime.ValidatableObject
{

    protected java.util.Calendar _Modified;
    protected java.lang.String _Value;
    protected java.math.BigInteger _Version;
    protected java.math.BigInteger _Mass;
    protected java.math.BigInteger _Length;
    protected java.lang.String _Checksum;
    public final static java.lang.Class version = (org.uniprot.uniprot.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;
    protected java.lang.Long _Hjid;
    protected java.lang.Long _Hjversion;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (org.uniprot.uniprot.SequenceType.class);
    }

    public java.util.Calendar getModified() {
        return _Modified;
    }

    public void setModified(java.util.Calendar value) {
        _Modified = value;
    }

    public java.lang.String getValue() {
        return _Value;
    }

    public void setValue(java.lang.String value) {
        _Value = value;
    }

    public java.math.BigInteger getVersion() {
        return _Version;
    }

    public void setVersion(java.math.BigInteger value) {
        _Version = value;
    }

    public java.math.BigInteger getMass() {
        return _Mass;
    }

    public void setMass(java.math.BigInteger value) {
        _Mass = value;
    }

    public java.math.BigInteger getLength() {
        return _Length;
    }

    public void setLength(java.math.BigInteger value) {
        _Length = value;
    }

    public java.lang.String getChecksum() {
        return _Checksum;
    }

    public void setChecksum(java.lang.String value) {
        _Checksum = value;
    }

    public org.uniprot.uniprot.impl.runtime.UnmarshallingEventHandler createUnmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context) {
        return new org.uniprot.uniprot.impl.SequenceTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        try {
            context.text(((java.lang.String) _Value), "Value");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
    }

    public void serializeAttributes(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startAttribute("", "checksum");
        try {
            context.text(((java.lang.String) _Checksum), "Checksum");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "length");
        try {
            context.text(javax.xml.bind.DatatypeConverter.printInteger(((java.math.BigInteger) _Length)), "Length");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "mass");
        try {
            context.text(javax.xml.bind.DatatypeConverter.printInteger(((java.math.BigInteger) _Mass)), "Mass");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "modified");
        try {
            context.text(javax.xml.bind.DatatypeConverter.printDate(((java.util.Calendar) _Modified)), "Modified");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "version");
        try {
            context.text(javax.xml.bind.DatatypeConverter.printInteger(((java.math.BigInteger) _Version)), "Version");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
    }

    public void serializeURIs(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (org.uniprot.uniprot.SequenceType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\u001bcom."
+"sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datat"
+"ype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/Strin"
+"gPair;xq\u0000~\u0000\u0003ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtom"
+"icType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0012L\u0000\nwh"
+"iteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt"
+"\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u00005com.sun.msv.d"
+"atatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.s"
+"un.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com"
+".sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~"
+"\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000"
+"~\u0000\u0012L\u0000\fnamespaceURIq\u0000~\u0000\u0012xpq\u0000~\u0000\u0016q\u0000~\u0000\u0015sr\u0000 com.sun.msv.grammar.A"
+"ttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClasst\u0000\u001fLcom/sun/msv"
+"/grammar/NameClass;xq\u0000~\u0000\u0003ppq\u0000~\u0000\rsr\u0000#com.sun.msv.grammar.Simp"
+"leNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0012L\u0000\fnamespaceURIq\u0000~\u0000\u0012x"
+"r\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\bchecksumt\u0000\u0000s"
+"q\u0000~\u0000\u001eppsq\u0000~\u0000\nppsr\u0000$com.sun.msv.datatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k"
+"\u00be\u0002\u0000\u0001L\u0000\nbaseFacetst\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl"
+";xq\u0000~\u0000\u000fq\u0000~\u0000\u0015t\u0000\u0007integersr\u00005com.sun.msv.datatype.xsd.WhiteSpac"
+"eProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0018sr\u0000,com.sun.msv.datatype"
+".xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.sun.msv.d"
+"atatype.xsd.DataTypeWithLexicalConstraintFacetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000xr\u0000"
+"*com.sun.msv.datatype.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisF"
+"acetFixedZ\u0000\u0012needValueCheckFlagL\u0000\bbaseTypeq\u0000~\u0000*L\u0000\fconcreteTyp"
+"et\u0000\'Lcom/sun/msv/datatype/xsd/ConcreteType;L\u0000\tfacetNameq\u0000~\u0000\u0012"
+"xq\u0000~\u0000\u0011ppq\u0000~\u0000.\u0001\u0000sr\u0000#com.sun.msv.datatype.xsd.NumberType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u000fq\u0000~\u0000\u0015t\u0000\u0007decimalq\u0000~\u0000.q\u0000~\u00005t\u0000\u000efractionDigits\u0000\u0000\u0000\u0000q\u0000~"
+"\u0000\u001bsq\u0000~\u0000\u001cq\u0000~\u0000,q\u0000~\u0000\u0015sq\u0000~\u0000!t\u0000\u0006lengthq\u0000~\u0000%sq\u0000~\u0000\u001eppq\u0000~\u0000\'sq\u0000~\u0000!t\u0000\u0004"
+"massq\u0000~\u0000%sq\u0000~\u0000\u001eppsq\u0000~\u0000\nsr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005va"
+"luexp\u0000psr\u0000!com.sun.msv.datatype.xsd.DateType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000)c"
+"om.sun.msv.datatype.xsd.DateTimeBaseType\u0014W\u001a@3\u00a5\u00b4\u00e5\u0002\u0000\u0000xq\u0000~\u0000\u000fq\u0000~"
+"\u0000\u0015t\u0000\u0004dateq\u0000~\u0000.q\u0000~\u0000\u001bsq\u0000~\u0000\u001cq\u0000~\u0000Eq\u0000~\u0000\u0015sq\u0000~\u0000!t\u0000\bmodifiedq\u0000~\u0000%sq\u0000"
+"~\u0000\u001eppq\u0000~\u0000\'sq\u0000~\u0000!t\u0000\u0007versionq\u0000~\u0000%sr\u0000\"com.sun.msv.grammar.Expre"
+"ssionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expre"
+"ssionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPoo"
+"l$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$L"
+"com/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0005\u0001pq\u0000~\u0000\bq\u0000~\u0000\tq\u0000~\u0000\u0007q\u0000"
+"~\u0000\u0005q\u0000~\u0000\u0006x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if ((null == obj)||(!(obj instanceof org.uniprot.uniprot.SequenceType))) {
            return false;
        }
        org.uniprot.uniprot.impl.SequenceTypeImpl target = ((org.uniprot.uniprot.impl.SequenceTypeImpl) obj);
        {
            java.util.Calendar value = this.getModified();
            java.util.Calendar targetValue = target.getModified();
            if (!((value == targetValue)||((value!= null)&&(value.getTime().getTime() == targetValue.getTime().getTime())))) {
                return false;
            }
        }
        {
            java.lang.String value = this.getValue();
            java.lang.String targetValue = target.getValue();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.math.BigInteger value = this.getVersion();
            java.math.BigInteger targetValue = target.getVersion();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.math.BigInteger value = this.getMass();
            java.math.BigInteger targetValue = target.getMass();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.math.BigInteger value = this.getLength();
            java.math.BigInteger targetValue = target.getLength();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.lang.String value = this.getChecksum();
            java.lang.String targetValue = target.getChecksum();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        {
            java.util.Calendar value = this.getModified();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.lang.String value = this.getValue();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.math.BigInteger value = this.getVersion();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.math.BigInteger value = this.getMass();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.math.BigInteger value = this.getLength();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.lang.String value = this.getChecksum();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        return hash;
    }

    public java.lang.Long getHjid() {
        return _Hjid;
    }

    public void setHjid(java.lang.Long value) {
        _Hjid = value;
    }

    public java.lang.Long getHjversion() {
        return _Hjversion;
    }

    public void setHjversion(java.lang.Long value) {
        _Hjversion = value;
    }

    public class Unmarshaller
        extends org.uniprot.uniprot.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context) {
            super(context, "-----------------");
        }

        protected Unmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return org.uniprot.uniprot.impl.SequenceTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  12 :
                        attIdx = context.getAttribute("", "version");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 15;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "checksum");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  3 :
                        attIdx = context.getAttribute("", "length");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText3(v);
                            continue outer;
                        }
                        break;
                    case  6 :
                        attIdx = context.getAttribute("", "mass");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText4(v);
                            continue outer;
                        }
                        break;
                    case  9 :
                        attIdx = context.getAttribute("", "modified");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText5(v);
                            continue outer;
                        }
                        break;
                    case  16 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Version = javax.xml.bind.DatatypeConverter.parseInteger(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Checksum = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText3(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Length = javax.xml.bind.DatatypeConverter.parseInteger(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText4(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Mass = javax.xml.bind.DatatypeConverter.parseInteger(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText5(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Modified = javax.xml.bind.DatatypeConverter.parseDate(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  12 :
                        attIdx = context.getAttribute("", "version");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 15;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "checksum");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  3 :
                        attIdx = context.getAttribute("", "length");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText3(v);
                            continue outer;
                        }
                        break;
                    case  6 :
                        attIdx = context.getAttribute("", "mass");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText4(v);
                            continue outer;
                        }
                        break;
                    case  9 :
                        attIdx = context.getAttribute("", "modified");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText5(v);
                            continue outer;
                        }
                        break;
                    case  16 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                }
                super.leaveElement(___uri, ___local, ___qname);
                break;
            }
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  12 :
                        if (("version" == ___local)&&("" == ___uri)) {
                            state = 13;
                            return ;
                        }
                        break;
                    case  0 :
                        if (("checksum" == ___local)&&("" == ___uri)) {
                            state = 1;
                            return ;
                        }
                        break;
                    case  3 :
                        if (("length" == ___local)&&("" == ___uri)) {
                            state = 4;
                            return ;
                        }
                        break;
                    case  6 :
                        if (("mass" == ___local)&&("" == ___uri)) {
                            state = 7;
                            return ;
                        }
                        break;
                    case  9 :
                        if (("modified" == ___local)&&("" == ___uri)) {
                            state = 10;
                            return ;
                        }
                        break;
                    case  16 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.enterAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  12 :
                        attIdx = context.getAttribute("", "version");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 15;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  2 :
                        if (("checksum" == ___local)&&("" == ___uri)) {
                            state = 3;
                            return ;
                        }
                        break;
                    case  14 :
                        if (("version" == ___local)&&("" == ___uri)) {
                            state = 15;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "checksum");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  8 :
                        if (("mass" == ___local)&&("" == ___uri)) {
                            state = 9;
                            return ;
                        }
                        break;
                    case  3 :
                        attIdx = context.getAttribute("", "length");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText3(v);
                            continue outer;
                        }
                        break;
                    case  5 :
                        if (("length" == ___local)&&("" == ___uri)) {
                            state = 6;
                            return ;
                        }
                        break;
                    case  11 :
                        if (("modified" == ___local)&&("" == ___uri)) {
                            state = 12;
                            return ;
                        }
                        break;
                    case  6 :
                        attIdx = context.getAttribute("", "mass");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText4(v);
                            continue outer;
                        }
                        break;
                    case  9 :
                        attIdx = context.getAttribute("", "modified");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText5(v);
                            continue outer;
                        }
                        break;
                    case  16 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.leaveAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void handleText(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                try {
                    switch (state) {
                        case  12 :
                            attIdx = context.getAttribute("", "version");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 15;
                                eatText1(v);
                                continue outer;
                            }
                            break;
                        case  15 :
                            state = 16;
                            eatText6(value);
                            return ;
                        case  0 :
                            attIdx = context.getAttribute("", "checksum");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 3;
                                eatText2(v);
                                continue outer;
                            }
                            break;
                        case  1 :
                            state = 2;
                            eatText2(value);
                            return ;
                        case  13 :
                            state = 14;
                            eatText1(value);
                            return ;
                        case  7 :
                            state = 8;
                            eatText4(value);
                            return ;
                        case  3 :
                            attIdx = context.getAttribute("", "length");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 6;
                                eatText3(v);
                                continue outer;
                            }
                            break;
                        case  4 :
                            state = 5;
                            eatText3(value);
                            return ;
                        case  6 :
                            attIdx = context.getAttribute("", "mass");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 9;
                                eatText4(v);
                                continue outer;
                            }
                            break;
                        case  9 :
                            attIdx = context.getAttribute("", "modified");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 12;
                                eatText5(v);
                                continue outer;
                            }
                            break;
                        case  10 :
                            state = 11;
                            eatText5(value);
                            return ;
                        case  16 :
                            revertToParentFromText(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText6(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Value = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
