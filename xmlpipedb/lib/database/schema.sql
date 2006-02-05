alter table EntryType drop constraint FK5ADFA2ACED94D783;
alter table EntryType drop constraint FK5ADFA2AC507077C1;
alter table FeatureType_VariationInternal drop constraint FK383C82813DF2424A;
alter table ReferenceType drop constraint FK8F0BD5059BF4374E;
alter table ReferenceType drop constraint FK8F0BD505CA90681B;
alter table ReferenceType drop constraint FK8F0BD505AA020AE7;
alter table EntryType_NameInternal drop constraint FK8FA1BD3B9BF4374E;
alter table Uniprot drop constraint FK52230837460B9345;
alter table Entry drop constraint FK4001852460B9345;
alter table CommentType drop constraint FKE0CB5219DF06BABA;
alter table CommentType drop constraint FKE0CB52199BF4374E;
alter table CommentType drop constraint FKE0CB5219B4EA46CF;
alter table EntryType$GeneType drop constraint FK54436B079BF4374E;
alter table PropertyType drop constraint FKD64442CF4467EC73;
alter table FeatureType drop constraint FK4CF1BB30714F9FB5;
alter table FeatureType drop constraint FK4CF1BB309BF4374E;
alter table SourceDataType$Species drop constraint FKD9AAB4D7460B9345;
alter table CommentType$LinkType drop constraint FK59A5209FD035C341;
alter table ProteinNameType drop constraint FKA7F500E81F067B9D;
alter table ProteinNameType drop constraint FKA7F500E84A095DE3;
alter table ProteinNameType drop constraint FKA7F500E83672DF7C;
alter table NameListType$Consortium drop constraint FK67F34942460B9345;
alter table GeneLocationType drop constraint FK3055BC649BF4374E;
alter table GeneLocationType drop constraint FK3055BC64337A8B;
alter table NameListType$PersonOrConsortium drop constraint FKDEE2DA7A7012F557;
alter table NameListType$PersonOrConsortium drop constraint FKDEE2DA7AEC277EA1;
alter table NameListType$PersonOrConsortium drop constraint FKDEE2DA7AC4E39B55;
alter table SourceDataType$Strain drop constraint FKDDF96E7A460B9345;
alter table DbReferenceType drop constraint FKE43B3D279A7346AE;
alter table DbReferenceType drop constraint FKE43B3D278EC00D79;
alter table DbReferenceType drop constraint FKE43B3D279BF4374E;
alter table KeywordType drop constraint FK1603ACA39BF4374E;
alter table ReferenceType_ScopeInternal drop constraint FK641CFF57B6545ED5;
alter table SourceDataType$Plasmid drop constraint FK33F397F5460B9345;
alter table ProteinType$DomainType drop constraint FK934294051F067B9D;
alter table EntryType_AccessionInternal drop constraint FKF90CF80E9BF4374E;
alter table OrganismNameType drop constraint FK8F6D77779A7346AE;
alter table OrganismType$LineageType_TaxonInternal drop constraint FK76EC3FB5679D0995;
alter table SourceDataType$SpeciesOrStrainOrPlasmid drop constraint FK5E4F46888849413C;
alter table SourceDataType$SpeciesOrStrainOrPlasmid drop constraint FK5E4F4688999BF18B;
alter table SourceDataType$SpeciesOrStrainOrPlasmid drop constraint FK5E4F4688CBEFD0E5;
alter table SourceDataType$SpeciesOrStrainOrPlasmid drop constraint FK5E4F4688E292245A;
alter table SourceDataType$SpeciesOrStrainOrPlasmid drop constraint FK5E4F4688CAD54175;
alter table SourceDataType$SpeciesOrStrainOrPlasmid drop constraint FK5E4F4688AF62DF3B;
alter table BpcCommentGroupKineticsType_VmaxInternal drop constraint FKD960CB419F350FF1;
alter table NameListType$Person drop constraint FK98B7DE76460B9345;
alter table OrganismType drop constraint FK44B91F4CA8B548B;
alter table OrganismType drop constraint FK44B91F4C9BF4374E;
alter table InteractantType drop constraint FKDCFA0AABD035C341;
alter table BpcCommentGroupKineticsType_KMInternal drop constraint FKEAA210D59F350FF1;
alter table ProteinType$ComponentType drop constraint FK1CE37E901F067B9D;
alter table LocationType drop constraint FK65214AFD035C341;
alter table LocationType drop constraint FK65214AF188DB;
alter table LocationType drop constraint FK65214AF59478A9;
alter table LocationType drop constraint FK65214AF2C929929;
alter table SourceDataType$Tissue drop constraint FKDF13FDEA460B9345;
alter table CitationType drop constraint FK7C9796E17C2975B;
alter table CitationType drop constraint FK7C9796E1A337C7AB;
alter table CitationType drop constraint FK7C9796E1A67F88C9;
alter table SourceDataType$Transposon drop constraint FKF5281410460B9345;
alter table EvidenceType drop constraint FK3691F2519BF4374E;
alter table GeneNameType drop constraint FKC89D6EBA8F4A648B;
alter table UniprotType$Entry drop constraint FK345495FFE36D82CC;
alter table UniprotType$Entry drop constraint FK345495FF3FDED649;
alter table UniprotType$Entry drop constraint FK345495FF5C30872;
drop table EntryType;
drop table FeatureType_VariationInternal;
drop table EventType;
drop table ReferenceType;
drop table EntryType_NameInternal;
drop table SequenceType;
drop table Uniprot;
drop table Entry;
drop table CommentType;
drop table EntryType$GeneType;
drop table SourceDataType$TransposonType;
drop table SourceDataType$PlasmidType;
drop table PropertyType;
drop table SourceDataType$StrainType;
drop table FeatureType;
drop table SourceDataType$Species;
drop table CommentType$LinkType;
drop table ProteinNameType;
drop table PositionType;
drop table NameListType;
drop table NameListType$Consortium;
drop table PersonType;
drop table Copyright;
drop table ProteinType;
drop table SourceDataType;
drop table GeneLocationType;
drop table StatusType;
drop table NameListType$PersonOrConsortium;
drop table SourceDataType$Strain;
drop table DbReferenceType;
drop table KeywordType;
drop table ReferenceType_ScopeInternal;
drop table SourceDataType$Plasmid;
drop table ProteinType$DomainType;
drop table BpcCommentGroupAbsorptionType;
drop table EntryType_AccessionInternal;
drop table OrganismNameType;
drop table SourceDataType$SpeciesType;
drop table UniprotType;
drop table OrganismType$LineageType_TaxonInternal;
drop table ConsortiumType;
drop table SourceDataType$SpeciesOrStrainOrPlasmid;
drop table BpcCommentGroupKineticsType_VmaxInternal;
drop table NameListType$Person;
drop table OrganismType;
drop table SourceDataType$TissueType;
drop table InteractantType;
drop table OrganismType$LineageType;
drop table BpcCommentGroupKineticsType_KMInternal;
drop table ProteinType$ComponentType;
drop table BpcCommentGroupKineticsType;
drop table LocationType;
drop table SourceDataType$Tissue;
drop table CitationType;
drop table SourceDataType$Transposon;
drop table EvidenceType;
drop table GeneNameType;
drop table UniprotType$Entry;
create table EntryType (
   idInternal varchar(32) not null,
   modified timestamp,
   sequence varchar(32),
   dataset varchar(255),
   created timestamp,
   protein varchar(32),
   primary key (idInternal)
);
create table FeatureType_VariationInternal (
   FeatureTypeImpl_id varchar(32) not null,
   value varchar(255),
   VariationInternal_index int4 not null,
   primary key (FeatureTypeImpl_id, VariationInternal_index)
);
create table EventType (
   idInternal varchar(32) not null,
   type varchar(255),
   value varchar(255),
   namedIsoforms int4,
   evidence varchar(255),
   primary key (idInternal)
);
create table ReferenceType (
   idInternal varchar(32) not null,
   key varchar(255),
   citation varchar(32),
   evidence varchar(255),
   source varchar(32),
   EntryTypeImpl_id varchar(32),
   ReferenceInternal_index int4,
   primary key (idInternal)
);
create table EntryType_NameInternal (
   EntryTypeImpl_id varchar(32) not null,
   value varchar(255),
   NameInternal_index int4 not null,
   primary key (EntryTypeImpl_id, NameInternal_index)
);
create table SequenceType (
   idInternal varchar(32) not null,
   modified timestamp,
   value varchar(255),
   mass bytea,
   length bytea,
   checksum varchar(255),
   primary key (idInternal)
);
create table Uniprot (
   parentid varchar(32) not null,
   primary key (parentid)
);
create table Entry (
   parentid varchar(32) not null,
   primary key (parentid)
);
create table CommentType (
   idInternal varchar(32) not null,
   status varchar(255),
   type varchar(255),
   experiments bytea,
   method varchar(255),
   text varchar(255),
   phDependence varchar(255),
   kinetics varchar(32),
   locationType varchar(255),
   mass float4,
   error varchar(255),
   note varchar(255),
   temperatureDependence varchar(255),
   organismsDiffer bool,
   evidence varchar(255),
   name varchar(255),
   redoxPotential varchar(255),
   absorption varchar(32),
   EntryTypeImpl_id varchar(32),
   CommentInternal_index int4,
   primary key (idInternal)
);
create table EntryType$GeneType (
   idInternal varchar(32) not null,
   EntryTypeImpl_id varchar(32),
   GeneInternal_index int4,
   primary key (idInternal)
);
create table SourceDataType$TransposonType (
   idInternal varchar(32) not null,
   value varchar(255),
   evidence varchar(255),
   primary key (idInternal)
);
create table SourceDataType$PlasmidType (
   idInternal varchar(32) not null,
   value varchar(255),
   evidence varchar(255),
   primary key (idInternal)
);
create table PropertyType (
   idInternal varchar(32) not null,
   type varchar(255),
   value varchar(255),
   DbReferenceTypeImpl_id varchar(32),
   PropertyInternal_index int4,
   primary key (idInternal)
);
create table SourceDataType$StrainType (
   idInternal varchar(32) not null,
   value varchar(255),
   evidence varchar(255),
   primary key (idInternal)
);
create table FeatureType (
   idInternal varchar(32) not null,
   original varchar(255),
   type varchar(255),
   status varchar(255),
   ref varchar(255),
   description varchar(255),
   evidence varchar(255),
   location varchar(32),
   id varchar(255),
   EntryTypeImpl_id varchar(32),
   FeatureInternal_index int4,
   primary key (idInternal)
);
create table SourceDataType$Species (
   parentid varchar(32) not null,
   primary key (parentid)
);
create table CommentType$LinkType (
   idInternal varchar(32) not null,
   uri varchar(255),
   CommentTypeImpl_id varchar(32),
   LinkInternal_index int4,
   primary key (idInternal)
);
create table ProteinNameType (
   idInternal varchar(32) not null,
   ref varchar(255),
   value varchar(255),
   evidence varchar(255),
   ComponentTypeImpl_id varchar(32),
   NameInternal_index int4,
   DomainTypeImpl_id varchar(32),
   ProteinTypeImpl_id varchar(32),
   primary key (idInternal)
);
create table PositionType (
   idInternal varchar(32) not null,
   status varchar(255),
   position bytea,
   primary key (idInternal)
);
create table NameListType (
   idInternal varchar(32) not null,
   primary key (idInternal)
);
create table NameListType$Consortium (
   parentid varchar(32) not null,
   primary key (parentid)
);
create table PersonType (
   idInternal varchar(32) not null,
   name varchar(255),
   primary key (idInternal)
);
create table Copyright (
   idInternal varchar(32) not null,
   value varchar(255),
   primary key (idInternal)
);
create table ProteinType (
   idInternal varchar(32) not null,
   type varchar(255),
   evidence varchar(255),
   primary key (idInternal)
);
create table SourceDataType (
   idInternal varchar(32) not null,
   primary key (idInternal)
);
create table GeneLocationType (
   idInternal varchar(32) not null,
   type varchar(255),
   evidence varchar(255),
   name varchar(32),
   EntryTypeImpl_id varchar(32),
   GeneLocationInternal_index int4,
   primary key (idInternal)
);
create table StatusType (
   idInternal varchar(32) not null,
   status varchar(255),
   value varchar(255),
   primary key (idInternal)
);
create table NameListType$PersonOrConsortium (
   idInternal varchar(32) not null,
   consortium varchar(32),
   person varchar(32),
   NameListTypeImpl_id varchar(32),
   PersonOrConsortiumInternal_index int4,
   primary key (idInternal)
);
create table SourceDataType$Strain (
   parentid varchar(32) not null,
   primary key (parentid)
);
create table DbReferenceType (
   idInternal varchar(32) not null,
   type varchar(255),
   key varchar(255),
   evidence varchar(255),
   id varchar(255),
   CitationTypeImpl_id varchar(32),
   DbReferenceInternal_index int4,
   EntryTypeImpl_id varchar(32),
   OrganismTypeImpl_id varchar(32),
   primary key (idInternal)
);
create table KeywordType (
   idInternal varchar(32) not null,
   value varchar(255),
   evidence varchar(255),
   id varchar(255),
   EntryTypeImpl_id varchar(32),
   KeywordInternal_index int4,
   primary key (idInternal)
);
create table ReferenceType_ScopeInternal (
   ReferenceTypeImpl_id varchar(32) not null,
   value varchar(255),
   ScopeInternal_index int4 not null,
   primary key (ReferenceTypeImpl_id, ScopeInternal_index)
);
create table SourceDataType$Plasmid (
   parentid varchar(32) not null,
   primary key (parentid)
);
create table ProteinType$DomainType (
   idInternal varchar(32) not null,
   ProteinTypeImpl_id varchar(32),
   DomainInternal_index int4,
   primary key (idInternal)
);
create table BpcCommentGroupAbsorptionType (
   idInternal varchar(32) not null,
   max varchar(255),
   text varchar(255),
   primary key (idInternal)
);
create table EntryType_AccessionInternal (
   EntryTypeImpl_id varchar(32) not null,
   value varchar(255),
   AccessionInternal_index int4 not null,
   primary key (EntryTypeImpl_id, AccessionInternal_index)
);
create table OrganismNameType (
   idInternal varchar(32) not null,
   type varchar(255),
   value varchar(255),
   OrganismTypeImpl_id varchar(32),
   NameInternal_index int4,
   primary key (idInternal)
);
create table SourceDataType$SpeciesType (
   idInternal varchar(32) not null,
   ref varchar(255),
   value varchar(255),
   primary key (idInternal)
);
create table UniprotType (
   idInternal varchar(32) not null,
   copyright varchar(255),
   primary key (idInternal)
);
create table OrganismType$LineageType_TaxonInternal (
   LineageTypeImpl_id varchar(32) not null,
   value varchar(255),
   TaxonInternal_index int4 not null,
   primary key (LineageTypeImpl_id, TaxonInternal_index)
);
create table ConsortiumType (
   idInternal varchar(32) not null,
   name varchar(255),
   primary key (idInternal)
);
create table SourceDataType$SpeciesOrStrainOrPlasmid (
   idInternal varchar(32) not null,
   plasmid varchar(32),
   tissue varchar(32),
   strain varchar(32),
   transposon varchar(32),
   species varchar(32),
   SourceDataTypeImpl_id varchar(32),
   SpeciesOrStrainOrPlasmidInternal_index int4,
   primary key (idInternal)
);
create table BpcCommentGroupKineticsType_VmaxInternal (
   BpcCommentGroupKineticsTypeImpl_id varchar(32) not null,
   value varchar(255),
   VmaxInternal_index int4 not null,
   primary key (BpcCommentGroupKineticsTypeImpl_id, VmaxInternal_index)
);
create table NameListType$Person (
   parentid varchar(32) not null,
   primary key (parentid)
);
create table OrganismType (
   idInternal varchar(32) not null,
   key varchar(255),
   lineage varchar(32),
   EntryTypeImpl_id varchar(32),
   OrganismInternal_index int4,
   primary key (idInternal)
);
create table SourceDataType$TissueType (
   idInternal varchar(32) not null,
   value varchar(255),
   evidence varchar(255),
   primary key (idInternal)
);
create table InteractantType (
   idInternal varchar(32) not null,
   label varchar(255),
   id varchar(255),
   intactId varchar(255),
   CommentTypeImpl_id varchar(32),
   InteractantInternal_index int4,
   primary key (idInternal)
);
create table OrganismType$LineageType (
   idInternal varchar(32) not null,
   primary key (idInternal)
);
create table BpcCommentGroupKineticsType_KMInternal (
   BpcCommentGroupKineticsTypeImpl_id varchar(32) not null,
   value varchar(255),
   KMInternal_index int4 not null,
   primary key (BpcCommentGroupKineticsTypeImpl_id, KMInternal_index)
);
create table ProteinType$ComponentType (
   idInternal varchar(32) not null,
   ProteinTypeImpl_id varchar(32),
   ComponentInternal_index int4,
   primary key (idInternal)
);
create table BpcCommentGroupKineticsType (
   idInternal varchar(32) not null,
   text varchar(255),
   primary key (idInternal)
);
create table LocationType (
   idInternal varchar(32) not null,
   sequence varchar(255),
   begin varchar(32),
   position varchar(32),
   end varchar(32),
   CommentTypeImpl_id varchar(32),
   LocationInternal_index int4,
   primary key (idInternal)
);
create table SourceDataType$Tissue (
   parentid varchar(32) not null,
   primary key (parentid)
);
create table CitationType (
   idInternal varchar(32) not null,
   db varchar(255),
   first varchar(255),
   type varchar(255),
   institute varchar(255),
   publisher varchar(255),
   locator varchar(255),
   date bytea,
   city varchar(255),
   last varchar(255),
   authorList varchar(32),
   number varchar(255),
   country varchar(255),
   volume varchar(255),
   title varchar(255),
   name varchar(255),
   editorList varchar(32),
   citingCitation varchar(32),
   primary key (idInternal)
);
create table SourceDataType$Transposon (
   parentid varchar(32) not null,
   primary key (parentid)
);
create table EvidenceType (
   idInternal varchar(32) not null,
   type varchar(255),
   key varchar(255),
   attribute varchar(255),
   date timestamp,
   category varchar(255),
   EntryTypeImpl_id varchar(32),
   EvidenceInternal_index int4,
   primary key (idInternal)
);
create table GeneNameType (
   idInternal varchar(32) not null,
   type varchar(255),
   value varchar(255),
   evidence varchar(255),
   GeneTypeImpl_id varchar(32),
   NameInternal_index int4,
   primary key (idInternal)
);
create table UniprotType$Entry (
   idInternal varchar(32) not null,
   entry varchar(32),
   entryType varchar(32),
   UniprotTypeImpl_id varchar(32),
   EntryInternal_index int4,
   primary key (idInternal)
);
alter table EntryType add constraint FK5ADFA2ACED94D783 foreign key (protein) references ProteinType;
alter table EntryType add constraint FK5ADFA2AC507077C1 foreign key (sequence) references SequenceType;
alter table FeatureType_VariationInternal add constraint FK383C82813DF2424A foreign key (FeatureTypeImpl_id) references FeatureType;
alter table ReferenceType add constraint FK8F0BD5059BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table ReferenceType add constraint FK8F0BD505CA90681B foreign key (source) references SourceDataType;
alter table ReferenceType add constraint FK8F0BD505AA020AE7 foreign key (citation) references CitationType;
alter table EntryType_NameInternal add constraint FK8FA1BD3B9BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table Uniprot add constraint FK52230837460B9345 foreign key (parentid) references UniprotType;
alter table Entry add constraint FK4001852460B9345 foreign key (parentid) references EntryType;
alter table CommentType add constraint FKE0CB5219DF06BABA foreign key (kinetics) references BpcCommentGroupKineticsType;
alter table CommentType add constraint FKE0CB52199BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table CommentType add constraint FKE0CB5219B4EA46CF foreign key (absorption) references BpcCommentGroupAbsorptionType;
alter table EntryType$GeneType add constraint FK54436B079BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table PropertyType add constraint FKD64442CF4467EC73 foreign key (DbReferenceTypeImpl_id) references DbReferenceType;
alter table FeatureType add constraint FK4CF1BB30714F9FB5 foreign key (location) references LocationType;
alter table FeatureType add constraint FK4CF1BB309BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table SourceDataType$Species add constraint FKD9AAB4D7460B9345 foreign key (parentid) references SourceDataType$SpeciesType;
alter table CommentType$LinkType add constraint FK59A5209FD035C341 foreign key (CommentTypeImpl_id) references CommentType;
alter table ProteinNameType add constraint FKA7F500E81F067B9D foreign key (ProteinTypeImpl_id) references ProteinType;
alter table ProteinNameType add constraint FKA7F500E84A095DE3 foreign key (ComponentTypeImpl_id) references ProteinType$ComponentType;
alter table ProteinNameType add constraint FKA7F500E83672DF7C foreign key (DomainTypeImpl_id) references ProteinType$DomainType;
alter table NameListType$Consortium add constraint FK67F34942460B9345 foreign key (parentid) references ConsortiumType;
alter table GeneLocationType add constraint FK3055BC649BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table GeneLocationType add constraint FK3055BC64337A8B foreign key (name) references StatusType;
alter table NameListType$PersonOrConsortium add constraint FKDEE2DA7A7012F557 foreign key (NameListTypeImpl_id) references NameListType;
alter table NameListType$PersonOrConsortium add constraint FKDEE2DA7AEC277EA1 foreign key (consortium) references NameListType$Consortium;
alter table NameListType$PersonOrConsortium add constraint FKDEE2DA7AC4E39B55 foreign key (person) references NameListType$Person;
alter table SourceDataType$Strain add constraint FKDDF96E7A460B9345 foreign key (parentid) references SourceDataType$StrainType;
alter table DbReferenceType add constraint FKE43B3D279A7346AE foreign key (OrganismTypeImpl_id) references OrganismType;
alter table DbReferenceType add constraint FKE43B3D278EC00D79 foreign key (CitationTypeImpl_id) references CitationType;
alter table DbReferenceType add constraint FKE43B3D279BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table KeywordType add constraint FK1603ACA39BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table ReferenceType_ScopeInternal add constraint FK641CFF57B6545ED5 foreign key (ReferenceTypeImpl_id) references ReferenceType;
alter table SourceDataType$Plasmid add constraint FK33F397F5460B9345 foreign key (parentid) references SourceDataType$PlasmidType;
alter table ProteinType$DomainType add constraint FK934294051F067B9D foreign key (ProteinTypeImpl_id) references ProteinType;
alter table EntryType_AccessionInternal add constraint FKF90CF80E9BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table OrganismNameType add constraint FK8F6D77779A7346AE foreign key (OrganismTypeImpl_id) references OrganismType;
alter table OrganismType$LineageType_TaxonInternal add constraint FK76EC3FB5679D0995 foreign key (LineageTypeImpl_id) references OrganismType$LineageType;
alter table SourceDataType$SpeciesOrStrainOrPlasmid add constraint FK5E4F46888849413C foreign key (species) references SourceDataType$Species;
alter table SourceDataType$SpeciesOrStrainOrPlasmid add constraint FK5E4F4688999BF18B foreign key (transposon) references SourceDataType$Transposon;
alter table SourceDataType$SpeciesOrStrainOrPlasmid add constraint FK5E4F4688CBEFD0E5 foreign key (tissue) references SourceDataType$Tissue;
alter table SourceDataType$SpeciesOrStrainOrPlasmid add constraint FK5E4F4688E292245A foreign key (plasmid) references SourceDataType$Plasmid;
alter table SourceDataType$SpeciesOrStrainOrPlasmid add constraint FK5E4F4688CAD54175 foreign key (strain) references SourceDataType$Strain;
alter table SourceDataType$SpeciesOrStrainOrPlasmid add constraint FK5E4F4688AF62DF3B foreign key (SourceDataTypeImpl_id) references SourceDataType;
alter table BpcCommentGroupKineticsType_VmaxInternal add constraint FKD960CB419F350FF1 foreign key (BpcCommentGroupKineticsTypeImpl_id) references BpcCommentGroupKineticsType;
alter table NameListType$Person add constraint FK98B7DE76460B9345 foreign key (parentid) references PersonType;
alter table OrganismType add constraint FK44B91F4CA8B548B foreign key (lineage) references OrganismType$LineageType;
alter table OrganismType add constraint FK44B91F4C9BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table InteractantType add constraint FKDCFA0AABD035C341 foreign key (CommentTypeImpl_id) references CommentType;
alter table BpcCommentGroupKineticsType_KMInternal add constraint FKEAA210D59F350FF1 foreign key (BpcCommentGroupKineticsTypeImpl_id) references BpcCommentGroupKineticsType;
alter table ProteinType$ComponentType add constraint FK1CE37E901F067B9D foreign key (ProteinTypeImpl_id) references ProteinType;
alter table LocationType add constraint FK65214AFD035C341 foreign key (CommentTypeImpl_id) references CommentType;
alter table LocationType add constraint FK65214AF188DB foreign key (end) references PositionType;
alter table LocationType add constraint FK65214AF59478A9 foreign key (begin) references PositionType;
alter table LocationType add constraint FK65214AF2C929929 foreign key (position) references PositionType;
alter table SourceDataType$Tissue add constraint FKDF13FDEA460B9345 foreign key (parentid) references SourceDataType$TissueType;
alter table CitationType add constraint FK7C9796E17C2975B foreign key (citingCitation) references CitationType;
alter table CitationType add constraint FK7C9796E1A337C7AB foreign key (editorList) references NameListType;
alter table CitationType add constraint FK7C9796E1A67F88C9 foreign key (authorList) references NameListType;
alter table SourceDataType$Transposon add constraint FKF5281410460B9345 foreign key (parentid) references SourceDataType$TransposonType;
alter table EvidenceType add constraint FK3691F2519BF4374E foreign key (EntryTypeImpl_id) references EntryType;
alter table GeneNameType add constraint FKC89D6EBA8F4A648B foreign key (GeneTypeImpl_id) references EntryType$GeneType;
alter table UniprotType$Entry add constraint FK345495FFE36D82CC foreign key (entryType) references EntryType;
alter table UniprotType$Entry add constraint FK345495FF3FDED649 foreign key (UniprotTypeImpl_id) references UniprotType;
alter table UniprotType$Entry add constraint FK345495FF5C30872 foreign key (entry) references Entry;
