# MIEX PFC-Source Book Makefile

# Depends on:
# 	* GNU Make
#		* Rubber 
#   * TeTeX || TeXlive

AUXEXT= aux log dvi toc pdf bbl blg lof bak lot out

all: build 

build:
	rubber --pdf main.tex

## Clean ##

clean:
	for i in $(AUXEXT); do rm -f *.$$i;	done
	find . -name "*.backup" | xargs rm -f
	find . -name "*~" | xargs rm -f
