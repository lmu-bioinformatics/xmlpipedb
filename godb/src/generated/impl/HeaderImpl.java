//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.09 at 03:44:18 PM PDT 
//


package generated.impl;

public class HeaderImpl implements generated.Header, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, generated.impl.runtime.UnmarshallableObject, generated.impl.runtime.XMLSerializable, generated.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Content;
    public final static java.lang.Class version = (generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;
    protected java.lang.Long _Hjid;
    protected java.lang.Long _Hjversion;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (generated.Header.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "header";
    }

    protected com.sun.xml.bind.util.ListImpl _getContent() {
        if (_Content == null) {
            _Content = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Content;
    }

    public java.util.List getContent() {
        return _getContent();
    }

    public generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(generated.impl.runtime.UnmarshallingContext context) {
        return new generated.impl.HeaderImpl.Unmarshaller(context);
    }

    public void serializeBody(generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Content == null)? 0 :_Content.size());
        context.startElement("", "header");
        int idx_0 = idx1;
        while (idx_0 != len1) {
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Content.get(idx_0 ++)), "Content");
        }
        context.endNamespaceDecls();
        int idx_1 = idx1;
        while (idx_1 != len1) {
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Content.get(idx_1 ++)), "Content");
        }
        context.endAttributes();
        while (idx1 != len1) {
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Content.get(idx1 ++)), "Content");
        }
        context.endElement();
    }

    public void serializeAttributes(generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Content == null)? 0 :_Content.size());
    }

    public void serializeURIs(generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Content == null)? 0 :_Content.size());
    }

    public java.lang.Class getPrimaryInterface() {
        return (generated.Header.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
+"grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
+"\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
+"msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
+"ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000\u001dcom.sun.msv.gra"
+"mmar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsr\u0000 com.sun.msv.gra"
+"mmar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001"
+"Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u0007q\u0000~\u0000\u000epsq\u0000~\u0000\u0007q\u0000~\u0000\u000epsq\u0000~\u0000\u0007q\u0000~\u0000\u000epsq\u0000~\u0000\u0007q\u0000~\u0000\u000ep"
+"sq\u0000~\u0000\u0007q\u0000~\u0000\u000epsq\u0000~\u0000\u0007q\u0000~\u0000\u000epsq\u0000~\u0000\u0007q\u0000~\u0000\u000epsq\u0000~\u0000\u0007q\u0000~\u0000\u000epsq\u0000~\u0000\u0007q\u0000~\u0000\u000ep"
+"sq\u0000~\u0000\u0007q\u0000~\u0000\u000epsq\u0000~\u0000\u0007q\u0000~\u0000\u000epsq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\nq\u0000~\u0000\u000epsr\u0000"
+" com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tna"
+"meClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u000epsr\u00002com.sun.msv.grammar.Expression$A"
+"nyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\r\u0001q\u0000~\u0000 sr\u0000 com.sun.m"
+"sv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.Na"
+"meClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$Epsil"
+"onExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000!q\u0000~\u0000&sr\u0000#com.sun.msv.gramm"
+"ar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/Strin"
+"g;L\u0000\fnamespaceURIq\u0000~\u0000(xq\u0000~\u0000#t\u0000\u0017generated.FormatVersiont\u0000+htt"
+"p://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007p"
+"psq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000epq\u0000~\u0000 q\u0000~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t\u0000\u0015generated.D"
+"ataVersionq\u0000~\u0000+sq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000ep"
+"q\u0000~\u0000 q\u0000~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t\u0000\u000egenerated.Dateq\u0000~\u0000+sq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~"
+"\u0000\u0007ppsq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000epq\u0000~\u0000 q\u0000~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t\u0000\u0011generate"
+"d.SavedByq\u0000~\u0000+sq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000epq"
+"\u0000~\u0000 q\u0000~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t\u0000\u0019generated.AutoGeneratedByq\u0000~\u0000+sq\u0000~\u0000\u0000q"
+"\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000epq\u0000~\u0000 q\u0000~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t"
+"\u0000\u001agenerated.DefaultNamespaceq\u0000~\u0000+sq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\n"
+"q\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000epq\u0000~\u0000 q\u0000~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t\u0000\u0010generated.Remarkq\u0000"
+"~\u0000+sq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000epq\u0000~\u0000 q\u0000~\u0000$q\u0000"
+"~\u0000&sq\u0000~\u0000\'t\u0000\u0012generated.Ontologyq\u0000~\u0000+sq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007ppsq\u0000~"
+"\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000epq\u0000~\u0000 q\u0000~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t\u0000\u0013generated.Subset"
+"defq\u0000~\u0000+sq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000epq\u0000~\u0000 q\u0000"
+"~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t\u0000\u0018generated.Synonymtypedefq\u0000~\u0000+sq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000s"
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000\u000epq\u0000~\u0000 q\u0000~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t\u0000\u0011gener"
+"ated.Idspaceq\u0000~\u0000+sq\u0000~\u0000\u0000q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u001dq\u0000~\u0000"
+"\u000epq\u0000~\u0000 q\u0000~\u0000$q\u0000~\u0000&sq\u0000~\u0000\'t\u0000\u0017generated.PropertyValueq\u0000~\u0000+q\u0000~\u0000&s"
+"q\u0000~\u0000\'t\u0000\u0006headert\u0000\u0000sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$Clos"
+"edHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j"
+"\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/gr"
+"ammar/ExpressionPool;xp\u0000\u0000\u0000%\u0001pq\u0000~\u0000\fq\u0000~\u0000\tq\u0000~\u0000\u0014q\u0000~\u0000\u0019q\u0000~\u0000\u0016q\u0000~\u0000\u000fq"
+"\u0000~\u0000\u001cq\u0000~\u0000.q\u0000~\u00004q\u0000~\u0000:q\u0000~\u0000@q\u0000~\u0000\u0018q\u0000~\u0000Fq\u0000~\u0000Lq\u0000~\u0000Rq\u0000~\u0000Xq\u0000~\u0000^q\u0000~\u0000dq"
+"\u0000~\u0000jq\u0000~\u0000\u0017q\u0000~\u0000\u0010q\u0000~\u0000\u0011q\u0000~\u0000\u0013q\u0000~\u0000\u0015q\u0000~\u0000\u0012q\u0000~\u0000\u001bq\u0000~\u0000-q\u0000~\u00003q\u0000~\u00009q\u0000~\u0000?q"
+"\u0000~\u0000Eq\u0000~\u0000Kq\u0000~\u0000Qq\u0000~\u0000Wq\u0000~\u0000]q\u0000~\u0000cq\u0000~\u0000ix"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if ((null == obj)||(!(obj instanceof generated.Header))) {
            return false;
        }
        generated.impl.HeaderImpl target = ((generated.impl.HeaderImpl) obj);
        {
            java.util.List value = this.getContent();
            java.util.List targetValue = target.getContent();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        {
            java.util.List value = this.getContent();
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
        extends generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(generated.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return generated.impl.HeaderImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        if (("format-version" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.FormatVersionImpl) spawnChildFromEnterElement((generated.impl.FormatVersionImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("data-version" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DataVersionImpl) spawnChildFromEnterElement((generated.impl.DataVersionImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("date" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DateImpl) spawnChildFromEnterElement((generated.impl.DateImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("saved-by" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SavedByImpl) spawnChildFromEnterElement((generated.impl.SavedByImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("auto-generated-by" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.AutoGeneratedByImpl) spawnChildFromEnterElement((generated.impl.AutoGeneratedByImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("default-namespace" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DefaultNamespaceImpl) spawnChildFromEnterElement((generated.impl.DefaultNamespaceImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("remark" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.RemarkImpl) spawnChildFromEnterElement((generated.impl.RemarkImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ontology" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.OntologyImpl) spawnChildFromEnterElement((generated.impl.OntologyImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("subsetdef" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SubsetdefImpl) spawnChildFromEnterElement((generated.impl.SubsetdefImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("synonymtypedef" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SynonymtypedefImpl) spawnChildFromEnterElement((generated.impl.SynonymtypedefImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("idspace" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IdspaceImpl) spawnChildFromEnterElement((generated.impl.IdspaceImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("property_value" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.PropertyValueImpl) spawnChildFromEnterElement((generated.impl.PropertyValueImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        state = 2;
                        continue outer;
                    case  2 :
                        if (("format-version" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.FormatVersionImpl) spawnChildFromEnterElement((generated.impl.FormatVersionImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("data-version" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DataVersionImpl) spawnChildFromEnterElement((generated.impl.DataVersionImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("date" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DateImpl) spawnChildFromEnterElement((generated.impl.DateImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("saved-by" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SavedByImpl) spawnChildFromEnterElement((generated.impl.SavedByImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("auto-generated-by" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.AutoGeneratedByImpl) spawnChildFromEnterElement((generated.impl.AutoGeneratedByImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("default-namespace" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DefaultNamespaceImpl) spawnChildFromEnterElement((generated.impl.DefaultNamespaceImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("remark" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.RemarkImpl) spawnChildFromEnterElement((generated.impl.RemarkImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ontology" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.OntologyImpl) spawnChildFromEnterElement((generated.impl.OntologyImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("subsetdef" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SubsetdefImpl) spawnChildFromEnterElement((generated.impl.SubsetdefImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("synonymtypedef" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SynonymtypedefImpl) spawnChildFromEnterElement((generated.impl.SynonymtypedefImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("idspace" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IdspaceImpl) spawnChildFromEnterElement((generated.impl.IdspaceImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("property_value" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.PropertyValueImpl) spawnChildFromEnterElement((generated.impl.PropertyValueImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  0 :
                        if (("header" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
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
                    case  1 :
                        state = 2;
                        continue outer;
                    case  2 :
                        if (("header" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  3 :
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
                    case  1 :
                        state = 2;
                        continue outer;
                    case  3 :
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
                    case  1 :
                        state = 2;
                        continue outer;
                    case  3 :
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
                        case  1 :
                            state = 2;
                            continue outer;
                        case  3 :
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
