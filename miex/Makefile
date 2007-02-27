# Makefile for MIEX.
#
# Targets:
#  - miex: Build all .java files and store .class in lib/miex.jar
#  - doc: Create javadoc of all packages listed in PACKAGES.
#
# Nacho Barrientos Arias <nacho@criptonita.com>

# General options
CLASSPATH = 'lib/JSAP-2.1.jar:lib/sax2.jar:../../../stanford-parser/stanford-parser-2006-06-11/stanford-parser.jar'
PATHTOMIEX = src/es/uniovi/aic/miex

PACKAGES= es.uniovi.aic.miex.input es.uniovi.aic.miex.run \
					es.uniovi.aic.miex.exceptions es.uniovi.aic.miex.config \
					es.uniovi.aic.miex.datastr

# Javac options
JAVAC = javac
JAVACOPTIONS = -O -d classes -cp $(CLASSPATH)

# Javadoc options
JAVADOC = javadoc

JAVADOCDEST = javadoc/
JAVADOCSRCPATH = src/
JAVADOCDOCTITLE = 'MIEX: Metadata and Information Extractor from small XML documents'
JAVADOCOPTIONS = -classpath $(CLASSPATH) -sourcepath $(JAVADOCSRCPATH) -private \
								 -windowtitle $(JAVADOCDOCTITLE)

# Jar options
JAR = jar

# All target
all: miex doc

# MIEX build target
miex: clean
	mkdir -p classes
	$(JAVAC) $(JAVACOPTIONS) $(PATHTOMIEX)/*/*.java 
	$(JAR) -vcmf src/manifest.txt miex-`date +%Y-%m-%d`.jar -C classes es;
	mv miex-`date +%Y-%m-%d`.jar lib/miex.jar

# Javadoc build target
doc: cleandoc
	mkdir -p javadoc
	$(JAVADOC) ${JAVADOCOPTIONS} -d $(JAVADOCDEST) $(PACKAGES)

# Clean targets
clean: cleandoc cleanbin

cleandoc:
	rm -rf javadoc

cleanbin:
	rm -rf classes lib/miex.jar
