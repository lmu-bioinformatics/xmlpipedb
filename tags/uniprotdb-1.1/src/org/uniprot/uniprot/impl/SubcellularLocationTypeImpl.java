//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.10.31 at 01:32:53 AM PDT 
//


package org.uniprot.uniprot.impl;

public class SubcellularLocationTypeImpl implements org.uniprot.uniprot.SubcellularLocationType, com.sun.xml.bind.JAXBObject, org.uniprot.uniprot.impl.runtime.UnmarshallableObject, org.uniprot.uniprot.impl.runtime.XMLSerializable, org.uniprot.uniprot.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Topology;
    protected com.sun.xml.bind.util.ListImpl _Location;
    protected com.sun.xml.bind.util.ListImpl _Orientation;
    public final static java.lang.Class version = (org.uniprot.uniprot.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;
    protected java.lang.Long _Hjid;
    protected java.lang.Long _Hjversion;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (org.uniprot.uniprot.SubcellularLocationType.class);
    }

    protected com.sun.xml.bind.util.ListImpl _getTopology() {
        if (_Topology == null) {
            _Topology = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Topology;
    }

    public java.util.List getTopology() {
        return _getTopology();
    }

    protected com.sun.xml.bind.util.ListImpl _getLocation() {
        if (_Location == null) {
            _Location = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Location;
    }

    public java.util.List getLocation() {
        return _getLocation();
    }

    protected com.sun.xml.bind.util.ListImpl _getOrientation() {
        if (_Orientation == null) {
            _Orientation = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Orientation;
    }

    public java.util.List getOrientation() {
        return _getOrientation();
    }

    public org.uniprot.uniprot.impl.runtime.UnmarshallingEventHandler createUnmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context) {
        return new org.uniprot.uniprot.impl.SubcellularLocationTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Topology == null)? 0 :_Topology.size());
        int idx2 = 0;
        final int len2 = ((_Location == null)? 0 :_Location.size());
        int idx3 = 0;
        final int len3 = ((_Orientation == null)? 0 :_Orientation.size());
        while (idx2 != len2) {
            context.startElement("http://uniprot.org/uniprot", "location");
            int idx_0 = idx2;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Location.get(idx_0 ++)), "Location");
            context.endNamespaceDecls();
            int idx_1 = idx2;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Location.get(idx_1 ++)), "Location");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Location.get(idx2 ++)), "Location");
            context.endElement();
        }
        while (idx1 != len1) {
            context.startElement("http://uniprot.org/uniprot", "topology");
            int idx_2 = idx1;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Topology.get(idx_2 ++)), "Topology");
            context.endNamespaceDecls();
            int idx_3 = idx1;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Topology.get(idx_3 ++)), "Topology");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Topology.get(idx1 ++)), "Topology");
            context.endElement();
        }
        while (idx3 != len3) {
            context.startElement("http://uniprot.org/uniprot", "orientation");
            int idx_4 = idx3;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Orientation.get(idx_4 ++)), "Orientation");
            context.endNamespaceDecls();
            int idx_5 = idx3;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Orientation.get(idx_5 ++)), "Orientation");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Orientation.get(idx3 ++)), "Orientation");
            context.endElement();
        }
    }

    public void serializeAttributes(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Topology == null)? 0 :_Topology.size());
        int idx2 = 0;
        final int len2 = ((_Location == null)? 0 :_Location.size());
        int idx3 = 0;
        final int len3 = ((_Orientation == null)? 0 :_Orientation.size());
        while (idx2 != len2) {
            idx2 += 1;
        }
        while (idx1 != len1) {
            idx1 += 1;
        }
        while (idx3 != len3) {
            idx3 += 1;
        }
    }

    public void serializeURIs(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Topology == null)? 0 :_Topology.size());
        int idx2 = 0;
        final int len2 = ((_Location == null)? 0 :_Location.size());
        int idx3 = 0;
        final int len3 = ((_Orientation == null)? 0 :_Orientation.size());
        while (idx2 != len2) {
            idx2 += 1;
        }
        while (idx1 != len1) {
            idx1 += 1;
        }
        while (idx3 != len3) {
            idx3 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (org.uniprot.uniprot.SubcellularLocationType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000 com.sun.msv.grammar.OneOrMor"
+"eExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000"
+"\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclare"
+"dAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\npp\u0000sr\u0000"
+"\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsq\u0000~\u0000\u0007sr\u0000\u0011j"
+"ava.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.gramm"
+"ar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u000bxq\u0000~\u0000\u0003"
+"q\u0000~\u0000\u0014psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0013\u0001q\u0000~\u0000\u0018sr\u0000 com.sun.msv.grammar.AnyName"
+"Class\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0019q\u0000~\u0000\u001esr\u0000#com.sun.msv.grammar.SimpleNameClass"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq"
+"\u0000~\u0000 xq\u0000~\u0000\u001bt\u0000\'org.uniprot.uniprot.EvidencedStringTypet\u0000+http:"
+"//java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0015q\u0000~\u0000\u0014psr"
+"\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng"
+"/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util"
+"/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr"
+"\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnames"
+"paceUriq\u0000~\u0000 L\u0000\btypeNameq\u0000~\u0000 L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/dat"
+"atype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XM"
+"LSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProces"
+"sor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpa"
+"ceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$N"
+"ullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.Stri"
+"ngPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000 L\u0000\fnamespaceURIq\u0000~\u0000 xpq\u0000~\u0000"
+"1q\u0000~\u00000sq\u0000~\u0000\u001ft\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-insta"
+"nceq\u0000~\u0000\u001esq\u0000~\u0000\u001ft\u0000\blocationt\u0000\u001ahttp://uniprot.org/uniprotsq\u0000~\u0000\u0010"
+"ppsq\u0000~\u0000\u0007q\u0000~\u0000\u0014psq\u0000~\u0000\nq\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\npp\u0000sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0007q\u0000"
+"~\u0000\u0014psq\u0000~\u0000\u0015q\u0000~\u0000\u0014pq\u0000~\u0000\u0018q\u0000~\u0000\u001cq\u0000~\u0000\u001esq\u0000~\u0000\u001fq\u0000~\u0000\"q\u0000~\u0000#sq\u0000~\u0000\u0010ppsq\u0000~\u0000"
+"\u0015q\u0000~\u0000\u0014pq\u0000~\u0000)q\u0000~\u00009q\u0000~\u0000\u001esq\u0000~\u0000\u001ft\u0000\btopologyq\u0000~\u0000>q\u0000~\u0000\u001esq\u0000~\u0000\u0010ppsq\u0000"
+"~\u0000\u0007q\u0000~\u0000\u0014psq\u0000~\u0000\nq\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\npp\u0000sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0007q\u0000~\u0000\u0014ps"
+"q\u0000~\u0000\u0015q\u0000~\u0000\u0014pq\u0000~\u0000\u0018q\u0000~\u0000\u001cq\u0000~\u0000\u001esq\u0000~\u0000\u001fq\u0000~\u0000\"q\u0000~\u0000#sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0015q\u0000~\u0000"
+"\u0014pq\u0000~\u0000)q\u0000~\u00009q\u0000~\u0000\u001esq\u0000~\u0000\u001ft\u0000\u000borientationq\u0000~\u0000>q\u0000~\u0000\u001esr\u0000\"com.sun.m"
+"sv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/m"
+"sv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.gramm"
+"ar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVers"
+"ionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0013\u0001pq"
+"\u0000~\u0000\u0011q\u0000~\u0000Dq\u0000~\u0000Qq\u0000~\u0000\u0005q\u0000~\u0000\u000eq\u0000~\u0000Bq\u0000~\u0000Oq\u0000~\u0000\tq\u0000~\u0000@q\u0000~\u0000Mq\u0000~\u0000\u0012q\u0000~\u0000Eq"
+"\u0000~\u0000Rq\u0000~\u0000\u0006q\u0000~\u0000?q\u0000~\u0000Lq\u0000~\u0000$q\u0000~\u0000Hq\u0000~\u0000Ux"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if ((null == obj)||(!(obj instanceof org.uniprot.uniprot.SubcellularLocationType))) {
            return false;
        }
        org.uniprot.uniprot.impl.SubcellularLocationTypeImpl target = ((org.uniprot.uniprot.impl.SubcellularLocationTypeImpl) obj);
        {
            java.util.List value = this.getTopology();
            java.util.List targetValue = target.getTopology();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getLocation();
            java.util.List targetValue = target.getLocation();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getOrientation();
            java.util.List targetValue = target.getOrientation();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        {
            java.util.List value = this.getTopology();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getLocation();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getOrientation();
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
            super(context, "----------");
        }

        protected Unmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return org.uniprot.uniprot.impl.SubcellularLocationTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  6 :
                        if (("topology" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 4;
                            return ;
                        }
                        if (("orientation" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 7;
                            return ;
                        }
                        state = 9;
                        continue outer;
                    case  9 :
                        if (("orientation" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 7;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  3 :
                        if (("location" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        if (("topology" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 4;
                            return ;
                        }
                        state = 6;
                        continue outer;
                    case  4 :
                        attIdx = context.getAttribute("", "evidence");
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
                        break;
                    case  1 :
                        attIdx = context.getAttribute("", "evidence");
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
                        break;
                    case  0 :
                        if (("location" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        break;
                    case  7 :
                        attIdx = context.getAttribute("", "evidence");
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
                        break;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  6 :
                        state = 9;
                        continue outer;
                    case  9 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  3 :
                        state = 6;
                        continue outer;
                    case  4 :
                        attIdx = context.getAttribute("", "evidence");
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
                        break;
                    case  1 :
                        attIdx = context.getAttribute("", "evidence");
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
                        break;
                    case  5 :
                        if (("topology" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                    case  8 :
                        if (("orientation" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  2 :
                        if (("location" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  7 :
                        attIdx = context.getAttribute("", "evidence");
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
                        break;
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
                    case  6 :
                        state = 9;
                        continue outer;
                    case  9 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  3 :
                        state = 6;
                        continue outer;
                    case  4 :
                        if (("evidence" == ___local)&&("" == ___uri)) {
                            _getTopology().add(((org.uniprot.uniprot.impl.EvidencedStringTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.EvidencedStringTypeImpl.class), 5, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("status" == ___local)&&("" == ___uri)) {
                            _getTopology().add(((org.uniprot.uniprot.impl.EvidencedStringTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.EvidencedStringTypeImpl.class), 5, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  1 :
                        if (("evidence" == ___local)&&("" == ___uri)) {
                            _getLocation().add(((org.uniprot.uniprot.impl.EvidencedStringTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.EvidencedStringTypeImpl.class), 2, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("status" == ___local)&&("" == ___uri)) {
                            _getLocation().add(((org.uniprot.uniprot.impl.EvidencedStringTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.EvidencedStringTypeImpl.class), 2, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  7 :
                        if (("evidence" == ___local)&&("" == ___uri)) {
                            _getOrientation().add(((org.uniprot.uniprot.impl.EvidencedStringTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.EvidencedStringTypeImpl.class), 8, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("status" == ___local)&&("" == ___uri)) {
                            _getOrientation().add(((org.uniprot.uniprot.impl.EvidencedStringTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.EvidencedStringTypeImpl.class), 8, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
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
                    case  6 :
                        state = 9;
                        continue outer;
                    case  9 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  3 :
                        state = 6;
                        continue outer;
                    case  4 :
                        attIdx = context.getAttribute("", "evidence");
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
                        break;
                    case  1 :
                        attIdx = context.getAttribute("", "evidence");
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
                        break;
                    case  7 :
                        attIdx = context.getAttribute("", "evidence");
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
                        case  6 :
                            state = 9;
                            continue outer;
                        case  9 :
                            revertToParentFromText(value);
                            return ;
                        case  3 :
                            state = 6;
                            continue outer;
                        case  4 :
                            attIdx = context.getAttribute("", "evidence");
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
                            _getTopology().add(((org.uniprot.uniprot.impl.EvidencedStringTypeImpl) spawnChildFromText((org.uniprot.uniprot.impl.EvidencedStringTypeImpl.class), 5, value)));
                            return ;
                        case  1 :
                            attIdx = context.getAttribute("", "evidence");
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
                            _getLocation().add(((org.uniprot.uniprot.impl.EvidencedStringTypeImpl) spawnChildFromText((org.uniprot.uniprot.impl.EvidencedStringTypeImpl.class), 2, value)));
                            return ;
                        case  7 :
                            attIdx = context.getAttribute("", "evidence");
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
                            _getOrientation().add(((org.uniprot.uniprot.impl.EvidencedStringTypeImpl) spawnChildFromText((org.uniprot.uniprot.impl.EvidencedStringTypeImpl.class), 8, value)));
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