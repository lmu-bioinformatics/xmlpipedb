//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.18 at 12:19:02 AM PDT 
//


package generated.impl;

public class SourceImpl implements generated.Source, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, generated.impl.runtime.UnmarshallableObject, generated.impl.runtime.XMLSerializable, generated.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Content;
    public final static java.lang.Class version = (generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;
    protected java.lang.Long _Hjid;
    protected java.lang.Long _Hjversion;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (generated.Source.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "source";
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
        return new generated.impl.SourceImpl.Unmarshaller(context);
    }

    public void serializeBody(generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Content == null)? 0 :_Content.size());
        context.startElement("", "source");
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
        return (generated.Source.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
+"grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
+"\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
+"msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
+"ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000 com.sun.msv.gra"
+"mmar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsr\u0000\u001dcom.sun.msv.grammar.Choice"
+"Exp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000"
+"\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\n"
+"ppsq\u0000~\u0000\nppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c"
+"\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u0016psr\u00002com.sun.msv."
+"grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000"
+"\u0015\u0001q\u0000~\u0000\u001asr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dco"
+"m.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.gram"
+"mar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u001bq\u0000~\u0000 s"
+"r\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNam"
+"et\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000\"xq\u0000~\u0000\u001dt\u0000\u0012generated"
+".SourceIdt\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000"
+"\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\u0016psq\u0000~\u0000\u0017q\u0000~\u0000\u0016pq\u0000~\u0000\u001aq\u0000~\u0000\u001eq\u0000~\u0000 sq\u0000~\u0000!t\u0000\u0014"
+"generated.SourceTypeq\u0000~\u0000%sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\u0016psq\u0000~\u0000\u0017"
+"q\u0000~\u0000\u0016pq\u0000~\u0000\u001aq\u0000~\u0000\u001eq\u0000~\u0000 sq\u0000~\u0000!t\u0000\u0018generated.SourceFullpathq\u0000~\u0000%s"
+"q\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\u0016psq\u0000~\u0000\u0017q\u0000~\u0000\u0016pq\u0000~\u0000\u001aq\u0000~\u0000\u001eq\u0000~\u0000 sq\u0000~\u0000"
+"!t\u0000\u0014generated.SourcePathq\u0000~\u0000%sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\u0016psq"
+"\u0000~\u0000\u0017q\u0000~\u0000\u0016pq\u0000~\u0000\u001aq\u0000~\u0000\u001eq\u0000~\u0000 sq\u0000~\u0000!t\u0000\u0013generated.SourceMd5q\u0000~\u0000%sq"
+"\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\u0016psq\u0000~\u0000\u0017q\u0000~\u0000\u0016pq\u0000~\u0000\u001aq\u0000~\u0000\u001eq\u0000~\u0000 sq\u0000~\u0000!"
+"t\u0000\u0015generated.SourceMtimeq\u0000~\u0000%sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\u0016psq"
+"\u0000~\u0000\u0017q\u0000~\u0000\u0016pq\u0000~\u0000\u001aq\u0000~\u0000\u001eq\u0000~\u0000 sq\u0000~\u0000!t\u0000\u0019generated.SourceParsetimeq"
+"\u0000~\u0000%sq\u0000~\u0000!t\u0000\u0006sourcet\u0000\u0000sr\u0000\"com.sun.msv.grammar.ExpressionPool"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool"
+"$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedH"
+"ash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/m"
+"sv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0015\u0001pq\u0000~\u0000(q\u0000~\u0000.q\u0000~\u00004q\u0000~\u0000:q\u0000~\u0000@q"
+"\u0000~\u0000Fq\u0000~\u0000\fq\u0000~\u0000\u0011q\u0000~\u0000\rq\u0000~\u0000\u000eq\u0000~\u0000\tq\u0000~\u0000\u000fq\u0000~\u0000\u0010q\u0000~\u0000\u0013q\u0000~\u0000\'q\u0000~\u0000-q\u0000~\u00003q"
+"\u0000~\u00009q\u0000~\u0000?q\u0000~\u0000Eq\u0000~\u0000\u0014x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if ((null == obj)||(!(obj instanceof generated.Source))) {
            return false;
        }
        generated.impl.SourceImpl target = ((generated.impl.SourceImpl) obj);
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
            return generated.impl.SourceImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  2 :
                        if (("source_id" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceIdImpl) spawnChildFromEnterElement((generated.impl.SourceIdImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_type" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceTypeImpl) spawnChildFromEnterElement((generated.impl.SourceTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_fullpath" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceFullpathImpl) spawnChildFromEnterElement((generated.impl.SourceFullpathImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_path" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourcePathImpl) spawnChildFromEnterElement((generated.impl.SourcePathImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_md5" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceMd5Impl) spawnChildFromEnterElement((generated.impl.SourceMd5Impl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_mtime" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceMtimeImpl) spawnChildFromEnterElement((generated.impl.SourceMtimeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_parsetime" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceParsetimeImpl) spawnChildFromEnterElement((generated.impl.SourceParsetimeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  1 :
                        if (("source_id" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceIdImpl) spawnChildFromEnterElement((generated.impl.SourceIdImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_type" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceTypeImpl) spawnChildFromEnterElement((generated.impl.SourceTypeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_fullpath" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceFullpathImpl) spawnChildFromEnterElement((generated.impl.SourceFullpathImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_path" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourcePathImpl) spawnChildFromEnterElement((generated.impl.SourcePathImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_md5" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceMd5Impl) spawnChildFromEnterElement((generated.impl.SourceMd5Impl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_mtime" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceMtimeImpl) spawnChildFromEnterElement((generated.impl.SourceMtimeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("source_parsetime" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SourceParsetimeImpl) spawnChildFromEnterElement((generated.impl.SourceParsetimeImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  0 :
                        if (("source" == ___local)&&("" == ___uri)) {
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
                    case  2 :
                        if (("source" == ___local)&&("" == ___uri)) {
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
