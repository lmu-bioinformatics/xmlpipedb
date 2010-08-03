This is the readme for XmlPipeDb Utilities.


-- QUESTIONS --
If you have questions about this set of utilities that are not answered here,
please go to sourceforge.org and search for the xmlpipedb project. Questions
can be posted in the help forum.

-- INTRODUCTION --
XmlPipeDb Utilities (XPDU) is a set of tools for configuring, loading 
and querying databases via hibernate. It was written specifically to support
the xmlpipedb project, but could be used for anyone wishing to use Hibernate
for database loading and access.

The XPDU package includes a sample application that shows one simple way 
to use these tools. Much of the development was done with the canonical 
books.xsd and books.xml examples. However, these tools have also been
tested on VERY large biological datasets from Uniprot and Gene Ontology.

-- USAGE --
Please refer to the javadocs for information on how to use the tools. If 
you downloaded the source, use "ant javadoc" in the root of XPDU to 
build the javadocs.

Use "ant run" to build and run the sample application and get a feel for
how the tools work.



-- CREDITS --
Dr. John David Dionisio of Loyola Marymount University for getting us
started, keeping us going and directing us to the right places.

Dr. Kam Dahlquist of Loyola Marymount University for providing the 
original goal and motivation -- helping the biological community with 
DNA micro array analysis of a variety of organisms.

David Hoffman - Import
Babak Naffas - Query
Jeffrey Nicholas - Configuration