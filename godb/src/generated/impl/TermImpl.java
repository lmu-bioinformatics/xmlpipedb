//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.26 at 12:22:45 AM PDT 
//


package generated.impl;

public class TermImpl implements generated.Term, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, generated.impl.runtime.UnmarshallableObject, generated.impl.runtime.XMLSerializable, generated.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Content;
    public final static java.lang.Class version = (generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;
    protected java.lang.Long _Hjid;
    protected java.lang.Long _Hjversion;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (generated.Term.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "term";
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
        return new generated.impl.TermImpl.Unmarshaller(context);
    }

    public void serializeBody(generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Content == null)? 0 :_Content.size());
        context.startElement("", "term");
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
        return (generated.Term.class);
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
+"ppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq"
+"\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\nppsq\u0000~\u0000\n"
+"ppsq\u0000~\u0000\nsr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\nq\u0000"
+"~\u0000\"psq\u0000~\u0000\nq\u0000~\u0000\"psq\u0000~\u0000\u0000q\u0000~\u0000\"p\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psr\u0000 com.sun"
+".msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq"
+"\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\"psr\u00002com.sun.msv.grammar.Expression$AnyString"
+"Expression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000!\u0001q\u0000~\u0000+sr\u0000 com.sun.msv.gramm"
+"ar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpres"
+"sion\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000,q\u0000~\u00001sr\u0000#com.sun.msv.grammar.Simpl"
+"eNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnam"
+"espaceURIq\u0000~\u00003xq\u0000~\u0000.t\u0000\fgenerated.Idt\u0000+http://java.sun.com/ja"
+"xb/xjc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000\"p\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000("
+"q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u000egenerated.Nameq\u0000~\u00006sq\u0000~\u0000\u0000q\u0000~\u0000\""
+"p\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0013ge"
+"nerated.Namespaceq\u0000~\u00006sq\u0000~\u0000\u0000q\u0000~\u0000\"p\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000"
+"(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\rgenerated.Defq\u0000~\u00006q\u0000~\u00001sq\u0000~\u0000\u0007"
+"ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq"
+"\u0000~\u00002t\u0000\rgenerated.IsAq\u0000~\u00006sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000"
+"\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u000fgenerated.AltIdq\u0000~\u00006sq"
+"\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~"
+"\u00001sq\u0000~\u00002t\u0000\u0010generated.Subsetq\u0000~\u00006sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\""
+"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0011generated.Commentq\u0000~\u00006s"
+"q\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u0000"
+"2t\u0000\u0015generated.IsAnonymousq\u0000~\u00006sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"ps"
+"q\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0014generated.IsObsoleteq\u0000~\u00006"
+"sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q"
+"\u0000~\u00001sq\u0000~\u00002t\u0000\u0012generated.Considerq\u0000~\u00006sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\np"
+"psq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0014generated.R"
+"eplacedByq\u0000~\u00006sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+"
+"q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0010generated.IsRootq\u0000~\u00006sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000"
+"~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0014generat"
+"ed.XrefAnalogq\u0000~\u00006sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000"
+"(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0015generated.XrefUnknownq\u0000~\u00006sq\u0000"
+"~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u0000"
+"1sq\u0000~\u00002t\u0000\u0011generated.Synonymq\u0000~\u00006sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000"
+"~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0016generated.Relat"
+"ionshipq\u0000~\u00006sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\""
+"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0018generated.IntersectionOfq\u0000~\u00006sq\u0000~\u0000\u0007"
+"ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq"
+"\u0000~\u00002t\u0000\u0011generated.UnionOfq\u0000~\u00006sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007"
+"q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0016generated.Disjoint"
+"Fromq\u0000~\u00006sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/"
+"q\u0000~\u00001sq\u0000~\u00002t\u0000\u0019generated.LexicalCategoryq\u0000~\u00006sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\np"
+"psq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0013generated.C"
+"reatedByq\u0000~\u00006sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007q\u0000~\u0000\"psq\u0000~\u0000(q\u0000~\u0000\"pq\u0000~\u0000+q"
+"\u0000~\u0000/q\u0000~\u00001sq\u0000~\u00002t\u0000\u0016generated.CreationDateq\u0000~\u00006sq\u0000~\u00002t\u0000\u0004termt\u0000"
+"\u0000sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTabl"
+"et\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com"
+".sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005coun"
+"tB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/Expression"
+"Pool;xp\u0000\u0000\u0000R\u0001pq\u0000~\u0000\fq\u0000~\u0000\u001eq\u0000~\u0000\u0017q\u0000~\u0000\u001dq\u0000~\u0000\u0014q\u0000~\u0000$q\u0000~\u0000\u000eq\u0000~\u0000\tq\u0000~\u0000\u001cq\u0000"
+"~\u0000\u0019q\u0000~\u0000\u0011q\u0000~\u0000\u009cq\u0000~\u0000\u0095q\u0000~\u0000\u008eq\u0000~\u0000\u0087q\u0000~\u0000\u0080q\u0000~\u0000zq\u0000~\u0000sq\u0000~\u0000lq\u0000~\u0000fq\u0000~\u0000`q\u0000"
+"~\u0000Zq\u0000~\u0000Sq\u0000~\u0000Lq\u0000~\u0000Eq\u0000~\u0000?q\u0000~\u00009q\u0000~\u0000\'q\u0000~\u0000\u001aq\u0000~\u0000\u00a3q\u0000~\u0000\u00aaq\u0000~\u0000\u00b1q\u0000~\u0000\u00b7q\u0000"
+"~\u0000\u00bdq\u0000~\u0000\u00c3q\u0000~\u0000\u0015q\u0000~\u0000\u0016q\u0000~\u0000\u000fq\u0000~\u0000\rq\u0000~\u0000\u0092q\u0000~\u0000\u008bq\u0000~\u0000\u0084q\u0000~\u0000wq\u0000~\u0000pq\u0000~\u0000Wq\u0000"
+"~\u0000Pq\u0000~\u0000Iq\u0000~\u0000\u0099q\u0000~\u0000\u00a0q\u0000~\u0000\u00a7q\u0000~\u0000\u00aeq\u0000~\u0000\u0012q\u0000~\u0000\u0010q\u0000~\u0000\u001fq\u0000~\u0000#q\u0000~\u0000\u0018q\u0000~\u0000\u001bq\u0000"
+"~\u0000\u0013q\u0000~\u0000 q\u0000~\u0000\u0094q\u0000~\u0000\u008dq\u0000~\u0000\u0086q\u0000~\u0000\u007fq\u0000~\u0000yq\u0000~\u0000rq\u0000~\u0000kq\u0000~\u0000eq\u0000~\u0000_q\u0000~\u0000Yq\u0000"
+"~\u0000Rq\u0000~\u0000Kq\u0000~\u0000Dq\u0000~\u0000>q\u0000~\u00008q\u0000~\u0000&q\u0000~\u0000\u009bq\u0000~\u0000\u00a2q\u0000~\u0000\u00a9q\u0000~\u0000\u00b0q\u0000~\u0000\u00b6q\u0000~\u0000\u00bcq\u0000"
+"~\u0000\u00c2x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if ((null == obj)||(!(obj instanceof generated.Term))) {
            return false;
        }
        generated.impl.TermImpl target = ((generated.impl.TermImpl) obj);
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
            return generated.impl.TermImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IdImpl) spawnChildFromEnterElement((generated.impl.IdImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("name" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.NameImpl) spawnChildFromEnterElement((generated.impl.NameImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("namespace" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.NamespaceImpl) spawnChildFromEnterElement((generated.impl.NamespaceImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("def" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DefImpl) spawnChildFromEnterElement((generated.impl.DefImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("is_a" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IsAImpl) spawnChildFromEnterElement((generated.impl.IsAImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("alt_id" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.AltIdImpl) spawnChildFromEnterElement((generated.impl.AltIdImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("subset" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SubsetImpl) spawnChildFromEnterElement((generated.impl.SubsetImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("comment" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.CommentImpl) spawnChildFromEnterElement((generated.impl.CommentImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("is_anonymous" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IsAnonymousImpl) spawnChildFromEnterElement((generated.impl.IsAnonymousImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("is_obsolete" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IsObsoleteImpl) spawnChildFromEnterElement((generated.impl.IsObsoleteImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("consider" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.ConsiderImpl) spawnChildFromEnterElement((generated.impl.ConsiderImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("replaced_by" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.ReplacedByImpl) spawnChildFromEnterElement((generated.impl.ReplacedByImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("is_root" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IsRootImpl) spawnChildFromEnterElement((generated.impl.IsRootImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("xref_analog" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.XrefAnalogImpl) spawnChildFromEnterElement((generated.impl.XrefAnalogImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("xref_unknown" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.XrefUnknownImpl) spawnChildFromEnterElement((generated.impl.XrefUnknownImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("synonym" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SynonymImpl) spawnChildFromEnterElement((generated.impl.SynonymImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("relationship" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.RelationshipImpl) spawnChildFromEnterElement((generated.impl.RelationshipImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("intersection_of" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IntersectionOfImpl) spawnChildFromEnterElement((generated.impl.IntersectionOfImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("union_of" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.UnionOfImpl) spawnChildFromEnterElement((generated.impl.UnionOfImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("disjoint_from" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DisjointFromImpl) spawnChildFromEnterElement((generated.impl.DisjointFromImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("lexical_category" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.LexicalCategoryImpl) spawnChildFromEnterElement((generated.impl.LexicalCategoryImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("created_by" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.CreatedByImpl) spawnChildFromEnterElement((generated.impl.CreatedByImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("creation_date" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.CreationDateImpl) spawnChildFromEnterElement((generated.impl.CreationDateImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        state = 2;
                        continue outer;
                    case  0 :
                        if (("term" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        break;
                    case  2 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IdImpl) spawnChildFromEnterElement((generated.impl.IdImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("name" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.NameImpl) spawnChildFromEnterElement((generated.impl.NameImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("namespace" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.NamespaceImpl) spawnChildFromEnterElement((generated.impl.NamespaceImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("def" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DefImpl) spawnChildFromEnterElement((generated.impl.DefImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("is_a" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IsAImpl) spawnChildFromEnterElement((generated.impl.IsAImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("alt_id" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.AltIdImpl) spawnChildFromEnterElement((generated.impl.AltIdImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("subset" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SubsetImpl) spawnChildFromEnterElement((generated.impl.SubsetImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("comment" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.CommentImpl) spawnChildFromEnterElement((generated.impl.CommentImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("is_anonymous" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IsAnonymousImpl) spawnChildFromEnterElement((generated.impl.IsAnonymousImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("is_obsolete" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IsObsoleteImpl) spawnChildFromEnterElement((generated.impl.IsObsoleteImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("consider" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.ConsiderImpl) spawnChildFromEnterElement((generated.impl.ConsiderImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("replaced_by" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.ReplacedByImpl) spawnChildFromEnterElement((generated.impl.ReplacedByImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("is_root" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IsRootImpl) spawnChildFromEnterElement((generated.impl.IsRootImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("xref_analog" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.XrefAnalogImpl) spawnChildFromEnterElement((generated.impl.XrefAnalogImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("xref_unknown" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.XrefUnknownImpl) spawnChildFromEnterElement((generated.impl.XrefUnknownImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("synonym" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.SynonymImpl) spawnChildFromEnterElement((generated.impl.SynonymImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("relationship" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.RelationshipImpl) spawnChildFromEnterElement((generated.impl.RelationshipImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("intersection_of" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.IntersectionOfImpl) spawnChildFromEnterElement((generated.impl.IntersectionOfImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("union_of" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.UnionOfImpl) spawnChildFromEnterElement((generated.impl.UnionOfImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("disjoint_from" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.DisjointFromImpl) spawnChildFromEnterElement((generated.impl.DisjointFromImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("lexical_category" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.LexicalCategoryImpl) spawnChildFromEnterElement((generated.impl.LexicalCategoryImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("created_by" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.CreatedByImpl) spawnChildFromEnterElement((generated.impl.CreatedByImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("creation_date" == ___local)&&("" == ___uri)) {
                            _getContent().add(((generated.impl.CreationDateImpl) spawnChildFromEnterElement((generated.impl.CreationDateImpl.class), 2, ___uri, ___local, ___qname, __atts)));
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
                        if (("term" == ___local)&&("" == ___uri)) {
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
