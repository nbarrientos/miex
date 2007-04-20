# MIEX PFC Makefile
# based on SWAML PFC Makefile

# Depends on:
# 	* Make
#		* Dia
#		* Rubber 
#   * TeTeX || TeXlive

#DESTDIR =

PFC = miex

IMAGES= images/build/bdd-e-r.png images/build/evolution.png

all: $(PFC).pdf

$(NAME).pdf: dirs $(IMAGES)	
#	umbrello --export png --directory images/uml/ uml/swaml.xmi 2> /dev/null
#	umbrello --export png --directory images/uml/ uml/swaml-clases.xmi 2> /dev/null
	rubber --pdf $(PFC).tex

dirs:
	mkdir images/build

images/build/%.png:  images/diagrams/%.dia
	dia -t png $? -e $@

#src:
#	python2.4 python2text.py ../../swaml/trunk/*.py #output dir?

clean:
	rm -f *.aux *.log *.dvi *.toc *.pdf *.bbl *.blg *.lof *.bak *.lot *.out
	rm -f `find . -name "*.backup"`
	rm -f `find . -name "*~"`
	rm -rf images/build
