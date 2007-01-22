import java.util.*;
import java.io.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.ling.HasWord;

class Demo {
  public static void main(String[] args) {

		// Probar a pillar un stream con reader y a procesarlo, en vez de un fichero

		DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor();
		List<List<? extends HasWord>> document = null;
		String[] filename = { "big-example.txt" };
		try
		{
//				StringReader theReader = new StringReader("The first sentence. The second one.");
			document = documentPreprocessor.getSentencesFromText(filename[0], null, null, -1);
//				document = documentPreprocessor.getSentencesFromText(theReader, null, null, -1);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		System.out.println("* DEBUG: Parseando fichero: " + filename[0] + " con " + document.size() + " frases.");
		System.out.println();

    LexicalizedParser lp = new LexicalizedParser("englishPCFG.ser.gz");
    lp.setOptionFlags(new String[]{"-maxLength", "80", "-retainTmpSubcategories"});

		int numSentences = 1;
		for (List sentence : document)
		{
	    System.out.println();
			System.out.println("* DEBUG: Parseando frase " + numSentences + " de " + document.size());
			System.out.println();
			lp.parse(sentence);
			numSentences++;

			Tree parse = (Tree) lp.getBestPCFGParse();
		  //parse.pennPrint();
	    //System.out.println();

	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
	    Collection tdl = gs.typedDependenciesCollapsed();
  	  System.out.println(tdl);

			printTitle("oneline");
			TreePrint tp = new TreePrint("oneline");
			tp.printTree(parse);

			printTitle("penn");
	//		tp = new TreePrint("penn", "xml", tlp);
			tp = new TreePrint("penn");
			tp.printTree(parse);

	    printTitle("latexTree");
	    tp = new TreePrint("latexTree");
	    tp.printTree(parse);

	    printTitle("words");
	    tp = new TreePrint("words");
	    tp.printTree(parse);

	    printTitle("wordsAndTags");
	    tp = new TreePrint("wordsAndTags");
	    tp.printTree(parse);

	    printTitle("rootSymbolOnly");
	    tp = new TreePrint("rootSymbolOnly");
	    tp.printTree(parse);

	    printTitle("dependencies");
	    tp = new TreePrint("dependencies");
	    tp.printTree(parse);

	    printTitle("typedDependencies");
	    tp = new TreePrint("typedDependencies");
	    tp.printTree(parse);

	    printTitle("typedDependenciesCollapsed");
	    tp = new TreePrint("typedDependenciesCollapsed");
	    tp.printTree(parse);

		}

  }

	public static void printTitle(String name)
	{
		System.out.println();
		System.out.println("* DEBUG: Imprimiendo salida en formato " + name);
		System.out.println();
	}

}
