//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.03.05 at 01:26:07 PM PST 
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
+"\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsr\u0000 com.s"
+"un.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.gramma"
+"r.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsr\u0000"
+"\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/"
+"datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/"
+"StringPair;xq\u0000~\u0000\u0004ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.Builti"
+"nAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteT"
+"ype\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\'"
+"L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcesso"
+"r;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u00005com.sun."
+"msv.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,"
+"com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr"
+"\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalN"
+"ameq\u0000~\u0000\'L\u0000\fnamespaceURIq\u0000~\u0000\'xpq\u0000~\u0000+q\u0000~\u0000*sr\u0000\u001dcom.sun.msv.gram"
+"mar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsr\u0000 com.sun.msv.grammar.Attr"
+"ibuteExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004sr\u0000\u0011jav"
+"a.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u001fppsr\u0000\"com.sun.msv"
+".datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000$q\u0000~\u0000*t\u0000\u0005QNamesr\u00005com"
+".sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xq\u0000~\u0000-q\u0000~\u00000sq\u0000~\u00001q\u0000~\u0000<q\u0000~\u0000*sr\u0000#com.sun.msv.grammar.SimpleNa"
+"meClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\'L\u0000\fnamespaceURIq\u0000~\u0000\'xr\u0000\u001dc"
+"om.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://ww"
+"w.w3.org/2001/XMLSchema-instancesr\u00000com.sun.msv.grammar.Expr"
+"ession$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u00007\u0001q\u0000~\u0000Fsq\u0000~\u0000@t"
+"\u0000\taccessiont\u0000\u001ahttp://uniprot.org/uniprotsq\u0000~\u0000\u001appsq\u0000~\u0000\u0000pp\u0000sq\u0000"
+"~\u0000\u0007ppq\u0000~\u0000\"sq\u0000~\u00003ppsq\u0000~\u00005q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u0004nameq\u0000"
+"~\u0000Jsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q\u0000~\u00008"
+"psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000Gq\u0000~\u0000Ysr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Aq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u001forg.uniprot.uniprot.ProteinType"
+"t\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u00003ppsq\u0000~\u00005"
+"q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u0007proteinq\u0000~\u0000Jsq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u0000"
+"8psq\u0000~\u0000\u0000q\u0000~\u00008p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q\u0000"
+"~\u00008pq\u0000~\u0000Yq\u0000~\u0000[q\u0000~\u0000Fsq\u0000~\u0000@t\u0000&org.uniprot.uniprot.EntryType.Ge"
+"neTypeq\u0000~\u0000^sq\u0000~\u00003ppsq\u0000~\u00005q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u0004geneq"
+"\u0000~\u0000Jq\u0000~\u0000Fsq\u0000~\u0000\u001appsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~"
+"\u00008psq\u0000~\u00005q\u0000~\u00008pq\u0000~\u0000Yq\u0000~\u0000[q\u0000~\u0000Fsq\u0000~\u0000@t\u0000 org.uniprot.uniprot.O"
+"rganismTypeq\u0000~\u0000^sq\u0000~\u00003ppsq\u0000~\u00005q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\b"
+"organismq\u0000~\u0000Jsq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u0000\u0000q\u0000~\u00008p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000"
+"pp\u0000sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q\u0000~\u00008pq\u0000~\u0000Yq\u0000~\u0000[q\u0000~\u0000Fsq\u0000~\u0000@t\u0000$o"
+"rg.uniprot.uniprot.GeneLocationTypeq\u0000~\u0000^sq\u0000~\u00003ppsq\u0000~\u00005q\u0000~\u00008p"
+"q\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\fgeneLocationq\u0000~\u0000Jq\u0000~\u0000Fsq\u0000~\u0000\u001appsq\u0000~\u0000\u0000"
+"pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q\u0000~\u00008pq\u0000~\u0000Yq\u0000~"
+"\u0000[q\u0000~\u0000Fsq\u0000~\u0000@t\u0000!org.uniprot.uniprot.ReferenceTypeq\u0000~\u0000^sq\u0000~\u00003"
+"ppsq\u0000~\u00005q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\treferenceq\u0000~\u0000Jsq\u0000~\u00003pp"
+"sq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u0000\u0000q\u0000~\u00008p\u0000sq\u0000~\u00003ppsq\u0000~\u00005ppsr\u0000\u001ccom.sun.msv.gra"
+"mmar.ValueExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtq\u0000~\u0000 L\u0000\u0004nameq\u0000~\u0000!L\u0000\u0005valuet\u0000\u0012Lja"
+"va/lang/Object;xq\u0000~\u0000\u0004ppsr\u0000$com.sun.msv.datatype.xsd.BooleanT"
+"ype\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000$q\u0000~\u0000*t\u0000\u0007booleanq\u0000~\u0000>sq\u0000~\u00001q\u0000~\u0000\u00a3t\u0000\u0000q\u0000~\u0000Gs"
+"q\u0000~\u0000@t\u0000\u0003nilq\u0000~\u0000Dsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q"
+"\u0000~\u00008pq\u0000~\u0000Yq\u0000~\u0000[q\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u001forg.uniprot.uniprot.CommentType"
+"q\u0000~\u0000^sq\u0000~\u00003ppsq\u0000~\u00005q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u0007commentq\u0000~\u0000"
+"Jq\u0000~\u0000Fsq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u0000\u0000q\u0000~\u00008p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~"
+"\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q\u0000~\u00008pq\u0000~\u0000Yq\u0000~\u0000[q\u0000~\u0000Fsq\u0000~\u0000@t\u0000#org.unip"
+"rot.uniprot.DbReferenceTypeq\u0000~\u0000^sq\u0000~\u00003ppsq\u0000~\u00005q\u0000~\u00008pq\u0000~\u00009q\u0000~"
+"\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u000bdbReferenceq\u0000~\u0000Jq\u0000~\u0000Fsq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000"
+"~\u0000\u0000q\u0000~\u00008p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q\u0000~\u00008pq"
+"\u0000~\u0000Yq\u0000~\u0000[q\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u001forg.uniprot.uniprot.KeywordTypeq\u0000~\u0000^s"
+"q\u0000~\u00003ppsq\u0000~\u00005q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u0007keywordq\u0000~\u0000Jq\u0000~\u0000F"
+"sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u0000\u0000q\u0000~\u00008p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00003ppsq"
+"\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q\u0000~\u00008pq\u0000~\u0000Yq\u0000~\u0000[q\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u001forg.uniprot.un"
+"iprot.FeatureTypeq\u0000~\u0000^sq\u0000~\u00003ppsq\u0000~\u00005q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000"
+"~\u0000@t\u0000\u0007featureq\u0000~\u0000Jq\u0000~\u0000Fsq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u0000\u0000q\u0000~\u00008p\u0000sq\u0000~"
+"\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q\u0000~\u00008pq\u0000~\u0000Yq\u0000~\u0000[q\u0000~\u0000F"
+"sq\u0000~\u0000@t\u0000 org.uniprot.uniprot.EvidenceTypeq\u0000~\u0000^sq\u0000~\u00003ppsq\u0000~\u00005"
+"q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\bevidenceq\u0000~\u0000Jq\u0000~\u0000Fsq\u0000~\u0000\u0000pp\u0000sq\u0000"
+"~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u00003ppsq\u0000~\u0000\u001aq\u0000~\u00008psq\u0000~\u00005q\u0000~\u00008pq\u0000~\u0000Yq\u0000~\u0000[q\u0000~\u0000"
+"Fsq\u0000~\u0000@t\u0000 org.uniprot.uniprot.SequenceTypeq\u0000~\u0000^sq\u0000~\u00003ppsq\u0000~\u0000"
+"5q\u0000~\u00008pq\u0000~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\bsequenceq\u0000~\u0000Jsq\u0000~\u00005ppsq\u0000~\u0000\u001fq\u0000"
+"~\u00008psr\u0000!com.sun.msv.datatype.xsd.DateType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000)com."
+"sun.msv.datatype.xsd.DateTimeBaseType\u0014W\u001a@3\u00a5\u00b4\u00e5\u0002\u0000\u0000xq\u0000~\u0000$q\u0000~\u0000*t"
+"\u0000\u0004dateq\u0000~\u0000>q\u0000~\u00000sq\u0000~\u00001q\u0000~\u0000\u00fcq\u0000~\u0000*sq\u0000~\u0000@t\u0000\u0007createdq\u0000~\u0000\u00a5sq\u0000~\u00005p"
+"psq\u0000~\u0000\u001fppsr\u0000)com.sun.msv.datatype.xsd.EnumerationFacet\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0001L\u0000\u0006valuest\u0000\u000fLjava/util/Set;xr\u00009com.sun.msv.datatype.xsd"
+".DataTypeWithValueConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv."
+"datatype.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012n"
+"eedValueCheckFlagL\u0000\bbaseTypet\u0000)Lcom/sun/msv/datatype/xsd/XSD"
+"atatypeImpl;L\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype/xsd/Conc"
+"reteType;L\u0000\tfacetNameq\u0000~\u0000\'xq\u0000~\u0000&q\u0000~\u0000Jpq\u0000~\u0000.\u0000\u0000q\u0000~\u0000)q\u0000~\u0000)t\u0000\u000ben"
+"umerationsr\u0000\u0011java.util.HashSet\u00baD\u0085\u0095\u0096\u00b8\u00b74\u0003\u0000\u0000xpw\f\u0000\u0000\u0000\u0010?@\u0000\u0000\u0000\u0000\u0000\u0002t\u0000\n"
+"Swiss-Prott\u0000\u0006TrEMBLxq\u0000~\u00000sq\u0000~\u00001t\u0000\u000estring-derivedq\u0000~\u0000Jsq\u0000~\u0000@t"
+"\u0000\u0007datasetq\u0000~\u0000\u00a5sq\u0000~\u00005ppq\u0000~\u0000\u00f8sq\u0000~\u0000@t\u0000\bmodifiedq\u0000~\u0000\u00a5sq\u0000~\u00005ppsq\u0000"
+"~\u0000\u001fppsr\u0000$com.sun.msv.datatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+"
+"com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0001L\u0000\nbas"
+"eFacetsq\u0000~\u0001\u0006xq\u0000~\u0000$q\u0000~\u0000*t\u0000\u0007integerq\u0000~\u0000>sr\u0000,com.sun.msv.dataty"
+"pe.xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.sun.msv"
+".datatype.xsd.DataTypeWithLexicalConstraintFacetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000x"
+"q\u0000~\u0001\u0005ppq\u0000~\u0000>\u0001\u0000sr\u0000#com.sun.msv.datatype.xsd.NumberType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000$q\u0000~\u0000*t\u0000\u0007decimalq\u0000~\u0000>q\u0000~\u0001\u001ft\u0000\u000efractionDigits\u0000\u0000\u0000\u0000q\u0000~\u0000"
+"0sq\u0000~\u00001q\u0000~\u0001\u001aq\u0000~\u0000*sq\u0000~\u0000@t\u0000\u0007versionq\u0000~\u0000\u00a5sq\u0000~\u00003ppsq\u0000~\u00005q\u0000~\u00008pq\u0000"
+"~\u00009q\u0000~\u0000Bq\u0000~\u0000Fsq\u0000~\u0000@t\u0000\u0005entryq\u0000~\u0000Jsr\u0000\"com.sun.msv.grammar.Expr"
+"essionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expr"
+"essionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPo"
+"ol$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$"
+"Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000U\u0001pq\u0000~\u0000\u0012q\u0000~\u0000\u00b8q\u0000~\u0000\u00aaq"
+"\u0000~\u0000\u0090q\u0000~\u0000\u0083q\u0000~\u0000uq\u0000~\u0000hq\u0000~\u0000Uq\u0000~\u0000\u00c6q\u0000~\u0000\u00d4q\u0000~\u0000\u00e2q\u0000~\u0000\u00eeq\u0000~\u0000\nq\u0000~\u0000\u0014q\u0000~\u0000\u000fq"
+"\u0000~\u0000\u0017q\u0000~\u0000\u0019q\u0000~\u0000fq\u0000~\u0000Sq\u0000~\u0000\u00c4q\u0000~\u0000\u00b6q\u0000~\u0000\u00a8q\u0000~\u0000\u008eq\u0000~\u0000\u0081q\u0000~\u0000sq\u0000~\u0000\u00d2q\u0000~\u0000\u00e0q"
+"\u0000~\u0000\u00ecq\u0000~\u0000\u0011q\u0000~\u0000\u009cq\u0000~\u0000\fq\u0000~\u0000\u00cbq\u0000~\u0000\u00bdq\u0000~\u0000\u00afq\u0000~\u0000\u0095q\u0000~\u0000\u0088q\u0000~\u0000zq\u0000~\u0000mq\u0000~\u0000_q"
+"\u0000~\u0000Nq\u0000~\u00004q\u0000~\u0000\u00d9q\u0000~\u0000\u00e7q\u0000~\u0000\u00f3q\u0000~\u0001%q\u0000~\u0000\rq\u0000~\u0000\u0018q\u0000~\u0000\u0099q\u0000~\u0000\u0015q\u0000~\u0000\u0010q\u0000~\u0000\u00b4q"
+"\u0000~\u0000\u008cq\u0000~\u0000\u007fq\u0000~\u0000qq\u0000~\u0000dq\u0000~\u0000\u00c2q\u0000~\u0000\u00d0q\u0000~\u0000\u00deq\u0000~\u0000\tq\u0000~\u0000\u009aq\u0000~\u0000\u00c7q\u0000~\u0000\u00b9q\u0000~\u0000\u00abq"
+"\u0000~\u0000\u0091q\u0000~\u0000\u0084q\u0000~\u0000vq\u0000~\u0000iq\u0000~\u0000Vq\u0000~\u0000\u00d5q\u0000~\u0000\u00e3q\u0000~\u0000\u00efq\u0000~\u0000Mq\u0000~\u0000\u001eq\u0000~\u0000\u0016q\u0000~\u0000\u00b3q"
+"\u0000~\u0000~q\u0000~\u0000cq\u0000~\u0000\u00c1q\u0000~\u0000\u00cfq\u0000~\u0000\u00ddq\u0000~\u0000\u000eq\u0000~\u0000\u000bq\u0000~\u0000\u0013q\u0000~\u0000Kq\u0000~\u0000\u001cx"));
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
            java.util.Calendar value = this.getModified();
            java.util.Calendar targetValue = target.getModified();
            if (!((value == targetValue)||((value!= null)&&(value.getTime().getTime() == targetValue.getTime().getTime())))) {
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
            java.util.List value = this.getFeature();
            java.util.List targetValue = target.getFeature();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getKeyword();
            java.util.List targetValue = target.getKeyword();
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
            java.util.List value = this.getAccession();
            java.util.List targetValue = target.getAccession();
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
            java.math.BigInteger value = this.getVersion();
            java.math.BigInteger targetValue = target.getVersion();
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
            java.util.List value = this.getEvidence();
            java.util.List targetValue = target.getEvidence();
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
            java.util.Calendar value = this.getCreated();
            java.util.Calendar targetValue = target.getCreated();
            if (!((value == targetValue)||((value!= null)&&(value.getTime().getTime() == targetValue.getTime().getTime())))) {
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
            org.uniprot.uniprot.ProteinType value = this.getProtein();
            org.uniprot.uniprot.ProteinType targetValue = target.getProtein();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.util.List value = this.getOrganism();
            java.util.List targetValue = target.getOrganism();
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
            java.util.List value = this.getComment();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getFeature();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getKeyword();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getDbReference();
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
            java.util.List value = this.getAccession();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getGene();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.math.BigInteger value = this.getVersion();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.lang.String value = this.getDataset();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getEvidence();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getGeneLocation();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.Calendar value = this.getCreated();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getName();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            org.uniprot.uniprot.ProteinType value = this.getProtein();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getOrganism();
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
