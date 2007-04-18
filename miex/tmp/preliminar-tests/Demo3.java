import java.util.*;
import java.io.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.MapLabel;
import edu.stanford.nlp.ling.*;

class ExtendedTaggedWord
extends TaggedWord
{

public ExtendedTaggedWord(String word, String tag, String _zone)
{
  super(word,tag);
  zone = new String(_zone);
}

public String zone()
{
  return zone;
}

public String toString()
{
	String old = super.toString();
	
	old = old + "/" + zone;

	return old;
}

private String zone;

}

class Demo3 {

  public static List myTaggedYield(Tree t, List ty, Label zone) {

		Tree[] kids = t.children();
/*
		if(t.label().toString().equals("S"))
		{
			for (int i = 0; i < kids.length; i++)
				System.out.println("S hijos label: " + kids[i].label());
		}
*/

    // this inlines the content of isPreTerminal()
    if (kids.length == 1 && kids[0].isLeaf()) 
		{
			System.out.println("Adding word " + kids[0].label() + " in zone: " + zone);

			ExtendedTaggedWord stw = new ExtendedTaggedWord(kids[0].label().value(), t.label().value(), zone.toString());
			
			System.out.println(stw.toString());

			ty.add(stw);
		
    } 
		else 
		{
      for (int i = 0; i < kids.length; i++) 
			{
				if(kids[i].isPhrasal() && t.label().toString().equals("S"))
				{
						zone = kids[i].label();
				} 
        myTaggedYield(kids[i],ty,zone);
      }
    }
    return ty;
  }

	public static Sentence myTaggedYield(Tree t)
	{
		return (Sentence) myTaggedYield(t,new Sentence(),null);
	}

	public static void printAsIs(Tree t)
	{
		Tree[] kids = t.children();

		System.out.println("Nivel: " + t.depth() + " " + t.label());

		for (int i = 0; i < kids.length; i++)
		{
			printAsIs(kids[i]);
		}
	
	}

  public static void main(String[] args) {

		DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor();
		List<List<? extends HasWord>> document = null;

//		StringReader theReader = new StringReader("The Debian project is an association of individuals who have made common cause to create a free operating system.");
//		StringReader theReader = new StringReader("The dog is over the table");

		StringReader theReader = new StringReader("Debian project");
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

//			List sent = parse.getLeaves();

//			System.out.println(parse);

//			parse.pennPrint();

			parse.indentedListPrint();
	
			System.out.println(myTaggedYield(parse));
//			printAsIs(parse);

//			for(Iterator itr = parse.iterator(); itr.hasNext(); )
//			{
//				System.out.println(itr.next());
//			}
	
			
	

/*
	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);

			ArrayList<TypedDependency> deps = (ArrayList<TypedDependency>)gs.getDeps(false);

			for(int i=0; i < deps.size(); i++)
			{
        System.out.println("Dependencia número: " + i);

				TreeGraphNode governor = deps.get(i).gov();
				MapLabel governorLabel = (MapLabel)governor.label();
				
				String governorWord = governorLabel.toString("value");
				System.out.println("Maestro: " + governorWord);

				System.out.println("Esclavo: " + deps.get(i).dep().toString());	
				System.out.println("Relación: " + deps.get(i).reln().toString());
				System.out.println();
			}		
*/
		}

  }

}
