import java.util.*;
import java.io.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.MapLabel;

class Demo2 {
  public static void main(String[] args) {

		DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor();
		List<List<? extends HasWord>> document = null;

		StringReader theReader = new StringReader("The first sentence. The second one.");
		document = documentPreprocessor.getSentencesFromText(theReader, null, null, -1);

    LexicalizedParser lp = new LexicalizedParser("englishPCFG.ser.gz");
    lp.setOptionFlags(new String[]{"-maxLength", "80", "-retainTmpSubcategories"});

		int numSentences = 1;
		for (List sentence : document)
		{
	    System.out.println();
			System.out.println("* DEBUG: Parseando frase " + numSentences + " de " + document.size());
			System.out.println();
			System.out.println("Frase: " + sentence.toString());
			System.out.println();

			lp.parse(sentence);
			numSentences++;

			Tree parse = (Tree) lp.getBestPCFGParse();

	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);

			ArrayList<TypedDependency> deps = (ArrayList<TypedDependency>)gs.getDeps(false);

			for(int i=0; i < deps.size(); i++)
			{
        System.out.println("Dependencia número: " + i);

				/* Governor */
				TreeGraphNode governor = deps.get(i).gov();
				MapLabel governorLabel = (MapLabel)governor.label();
				
				String governorWord = governorLabel.toString("value");
				System.out.println("Maestro: " + governorWord);

				/* Slave */
				System.out.println("Esclavo: " + deps.get(i).dep().toString());	
				System.out.println("Relación: " + deps.get(i).reln().toString());
				System.out.println();
			}		

		}

  }

}
