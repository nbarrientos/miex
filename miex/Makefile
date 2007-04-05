# Makefile for MIEX.
#
# Dear MIEX user,
#  I strongly recommend using Ant instead of this Makefile. Use it
#  at your own risk!
#
# Targets:
#  - miex: Build all .java files and store .class in lib/miex.jar
#  - doc: Create javadoc of all packages listed in PACKAGES.
#
# Nacho Barrientos Arias <nacho@criptonita.com>

# General options
CLASSPATH = 'lib/JSAP-2.1.jar:lib/sax2.jar:lib/stanford-parser-2006-06-11.jar:lib/mysql-connector-java-5.0.5-bin.jar'
PATHTOMIEX = src/es/uniovi/aic/miex
MIEXVERSION = 0.1

PACKAGES= es.uniovi.aic.miex.input es.uniovi.aic.miex.run \
					es.uniovi.aic.miex.config es.uniovi.aic.miex.datastr \
					es.uniovi.aic.miex.semantic es.uniovi.aic.miex.filter \
					es.uniovi.aic.miex.sql

# Javac options
JAVAC = javac
JAVACOPTIONS = -O -d build/classes -cp $(CLASSPATH)

# Javadoc options
JAVADOC = javadoc

JAVADOCDEST = build/doc/api/
JAVADOCSRCPATH = src/
JAVADOCDOCTITLE = 'MIEX: Metadata and Information Extractor from small XML documents'
JAVADOCOPTIONS = -classpath $(CLASSPATH) -sourcepath $(JAVADOCSRCPATH) -private \
								 -windowtitle "MIEX v$(MIEXVERSION) API Specification" \
								 -doctitle "MIEX v$(MIEXVERSION) API Specification" \
								 -link "http://java.sun.com/javase/6/docs/api"

# Jar options
JAR = jar

############################################
## DO NOT CHANGE ANYTHING BELOW THIS LINE ##
############################################

# All target
all: miex doc

# MIEX build target
miex: clean
	mkdir -p build/classes build/doc/api build/lib
	$(JAVAC) $(JAVACOPTIONS) $(PATHTOMIEX)/*/*.java 
	$(JAR) -vcmf src/manifest.txt miex-`date +%Y-%m-%d`.jar -C build/classes es;
	mv miex-`date +%Y-%m-%d`.jar build/lib/miex.jar

# Javadoc build target
doc: cleandoc
	mkdir -p build/doc/api/
	$(JAVADOC) ${JAVADOCOPTIONS} -d $(JAVADOCDEST) $(PACKAGES)

# Clean targets
clean: cleandoc cleanbin
	rm -rf build

cleandoc:
	rm -rf build/doc

cleanbin:
	rm -rf build/classes build/lib
