//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.03.28 at 08:50:02 PM PST 
//


package org.uniprot.uniprot.impl;

public class LocationTypeImpl implements org.uniprot.uniprot.LocationType, com.sun.xml.bind.JAXBObject, org.uniprot.uniprot.impl.runtime.UnmarshallableObject, org.uniprot.uniprot.impl.runtime.XMLSerializable, org.uniprot.uniprot.impl.runtime.ValidatableObject
{

    protected java.lang.String _Sequence;
    protected org.uniprot.uniprot.PositionType _Begin;
    protected org.uniprot.uniprot.PositionType _Position;
    protected org.uniprot.uniprot.PositionType _End;
    public final static java.lang.Class version = (org.uniprot.uniprot.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;
    protected java.lang.Long _Hjid;
    protected java.lang.Long _Hjversion;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (org.uniprot.uniprot.LocationType.class);
    }

    public java.lang.String getSequence() {
        return _Sequence;
    }

    public void setSequence(java.lang.String value) {
        _Sequence = value;
    }

    public org.uniprot.uniprot.PositionType getBegin() {
        return _Begin;
    }

    public void setBegin(org.uniprot.uniprot.PositionType value) {
        _Begin = value;
    }

    public org.uniprot.uniprot.PositionType getPosition() {
        return _Position;
    }

    public void setPosition(org.uniprot.uniprot.PositionType value) {
        _Position = value;
    }

    public org.uniprot.uniprot.PositionType getEnd() {
        return _End;
    }

    public void setEnd(org.uniprot.uniprot.PositionType value) {
        _End = value;
    }

    public org.uniprot.uniprot.impl.runtime.UnmarshallingEventHandler createUnmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context) {
        return new org.uniprot.uniprot.impl.LocationTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if (((_Begin!= null)&&(_Position == null))&&(_End!= null)) {
            context.startElement("http://uniprot.org/uniprot", "begin");
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Begin), "Begin");
            context.endNamespaceDecls();
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Begin), "Begin");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Begin), "Begin");
            context.endElement();
            context.startElement("http://uniprot.org/uniprot", "end");
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _End), "End");
            context.endNamespaceDecls();
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _End), "End");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _End), "End");
            context.endElement();
        } else {
            if (((_Begin == null)&&(_Position!= null))&&(_End == null)) {
                context.startElement("http://uniprot.org/uniprot", "position");
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Position), "Position");
                context.endNamespaceDecls();
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Position), "Position");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _Position), "Position");
                context.endElement();
            }
        }
    }

    public void serializeAttributes(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if (_Sequence!= null) {
            context.startAttribute("", "sequence");
            try {
                context.text(((java.lang.String) _Sequence), "Sequence");
            } catch (java.lang.Exception e) {
                org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
    }

    public void serializeURIs(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (org.uniprot.uniprot.LocationType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsq\u0000~\u0000\u0000ppsr\u0000\'com.sun.msv.grammar.trex.ElementPatt"
+"ern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;"
+"xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndecl"
+"aredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\tpp\u0000"
+"sq\u0000~\u0000\u0006ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001cc"
+"om.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003sr\u0000\u0011j"
+"ava.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.gramm"
+"ar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\nxq\u0000~\u0000\u0003"
+"q\u0000~\u0000\u0014psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0013\u0001q\u0000~\u0000\u0018sr\u0000 com.sun.msv.grammar.AnyName"
+"Class\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0019q\u0000~\u0000\u001esr\u0000#com.sun.msv.grammar.SimpleNameClass"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq"
+"\u0000~\u0000 xq\u0000~\u0000\u001bt\u0000 org.uniprot.uniprot.PositionTypet\u0000+http://java."
+"sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0015q\u0000~\u0000\u0014psr\u0000\u001bcom.s"
+"un.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/dataty"
+"pe/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/String"
+"Pair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
+"\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"r\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.s"
+"un.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUri"
+"q\u0000~\u0000 L\u0000\btypeNameq\u0000~\u0000 L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/x"
+"sd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchema"
+"t\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Col"
+"lapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProce"
+"ssor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetE"
+"xpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0"
+"t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000 L\u0000\fnamespaceURIq\u0000~\u0000 xpq\u0000~\u00001q\u0000~\u00000s"
+"q\u0000~\u0000\u001ft\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-instanceq\u0000~\u0000"
+"\u001esq\u0000~\u0000\u001ft\u0000\u0005begint\u0000\u001ahttp://uniprot.org/uniprotsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0000p"
+"psq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0010q\u0000~\u0000\u0014psq\u0000~\u0000\u0015q\u0000~\u0000\u0014pq\u0000~\u0000\u0018q\u0000~\u0000\u001cq\u0000~\u0000\u001esq\u0000"
+"~\u0000\u001fq\u0000~\u0000\"q\u0000~\u0000#sq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0015q\u0000~\u0000\u0014pq\u0000~\u0000)q\u0000~\u00009q\u0000~\u0000\u001esq\u0000~\u0000\u001ft\u0000\u0003end"
+"q\u0000~\u0000>sq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0010q\u0000~\u0000\u0014psq\u0000~\u0000\u0015q\u0000~"
+"\u0000\u0014pq\u0000~\u0000\u0018q\u0000~\u0000\u001cq\u0000~\u0000\u001esq\u0000~\u0000\u001fq\u0000~\u0000\"q\u0000~\u0000#sq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0015q\u0000~\u0000\u0014pq\u0000~\u0000)q"
+"\u0000~\u00009q\u0000~\u0000\u001esq\u0000~\u0000\u001ft\u0000\bpositionq\u0000~\u0000>sq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0015q\u0000~\u0000\u0014psq\u0000~\u0000&pps"
+"r\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlways"
+"Validxq\u0000~\u0000+q\u0000~\u00000t\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.WhiteS"
+"paceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00003\u0001q\u0000~\u00006sq\u0000~\u00007q\u0000~\u0000Zq\u0000~\u0000"
+"0sq\u0000~\u0000\u001ft\u0000\bsequencet\u0000\u0000q\u0000~\u0000\u001esr\u0000\"com.sun.msv.grammar.Expression"
+"Pool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expression"
+"Pool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$Clo"
+"sedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/s"
+"un/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0010\u0001pq\u0000~\u0000\u0012q\u0000~\u0000Cq\u0000~\u0000Nq\u0000~\u0000Uq\u0000"
+"~\u0000\bq\u0000~\u0000\u0007q\u0000~\u0000\u000fq\u0000~\u0000Bq\u0000~\u0000Mq\u0000~\u0000\rq\u0000~\u0000@q\u0000~\u0000Kq\u0000~\u0000$q\u0000~\u0000Fq\u0000~\u0000Qq\u0000~\u0000\u0005x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if ((null == obj)||(!(obj instanceof org.uniprot.uniprot.LocationType))) {
            return false;
        }
        org.uniprot.uniprot.impl.LocationTypeImpl target = ((org.uniprot.uniprot.impl.LocationTypeImpl) obj);
        {
            java.lang.String value = this.getSequence();
            java.lang.String targetValue = target.getSequence();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            org.uniprot.uniprot.PositionType value = this.getBegin();
            org.uniprot.uniprot.PositionType targetValue = target.getBegin();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            org.uniprot.uniprot.PositionType value = this.getPosition();
            org.uniprot.uniprot.PositionType targetValue = target.getPosition();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            org.uniprot.uniprot.PositionType value = this.getEnd();
            org.uniprot.uniprot.PositionType targetValue = target.getEnd();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        {
            java.lang.String value = this.getSequence();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            org.uniprot.uniprot.PositionType value = this.getBegin();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            org.uniprot.uniprot.PositionType value = this.getPosition();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            org.uniprot.uniprot.PositionType value = this.getEnd();
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
            super(context, "------------");
        }

        protected Unmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return org.uniprot.uniprot.impl.LocationTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  10 :
                        attIdx = context.getAttribute("", "position");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "status");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        _Position = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterElement((org.uniprot.uniprot.impl.PositionTypeImpl.class), 11, ___uri, ___local, ___qname, __atts));
                        return ;
                    case  9 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  4 :
                        attIdx = context.getAttribute("", "position");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "status");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        _Begin = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterElement((org.uniprot.uniprot.impl.PositionTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                        return ;
                    case  6 :
                        if (("end" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "sequence");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText1(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  7 :
                        attIdx = context.getAttribute("", "position");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "status");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        _End = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterElement((org.uniprot.uniprot.impl.PositionTypeImpl.class), 8, ___uri, ___local, ___qname, __atts));
                        return ;
                    case  3 :
                        if (("begin" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 4;
                            return ;
                        }
                        if (("position" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 10;
                            return ;
                        }
                        break;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Sequence = value;
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
                    case  10 :
                        attIdx = context.getAttribute("", "position");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "status");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        _Position = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromLeaveElement((org.uniprot.uniprot.impl.PositionTypeImpl.class), 11, ___uri, ___local, ___qname));
                        return ;
                    case  9 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  8 :
                        if (("end" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  4 :
                        attIdx = context.getAttribute("", "position");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "status");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        _Begin = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromLeaveElement((org.uniprot.uniprot.impl.PositionTypeImpl.class), 5, ___uri, ___local, ___qname));
                        return ;
                    case  11 :
                        if (("position" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  5 :
                        if (("begin" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "sequence");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText1(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  7 :
                        attIdx = context.getAttribute("", "position");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "status");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        _End = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromLeaveElement((org.uniprot.uniprot.impl.PositionTypeImpl.class), 8, ___uri, ___local, ___qname));
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
                    case  10 :
                        if (("position" == ___local)&&("" == ___uri)) {
                            _Position = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 11, ___uri, ___local, ___qname));
                            return ;
                        }
                        if (("status" == ___local)&&("" == ___uri)) {
                            _Position = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 11, ___uri, ___local, ___qname));
                            return ;
                        }
                        _Position = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 11, ___uri, ___local, ___qname));
                        return ;
                    case  9 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  4 :
                        if (("position" == ___local)&&("" == ___uri)) {
                            _Begin = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 5, ___uri, ___local, ___qname));
                            return ;
                        }
                        if (("status" == ___local)&&("" == ___uri)) {
                            _Begin = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 5, ___uri, ___local, ___qname));
                            return ;
                        }
                        _Begin = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 5, ___uri, ___local, ___qname));
                        return ;
                    case  0 :
                        if (("sequence" == ___local)&&("" == ___uri)) {
                            state = 1;
                            return ;
                        }
                        state = 3;
                        continue outer;
                    case  7 :
                        if (("position" == ___local)&&("" == ___uri)) {
                            _End = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 8, ___uri, ___local, ___qname));
                            return ;
                        }
                        if (("status" == ___local)&&("" == ___uri)) {
                            _End = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 8, ___uri, ___local, ___qname));
                            return ;
                        }
                        _End = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 8, ___uri, ___local, ___qname));
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
                    case  10 :
                        attIdx = context.getAttribute("", "position");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "status");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        _Position = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromLeaveAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 11, ___uri, ___local, ___qname));
                        return ;
                    case  9 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  4 :
                        attIdx = context.getAttribute("", "position");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "status");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        _Begin = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromLeaveAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 5, ___uri, ___local, ___qname));
                        return ;
                    case  0 :
                        attIdx = context.getAttribute("", "sequence");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText1(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  7 :
                        attIdx = context.getAttribute("", "position");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "status");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        _End = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromLeaveAttribute((org.uniprot.uniprot.impl.PositionTypeImpl.class), 8, ___uri, ___local, ___qname));
                        return ;
                    case  2 :
                        if (("sequence" == ___local)&&("" == ___uri)) {
                            state = 3;
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
                        case  10 :
                            attIdx = context.getAttribute("", "position");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "status");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            _Position = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromText((org.uniprot.uniprot.impl.PositionTypeImpl.class), 11, value));
                            return ;
                        case  9 :
                            revertToParentFromText(value);
                            return ;
                        case  4 :
                            attIdx = context.getAttribute("", "position");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "status");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            _Begin = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromText((org.uniprot.uniprot.impl.PositionTypeImpl.class), 5, value));
                            return ;
                        case  0 :
                            attIdx = context.getAttribute("", "sequence");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 3;
                                eatText1(v);
                                continue outer;
                            }
                            state = 3;
                            continue outer;
                        case  7 :
                            attIdx = context.getAttribute("", "position");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "status");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            _End = ((org.uniprot.uniprot.impl.PositionTypeImpl) spawnChildFromText((org.uniprot.uniprot.impl.PositionTypeImpl.class), 8, value));
                            return ;
                        case  1 :
                            state = 2;
                            eatText1(value);
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
