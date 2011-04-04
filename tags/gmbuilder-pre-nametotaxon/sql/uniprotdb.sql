alter table BpcCommentGroupKineticsType_KM drop constraint FK7F295DF8108A5F95;
alter table BpcCommentGroupKineticsType_Vmax drop constraint FK5A4F44644A8788C9;
alter table CitationType drop constraint FK7C9796E136491E54;
alter table CitationType drop constraint FK7C9796E1F57415A4;
alter table CitationType drop constraint FK7C9796E1F22C5486;
alter table CommentType drop constraint FKE0CB5219C81D818D;
alter table CommentType drop constraint FKE0CB521925E10D55;
alter table CommentType drop constraint FKE0CB5219D8CF5AEB;
alter table CommentType drop constraint FKE0CB5219CFA3BF01;
alter table CommentType_ConflictType drop constraint FK6F5452B2BCD5B9D1;
alter table CommentType_LinkType drop constraint FK856AB5DA6ECF92FD;
alter table DbReferenceType drop constraint FKE43B3D27E13D04B6;
alter table DbReferenceType drop constraint FKE43B3D279338FF06;
alter table DbReferenceType drop constraint FKE43B3D2762738266;
alter table EntryType drop constraint FK5ADFA2AC25CD0850;
alter table EntryType drop constraint FK5ADFA2AC4B853B11;
alter table EntryType drop constraint FK5ADFA2AC4029D2B4;
alter table EntryType drop constraint FK5ADFA2AC3FCD2588;
alter table EntryType_Accession drop constraint FKF3878E31135B043F;
alter table EntryType_Comment drop constraint FK208663ACA2DDBA4;
alter table EntryType_GeneType drop constraint FK80090042CB570028;
alter table EntryType_Name drop constraint FK3476D05E4CB1A72;
alter table EventType drop constraint FK7951B8D4603AD4E9;
alter table EvidenceType drop constraint FK3691F25187BE97A6;
alter table EvidencedStringType drop constraint FK45D53F9828B45AC8;
alter table EvidencedStringType drop constraint FK45D53F98E79B22E0;
alter table EvidencedStringType drop constraint FK45D53F989043D942;
alter table EvidencedStringType drop constraint FK45D53F98C23C0BE6;
alter table EvidencedStringType drop constraint FK45D53F98E6DAD713;
alter table EvidencedStringType drop constraint FK45D53F98440FD91C;
alter table EvidencedStringType drop constraint FK45D53F9833363EE4;
alter table EvidencedStringType drop constraint FK45D53F9899B8B42A;
alter table EvidencedStringType drop constraint FK45D53F987DE00ADC;
alter table EvidencedStringType drop constraint FK45D53F98ECF3308B;
alter table EvidencedStringType drop constraint FK45D53F9857F232CF;
alter table FeatureType drop constraint FK4CF1BB3022D4956D;
alter table FeatureType drop constraint FK4CF1BB302D6B089C;
alter table FeatureType_Variation drop constraint FKA6F63BA4953DD530;
alter table GeneLocationType drop constraint FK3055BC64B7CEE32F;
alter table GeneLocationType drop constraint FK3055BC647F1C3193;
alter table GeneNameType drop constraint FKC89D6EBA37EA5013;
alter table InteractantType drop constraint FKDCFA0AABDB8BD172;
alter table IsoformType drop constraint FK981CF9833419C097;
alter table IsoformType drop constraint FK981CF9838394509A;
alter table IsoformType drop constraint FK981CF98352DFFC35;
alter table IsoformType_Id drop constraint FK93D161776BE415D0;
alter table IsoformType_NameType drop constraint FKAEF4DFC12DDC4D60;
alter table KeywordType drop constraint FK1603ACA37C00D25A;
alter table LocationType drop constraint FK65214AFEE2F2184;
alter table LocationType drop constraint FK65214AFC7310104;
alter table LocationType drop constraint FK65214AF9D640322;
alter table LocationType drop constraint FK65214AFC19E1136;
alter table NameListType_PersonOrConsortium drop constraint FK452761F5969E4EA2;
alter table OrganismNameType drop constraint FK8F6D77774EBD64E2;
alter table OrganismType drop constraint FK44B91F4C46DCD7E3;
alter table OrganismType drop constraint FK44B91F4C3E7D0A0B;
alter table OrganismType drop constraint FK44B91F4C167DA5B0;
alter table OrganismType_LineageType_Taxon drop constraint FK261F89FDF8D2E7E4;
alter table PropertyType drop constraint FKD64442CF68B562BE;
alter table ProteinNameGroupAlternativeNameType drop constraint FK872C08E15ABF3D8B;
alter table ProteinNameGroupAlternativeNameType drop constraint FK872C08E11748F9FA;
alter table ProteinNameGroupAlternativeNameType drop constraint FK872C08E1D2FF36E6;
alter table ProteinNameGroupAlternativeNameType drop constraint FK872C08E154D01CA0;
alter table ProteinNameGroupRecommendedNameType drop constraint FK7709318F1748F9FA;
alter table ProteinNameGroupSubmittedNameType drop constraint FKF248BBEF5298F7D8;
alter table ProteinNameGroupSubmittedNameType drop constraint FKF248BBEF77F80ED2;
alter table ProteinNameGroupSubmittedNameType drop constraint FKF248BBEF1748F9FA;
alter table ProteinNameGroupSubmittedNameType drop constraint FKF248BBEF7D501C7D;
alter table ProteinType drop constraint FK4419093DE2F9B389;
alter table ProteinType drop constraint FK4419093D8BA451BD;
alter table ProteinType drop constraint FK4419093D4E30B06D;
alter table ProteinType_ComponentType drop constraint FK2B00F635B41920E6;
alter table ProteinType_ComponentType drop constraint FK2B00F635E2F9B389;
alter table ProteinType_ComponentType drop constraint FK2B00F6358BA451BD;
alter table ProteinType_ComponentType drop constraint FK2B00F6354E30B06D;
alter table ProteinType_DomainType drop constraint FKE3F7C680E2F9B389;
alter table ProteinType_DomainType drop constraint FKE3F7C6802D1C63B;
alter table ProteinType_DomainType drop constraint FKE3F7C6808BA451BD;
alter table ProteinType_DomainType drop constraint FKE3F7C6804E30B06D;
alter table ReferenceType drop constraint FK8F0BD50574685D78;
alter table ReferenceType drop constraint FK8F0BD50510F9E9B2;
alter table ReferenceType drop constraint FK8F0BD505DC62F600;
alter table ReferenceType_Scope drop constraint FK1973EE7AA4A5F7EF;
alter table SourceDataType_SpeciesOrStrainOrPlasmid drop constraint FKD57105C356B60B70;
alter table SubcellularLocationType drop constraint FKDAB41539501D42C4;
drop table BpcCommentGroupAbsorptionType;
drop table BpcCommentGroupKineticsType;
drop table BpcCommentGroupKineticsType_KM;
drop table BpcCommentGroupKineticsType_Vmax;
drop table CitationType;
drop table CommentType;
drop table CommentType_ConflictType;
drop table CommentType_ConflictType_SequenceType;
drop table CommentType_LinkType;
drop table ConsortiumType;
drop table Copyright;
drop table DbReferenceType;
drop table EntryType;
drop table EntryType_Accession;
drop table EntryType_Comment;
drop table EntryType_GeneType;
drop table EntryType_Name;
drop table EventType;
drop table EvidenceType;
drop table EvidencedStringType;
drop table FeatureType;
drop table FeatureType_Variation;
drop table GeneLocationType;
drop table GeneNameType;
drop table InteractantType;
drop table IsoformType;
drop table IsoformType_Id;
drop table IsoformType_NameType;
drop table IsoformType_NoteType;
drop table IsoformType_SequenceType;
drop table KeywordType;
drop table LocationType;
drop table NameListType;
drop table NameListType_PersonOrConsortium;
drop table OrganismNameType;
drop table OrganismType;
drop table OrganismType_LineageType;
drop table OrganismType_LineageType_Taxon;
drop table PersonType;
drop table PositionType;
drop table PropertyType;
drop table ProteinExistenceType;
drop table ProteinNameGroupAlternativeNameType;
drop table ProteinNameGroupRecommendedNameType;
drop table ProteinNameGroupSubmittedNameType;
drop table ProteinType;
drop table ProteinType_ComponentType;
drop table ProteinType_DomainType;
drop table ReferenceType;
drop table ReferenceType_Scope;
drop table SequenceType;
drop table SourceDataType;
drop table SourceDataType_PlasmidType;
drop table SourceDataType_SpeciesOrStrainOrPlasmid;
drop table SourceDataType_SpeciesType;
drop table SourceDataType_StrainType;
drop table SourceDataType_TissueType;
drop table SourceDataType_TransposonType;
drop table StatusType;
drop table SubcellularLocationType;
drop table UniprotType;
drop sequence hibernate_sequence;
create table BpcCommentGroupAbsorptionType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Max varchar,
    Text varchar,
    primary key (Hjid)
);
create table BpcCommentGroupKineticsType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Text varchar,
    primary key (Hjid)
);
create table BpcCommentGroupKineticsType_KM (
    BpcCommentGroupKineticsType_KM_Hjid int8 not null,
    Hjvalue varchar,
    BpcCommentGroupKineticsType_KM_Hjindex int4 not null,
    primary key (BpcCommentGroupKineticsType_KM_Hjid, BpcCommentGroupKineticsType_KM_Hjindex)
);
create table BpcCommentGroupKineticsType_Vmax (
    BpcCommentGroupKineticsType_Vmax_Hjid int8 not null,
    Hjvalue varchar,
    BpcCommentGroupKineticsType_Vmax_Hjindex int4 not null,
    primary key (BpcCommentGroupKineticsType_Vmax_Hjid, BpcCommentGroupKineticsType_Vmax_Hjindex)
);
create table CitationType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Db varchar,
    First varchar,
    Type varchar,
    Institute varchar,
    Publisher varchar,
    Locator varchar,
    Date_Hjclass varchar,
    Date_Hjid int8,
    City varchar,
    Last varchar,
    AuthorList int8,
    Number varchar,
    Country varchar,
    Volume varchar,
    Title varchar,
    Name varchar,
    EditorList int8,
    CitingCitation int8,
    primary key (Hjid)
);
create table CommentType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    Experiments numeric,
    Conflict int8,
    Method varchar,
    Text int8,
    PhDependence varchar,
    Kinetics int8,
    Mass float4,
    Error varchar,
    TemperatureDependence varchar,
    OrganismsDiffer bool,
    Molecule varchar,
    Evidence varchar,
    Name varchar,
    RedoxPotential varchar,
    Absorption int8,
    primary key (Hjid)
);
create table CommentType_ConflictType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    Ref varchar,
    Sequence int8,
    primary key (Hjid)
);
create table CommentType_ConflictType_SequenceType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Resource varchar,
    Version numeric,
    Id varchar,
    primary key (Hjid)
);
create table CommentType_LinkType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Uri varchar,
    CommentType_Link_Hjid int8,
    CommentType_Link_Hjindex int4,
    primary key (Hjid)
);
create table ConsortiumType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Name varchar,
    primary key (Hjid)
);
create table Copyright (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Value varchar,
    primary key (Hjid)
);
create table DbReferenceType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    Key varchar,
    Evidence varchar,
    Id varchar,
    CitationType_DbReference_Hjid int8,
    CitationType_DbReference_Hjindex int4,
    EntryType_DbReference_Hjid int8,
    EntryType_DbReference_Hjindex int4,
    OrganismType_DbReference_Hjid int8,
    OrganismType_DbReference_Hjindex int4,
    primary key (Hjid)
);
create table EntryType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Modified timestamp,
    Sequence int8,
    Version numeric,
    ProteinExistence int8,
    Dataset varchar,
    Created timestamp,
    Protein int8,
    UniprotType_Entry_Hjid int8,
    UniprotType_Entry_Hjindex int4,
    primary key (Hjid)
);
create table EntryType_Accession (
    EntryType_Accession_Hjid int8 not null,
    Hjvalue varchar,
    EntryType_Accession_Hjindex int4 not null,
    primary key (EntryType_Accession_Hjid, EntryType_Accession_Hjindex)
);
create table EntryType_Comment (
    EntryType_Comment_Hjid int8 not null,
    EntryType_Comment_Hjclass varchar,
    EntryType_Comment_Hjchildid int8,
    EntryType_Comment_Hjindex int4 not null,
    primary key (EntryType_Comment_Hjid, EntryType_Comment_Hjindex)
);
create table EntryType_GeneType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    EntryType_Gene_Hjid int8,
    EntryType_Gene_Hjindex int4,
    primary key (Hjid)
);
create table EntryType_Name (
    EntryType_Name_Hjid int8 not null,
    Hjvalue varchar,
    EntryType_Name_Hjindex int4 not null,
    primary key (EntryType_Name_Hjid, EntryType_Name_Hjindex)
);
create table EventType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    CommentType_Event_Hjid int8,
    CommentType_Event_Hjindex int4,
    primary key (Hjid)
);
create table EvidenceType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    Key varchar,
    Attribute varchar,
    Date timestamp,
    Category varchar,
    EntryType_Evidence_Hjid int8,
    EntryType_Evidence_Hjindex int4,
    primary key (Hjid)
);
create table EvidencedStringType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Status varchar,
    Value varchar,
    Evidence varchar,
    ProteinNameGroupAlternativeNameType_ShortName_Hjid int8,
    ProteinNameGroupAlternativeNameType_ShortName_Hjindex int4,
    ProteinNameGroupRecommendedNameType_ShortName_Hjid int8,
    ProteinNameGroupRecommendedNameType_ShortName_Hjindex int4,
    ProteinType_ComponentType_CdAntigenName_Hjid int8,
    ProteinType_ComponentType_CdAntigenName_Hjindex int4,
    ProteinType_ComponentType_InnName_Hjid int8,
    ProteinType_ComponentType_InnName_Hjindex int4,
    ProteinType_DomainType_CdAntigenName_Hjid int8,
    ProteinType_DomainType_CdAntigenName_Hjindex int4,
    ProteinType_DomainType_InnName_Hjid int8,
    ProteinType_DomainType_InnName_Hjindex int4,
    ProteinType_CdAntigenName_Hjid int8,
    ProteinType_CdAntigenName_Hjindex int4,
    ProteinType_InnName_Hjid int8,
    ProteinType_InnName_Hjindex int4,
    SubcellularLocationType_Topology_Hjid int8,
    SubcellularLocationType_Topology_Hjindex int4,
    SubcellularLocationType_Location_Hjid int8,
    SubcellularLocationType_Location_Hjindex int4,
    SubcellularLocationType_Orientation_Hjid int8,
    SubcellularLocationType_Orientation_Hjindex int4,
    primary key (Hjid)
);
create table FeatureType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Original varchar,
    Type varchar,
    Status varchar,
    Ref varchar,
    Description varchar,
    Evidence varchar,
    Location int8,
    Id varchar,
    EntryType_Feature_Hjid int8,
    EntryType_Feature_Hjindex int4,
    primary key (Hjid)
);
create table FeatureType_Variation (
    FeatureType_Variation_Hjid int8 not null,
    Hjvalue varchar,
    FeatureType_Variation_Hjindex int4 not null,
    primary key (FeatureType_Variation_Hjid, FeatureType_Variation_Hjindex)
);
create table GeneLocationType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    Evidence varchar,
    Name int8,
    EntryType_GeneLocation_Hjid int8,
    EntryType_GeneLocation_Hjindex int4,
    primary key (Hjid)
);
create table GeneNameType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    Value varchar,
    Evidence varchar,
    EntryType_GeneType_Name_Hjid int8,
    EntryType_GeneType_Name_Hjindex int4,
    primary key (Hjid)
);
create table InteractantType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Label varchar,
    Id varchar,
    IntactId varchar,
    CommentType_Interactant_Hjid int8,
    CommentType_Interactant_Hjindex int4,
    primary key (Hjid)
);
create table IsoformType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Sequence int8,
    Note int8,
    CommentType_Isoform_Hjid int8,
    CommentType_Isoform_Hjindex int4,
    primary key (Hjid)
);
create table IsoformType_Id (
    IsoformType_Id_Hjid int8 not null,
    Hjvalue varchar,
    IsoformType_Id_Hjindex int4 not null,
    primary key (IsoformType_Id_Hjid, IsoformType_Id_Hjindex)
);
create table IsoformType_NameType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Value varchar,
    Evidence varchar,
    IsoformType_Name_Hjid int8,
    IsoformType_Name_Hjindex int4,
    primary key (Hjid)
);
create table IsoformType_NoteType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Value varchar,
    Evidence varchar,
    primary key (Hjid)
);
create table IsoformType_SequenceType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    Ref varchar,
    primary key (Hjid)
);
create table KeywordType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Value varchar,
    Evidence varchar,
    Id varchar,
    EntryType_Keyword_Hjid int8,
    EntryType_Keyword_Hjindex int4,
    primary key (Hjid)
);
create table LocationType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Sequence varchar,
    Begin int8,
    Position int8,
    EndPosition int8,
    CommentType_Location_Hjid int8,
    CommentType_Location_Hjindex int4,
    primary key (Hjid)
);
create table NameListType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    primary key (Hjid)
);
create table NameListType_PersonOrConsortium (
    NameListType_PersonOrConsortium_Hjid int8 not null,
    NameListType_PersonOrConsortium_Hjclass varchar,
    NameListType_PersonOrConsortium_Hjchildid int8,
    NameListType_PersonOrConsortium_Hjindex int4 not null,
    primary key (NameListType_PersonOrConsortium_Hjid, NameListType_PersonOrConsortium_Hjindex)
);
create table OrganismNameType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    Value varchar,
    OrganismType_Name_Hjid int8,
    OrganismType_Name_Hjindex int4,
    primary key (Hjid)
);
create table OrganismType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Key varchar,
    Lineage int8,
    EntryType_OrganismHost_Hjid int8,
    EntryType_OrganismHost_Hjindex int4,
    EntryType_Organism_Hjid int8,
    EntryType_Organism_Hjindex int4,
    primary key (Hjid)
);
create table OrganismType_LineageType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    primary key (Hjid)
);
create table OrganismType_LineageType_Taxon (
    OrganismType_LineageType_Taxon_Hjid int8 not null,
    Hjvalue varchar,
    OrganismType_LineageType_Taxon_Hjindex int4 not null,
    primary key (OrganismType_LineageType_Taxon_Hjid, OrganismType_LineageType_Taxon_Hjindex)
);
create table PersonType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Name varchar,
    primary key (Hjid)
);
create table PositionType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Status varchar,
    Position numeric,
    primary key (Hjid)
);
create table PropertyType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    Value varchar,
    DbReferenceType_Property_Hjid int8,
    DbReferenceType_Property_Hjindex int4,
    primary key (Hjid)
);
create table ProteinExistenceType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Type varchar,
    primary key (Hjid)
);
create table ProteinNameGroupAlternativeNameType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Ref varchar,
    FullName int8,
    ProteinType_ComponentType_AlternativeName_Hjid int8,
    ProteinType_ComponentType_AlternativeName_Hjindex int4,
    ProteinType_DomainType_AlternativeName_Hjid int8,
    ProteinType_DomainType_AlternativeName_Hjindex int4,
    ProteinType_AlternativeName_Hjid int8,
    ProteinType_AlternativeName_Hjindex int4,
    primary key (Hjid)
);
create table ProteinNameGroupRecommendedNameType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Ref varchar,
    FullName int8,
    primary key (Hjid)
);
create table ProteinNameGroupSubmittedNameType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Ref varchar,
    FullName int8,
    ProteinType_ComponentType_SubmittedName_Hjid int8,
    ProteinType_ComponentType_SubmittedName_Hjindex int4,
    ProteinType_DomainType_SubmittedName_Hjid int8,
    ProteinType_DomainType_SubmittedName_Hjindex int4,
    ProteinType_SubmittedName_Hjid int8,
    ProteinType_SubmittedName_Hjindex int4,
    primary key (Hjid)
);
create table ProteinType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    AllergenName int8,
    BiotechName int8,
    RecommendedName int8,
    primary key (Hjid)
);
create table ProteinType_ComponentType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    AllergenName int8,
    BiotechName int8,
    RecommendedName int8,
    ProteinType_Component_Hjid int8,
    ProteinType_Component_Hjindex int4,
    primary key (Hjid)
);
create table ProteinType_DomainType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    AllergenName int8,
    BiotechName int8,
    RecommendedName int8,
    ProteinType_Domain_Hjid int8,
    ProteinType_Domain_Hjindex int4,
    primary key (Hjid)
);
create table ReferenceType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Key varchar,
    Citation int8,
    Evidence varchar,
    Source int8,
    EntryType_Reference_Hjid int8,
    EntryType_Reference_Hjindex int4,
    primary key (Hjid)
);
create table ReferenceType_Scope (
    ReferenceType_Scope_Hjid int8 not null,
    Hjvalue varchar,
    ReferenceType_Scope_Hjindex int4 not null,
    primary key (ReferenceType_Scope_Hjid, ReferenceType_Scope_Hjindex)
);
create table SequenceType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Modified timestamp,
    Value varchar,
    Version numeric,
    Precursor bool,
    Mass numeric,
    Fragment varchar,
    Length numeric,
    Checksum varchar,
    primary key (Hjid)
);
create table SourceDataType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    primary key (Hjid)
);
create table SourceDataType_PlasmidType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Value varchar,
    Evidence varchar,
    primary key (Hjid)
);
create table SourceDataType_SpeciesOrStrainOrPlasmid (
    SourceDataType_SpeciesOrStrainOrPlasmid_Hjid int8 not null,
    SourceDataType_SpeciesOrStrainOrPlasmid_Hjclass varchar,
    SourceDataType_SpeciesOrStrainOrPlasmid_Hjchildid int8,
    SourceDataType_SpeciesOrStrainOrPlasmid_Hjindex int4 not null,
    primary key (SourceDataType_SpeciesOrStrainOrPlasmid_Hjid, SourceDataType_SpeciesOrStrainOrPlasmid_Hjindex)
);
create table SourceDataType_SpeciesType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Ref varchar,
    Value varchar,
    primary key (Hjid)
);
create table SourceDataType_StrainType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Value varchar,
    Evidence varchar,
    primary key (Hjid)
);
create table SourceDataType_TissueType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Value varchar,
    Evidence varchar,
    primary key (Hjid)
);
create table SourceDataType_TransposonType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Value varchar,
    Evidence varchar,
    primary key (Hjid)
);
create table StatusType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Status varchar,
    Value varchar,
    primary key (Hjid)
);
create table SubcellularLocationType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    CommentType_SubcellularLocation_Hjid int8,
    CommentType_SubcellularLocation_Hjindex int4,
    primary key (Hjid)
);
create table UniprotType (
    Hjid int8 not null,
    Hjtype varchar not null,
    Hjversion int8 not null,
    Copyright varchar,
    primary key (Hjid)
);
alter table BpcCommentGroupKineticsType_KM 
    add constraint FK7F295DF8108A5F95 
    foreign key (BpcCommentGroupKineticsType_KM_Hjid) 
    references BpcCommentGroupKineticsType;
alter table BpcCommentGroupKineticsType_Vmax 
    add constraint FK5A4F44644A8788C9 
    foreign key (BpcCommentGroupKineticsType_Vmax_Hjid) 
    references BpcCommentGroupKineticsType;
alter table CitationType 
    add constraint FK7C9796E136491E54 
    foreign key (CitingCitation) 
    references CitationType;
alter table CitationType 
    add constraint FK7C9796E1F57415A4 
    foreign key (AuthorList) 
    references NameListType;
alter table CitationType 
    add constraint FK7C9796E1F22C5486 
    foreign key (EditorList) 
    references NameListType;
alter table CommentType 
    add constraint FKE0CB5219C81D818D 
    foreign key (Text) 
    references EvidencedStringType;
alter table CommentType 
    add constraint FKE0CB521925E10D55 
    foreign key (Absorption) 
    references BpcCommentGroupAbsorptionType;
alter table CommentType 
    add constraint FKE0CB5219D8CF5AEB 
    foreign key (Kinetics) 
    references BpcCommentGroupKineticsType;
alter table CommentType 
    add constraint FKE0CB5219CFA3BF01 
    foreign key (Conflict) 
    references CommentType_ConflictType;
alter table CommentType_ConflictType 
    add constraint FK6F5452B2BCD5B9D1 
    foreign key (Sequence) 
    references CommentType_ConflictType_SequenceType;
alter table CommentType_LinkType 
    add constraint FK856AB5DA6ECF92FD 
    foreign key (CommentType_Link_Hjid) 
    references CommentType;
alter table DbReferenceType 
    add constraint FKE43B3D27E13D04B6 
    foreign key (EntryType_DbReference_Hjid) 
    references EntryType;
alter table DbReferenceType 
    add constraint FKE43B3D279338FF06 
    foreign key (CitationType_DbReference_Hjid) 
    references CitationType;
alter table DbReferenceType 
    add constraint FKE43B3D2762738266 
    foreign key (OrganismType_DbReference_Hjid) 
    references OrganismType;
alter table EntryType 
    add constraint FK5ADFA2AC25CD0850 
    foreign key (ProteinExistence) 
    references ProteinExistenceType;
alter table EntryType 
    add constraint FK5ADFA2AC4B853B11 
    foreign key (UniprotType_Entry_Hjid) 
    references UniprotType;
alter table EntryType 
    add constraint FK5ADFA2AC4029D2B4 
    foreign key (Sequence) 
    references SequenceType;
alter table EntryType 
    add constraint FK5ADFA2AC3FCD2588 
    foreign key (Protein) 
    references ProteinType;
alter table EntryType_Accession 
    add constraint FKF3878E31135B043F 
    foreign key (EntryType_Accession_Hjid) 
    references EntryType;
alter table EntryType_Comment 
    add constraint FK208663ACA2DDBA4 
    foreign key (EntryType_Comment_Hjid) 
    references EntryType;
alter table EntryType_GeneType 
    add constraint FK80090042CB570028 
    foreign key (EntryType_Gene_Hjid) 
    references EntryType;
alter table EntryType_Name 
    add constraint FK3476D05E4CB1A72 
    foreign key (EntryType_Name_Hjid) 
    references EntryType;
alter table EventType 
    add constraint FK7951B8D4603AD4E9 
    foreign key (CommentType_Event_Hjid) 
    references CommentType;
alter table EvidenceType 
    add constraint FK3691F25187BE97A6 
    foreign key (EntryType_Evidence_Hjid) 
    references EntryType;
alter table EvidencedStringType 
    add constraint FK45D53F9828B45AC8 
    foreign key (SubcellularLocationType_Topology_Hjid) 
    references SubcellularLocationType;
alter table EvidencedStringType 
    add constraint FK45D53F98E79B22E0 
    foreign key (ProteinType_DomainType_CdAntigenName_Hjid) 
    references ProteinType_DomainType;
alter table EvidencedStringType 
    add constraint FK45D53F989043D942 
    foreign key (SubcellularLocationType_Location_Hjid) 
    references SubcellularLocationType;
alter table EvidencedStringType 
    add constraint FK45D53F98C23C0BE6 
    foreign key (ProteinType_ComponentType_CdAntigenName_Hjid) 
    references ProteinType_ComponentType;
alter table EvidencedStringType 
    add constraint FK45D53F98E6DAD713 
    foreign key (SubcellularLocationType_Orientation_Hjid) 
    references SubcellularLocationType;
alter table EvidencedStringType 
    add constraint FK45D53F98440FD91C 
    foreign key (ProteinNameGroupRecommendedNameType_ShortName_Hjid) 
    references ProteinNameGroupRecommendedNameType;
alter table EvidencedStringType 
    add constraint FK45D53F9833363EE4 
    foreign key (ProteinType_DomainType_InnName_Hjid) 
    references ProteinType_DomainType;
alter table EvidencedStringType 
    add constraint FK45D53F9899B8B42A 
    foreign key (ProteinType_ComponentType_InnName_Hjid) 
    references ProteinType_ComponentType;
alter table EvidencedStringType 
    add constraint FK45D53F987DE00ADC 
    foreign key (ProteinNameGroupAlternativeNameType_ShortName_Hjid) 
    references ProteinNameGroupAlternativeNameType;
alter table EvidencedStringType 
    add constraint FK45D53F98ECF3308B 
    foreign key (ProteinType_CdAntigenName_Hjid) 
    references ProteinType;
alter table EvidencedStringType 
    add constraint FK45D53F9857F232CF 
    foreign key (ProteinType_InnName_Hjid) 
    references ProteinType;
alter table FeatureType 
    add constraint FK4CF1BB3022D4956D 
    foreign key (EntryType_Feature_Hjid) 
    references EntryType;
alter table FeatureType 
    add constraint FK4CF1BB302D6B089C 
    foreign key (Location) 
    references LocationType;
alter table FeatureType_Variation 
    add constraint FKA6F63BA4953DD530 
    foreign key (FeatureType_Variation_Hjid) 
    references FeatureType;
alter table GeneLocationType 
    add constraint FK3055BC64B7CEE32F 
    foreign key (Name) 
    references StatusType;
alter table GeneLocationType 
    add constraint FK3055BC647F1C3193 
    foreign key (EntryType_GeneLocation_Hjid) 
    references EntryType;
alter table GeneNameType 
    add constraint FKC89D6EBA37EA5013 
    foreign key (EntryType_GeneType_Name_Hjid) 
    references EntryType_GeneType;
alter table InteractantType 
    add constraint FKDCFA0AABDB8BD172 
    foreign key (CommentType_Interactant_Hjid) 
    references CommentType;
alter table IsoformType 
    add constraint FK981CF9833419C097 
    foreign key (Note) 
    references IsoformType_NoteType;
alter table IsoformType 
    add constraint FK981CF9838394509A 
    foreign key (CommentType_Isoform_Hjid) 
    references CommentType;
alter table IsoformType 
    add constraint FK981CF98352DFFC35 
    foreign key (Sequence) 
    references IsoformType_SequenceType;
alter table IsoformType_Id 
    add constraint FK93D161776BE415D0 
    foreign key (IsoformType_Id_Hjid) 
    references IsoformType;
alter table IsoformType_NameType 
    add constraint FKAEF4DFC12DDC4D60 
    foreign key (IsoformType_Name_Hjid) 
    references IsoformType;
alter table KeywordType 
    add constraint FK1603ACA37C00D25A 
    foreign key (EntryType_Keyword_Hjid) 
    references EntryType;
alter table LocationType 
    add constraint FK65214AFEE2F2184 
    foreign key (Position) 
    references PositionType;
alter table LocationType 
    add constraint FK65214AFC7310104 
    foreign key (Begin) 
    references PositionType;
alter table LocationType 
    add constraint FK65214AF9D640322 
    foreign key (CommentType_Location_Hjid) 
    references CommentType;
alter table LocationType 
    add constraint FK65214AFC19E1136 
    foreign key (EndPosition) 
    references PositionType;
alter table NameListType_PersonOrConsortium 
    add constraint FK452761F5969E4EA2 
    foreign key (NameListType_PersonOrConsortium_Hjid) 
    references NameListType;
alter table OrganismNameType 
    add constraint FK8F6D77774EBD64E2 
    foreign key (OrganismType_Name_Hjid) 
    references OrganismType;
alter table OrganismType 
    add constraint FK44B91F4C46DCD7E3 
    foreign key (EntryType_OrganismHost_Hjid) 
    references EntryType;
alter table OrganismType 
    add constraint FK44B91F4C3E7D0A0B 
    foreign key (EntryType_Organism_Hjid) 
    references EntryType;
alter table OrganismType 
    add constraint FK44B91F4C167DA5B0 
    foreign key (Lineage) 
    references OrganismType_LineageType;
alter table OrganismType_LineageType_Taxon 
    add constraint FK261F89FDF8D2E7E4 
    foreign key (OrganismType_LineageType_Taxon_Hjid) 
    references OrganismType_LineageType;
alter table PropertyType 
    add constraint FKD64442CF68B562BE 
    foreign key (DbReferenceType_Property_Hjid) 
    references DbReferenceType;
alter table ProteinNameGroupAlternativeNameType 
    add constraint FK872C08E15ABF3D8B 
    foreign key (ProteinType_AlternativeName_Hjid) 
    references ProteinType;
alter table ProteinNameGroupAlternativeNameType 
    add constraint FK872C08E11748F9FA 
    foreign key (FullName) 
    references EvidencedStringType;
alter table ProteinNameGroupAlternativeNameType 
    add constraint FK872C08E1D2FF36E6 
    foreign key (ProteinType_ComponentType_AlternativeName_Hjid) 
    references ProteinType_ComponentType;
alter table ProteinNameGroupAlternativeNameType 
    add constraint FK872C08E154D01CA0 
    foreign key (ProteinType_DomainType_AlternativeName_Hjid) 
    references ProteinType_DomainType;
alter table ProteinNameGroupRecommendedNameType 
    add constraint FK7709318F1748F9FA 
    foreign key (FullName) 
    references EvidencedStringType;
alter table ProteinNameGroupSubmittedNameType 
    add constraint FKF248BBEF5298F7D8 
    foreign key (ProteinType_ComponentType_SubmittedName_Hjid) 
    references ProteinType_ComponentType;
alter table ProteinNameGroupSubmittedNameType 
    add constraint FKF248BBEF77F80ED2 
    foreign key (ProteinType_DomainType_SubmittedName_Hjid) 
    references ProteinType_DomainType;
alter table ProteinNameGroupSubmittedNameType 
    add constraint FKF248BBEF1748F9FA 
    foreign key (FullName) 
    references EvidencedStringType;
alter table ProteinNameGroupSubmittedNameType 
    add constraint FKF248BBEF7D501C7D 
    foreign key (ProteinType_SubmittedName_Hjid) 
    references ProteinType;
alter table ProteinType 
    add constraint FK4419093DE2F9B389 
    foreign key (BiotechName) 
    references EvidencedStringType;
alter table ProteinType 
    add constraint FK4419093D8BA451BD 
    foreign key (RecommendedName) 
    references ProteinNameGroupRecommendedNameType;
alter table ProteinType 
    add constraint FK4419093D4E30B06D 
    foreign key (AllergenName) 
    references EvidencedStringType;
alter table ProteinType_ComponentType 
    add constraint FK2B00F635B41920E6 
    foreign key (ProteinType_Component_Hjid) 
    references ProteinType;
alter table ProteinType_ComponentType 
    add constraint FK2B00F635E2F9B389 
    foreign key (BiotechName) 
    references EvidencedStringType;
alter table ProteinType_ComponentType 
    add constraint FK2B00F6358BA451BD 
    foreign key (RecommendedName) 
    references ProteinNameGroupRecommendedNameType;
alter table ProteinType_ComponentType 
    add constraint FK2B00F6354E30B06D 
    foreign key (AllergenName) 
    references EvidencedStringType;
alter table ProteinType_DomainType 
    add constraint FKE3F7C680E2F9B389 
    foreign key (BiotechName) 
    references EvidencedStringType;
alter table ProteinType_DomainType 
    add constraint FKE3F7C6802D1C63B 
    foreign key (ProteinType_Domain_Hjid) 
    references ProteinType;
alter table ProteinType_DomainType 
    add constraint FKE3F7C6808BA451BD 
    foreign key (RecommendedName) 
    references ProteinNameGroupRecommendedNameType;
alter table ProteinType_DomainType 
    add constraint FKE3F7C6804E30B06D 
    foreign key (AllergenName) 
    references EvidencedStringType;
alter table ReferenceType 
    add constraint FK8F0BD50574685D78 
    foreign key (EntryType_Reference_Hjid) 
    references EntryType;
alter table ReferenceType 
    add constraint FK8F0BD50510F9E9B2 
    foreign key (Source) 
    references SourceDataType;
alter table ReferenceType 
    add constraint FK8F0BD505DC62F600 
    foreign key (Citation) 
    references CitationType;
alter table ReferenceType_Scope 
    add constraint FK1973EE7AA4A5F7EF 
    foreign key (ReferenceType_Scope_Hjid) 
    references ReferenceType;
alter table SourceDataType_SpeciesOrStrainOrPlasmid 
    add constraint FKD57105C356B60B70 
    foreign key (SourceDataType_SpeciesOrStrainOrPlasmid_Hjid) 
    references SourceDataType;
alter table SubcellularLocationType 
    add constraint FKDAB41539501D42C4 
    foreign key (CommentType_SubcellularLocation_Hjid) 
    references CommentType;
create sequence hibernate_sequence;
