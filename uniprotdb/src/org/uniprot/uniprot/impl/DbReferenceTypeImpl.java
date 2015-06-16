//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.16 at 12:18:25 AM PDT 
//


package org.uniprot.uniprot.impl;

public class DbReferenceTypeImpl implements org.uniprot.uniprot.DbReferenceType, com.sun.xml.bind.JAXBObject, org.uniprot.uniprot.impl.runtime.UnmarshallableObject, org.uniprot.uniprot.impl.runtime.XMLSerializable, org.uniprot.uniprot.impl.runtime.ValidatableObject
{

    protected org.uniprot.uniprot.MoleculeType _Molecule;
    protected java.lang.String _Type;
    protected com.sun.xml.bind.util.ListImpl _Evidence;
    protected java.lang.String _Id;
    protected com.sun.xml.bind.util.ListImpl _Property;
    public final static java.lang.Class version = (org.uniprot.uniprot.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;
    protected java.lang.Long _Hjid;
    protected java.lang.Long _Hjversion;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (org.uniprot.uniprot.DbReferenceType.class);
    }

    public org.uniprot.uniprot.MoleculeType getMolecule() {
        return _Molecule;
    }

    public void setMolecule(org.uniprot.uniprot.MoleculeType value) {
        _Molecule = value;
    }

    public java.lang.String getType() {
        return _Type;
    }

    public void setType(java.lang.String value) {
        _Type = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getEvidence() {
        if (_Evidence == null) {
            _Evidence = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Evidence;
    }

    public java.util.List getEvidence() {
        return _getEvidence();
    }

    public java.lang.String getId() {
        return _Id;
    }

    public void setId(java.lang.String value) {
        _Id = value;
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

    public org.uniprot.uniprot.impl.runtime.UnmarshallingEventHandler createUnmarshaller(org.uniprot.uniprot.impl.runtime.UnmarshallingContext context) {
        return new org.uniprot.uniprot.impl.DbReferenceTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = ((_Evidence == null)? 0 :_Evidence.size());
        int idx5 = 0;
        final int len5 = ((_Property == null)? 0 :_Property.size());
        if (_Molecule!= null) {
            context.startElement("http://uniprot.org/uniprot", "molecule");
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Molecule), "Molecule");
            context.endNamespaceDecls();
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Molecule), "Molecule");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Molecule), "Molecule");
            context.endElement();
        }
        while (idx5 != len5) {
            context.startElement("http://uniprot.org/uniprot", "property");
            int idx_2 = idx5;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Property.get(idx_2 ++)), "Property");
            context.endNamespaceDecls();
            int idx_3 = idx5;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Property.get(idx_3 ++)), "Property");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Property.get(idx5 ++)), "Property");
            context.endElement();
        }
    }

    public void serializeAttributes(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = ((_Evidence == null)? 0 :_Evidence.size());
        int idx5 = 0;
        final int len5 = ((_Property == null)? 0 :_Property.size());
        if (true) {
            context.startAttribute("", "evidence");
            while (idx3 != len3) {
                try {
                    context.text(javax.xml.bind.DatatypeConverter.printInt(((int)((java.lang.Integer) _Evidence.get(idx3 ++)).intValue())), "Evidence");
                } catch (java.lang.Exception e) {
                    org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
                }
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
        context.startAttribute("", "type");
        try {
            context.text(((java.lang.String) _Type), "Type");
        } catch (java.lang.Exception e) {
            org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        while (idx5 != len5) {
            idx5 += 1;
        }
    }

    public void serializeURIs(org.uniprot.uniprot.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = ((_Evidence == null)? 0 :_Evidence.size());
        int idx5 = 0;
        final int len5 = ((_Property == null)? 0 :_Property.size());
        if (true) {
            while (idx3 != len3) {
                try {
                    idx3 += 1;
                } catch (java.lang.Exception e) {
                    org.uniprot.uniprot.impl.runtime.Util.handlePrintConversionException(this, e, context);
                }
            }
        }
        while (idx5 != len5) {
            idx5 += 1;
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
+"grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000\'com.sun.msv.grammar."
+"trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/gr"
+"ammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003sr\u0000\u0011"
+"java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000bpp\u0000sq"
+"\u0000~\u0000\tppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom"
+".sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000\u0010ps"
+"r\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\t"
+"nameClassq\u0000~\u0000\fxq\u0000~\u0000\u0003q\u0000~\u0000\u0010psr\u00002com.sun.msv.grammar.Expression"
+"$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u000f\u0001q\u0000~\u0000\u001asr\u0000 com.sun"
+".msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar."
+"NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$Eps"
+"ilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u001bq\u0000~\u0000 sr\u0000#com.sun.msv.gra"
+"mmar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/Str"
+"ing;L\u0000\fnamespaceURIq\u0000~\u0000\"xq\u0000~\u0000\u001dt\u0000 org.uniprot.uniprot.Molecul"
+"eTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\tpps"
+"q\u0000~\u0000\u0017q\u0000~\u0000\u0010psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000"
+"\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom"
+"/sun/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xs"
+"d.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAt"
+"omicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000\"L\u0000\btypeNameq\u0000~\u0000\"L\u0000\nwhiteSpacet\u0000.Lco"
+"m/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w"
+"3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.Whi"
+"teSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype"
+".xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar"
+".Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun."
+"msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\"L\u0000\fnamespaceU"
+"RIq\u0000~\u0000\"xpq\u0000~\u00003q\u0000~\u00002sq\u0000~\u0000!t\u0000\u0004typet\u0000)http://www.w3.org/2001/XM"
+"LSchema-instanceq\u0000~\u0000 sq\u0000~\u0000!t\u0000\bmoleculet\u0000\u001ahttp://uniprot.org/"
+"uniprotq\u0000~\u0000 sq\u0000~\u0000\tppsq\u0000~\u0000\u0014q\u0000~\u0000\u0010psq\u0000~\u0000\u000bq\u0000~\u0000\u0010p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000bp"
+"p\u0000sq\u0000~\u0000\tppsq\u0000~\u0000\u0014q\u0000~\u0000\u0010psq\u0000~\u0000\u0017q\u0000~\u0000\u0010pq\u0000~\u0000\u001aq\u0000~\u0000\u001eq\u0000~\u0000 sq\u0000~\u0000!t\u0000 or"
+"g.uniprot.uniprot.PropertyTypeq\u0000~\u0000%sq\u0000~\u0000\tppsq\u0000~\u0000\u0017q\u0000~\u0000\u0010pq\u0000~\u0000+"
+"q\u0000~\u0000;q\u0000~\u0000 sq\u0000~\u0000!t\u0000\bpropertyq\u0000~\u0000@q\u0000~\u0000 sq\u0000~\u0000\tppsq\u0000~\u0000\u0017q\u0000~\u0000\u0010psq\u0000"
+"~\u0000(ppsr\u0000!com.sun.msv.datatype.xsd.ListType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bitem"
+"Typet\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\u0000~\u0000.q\u0000~\u0000@t"
+"\u0000\u000bintListTypeq\u0000~\u00006sr\u0000 com.sun.msv.datatype.xsd.IntType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be"
+"\u0002\u0000\u0001L\u0000\nbaseFacetsq\u0000~\u0000Sxq\u0000~\u0000-q\u0000~\u00002t\u0000\u0003intq\u0000~\u00006sr\u0000*com.sun.msv.d"
+"atatype.xsd.MaxInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.data"
+"type.xsd.RangeFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\nlimitValuet\u0000\u0012Ljava/lang/Obj"
+"ect;xr\u00009com.sun.msv.datatype.xsd.DataTypeWithValueConstraint"
+"Facet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.DataTypeWithFac"
+"et\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needValueCheckFlagL\u0000\bbaseType"
+"q\u0000~\u0000SL\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype/xsd/ConcreteTyp"
+"e;L\u0000\tfacetNameq\u0000~\u0000\"xq\u0000~\u0000/ppq\u0000~\u00006\u0000\u0001sr\u0000*com.sun.msv.datatype.x"
+"sd.MinInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000[ppq\u0000~\u00006\u0000\u0000sr\u0000!com.sun.ms"
+"v.datatype.xsd.LongType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Wq\u0000~\u00002t\u0000\u0004longq\u0000~\u00006sq\u0000"
+"~\u0000Zppq\u0000~\u00006\u0000\u0001sq\u0000~\u0000appq\u0000~\u00006\u0000\u0000sr\u0000$com.sun.msv.datatype.xsd.Inte"
+"gerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Wq\u0000~\u00002t\u0000\u0007integerq\u0000~\u00006sr\u0000,com.sun.msv."
+"datatype.xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.s"
+"un.msv.datatype.xsd.DataTypeWithLexicalConstraintFacetT\u0090\u001c>\u001az"
+"b\u00ea\u0002\u0000\u0000xq\u0000~\u0000^ppq\u0000~\u00006\u0001\u0000sr\u0000#com.sun.msv.datatype.xsd.NumberType\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000-q\u0000~\u00002t\u0000\u0007decimalq\u0000~\u00006q\u0000~\u0000ot\u0000\u000efractionDigits\u0000\u0000"
+"\u0000\u0000q\u0000~\u0000it\u0000\fminInclusivesr\u0000\u000ejava.lang.Long;\u008b\u00e4\u0090\u00cc\u008f#\u00df\u0002\u0000\u0001J\u0000\u0005valuex"
+"r\u0000\u0010java.lang.Number\u0086\u00ac\u0095\u001d\u000b\u0094\u00e0\u008b\u0002\u0000\u0000xp\u0080\u0000\u0000\u0000\u0000\u0000\u0000\u0000q\u0000~\u0000it\u0000\fmaxInclusive"
+"sq\u0000~\u0000s\u007f\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ffq\u0000~\u0000dq\u0000~\u0000rsr\u0000\u0011java.lang.Integer\u0012\u00e2\u00a0\u00a4\u00f7\u0081\u00878\u0002\u0000\u0001I\u0000\u0005v"
+"aluexq\u0000~\u0000t\u0080\u0000\u0000\u0000q\u0000~\u0000dq\u0000~\u0000vsq\u0000~\u0000x\u007f\u00ff\u00ff\u00ffq\u0000~\u00008psq\u0000~\u0000!t\u0000\bevidencet\u0000\u0000"
+"q\u0000~\u0000 sq\u0000~\u0000\u0017ppsq\u0000~\u0000(ppsr\u0000#com.sun.msv.datatype.xsd.StringType"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000-q\u0000~\u00002t\u0000\u0006stringsr\u00005com.sun.m"
+"sv.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000"
+"5\u0001q\u0000~\u00008sq\u0000~\u00009q\u0000~\u0000\u0082q\u0000~\u00002sq\u0000~\u0000!t\u0000\u0002idq\u0000~\u0000}sq\u0000~\u0000\u0017ppq\u0000~\u0000\u007fsq\u0000~\u0000!t\u0000"
+"\u0004typeq\u0000~\u0000}sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L"
+"\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;"
+"xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003"
+"\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/E"
+"xpressionPool;xp\u0000\u0000\u0000\u0010\u0001pq\u0000~\u0000\u0007q\u0000~\u0000\nq\u0000~\u0000\u0011q\u0000~\u0000Dq\u0000~\u0000\u0006q\u0000~\u0000Aq\u0000~\u0000Bq\u0000~"
+"\u0000\u0016q\u0000~\u0000Gq\u0000~\u0000\u0005q\u0000~\u0000Oq\u0000~\u0000\bq\u0000~\u0000\u0013q\u0000~\u0000Fq\u0000~\u0000&q\u0000~\u0000Kx"));
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
            org.uniprot.uniprot.MoleculeType value = this.getMolecule();
            org.uniprot.uniprot.MoleculeType targetValue = target.getMolecule();
            if (!((value == targetValue)||((value!= null)&&value.equals(targetValue)))) {
                return false;
            }
        }
        {
            java.lang.String value = this.getType();
            java.lang.String targetValue = target.getType();
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
            java.lang.String value = this.getId();
            java.lang.String targetValue = target.getId();
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
        return true;
    }

    public int hashCode() {
        int hash = 7;
        {
            org.uniprot.uniprot.MoleculeType value = this.getMolecule();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.lang.String value = this.getType();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getEvidence();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.lang.String value = this.getId();
            hash = ((31 *hash)+((null == value)? 0 :value.hashCode()));
        }
        {
            java.util.List value = this.getProperty();
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
            super(context, "-L-------------");
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
                    case  2 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 5;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  14 :
                        if (("property" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 12;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  5 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 8;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  8 :
                        if (("molecule" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 9;
                            return ;
                        }
                        state = 11;
                        continue outer;
                    case  9 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "evidence");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        state = 2;
                        continue outer;
                    case  12 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  11 :
                        if (("property" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 12;
                            return ;
                        }
                        state = 14;
                        continue outer;
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
                _Type = value;
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
                    case  2 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 5;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  14 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 8;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  8 :
                        state = 11;
                        continue outer;
                    case  9 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "evidence");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        state = 2;
                        continue outer;
                    case  12 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  13 :
                        if (("property" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.popAttributes();
                            state = 14;
                            return ;
                        }
                        break;
                    case  10 :
                        if (("molecule" == ___local)&&("http://uniprot.org/uniprot" == ___uri)) {
                            context.popAttributes();
                            state = 11;
                            return ;
                        }
                        break;
                    case  11 :
                        state = 14;
                        continue outer;
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
                    case  2 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            state = 3;
                            return ;
                        }
                        break;
                    case  14 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        if (("type" == ___local)&&("" == ___uri)) {
                            state = 6;
                            return ;
                        }
                        break;
                    case  8 :
                        state = 11;
                        continue outer;
                    case  9 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            _Molecule = ((org.uniprot.uniprot.impl.MoleculeTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.MoleculeTypeImpl.class), 10, ___uri, ___local, ___qname));
                            return ;
                        }
                        break;
                    case  0 :
                        if (("evidence" == ___local)&&("" == ___uri)) {
                            state = 1;
                            return ;
                        }
                        state = 2;
                        continue outer;
                    case  12 :
                        if (("type" == ___local)&&("" == ___uri)) {
                            _getProperty().add(((org.uniprot.uniprot.impl.PropertyTypeImpl) spawnChildFromEnterAttribute((org.uniprot.uniprot.impl.PropertyTypeImpl.class), 13, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  11 :
                        state = 14;
                        continue outer;
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
                        if (("evidence" == ___local)&&("" == ___uri)) {
                            state = 2;
                            return ;
                        }
                        break;
                    case  2 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 5;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  4 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            state = 5;
                            return ;
                        }
                        break;
                    case  14 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 8;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  8 :
                        state = 11;
                        continue outer;
                    case  9 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  7 :
                        if (("type" == ___local)&&("" == ___uri)) {
                            state = 8;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "evidence");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        state = 2;
                        continue outer;
                    case  12 :
                        attIdx = context.getAttribute("", "type");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  11 :
                        state = 14;
                        continue outer;
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
                            state = 1;
                            eatText3(value);
                            return ;
                        case  2 :
                            attIdx = context.getAttribute("", "id");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 5;
                                eatText1(v);
                                continue outer;
                            }
                            break;
                        case  14 :
                            revertToParentFromText(value);
                            return ;
                        case  5 :
                            attIdx = context.getAttribute("", "type");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 8;
                                eatText2(v);
                                continue outer;
                            }
                            break;
                        case  8 :
                            state = 11;
                            continue outer;
                        case  9 :
                            attIdx = context.getAttribute("", "id");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            _Molecule = ((org.uniprot.uniprot.impl.MoleculeTypeImpl) spawnChildFromText((org.uniprot.uniprot.impl.MoleculeTypeImpl.class), 10, value));
                            return ;
                        case  0 :
                            attIdx = context.getAttribute("", "evidence");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            state = 2;
                            continue outer;
                        case  12 :
                            attIdx = context.getAttribute("", "type");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  3 :
                            state = 4;
                            eatText1(value);
                            return ;
                        case  11 :
                            state = 14;
                            continue outer;
                        case  6 :
                            state = 7;
                            eatText2(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText3(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _getEvidence().add(new java.lang.Integer(javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value))));
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
