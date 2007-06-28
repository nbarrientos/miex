# Makefile for MIEX.
#
# Dear MIEX user and/or developer,
#  I strongly recommend using Ant instead of this Makefile. Use it
#  at your own risk!
#
# Targets:
#  - miex: Builds all .java files and stores .class into lib/miex.jar using jar
#  - doc: Creates javadoc for all packages listed in PACKAGES.
#  - install: Installs MIEX into PREFIX (you probably will need run this target as root)
#
# Nacho Barrientos Arias <nacho@criptonita.com>

# General options
CBASEDIR = /usr/lib/java
CLASSPATH = '$(CBASEDIR)/JSAP-2.1.jar:$(CBASEDIR)/sax2.jar:$(CBASEDIR)/stanford-parser-2006-06-11.jar:$(CBASEDIR)/mysql-connector-java-5.0.5-bin.jar'
PATHTOMIEX = src/es/uniovi/aic/miex
MIEXVERSION = 0.1

PACKAGES= es.uniovi.aic.miex.input es.uniovi.aic.miex.run \
					es.uniovi.aic.miex.config es.uniovi.aic.miex.datastr \
					es.uniovi.aic.miex.semantic es.uniovi.aic.miex.filter \
					es.uniovi.aic.miex.sql es.uniovi.aic.miex.tools

# Javac options
JAVAC = javac
JAVACOPTIONS = -O -d build/classes -cp $(CLASSPATH)

# Javadoc options
JAVADOC = javadoc

JAVADOCDEST = build/doc/api/
JAVADOCSRCPATH = src/
JAVADOCDOCTITLE = 'MIEX: Metadata and Information Extractor from small XML documents'
JAVADOCOPTIONS = -classpath $(CLASSPATH) -sourcepath $(JAVADOCSRCPATH) \
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

install: 
	install -D build/lib/miex.jar $(PREFIX)/usr/lib/miex/miex.jar
	install miex.sh $(PREFIX)/usr/bin/miex
	install -D -m 644 share/sql/skeleton-toread.sql $(PREFIX)/usr/share/miex/sql/skeleton.sql
	install -D -m 644 share/xml/collection.xsd $(PREFIX)/usr/share/miex/schemas/default.xsd
	install -D -m 644 etc/example-default.conf $(PREFIX)/usr/share/doc/miex/examples/conf/example.conf
	install -D -m 644 share/input/collection-well-formed-easy.xml $(PREFIX)/usr/share/doc/miex/examples/input/example.xml
	install -D -m 644 lib/grammars/englishPCFG.ser.gz $(PREFIX)/usr/share/miex/grammars/englishPCFG.ser.gz
	cp -r build/doc/api $(PREFIX)/usr/share/doc/miex

uninstall:
	rm -rf $(PREFIX)/usr/lib/miex $(PREFIX)/usr/share/miex \
	$(PREFIX)/usr/share/doc/miex $(PREFIX)/usr/bin/miex
