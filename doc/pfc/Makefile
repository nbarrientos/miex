# MIEX PFC Book Makefile

# Depends on:
# 	* GNU Make
#		* Dia
#		* Rubber 
#   * TeTeX || TeXlive

IMAGES= bdd-e-r evolution
AUXEXT= aux log dvi toc pdf bbl blg lof bak lot out

all: build 

build: dirs images	
#	umbrello --export png --directory images/uml/ uml/swaml.xmi 2> /dev/null
#	umbrello --export png --directory images/uml/ uml/swaml-clases.xmi 2> /dev/null
	rubber --pdf miex.tex

## Directories ##

dirs: dirs-stamp

dirs-stamp:
	mkdir images/build
	touch $@

## Images ##

images: images-stamp

images-stamp: dirs-stamp $(IMAGES:%=images/build/%.png)
	touch $@

images/build/%.png: images/diagrams/%.dia
	dia -t png $? -e $@

## Clean ##

clean:
	for i in $(AUXEXT); do rm -f *.$$i;	done
	find . -name "*.backup" | xargs rm -f
	find . -name "*~" | xargs rm -f
	rm -rf images/build dirs-stamp images-stamp
