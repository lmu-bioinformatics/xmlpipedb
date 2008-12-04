Last updated:  07 / 26 / 2006

What is GenMAPP Builder?
GenMAPP Builder (GMB) is an application for creating GenMAPP database files (.gdb files) for any species you like! GenMAPP (http://genmapp.org/) is an application for studying and analyzing DNA micro arrays. Traditionally GenMAPP's databases have come from GenMAPP and have concentrated on furry creatures. With GMB, you can create fully functional GMB files for any organism you like, such as the wonderful ecoli bacteria.  

Features AKA Alright, so how does it work?
GMB has 4 main features:
- Import Uniprot XML files
- Import GeneOntology XML files
- Query the imported date (just incase your a bit curious or a bit nerdy)
- Export to a GMB file

So, that doesn't tell you how it works, but it does say specifically what it does.

Requirements:
- Relational Database of your choice (We've only tested with PostgreSQL 8.1 -- a damn fine DB)
    * This is where all that data that you're importing goes.
- Windows 2000, XP, or Vista (We've only tested on Windows XP, AFAIK)
    * note: this is needed since the GDB file is really a Microsoft Access MDB file in disguise. No, we don't like it either, but that's how GenMAPP works, so (for now) we are stuck with it.
    * If you don't need to export, you can run under MAC or Unix. It is only the export that is tied to the availability of ODBC.    


Installation:
-- clean install --
Simply extract all files into a folder (e.g. gmbuilder).
WARNING: DO NOT use folders with special charactes in them, e.g. "!temp" will not work. (note: this warning is left over from another project, and may not be applicable, but just do it to be on the safe side).

-- upgrade --
Delete all previous file / versions, then follow the instructions for a "clean install".


Running GMB:
Windows: 
double-click the batch file gmbuilder.bat


Unix/Linux/OSX (aka MAC):
double-click the gmbuilder.jar located in the lib folder (or run it from the command line, if your that type)



Internationalization:
Although internationalization was thought of when writing this program, it has not yet been internationalized. If you would like to internationalize it, please join the project and have at it! 

Known Issues:
- None at this moment, but please let us know if any come up.


Copyright Notice:
(c) Copyright 2006 Loyola Marymount University Computer Science Deptartment.

License:
This program is covered under the Lesser GNU Public License. For details on this license, please refer to: http://www.gnu.org/licenses/lgpl.html

Credits:
We would like to thank Loyola Marymount University and both the Biology and Computer Science departments for making this program possible.

Numerous students have put their blood sweat and tears into bringing this to you. So, if this program is helpful, be kind to a student today. If not, then, well, you know what to do.

Free/OpenSource Tools used in creating this application include (but are not limited to): Eclipse, PostgreSQL, 7zip, Java, PSPad, Firefox, and many others. No tools were harmed in the creation of this application. Hug an open source contributer today!

This application is brought to you by the letter Omega.


Version History:
1.0
First release of GMB to the public.


Documentation & Support:
The first-level of support is YOU. Read the User's and Developers Manual and this readme. After that, if something doesn't work or you just can't figure it out, second-level support can be obtained by going to http://sourceforge.net/projects/xmlpipedb/ and clicking on "support requests". We will try to help you as soon as possible. In the meantime (or even before submitting your request) check to see if someone else had the same problem -- maybe all the answers you need are already there! 
