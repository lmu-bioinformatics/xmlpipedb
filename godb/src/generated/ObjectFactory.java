//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-09/29/2005 11:56 AM(valikov)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.26 at 12:22:45 AM PDT 
//


package generated;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
public class ObjectFactory
    extends generated.impl.runtime.DefaultJAXBContextImpl
{

    private static java.util.HashMap defaultImplementations = new java.util.HashMap(87, 0.75F);
    private static java.util.HashMap rootTagMap = new java.util.HashMap();
    public final static generated.impl.runtime.GrammarInfo grammarInfo = new generated.impl.runtime.GrammarInfoImpl(rootTagMap, defaultImplementations, (generated.ObjectFactory.class));
    public final static java.lang.Class version = (generated.impl.JAXBVersion.class);

    static {
        defaultImplementations.put((generated.IsSymmetric.class), "generated.impl.IsSymmetricImpl");
        defaultImplementations.put((generated.IsReflexive.class), "generated.impl.IsReflexiveImpl");
        defaultImplementations.put((generated.Defstr.class), "generated.impl.DefstrImpl");
        defaultImplementations.put((generated.Local.class), "generated.impl.LocalImpl");
        defaultImplementations.put((generated.Header.class), "generated.impl.HeaderImpl");
        defaultImplementations.put((generated.AutoGeneratedBy.class), "generated.impl.AutoGeneratedByImpl");
        defaultImplementations.put((generated.UnionOf.class), "generated.impl.UnionOfImpl");
        defaultImplementations.put((generated.Def.class), "generated.impl.DefImpl");
        defaultImplementations.put((generated.Date.class), "generated.impl.DateImpl");
        defaultImplementations.put((generated.DataVersion.class), "generated.impl.DataVersionImpl");
        defaultImplementations.put((generated.Synonym.class), "generated.impl.SynonymImpl");
        defaultImplementations.put((generated.SourceType.class), "generated.impl.SourceTypeImpl");
        defaultImplementations.put((generated.IsRoot.class), "generated.impl.IsRootImpl");
        defaultImplementations.put((generated.Acc.class), "generated.impl.AccImpl");
        defaultImplementations.put((generated.SynonymCategory.class), "generated.impl.SynonymCategoryImpl");
        defaultImplementations.put((generated.Remark.class), "generated.impl.RemarkImpl");
        defaultImplementations.put((generated.To.class), "generated.impl.ToImpl");
        defaultImplementations.put((generated.Typedef.class), "generated.impl.TypedefImpl");
        defaultImplementations.put((generated.Subset.class), "generated.impl.SubsetImpl");
        defaultImplementations.put((generated.IntersectionOf.class), "generated.impl.IntersectionOfImpl");
        defaultImplementations.put((generated.Type.class), "generated.impl.TypeImpl");
        defaultImplementations.put((generated.Idspace.class), "generated.impl.IdspaceImpl");
        defaultImplementations.put((generated.Source.class), "generated.impl.SourceImpl");
        defaultImplementations.put((generated.SourceMd5 .class), "generated.impl.SourceMd5Impl");
        defaultImplementations.put((generated.SourceId.class), "generated.impl.SourceIdImpl");
        defaultImplementations.put((generated.IsTransitive.class), "generated.impl.IsTransitiveImpl");
        defaultImplementations.put((generated.Comment.class), "generated.impl.CommentImpl");
        defaultImplementations.put((generated.Consider.class), "generated.impl.ConsiderImpl");
        defaultImplementations.put((generated.DefaultNamespace.class), "generated.impl.DefaultNamespaceImpl");
        defaultImplementations.put((generated.Name.class), "generated.impl.NameImpl");
        defaultImplementations.put((generated.AltId.class), "generated.impl.AltIdImpl");
        defaultImplementations.put((generated.Id.class), "generated.impl.IdImpl");
        defaultImplementations.put((generated.Obo.class), "generated.impl.OboImpl");
        defaultImplementations.put((generated.CreationDate.class), "generated.impl.CreationDateImpl");
        defaultImplementations.put((generated.SourceFullpath.class), "generated.impl.SourceFullpathImpl");
        defaultImplementations.put((generated.LexicalCategory.class), "generated.impl.LexicalCategoryImpl");
        defaultImplementations.put((generated.IsObsolete.class), "generated.impl.IsObsoleteImpl");
        defaultImplementations.put((generated.XrefAnalog.class), "generated.impl.XrefAnalogImpl");
        defaultImplementations.put((generated.SourceParsetime.class), "generated.impl.SourceParsetimeImpl");
        defaultImplementations.put((generated.Global.class), "generated.impl.GlobalImpl");
        defaultImplementations.put((generated.SynonymText.class), "generated.impl.SynonymTextImpl");
        defaultImplementations.put((generated.Range.class), "generated.impl.RangeImpl");
        defaultImplementations.put((generated.ReplacedBy.class), "generated.impl.ReplacedByImpl");
        defaultImplementations.put((generated.IsAnonymous.class), "generated.impl.IsAnonymousImpl");
        defaultImplementations.put((generated.FormatVersion.class), "generated.impl.FormatVersionImpl");
        defaultImplementations.put((generated.Synonymtypedef.class), "generated.impl.SynonymtypedefImpl");
        defaultImplementations.put((generated.Dbxref.class), "generated.impl.DbxrefImpl");
        defaultImplementations.put((generated.DisjointFrom.class), "generated.impl.DisjointFromImpl");
        defaultImplementations.put((generated.IsA.class), "generated.impl.IsAImpl");
        defaultImplementations.put((generated.InverseOf.class), "generated.impl.InverseOfImpl");
        defaultImplementations.put((generated.TransitiveOver.class), "generated.impl.TransitiveOverImpl");
        defaultImplementations.put((generated.Domain.class), "generated.impl.DomainImpl");
        defaultImplementations.put((generated.Dbname.class), "generated.impl.DbnameImpl");
        defaultImplementations.put((generated.IsAntiSymmetric.class), "generated.impl.IsAntiSymmetricImpl");
        defaultImplementations.put((generated.SavedBy.class), "generated.impl.SavedByImpl");
        defaultImplementations.put((generated.Subsetdef.class), "generated.impl.SubsetdefImpl");
        defaultImplementations.put((generated.Term.class), "generated.impl.TermImpl");
        defaultImplementations.put((generated.XrefUnknown.class), "generated.impl.XrefUnknownImpl");
        defaultImplementations.put((generated.Relationship.class), "generated.impl.RelationshipImpl");
        defaultImplementations.put((generated.CreatedBy.class), "generated.impl.CreatedByImpl");
        defaultImplementations.put((generated.Namespace.class), "generated.impl.NamespaceImpl");
        defaultImplementations.put((generated.SourceMtime.class), "generated.impl.SourceMtimeImpl");
        defaultImplementations.put((generated.SourcePath.class), "generated.impl.SourcePathImpl");
        defaultImplementations.put((generated.Scope.class), "generated.impl.ScopeImpl");
        rootTagMap.put(new javax.xml.namespace.QName("", "to"), (generated.To.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "is_root"), (generated.IsRoot.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "dbxref"), (generated.Dbxref.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "inverse_of"), (generated.InverseOf.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "source_mtime"), (generated.SourceMtime.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "defstr"), (generated.Defstr.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "remark"), (generated.Remark.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "synonym"), (generated.Synonym.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "source_type"), (generated.SourceType.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "type"), (generated.Type.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "namespace"), (generated.Namespace.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "source_md5"), (generated.SourceMd5 .class));
        rootTagMap.put(new javax.xml.namespace.QName("", "transitive_over"), (generated.TransitiveOver.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "data-version"), (generated.DataVersion.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "is_transitive"), (generated.IsTransitive.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "is_anonymous"), (generated.IsAnonymous.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "is_anti_symmetric"), (generated.IsAntiSymmetric.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "created_by"), (generated.CreatedBy.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "synonymtypedef"), (generated.Synonymtypedef.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "format-version"), (generated.FormatVersion.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "synonym_text"), (generated.SynonymText.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "source_id"), (generated.SourceId.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "global"), (generated.Global.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "is_symmetric"), (generated.IsSymmetric.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "is_a"), (generated.IsA.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "auto-generated-by"), (generated.AutoGeneratedBy.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "saved-by"), (generated.SavedBy.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "lexical_category"), (generated.LexicalCategory.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "typedef"), (generated.Typedef.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "alt_id"), (generated.AltId.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "header"), (generated.Header.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "source_fullpath"), (generated.SourceFullpath.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "replaced_by"), (generated.ReplacedBy.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "comment"), (generated.Comment.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "idspace"), (generated.Idspace.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "source_path"), (generated.SourcePath.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "union_of"), (generated.UnionOf.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "scope"), (generated.Scope.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "range"), (generated.Range.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "xref_unknown"), (generated.XrefUnknown.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "date"), (generated.Date.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "id"), (generated.Id.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "subset"), (generated.Subset.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "creation_date"), (generated.CreationDate.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "synonym_category"), (generated.SynonymCategory.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "def"), (generated.Def.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "consider"), (generated.Consider.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "name"), (generated.Name.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "domain"), (generated.Domain.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "disjoint_from"), (generated.DisjointFrom.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "source_parsetime"), (generated.SourceParsetime.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "is_reflexive"), (generated.IsReflexive.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "intersection_of"), (generated.IntersectionOf.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "subsetdef"), (generated.Subsetdef.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "is_obsolete"), (generated.IsObsolete.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "acc"), (generated.Acc.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "relationship"), (generated.Relationship.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "obo"), (generated.Obo.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "xref_analog"), (generated.XrefAnalog.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "default-namespace"), (generated.DefaultNamespace.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "source"), (generated.Source.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "term"), (generated.Term.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "dbname"), (generated.Dbname.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "local"), (generated.Local.class));
    }

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
        super(grammarInfo);
    }

    /**
     * Create an instance of the specified Java content interface.
     * 
     * @param javaContentInterface
     *     the Class object of the javacontent interface to instantiate
     * @return
     *     a new instance
     * @throws JAXBException
     *     if an error occurs
     */
    public java.lang.Object newInstance(java.lang.Class javaContentInterface)
        throws javax.xml.bind.JAXBException
    {
        return super.newInstance(javaContentInterface);
    }

    /**
     * Get the specified property. This method can only be
     * used to get provider specific properties.
     * Attempting to get an undefined property will result
     * in a PropertyException being thrown.
     * 
     * @param name
     *     the name of the property to retrieve
     * @return
     *     the value of the requested property
     * @throws PropertyException
     *     when there is an error retrieving the given property or value
     */
    public java.lang.Object getProperty(java.lang.String name)
        throws javax.xml.bind.PropertyException
    {
        return super.getProperty(name);
    }

    /**
     * Set the specified property. This method can only be
     * used to set provider specific properties.
     * Attempting to set an undefined property will result
     * in a PropertyException being thrown.
     * 
     * @param name
     *     the name of the property to retrieve
     * @param value
     *     the value of the property to be set
     * @throws PropertyException
     *     when there is an error processing the given property or value
     */
    public void setProperty(java.lang.String name, java.lang.Object value)
        throws javax.xml.bind.PropertyException
    {
        super.setProperty(name, value);
    }

    /**
     * Create an instance of IsSymmetric
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.IsSymmetric createIsSymmetric()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IsSymmetricImpl();
    }

    /**
     * Create an instance of IsReflexive
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.IsReflexive createIsReflexive()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IsReflexiveImpl();
    }

    /**
     * Create an instance of Defstr
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Defstr createDefstr()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.DefstrImpl();
    }

    /**
     * Create an instance of Local
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Local createLocal()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.LocalImpl();
    }

    /**
     * Create an instance of Header
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Header createHeader()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.HeaderImpl();
    }

    /**
     * Create an instance of AutoGeneratedBy
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.AutoGeneratedBy createAutoGeneratedBy()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.AutoGeneratedByImpl();
    }

    /**
     * Create an instance of UnionOf
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.UnionOf createUnionOf()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.UnionOfImpl();
    }

    /**
     * Create an instance of Def
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Def createDef()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.DefImpl();
    }

    /**
     * Create an instance of Date
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Date createDate()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.DateImpl();
    }

    /**
     * Create an instance of DataVersion
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.DataVersion createDataVersion()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.DataVersionImpl();
    }

    /**
     * Create an instance of Synonym
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Synonym createSynonym()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SynonymImpl();
    }

    /**
     * Create an instance of SourceType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SourceType createSourceType()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SourceTypeImpl();
    }

    /**
     * Create an instance of IsRoot
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.IsRoot createIsRoot()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IsRootImpl();
    }

    /**
     * Create an instance of Acc
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Acc createAcc()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.AccImpl();
    }

    /**
     * Create an instance of SynonymCategory
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SynonymCategory createSynonymCategory()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SynonymCategoryImpl();
    }

    /**
     * Create an instance of Remark
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Remark createRemark()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.RemarkImpl();
    }

    /**
     * Create an instance of To
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.To createTo()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.ToImpl();
    }

    /**
     * Create an instance of Typedef
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Typedef createTypedef()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.TypedefImpl();
    }

    /**
     * Create an instance of Subset
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Subset createSubset()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SubsetImpl();
    }

    /**
     * Create an instance of IntersectionOf
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.IntersectionOf createIntersectionOf()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IntersectionOfImpl();
    }

    /**
     * Create an instance of Type
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Type createType()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.TypeImpl();
    }

    /**
     * Create an instance of Idspace
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Idspace createIdspace()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IdspaceImpl();
    }

    /**
     * Create an instance of Source
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Source createSource()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SourceImpl();
    }

    /**
     * Create an instance of SourceMd5
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SourceMd5 createSourceMd5()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SourceMd5Impl();
    }

    /**
     * Create an instance of SourceId
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SourceId createSourceId()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SourceIdImpl();
    }

    /**
     * Create an instance of IsTransitive
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.IsTransitive createIsTransitive()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IsTransitiveImpl();
    }

    /**
     * Create an instance of Comment
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Comment createComment()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.CommentImpl();
    }

    /**
     * Create an instance of Consider
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Consider createConsider()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.ConsiderImpl();
    }

    /**
     * Create an instance of DefaultNamespace
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.DefaultNamespace createDefaultNamespace()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.DefaultNamespaceImpl();
    }

    /**
     * Create an instance of Name
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Name createName()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.NameImpl();
    }

    /**
     * Create an instance of AltId
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.AltId createAltId()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.AltIdImpl();
    }

    /**
     * Create an instance of Id
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Id createId()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IdImpl();
    }

    /**
     * Create an instance of Obo
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Obo createObo()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.OboImpl();
    }

    /**
     * Create an instance of CreationDate
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.CreationDate createCreationDate()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.CreationDateImpl();
    }

    /**
     * Create an instance of SourceFullpath
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SourceFullpath createSourceFullpath()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SourceFullpathImpl();
    }

    /**
     * Create an instance of LexicalCategory
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.LexicalCategory createLexicalCategory()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.LexicalCategoryImpl();
    }

    /**
     * Create an instance of IsObsolete
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.IsObsolete createIsObsolete()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IsObsoleteImpl();
    }

    /**
     * Create an instance of XrefAnalog
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.XrefAnalog createXrefAnalog()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.XrefAnalogImpl();
    }

    /**
     * Create an instance of SourceParsetime
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SourceParsetime createSourceParsetime()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SourceParsetimeImpl();
    }

    /**
     * Create an instance of Global
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Global createGlobal()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.GlobalImpl();
    }

    /**
     * Create an instance of SynonymText
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SynonymText createSynonymText()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SynonymTextImpl();
    }

    /**
     * Create an instance of Range
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Range createRange()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.RangeImpl();
    }

    /**
     * Create an instance of ReplacedBy
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.ReplacedBy createReplacedBy()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.ReplacedByImpl();
    }

    /**
     * Create an instance of IsAnonymous
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.IsAnonymous createIsAnonymous()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IsAnonymousImpl();
    }

    /**
     * Create an instance of FormatVersion
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.FormatVersion createFormatVersion()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.FormatVersionImpl();
    }

    /**
     * Create an instance of Synonymtypedef
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Synonymtypedef createSynonymtypedef()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SynonymtypedefImpl();
    }

    /**
     * Create an instance of Dbxref
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Dbxref createDbxref()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.DbxrefImpl();
    }

    /**
     * Create an instance of DisjointFrom
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.DisjointFrom createDisjointFrom()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.DisjointFromImpl();
    }

    /**
     * Create an instance of IsA
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.IsA createIsA()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IsAImpl();
    }

    /**
     * Create an instance of InverseOf
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.InverseOf createInverseOf()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.InverseOfImpl();
    }

    /**
     * Create an instance of TransitiveOver
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.TransitiveOver createTransitiveOver()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.TransitiveOverImpl();
    }

    /**
     * Create an instance of Domain
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Domain createDomain()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.DomainImpl();
    }

    /**
     * Create an instance of Dbname
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Dbname createDbname()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.DbnameImpl();
    }

    /**
     * Create an instance of IsAntiSymmetric
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.IsAntiSymmetric createIsAntiSymmetric()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.IsAntiSymmetricImpl();
    }

    /**
     * Create an instance of SavedBy
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SavedBy createSavedBy()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SavedByImpl();
    }

    /**
     * Create an instance of Subsetdef
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Subsetdef createSubsetdef()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SubsetdefImpl();
    }

    /**
     * Create an instance of Term
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Term createTerm()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.TermImpl();
    }

    /**
     * Create an instance of XrefUnknown
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.XrefUnknown createXrefUnknown()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.XrefUnknownImpl();
    }

    /**
     * Create an instance of Relationship
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Relationship createRelationship()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.RelationshipImpl();
    }

    /**
     * Create an instance of CreatedBy
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.CreatedBy createCreatedBy()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.CreatedByImpl();
    }

    /**
     * Create an instance of Namespace
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Namespace createNamespace()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.NamespaceImpl();
    }

    /**
     * Create an instance of SourceMtime
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SourceMtime createSourceMtime()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SourceMtimeImpl();
    }

    /**
     * Create an instance of SourcePath
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.SourcePath createSourcePath()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.SourcePathImpl();
    }

    /**
     * Create an instance of Scope
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public generated.Scope createScope()
        throws javax.xml.bind.JAXBException
    {
        return new generated.impl.ScopeImpl();
    }

}
