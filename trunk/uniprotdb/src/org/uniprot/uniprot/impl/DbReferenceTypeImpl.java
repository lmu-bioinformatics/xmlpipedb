//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.03.28 at 08:50:02 PM PST 
//


package org.uniprot.uniprot.impl;

public class DbReferenceTypeImpl implements org.uniprot.uniprot.DbReferenceType, com.sun.xml.bind.JAXBObject, org.uniprot.uniprot.impl.runtime.UnmarshallableObject, org.uniprot.uniprot.impl.runtime.XMLSerializable, org.uniprot.uniprot.impl.runtime.ValidatableObject
{

    protected java.lang.String _Type;
    protected java.lang.String _Key;
    protected com.sun.xml.bind.util.ListImpl _Property;
    protected java.lang.String _Evidence;
    protected java.lang.String _Id;
    public final static java.lang.Class version = (org.uniprot.uniprot.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;
    protected java.lang.Long _Hjid;
    protected java.lang.Long _Hjversion;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (org.uniprot.uniprot.DbReferenceType.class);
    }

    public java.lang.String getType() {
        return _Type;
    }

    public void setType(java.lang.String value) {
        _Type = value;
    }

    public java.lang.String getKey() {
        return _Key;
    }

    public void setKey(java.lang.String value) {
        _Key = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getProperty() {
        if (_Property == null) {
            _Property = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Property;
    }

    public java.util.List getProperty() {
        return _getProperty();
    }

    public java.lang.String getEvidence() {
        return _Evidence;
    }

    public void setEvidence(java.lang.String value) {
        _Evidence = value;
    }

    public java.lang.String getId() {
        return _Id;
    }

    public void setId(java.lang.String value) {
        _Id = value;
    }

    public org.uniprot.uniprot.impl.runtime.UnmarshallingEventHandler createUnmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context) {
        return new org.uniprot.uniprot.impl.DbReferenceTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = ((_Property == null)? 0 :_Property.size());
        while (idx3 != len3) {
            context.startElement("http://uniprot.org/uniprot", "property");
            int idx_0 = idx3;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Property.get(idx_0 ++)), "Property");
            context.endNamespaceDecls();
            int idx_1 = idx3;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Property.get(idx_1 ++)), "Property");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Property.get(idx3 ++)), "Property");
            context.endElement();
        }
    }

    public void serializeAttributes(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = ((_Property == null)? 0 :_Property.size());
        if (_Evidence!= null) {
            context.startAttribute("", "evidence");
            try {
                context.text(((java.lang.String) _Evidence), "Evidence");
            } catch (java.lang.Exception e) {
                org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        context.startAttribute("", "id");
        try {
            context.text(((java.lang.String) _Id), "Id");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "key");
        try {
            context.text(((java.lang.String) _Key), "Key");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        context.startAttribute("", "type");
        try {
            context.text(((java.lang.String) _Type), "Type");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        while (idx3 != len3) {
            idx3 += 1;
        }
    }

    public void serializeURIs(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = ((_Property == null)? 0 :_Property.size());
        while (idx3 != len3) {
            idx3 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (org.uniprot.uniprot.DbReferenceType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\u001dcom.sun.msv."
+"grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar."
+"OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005va"
+"luexp\u0000psr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.m"
+"sv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttribute"
+"sL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000\u000fp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0010pp\u0000sq\u0000~\u0000\tpps"
+"q\u0000~\u0000\u000bq\u0000~\u0000\u000fpsr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000"
+"\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u0011xq\u0000~\u0000\u0003q\u0000~\u0000\u000fpsr\u00002com.sun.msv.gramma"
+"r.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u000e\u0001q\u0000~\u0000"
+"\u001bsr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun."
+"msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Ex"
+"pression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u001cq\u0000~\u0000!sr\u0000#com"
+".sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Lj"
+"ava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000#xq\u0000~\u0000\u001et\u0000 org.uniprot.uni"
+"prot.PropertyTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-eleme"
+"ntssq\u0000~\u0000\tppsq\u0000~\u0000\u0018q\u0000~\u0000\u000fpsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000"
+"\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv"
+".datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.x"
+"sd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd."
+"ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDataty"
+"peImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000#L\u0000\btypeNameq\u0000~\u0000#L\u0000\nwhit"
+"eSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 "
+"http://www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.data"
+"type.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun."
+"msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun"
+".msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003pp"
+"sr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000#L"
+"\u0000\fnamespaceURIq\u0000~\u0000#xpq\u0000~\u00004q\u0000~\u00003sq\u0000~\u0000\"t\u0000\u0004typet\u0000)http://www.w3"
+".org/2001/XMLSchema-instanceq\u0000~\u0000!sq\u0000~\u0000\"t\u0000\bpropertyt\u0000\u001ahttp://"
+"uniprot.org/uniprotq\u0000~\u0000!sq\u0000~\u0000\tppsq\u0000~\u0000\u0018q\u0000~\u0000\u000fpsq\u0000~\u0000)ppsr\u0000#com."
+"sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq"
+"\u0000~\u0000.q\u0000~\u00003t\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.WhiteSpacePro"
+"cessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00006\u0001q\u0000~\u00009sq\u0000~\u0000:q\u0000~\u0000Gq\u0000~\u00003sq\u0000~\u0000\""
+"t\u0000\bevidencet\u0000\u0000q\u0000~\u0000!sq\u0000~\u0000\u0018ppq\u0000~\u0000Dsq\u0000~\u0000\"t\u0000\u0002idq\u0000~\u0000Msq\u0000~\u0000\u0018ppq\u0000~\u0000"
+"Dsq\u0000~\u0000\"t\u0000\u0003keyq\u0000~\u0000Msq\u0000~\u0000\u0018ppq\u0000~\u0000Dsq\u0000~\u0000\"t\u0000\u0004typeq\u0000~\u0000Msr\u0000\"com.sun"
+".msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun"
+"/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.gra"
+"mmar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVe"
+"rsionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u000b\u0001"
+"pq\u0000~\u0000\u0017q\u0000~\u0000\u0006q\u0000~\u0000\u0007q\u0000~\u0000\rq\u0000~\u0000\nq\u0000~\u0000\u0016q\u0000~\u0000\bq\u0000~\u0000\u0014q\u0000~\u0000\u0005q\u0000~\u0000\'q\u0000~\u0000Bx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if ((null == obj)||(!(obj instanceof org.uniprot.uniprot.DbReferenceType))) {
            return false;
        }
        org.uniprot.uniprot.impl.DbReferenceTypeImpl target = ((org.uniprot.uniprot.impl.DbReferenceTypeImpl) obj);
        {
            java.lang.String value = this.getType();
            java.lang.String targetValue = target.getType();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.lang.String value = this.getKey();
            java.lang.String targetValue = target.getKey();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getProperty();
            java.util.List targetValue = target.getProperty();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.lang.String value = this.getEvidence();
            java.lang.String targetValue = target.getEvidence();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.lang.String value = this.getId();
            java.lang.String targetValue = target.getId();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        {
            java.lang.String value = this.getType();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.lang.String value = this.getKey();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getProperty();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.lang.String value = this.getEvidence();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.lang.String value = this.getId();
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
            super(context, "----------------");
        }

        protected Unmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return org.uniprot.uniprot.impl.DbReferenceTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  13 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  3 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  12 :
                        if (("property" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 13;
                            return ;
                        }
                        state = 15;
                        continue outer;
                    case  6 :
                        attIdx = context.getAttribute("", "key");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  9 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText3(v);
                            continue outer;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "evidence");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText4(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  15 :
                        if (("property" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 13;
                            return ;
                        }
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
                _Id = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Key = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText3(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Type = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText4(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Evidence = value;
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
                    case  13 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  14 :
                        if (("property" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.popAttributes();
                            state = 15;
                            return ;
                        }
                        break;
                    case  3 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  12 :
                        state = 15;
                        continue outer;
                    case  6 :
                        attIdx = context.getAttribute("", "key");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  9 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText3(v);
                            continue outer;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "evidence");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText4(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  15 :
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
                    case  13 :
                        if (("type" == ___local)&&("" == ___uri)) {
                            _getProperty().add(((org.uniprot.uniprot.impl.PropertyTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PropertyTypeImpl.class), 14, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  3 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            state = 4;
                            return ;
                        }
                        break;
                    case  12 :
                        state = 15;
                        continue outer;
                    case  6 :
                        if (("key" == ___local)&&("" == ___uri)) {
                            state = 7;
                            return ;
                        }
                        break;
                    case  9 :
                        if (("type" == ___local)&&("" == ___uri)) {
                            state = 10;
                            return ;
                        }
                        break;
                    case  0 :
                        if (("evidence" == ___local)&&("" == ___uri)) {
                            state = 1;
                            return ;
                        }
                        state = 3;
                        continue outer;
                    case  15 :
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
                    case  13 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  12 :
                        state = 15;
                        continue outer;
                    case  8 :
                        if (("key" == ___local)&&("" == ___uri)) {
                            state = 9;
                            return ;
                        }
                        break;
                    case  2 :
                        if (("evidence" == ___local)&&("" == ___uri)) {
                            state = 3;
                            return ;
                        }
                        break;
                    case  6 :
                        attIdx = context.getAttribute("", "key");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  9 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText3(v);
                            continue outer;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "evidence");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText4(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  11 :
                        if (("type" == ___local)&&("" == ___uri)) {
                            state = 12;
                            return ;
                        }
                        break;
                    case  15 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            state = 6;
                            return ;
                        }
                        break;
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
                        case  13 :
                            attIdx = context.getAttribute("", "type");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  3 :
                            attIdx = context.getAttribute("", "id");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 6;
                                eatText1(v);
                                continue outer;
                            }
                            break;
                        case  12 :
                            state = 15;
                            continue outer;
                        case  6 :
                            attIdx = context.getAttribute("", "key");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 9;
                                eatText2(v);
                                continue outer;
                            }
                            break;
                        case  9 :
                            attIdx = context.getAttribute("", "type");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 12;
                                eatText3(v);
                                continue outer;
                            }
                            break;
                        case  0 :
                            attIdx = context.getAttribute("", "evidence");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 3;
                                eatText4(v);
                                continue outer;
                            }
                            state = 3;
                            continue outer;
                        case  7 :
                            state = 8;
                            eatText2(value);
                            return ;
                        case  4 :
                            state = 5;
                            eatText1(value);
                            return ;
                        case  1 :
                            state = 2;
                            eatText4(value);
                            return ;
                        case  10 :
                            state = 11;
                            eatText3(value);
                            return ;
                        case  15 :
                            revertToParentFromText(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
