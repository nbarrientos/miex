/* 
	Stop words extrated from porter.c by Al Popescul <popescul@unagi.cis.upenn.edu>
	anyway, this list is Public Domain.
*/ 

package es.uniovi.aic.miex.filter;

import java.util.HashSet;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.ling.MapLabel;
import edu.stanford.nlp.ling.TaggedWord;

public class StopWordsDetector 
{
  public StopWordsDetector()
	{
		
		String[] stopWordsLocal = new String[]{"a", "about", "above",
		"across", "after", "afterwards", "again", "against", "all", "almost",
		"alone", "along", "already", "also", "although", "always", "am",
		"among", "amongst", "amoungst", "amount", "an", "and", "another",
		"any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are",
		"around", "as", "at", "back", "be", "became", "because", "become",
		"becomes", "becoming", "been", "before", "beforehand", "behind",
		"being", "below", "beside", "besides", "between", "beyond", "bill",
		"both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co",
		"computer", "con", "could", "couldnt", "cry", "de", "describe",
		"detail", "do", "done", "down", "due", "during", "each", "eg",
		"eight", "either", "eleven", "else", "elsewhere", "empty", "enough",
		"etc", "even", "ever", "every", "everyone", "everything",
		"everywhere", "except", "few", "fifteen", "fify", "fill", "find",
		"fire", "first", "five", "for", "former", "formerly", "forty",
		"found", "four", "from", "front", "full", "further", "get", "give",
		"go", "had", "has", "hasnt", "have", "he", "hence", "her", "here",
		"hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him",
		"himself", "his", "how", "however", "hundred", "i", "ie", "if", "in",
		"inc", "indeed", "interest", "into", "is", "it", "its", "itself",
		"keep", "last", "latter", "latterly", "least", "less", "ltd", "made",
		"many", "may", "me", "meanwhile", "might", "mill", "mine", "more",
		"moreover", "most", "mostly", "move", "much", "must", "my", "myself",
		"name", "namely", "neither", "never", "nevertheless", "next", "nine",
		"no", "nobody", "none", "noone", "nor", "not", "nothing", "now",
		"nowhere", "of", "off", "often", "on", "once", "one", "only", "onto",
		"or", "other", "others", "otherwise", "our", "ours", "ourselves",
		"out", "over", "own", "part", "per", "perhaps", "please", "put",
		"rather", "re", "same", "see", "seem", "seemed", "seeming", "seems",
		"serious", "several", "she", "should", "show", "side", "since",
		"sincere", "six", "sixty", "so", "some", "somehow", "someone",
		"something", "sometime", "sometimes", "somewhere", "still", "such",
		"system", "take", "ten", "than", "that", "the", "their", "them",
		"themselves", "then", "thence", "there", "thereafter", "thereby",
		"therefore", "therein", "thereupon", "these", "they", "thick", "thin",
		"third", "this", "those", "though", "three", "through", "throughout",
		"thru", "thus", "to", "together", "too", "top", "toward", "towards",
		"twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us",
		"very", "via", "was", "we", "well", "were", "what", "whatever",
		"when", "whence", "whenever", "where", "whereafter", "whereas",
		"whereby", "wherein", "whereupon", "wherever", "whether", "which",
		"while", "whither", "who", "whoever", "whole", "whom", "whose", "why",
		"will", "with", "within", "without", "would", "yet", "you", "your",
		"yours", "yourself", "yourselves",
		"arose", "arisen", "awoke", "awoken", "been", "bore", "borne", "born",
		"beat", "beaten", "began", "begun", "bent", "bet", "bid", "bit", "bled",
		"blew", "blown", "broke", "broken", "bred", "brought", "built", "burnt",
		"burst", "bought", "cast", "caught", "child", "chidden", "came",
		"chose", "chosen", "clung", "crept", "dealt", "dug", "did", "drew",
		"drawn", "dreamt", "drank", "drunk", "drove", "driven", "ate", "eaten",
		"fell", "fallen", "fed", "felt", "fought", "fled", "flew", "flown",
		"forbade", "forbidden", "forgot", "forgotten", "forgave", "forgiven",
		"froze", "frozen", "got", "gave", "given", "went", "gone", "grew", "grown",
		"ground", "hung", "heard", "hid", "hidden", "hit", "held", "hurt", "kept",
		"knew", "knelt", "knit", "laid", "led", "leant", "leapt", "learnt", "learned",
		"left", "lent", "let", "lay", "lit", "lost", "meant", "met", "mistook",
		"overcame", "paid", "put", "rode", "rang", "rose", "ran", "said", "saw",
		"seen", "sought", "sold", "sent","sewed", "shook", "shore", "shorn", "shone",
		"shot", "shown", "shrank", "shrunk", "shut", "sang", "sung", "sank", "sat",
		"slept", "slid", "smelt", "sowed", "spoke", "spoken", "sped", "spelt", "spent",
		"split", "spilled", "spun", "spat", "split", "spoilt", "spoiled", "spread",
		"sprang", "stood", "stole", "stolen", "stuck", "stung", "stank", "stunk",
		"strode", "stridden", "struck", "swore", "sworn", "sweat", "swept", "swelled",
		"swollen", "swam", "swum", "swung", "took", "taken", "taught", "tore",
		"torn", "told", "thought", "threw", "thrust", "trod", "trodden",
		"understood", "underwent", "undertook", "woke", "woken", "wore", "worn",
		"wove", "woven", "wept", "wet", "won", "wound", "withdrew", "wrung",
		"wrote", "written", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l",
		"m", "n", "o", "p","q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
		"unless" };

		stopWords = new HashSet<String>();

		for(String word: stopWordsLocal)
			stopWords.add(word);

	} // constructor

	private boolean is(String word)
	{
		return stopWords.contains(word.toLowerCase());
/*
		if(stopWords.contains(word.toLowerCase()))
		{
			System.out.println("Found stop word: " + word);
			return true;
		}
		else
			return false;
*/
					
	}

	public boolean isCleanDep(TypedDependency dep)
	{
		MapLabel governorLabel = (MapLabel)dep.gov().label();
		MapLabel depLabel = (MapLabel)dep.dep().label();

		String governorWord = governorLabel.toString("value");
		String depWord = depLabel.toString("value");

		return (!is(governorWord) && !is(depWord));
	}

	public boolean isCleanProp(TaggedWord wordAndProp)
  {
		return !(is(wordAndProp.word()));
	}

	HashSet<String> stopWords;

} //class

