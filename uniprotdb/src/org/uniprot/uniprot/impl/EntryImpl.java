//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.22 at 05:56:20 PM PDT 
//


package org.uniprot.uniprot.impl;

public class EntryImpl
    extends org.uniprot.uniprot.impl.EntryTypeImpl
    implements org.uniprot.uniprot.Entry, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, org.uniprot.uniprot.impl.runtime.UnmarshallableObject, org.uniprot.uniprot.impl.runtime.XMLSerializable, org.uniprot.uniprot.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (org.uniprot.uniprot.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (org.uniprot.uniprot.Entry.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "http://uniprot.org/uniprot";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "entry";
    }

    public org.uniprot.uniprot.impl.runtime.UnmarshallingEventHandler createUnmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context) {
        return new org.uniprot.uniprot.impl.EntryImpl.Unmarshaller(context);
    }

    public void serializeBody(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("http://uniprot.org/uniprot", "entry");
        super.serializeURIs(context);
        context.endNamespaceDecls();
        super.serializeAttributes(context);
        context.endAttributes();
        super.serializeBody(context);
        context.endElement();
    }

    public void serializeAttributes(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public void serializeURIs(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (org.uniprot.uniprot.Entry.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
+"grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
+"\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
+"msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
+"ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000\u001fcom.sun.msv.gra"
+"mmar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007pps"
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000"
+"\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007pps"
+"q\u0000~\u0000\u0007ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001cco"
+"m.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~"
+"\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dt"
+"t\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLc"
+"om/sun/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000#com.sun.msv.datatype."
+"xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.dat"
+"atype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.dataty"
+"pe.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.X"
+"SDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;"
+"L\u0000\btypeNameq\u0000~\u0000)L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/Wh"
+"iteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006st"
+"ringsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserv"
+"e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSetExpr"
+"ession\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ej"
+"B\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000)L\u0000\fnamespaceURIq\u0000~\u0000)xpq\u0000~\u0000-q\u0000~\u0000,sr\u0000\u001d"
+"com.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsr\u0000 com.sun."
+"msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000"
+"~\u0000\u0001xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000!p"
+"psr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000&q\u0000~\u0000"
+",t\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Co"
+"llapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000/q\u0000~\u00002sq\u0000~\u00003q\u0000~\u0000>q\u0000~\u0000,sr\u0000#com.sun.msv."
+"grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000)L\u0000\fnamesp"
+"aceURIq\u0000~\u0000)xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004"
+"typet\u0000)http://www.w3.org/2001/XMLSchema-instancesr\u00000com.sun."
+"msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000"
+"~\u00009\u0001q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\taccessiont\u0000\u001ahttp://uniprot.org/uniprotsq\u0000~"
+"\u0000\u001cppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000$sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000"
+"Hsq\u0000~\u0000Bt\u0000\u0004nameq\u0000~\u0000Lsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00005ppsq\u0000~\u0000\u001cq"
+"\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:psr\u00002com.sun.msv.grammar.Expression$AnyStrin"
+"gExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000Iq\u0000~\u0000[sr\u0000 com.sun.msv.gramma"
+"r.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Cq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u001forg.uniprot.uni"
+"prot.ProteinTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elemen"
+"tssq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u0007proteinq\u0000~\u0000Lsq"
+"\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u0000\u0000q\u0000~\u0000:p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00005ppsq\u0000~"
+"\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000[q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u001corg.uniprot.unip"
+"rot.GeneTypeq\u0000~\u0000`sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000"
+"\u0004geneq\u0000~\u0000Lq\u0000~\u0000Hsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:"
+"psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000[q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000 org.uniprot.uniprot.Org"
+"anismTypeq\u0000~\u0000`sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\bor"
+"ganismq\u0000~\u0000Lsq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u0000\u0000q\u0000~\u0000:p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp"
+"\u0000sq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000[q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bq\u0000~\u0000zq"
+"\u0000~\u0000`sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\forganismHost"
+"q\u0000~\u0000Lq\u0000~\u0000Hsq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u0000\u0000q\u0000~\u0000:p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000"
+"sq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000[q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000$org."
+"uniprot.uniprot.GeneLocationTypeq\u0000~\u0000`sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~"
+"\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\fgeneLocationq\u0000~\u0000Lq\u0000~\u0000Hsq\u0000~\u0000\u001cppsq\u0000~\u0000\u0000pp\u0000"
+"sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000[q\u0000~\u0000]q"
+"\u0000~\u0000Hsq\u0000~\u0000Bt\u0000!org.uniprot.uniprot.ReferenceTypeq\u0000~\u0000`sq\u0000~\u00005pps"
+"q\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\treferenceq\u0000~\u0000Lsq\u0000~\u00005ppsq\u0000"
+"~\u0000\u001cq\u0000~\u0000:psq\u0000~\u0000\u0000q\u0000~\u0000:p\u0000sq\u0000~\u00005ppsq\u0000~\u00007ppsr\u0000\u001ccom.sun.msv.gramma"
+"r.ValueExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtq\u0000~\u0000\"L\u0000\u0004nameq\u0000~\u0000#L\u0000\u0005valuet\u0000\u0012Ljava/"
+"lang/Object;xq\u0000~\u0000\u0004ppsr\u0000$com.sun.msv.datatype.xsd.BooleanType"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000&q\u0000~\u0000,t\u0000\u0007booleanq\u0000~\u0000@sq\u0000~\u00003q\u0000~\u0000\u00b1t\u0000\u0000q\u0000~\u0000Isq\u0000~"
+"\u0000Bt\u0000\u0003nilq\u0000~\u0000Fsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000"
+":pq\u0000~\u0000[q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u001forg.uniprot.uniprot.CommentTypeq\u0000~"
+"\u0000`sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u0007commentq\u0000~\u0000Lq\u0000"
+"~\u0000Hsq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u0000\u0000q\u0000~\u0000:p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00005p"
+"psq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000[q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000#org.uniprot"
+".uniprot.DbReferenceTypeq\u0000~\u0000`sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq"
+"\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u000bdbReferenceq\u0000~\u0000Lq\u0000~\u0000Hsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000"
+"sq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000[q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000(org."
+"uniprot.uniprot.ProteinExistenceTypeq\u0000~\u0000`sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:"
+"pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u0010proteinExistenceq\u0000~\u0000Lsq\u0000~\u00005ppsq\u0000~\u0000\u001c"
+"q\u0000~\u0000:psq\u0000~\u0000\u0000q\u0000~\u0000:p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~"
+"\u00007q\u0000~\u0000:pq\u0000~\u0000[q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u001forg.uniprot.uniprot.KeywordT"
+"ypeq\u0000~\u0000`sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u0007keywordq"
+"\u0000~\u0000Lq\u0000~\u0000Hsq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u0000\u0000q\u0000~\u0000:p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000s"
+"q\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000[q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u001forg.u"
+"niprot.uniprot.FeatureTypeq\u0000~\u0000`sq\u0000~\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000"
+"Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u0007featureq\u0000~\u0000Lq\u0000~\u0000Hsq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u0000\u0000q\u0000"
+"~\u0000:p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000[q"
+"\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000 org.uniprot.uniprot.EvidenceTypeq\u0000~\u0000`sq\u0000~\u0000"
+"5ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\bevidenceq\u0000~\u0000Lq\u0000~\u0000Hsq\u0000"
+"~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00005ppsq\u0000~\u0000\u001cq\u0000~\u0000:psq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000["
+"q\u0000~\u0000]q\u0000~\u0000Hsq\u0000~\u0000Bt\u0000 org.uniprot.uniprot.SequenceTypeq\u0000~\u0000`sq\u0000~"
+"\u00005ppsq\u0000~\u00007q\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\bsequenceq\u0000~\u0000Lsq\u0000~\u00007p"
+"psq\u0000~\u0000!q\u0000~\u0000:psr\u0000!com.sun.msv.datatype.xsd.DateType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xr\u0000)com.sun.msv.datatype.xsd.DateTimeBaseType\u0014W\u001a@3\u00a5\u00b4\u00e5\u0002\u0000\u0000xq\u0000"
+"~\u0000&q\u0000~\u0000,t\u0000\u0004dateq\u0000~\u0000@q\u0000~\u00002sq\u0000~\u00003q\u0000~\u0001\u0016q\u0000~\u0000,sq\u0000~\u0000Bt\u0000\u0007createdq\u0000~"
+"\u0000\u00b3sq\u0000~\u00007ppsq\u0000~\u0000!ppsr\u0000)com.sun.msv.datatype.xsd.EnumerationFa"
+"cet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0006valuest\u0000\u000fLjava/util/Set;xr\u00009com.sun.msv.dat"
+"atype.xsd.DataTypeWithValueConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com"
+".sun.msv.datatype.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacet"
+"FixedZ\u0000\u0012needValueCheckFlagL\u0000\bbaseTypet\u0000)Lcom/sun/msv/datatyp"
+"e/xsd/XSDatatypeImpl;L\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype"
+"/xsd/ConcreteType;L\u0000\tfacetNameq\u0000~\u0000)xq\u0000~\u0000(q\u0000~\u0000Lpq\u0000~\u00000\u0000\u0000q\u0000~\u0000+q"
+"\u0000~\u0000+t\u0000\u000benumerationsr\u0000\u0011java.util.HashSet\u00baD\u0085\u0095\u0096\u00b8\u00b74\u0003\u0000\u0000xpw\f\u0000\u0000\u0000\u0010?@"
+"\u0000\u0000\u0000\u0000\u0000\u0002t\u0000\nSwiss-Prott\u0000\u0006TrEMBLxq\u0000~\u00002sq\u0000~\u00003t\u0000\u000estring-derivedq\u0000~"
+"\u0000Lsq\u0000~\u0000Bt\u0000\u0007datasetq\u0000~\u0000\u00b3sq\u0000~\u00007ppq\u0000~\u0001\u0012sq\u0000~\u0000Bt\u0000\bmodifiedq\u0000~\u0000\u00b3sq"
+"\u0000~\u00007ppsq\u0000~\u0000!q\u0000~\u0000:psr\u0000 com.sun.msv.datatype.xsd.IntType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be"
+"\u0002\u0000\u0001L\u0000\nbaseFacetsq\u0000~\u0001 xq\u0000~\u0000&q\u0000~\u0000,t\u0000\u0003intq\u0000~\u0000@sr\u0000*com.sun.msv.d"
+"atatype.xsd.MaxInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.data"
+"type.xsd.RangeFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\nlimitValueq\u0000~\u0000\u00adxq\u0000~\u0001\u001eppq\u0000~\u0000"
+"@\u0000\u0001sr\u0000*com.sun.msv.datatype.xsd.MinInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xq\u0000~\u00016ppq\u0000~\u0000@\u0000\u0000sr\u0000!com.sun.msv.datatype.xsd.LongType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xq\u0000~\u00012q\u0000~\u0000,t\u0000\u0004longq\u0000~\u0000@sq\u0000~\u00015ppq\u0000~\u0000@\u0000\u0001sq\u0000~\u00018ppq\u0000~\u0000@\u0000\u0000sr\u0000$"
+"com.sun.msv.datatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00012q\u0000~\u0000,t\u0000"
+"\u0007integerq\u0000~\u0000@sr\u0000,com.sun.msv.datatype.xsd.FractionDigitsFace"
+"t\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.sun.msv.datatype.xsd.DataTypeWit"
+"hLexicalConstraintFacetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000xq\u0000~\u0001\u001fppq\u0000~\u0000@\u0001\u0000sr\u0000#com.sun"
+".msv.datatype.xsd.NumberType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000&q\u0000~\u0000,t\u0000\u0007decimal"
+"q\u0000~\u0000@q\u0000~\u0001Ft\u0000\u000efractionDigits\u0000\u0000\u0000\u0000q\u0000~\u0001@t\u0000\fminInclusivesr\u0000\u000ejava."
+"lang.Long;\u008b\u00e4\u0090\u00cc\u008f#\u00df\u0002\u0000\u0001J\u0000\u0005valuexr\u0000\u0010java.lang.Number\u0086\u00ac\u0095\u001d\u000b\u0094\u00e0\u008b\u0002\u0000\u0000x"
+"p\u0080\u0000\u0000\u0000\u0000\u0000\u0000\u0000q\u0000~\u0001@t\u0000\fmaxInclusivesq\u0000~\u0001J\u007f\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ffq\u0000~\u0001;q\u0000~\u0001Isr\u0000\u0011jav"
+"a.lang.Integer\u0012\u00e2\u00a0\u00a4\u00f7\u0081\u00878\u0002\u0000\u0001I\u0000\u0005valuexq\u0000~\u0001K\u0080\u0000\u0000\u0000q\u0000~\u0001;q\u0000~\u0001Msq\u0000~\u0001O\u007f"
+"\u00ff\u00ff\u00ffq\u0000~\u00002sq\u0000~\u00003q\u0000~\u00014q\u0000~\u0000,sq\u0000~\u0000Bt\u0000\u0007versionq\u0000~\u0000\u00b3sq\u0000~\u00005ppsq\u0000~\u00007q"
+"\u0000~\u0000:pq\u0000~\u0000;q\u0000~\u0000Dq\u0000~\u0000Hsq\u0000~\u0000Bt\u0000\u0005entryq\u0000~\u0000Lsr\u0000\"com.sun.msv.gramm"
+"ar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/gramm"
+"ar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.Expre"
+"ssionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006pa"
+"rentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000`\u0001pq\u0000~\u0001\u0001q\u0000~\u0001"
+"\rq\u0000~\u0001Uq\u0000~\u0000Oq\u0000~\u0000 q\u0000~\u0000\u0018q\u0000~\u0000\u0015q\u0000~\u0000\u00aaq\u0000~\u0000\u001aq\u0000~\u0000\u001bq\u0000~\u0000\rq\u0000~\u0000\u0012q\u0000~\u0000\fq\u0000~\u0000"
+"\u000bq\u0000~\u0000\u0017q\u0000~\u0000\tq\u0000~\u0000\u00a8q\u0000~\u0000\u0016q\u0000~\u0000\u0019q\u0000~\u0000\u00a7q\u0000~\u0000\u0014q\u0000~\u0000\u000eq\u0000~\u0000\u0010q\u0000~\u0000\u008cq\u0000~\u0000\u007fq\u0000~\u0000"
+"eq\u0000~\u0000\u00c1q\u0000~\u0000\u00dbq\u0000~\u0000\u00e9q\u0000~\u0000\u00f7q\u0000~\u0000\u00c4q\u0000~\u0000\u00b6q\u0000~\u0000\u009cq\u0000~\u0000\u008fq\u0000~\u0000\u0082q\u0000~\u0000tq\u0000~\u0000hq\u0000~\u0000"
+"Uq\u0000~\u0000\u00d0q\u0000~\u0000\u00deq\u0000~\u0000\u00ecq\u0000~\u0000\u00faq\u0000~\u0001\u0006q\u0000~\u0000\u00c6q\u0000~\u0000\u00b8q\u0000~\u0000\u009eq\u0000~\u0000\u0091q\u0000~\u0000\u0084q\u0000~\u0000vq\u0000~\u0000"
+"jq\u0000~\u0000Wq\u0000~\u0000\u00d2q\u0000~\u0000\u00e0q\u0000~\u0000\u00eeq\u0000~\u0000\u00fcq\u0000~\u0000\u000fq\u0000~\u0001\bq\u0000~\u0000Mq\u0000~\u0000\u001eq\u0000~\u0000\nq\u0000~\u0000\u00c7q\u0000~\u0000"
+"\u00b9q\u0000~\u0000\u009fq\u0000~\u0000\u0092q\u0000~\u0000\u0085q\u0000~\u0000wq\u0000~\u0000kq\u0000~\u0000Xq\u0000~\u0000\u00d3q\u0000~\u0000\u00e1q\u0000~\u0000\u00efq\u0000~\u0000\u00fdq\u0000~\u0001\tq\u0000~\u0000"
+"\u00c2q\u0000~\u0000\u009aq\u0000~\u0000\u008dq\u0000~\u0000\u0080q\u0000~\u0000fq\u0000~\u0000\u00dcq\u0000~\u0000\u00eaq\u0000~\u0000\u00f8q\u0000~\u0000\u0013q\u0000~\u0000\u00cbq\u0000~\u0000\u00bdq\u0000~\u0000\u00a3q\u0000~\u0000"
+"\u0096q\u0000~\u0000\u0088q\u0000~\u0000{q\u0000~\u0000oq\u0000~\u0000aq\u0000~\u0000Pq\u0000~\u00006q\u0000~\u0000\u00d7q\u0000~\u0000\u00e5q\u0000~\u0000\u00f3q\u0000~\u0000\u0011x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if ((null == obj)||(!(obj instanceof org.uniprot.uniprot.Entry))) {
            return false;
        }
        org.uniprot.uniprot.impl.EntryImpl target = ((org.uniprot.uniprot.impl.EntryImpl) obj);
        {
            java.util.List value = this.getKeyword();
            java.util.List targetValue = target.getKeyword();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            org.uniprot.uniprot.ProteinExistenceType value = this.getProteinExistence();
            org.uniprot.uniprot.ProteinExistenceType targetValue = target.getProteinExistence();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getGene();
            java.util.List targetValue = target.getGene();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.lang.String value = this.getDataset();
            java.lang.String targetValue = target.getDataset();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getComment();
            java.util.List targetValue = target.getComment();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getGeneLocation();
            java.util.List targetValue = target.getGeneLocation();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getFeature();
            java.util.List targetValue = target.getFeature();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getName();
            java.util.List targetValue = target.getName();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getOrganismHost();
            java.util.List targetValue = target.getOrganismHost();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.Calendar value = this.getCreated();
            java.util.Calendar targetValue = target.getCreated();
            if (!((value == targetValue)||((value!= null)&&(value.getTime().getTime() == targetValue.getTime().getTime())))) {
                return false;
            }
        }
        {
            java.util.Calendar value = this.getModified();
            java.util.Calendar targetValue = target.getModified();
            if (!((value == targetValue)||((value!= null)&&(value.getTime().getTime() == targetValue.getTime().getTime())))) {
                return false;
            }
        }
        {
            java.util.List value = this.getReference();
            java.util.List targetValue = target.getReference();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            org.uniprot.uniprot.SequenceType value = this.getSequence();
            org.uniprot.uniprot.SequenceType targetValue = target.getSequence();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getEvidence();
            java.util.List targetValue = target.getEvidence();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            int value = this.getVersion();
            int targetValue = target.getVersion();
            if (value!= targetValue) {
                return false;
            }
        }
        {
            java.util.List value = this.getAccession();
            java.util.List targetValue = target.getAccession();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getDbReference();
            java.util.List targetValue = target.getDbReference();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            org.uniprot.uniprot.OrganismType value = this.getOrganism();
            org.uniprot.uniprot.OrganismType targetValue = target.getOrganism();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            org.uniprot.uniprot.ProteinType value = this.getProtein();
            org.uniprot.uniprot.ProteinType targetValue = target.getProtein();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        {
            java.util.List value = this.getKeyword();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            org.uniprot.uniprot.ProteinExistenceType value = this.getProteinExistence();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getGene();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.lang.String value = this.getDataset();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getComment();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getGeneLocation();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getFeature();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getName();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getOrganismHost();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.Calendar value = this.getCreated();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.Calendar value = this.getModified();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getReference();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            org.uniprot.uniprot.SequenceType value = this.getSequence();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getEvidence();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            int value = this.getVersion();
            hash = ((31 *hash)+ value);
        }
        {
            java.util.List value = this.getAccession();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getDbReference();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            org.uniprot.uniprot.OrganismType value = this.getOrganism();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            org.uniprot.uniprot.ProteinType value = this.getProtein();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        return hash;
    }

    public class Unmarshaller
        extends org.uniprot.uniprot.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return org.uniprot.uniprot.impl.EntryImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        attIdx = context.getAttribute("", "created");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  0 :
                        if (("entry" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
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
                        attIdx = context.getAttribute("", "created");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  2 :
                        if (("entry" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
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
                        if (("created" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((org.uniprot.uniprot.impl.EntryTypeImpl)org.uniprot.uniprot.impl.EntryImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        break;
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
                        attIdx = context.getAttribute("", "created");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
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
                            attIdx = context.getAttribute("", "created");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
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
