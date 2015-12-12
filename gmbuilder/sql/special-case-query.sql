select propertytype.value from propertytype inner join dbreferencetype on
    (propertytype.dbreferencetype_property_hjid = dbreferencetype.hjid)
    where dbreferencetype.type = 'EnsemblBacteria' and dbreferencetype.id ~ 'AAN[0-9][0-9][0-9][0-9][0-9]' and propertytype.type = 'gene ID' 
    and propertytype.value ~ 'SF[0-9][0-9][0-9][0-9]' order by propertytype.value;
