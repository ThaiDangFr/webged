#!/bin/ksh

LIBDIR=../WebGedWeb/WebRoot/WEB-INF/lib/
TLDDIR=../WebGedWeb/WebRoot/WEB-INF/tld/

#java -classpath ${LIBDIR}/tlddoc-1.3.jar:${LIBDIR}/xercesImpl.jar:${LIBDIR}/xalan.jar com.sun.tlddoc.TLDDoc -d TLD  ${TLDDIR}/dangconsulting.tld
java -Djava.ext.dirs=${LIBDIR} com.sun.tlddoc.TLDDoc -d TLD  ${TLDDIR}/dangconsulting.tld
