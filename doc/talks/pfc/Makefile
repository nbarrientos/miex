ALLTEX= miex.tex
AUXEXT= aux log dvi toc pdf bbl blg lof bak lot out nav snm

PDF= miex.pdf
TEX= miex.tex

all: $(PDF)

miex.pdf: $(ALLTEX)
	rubber --pdf $(TEX)

clean:
	for i in $(AUXEXT); do rm -f *.$$i; done
