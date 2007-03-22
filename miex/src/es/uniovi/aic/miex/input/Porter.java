/* author:   Fotis Lazarinis (actually I translated from C to Java)
   date:     June 1997
   address:  Psilovraxou 12, Agrinio, 30100

   comments: Compile it, import the Porter class into you program and create an instance.
	     Then use the stripAffixes method of this method which takes a String as 
             input and returns the stem of this String again as a String.

*/ 

package es.uniovi.aic.miex.input;

import java.io.*;
import java.util.HashSet;

class NewString {
  public String str;

  NewString() {
     str = "";
  }
}

public class Porter {

  private String Clean( String str ) {
     int last = str.length();
     
     Character ch = new Character( str.charAt(0) );
     String temp = "";

     for ( int i=0; i < last; i++ ) {
         if ( ch.isLetterOrDigit( str.charAt(i) ) )
            temp += str.charAt(i);
     }
   
     return temp;
  } //clean
 
  private boolean hasSuffix( String word, String suffix, NewString stem ) {

     String tmp = "";

     if ( word.length() <= suffix.length() )
        return false;
     if (suffix.length() > 1) 
        if ( word.charAt( word.length()-2 ) != suffix.charAt( suffix.length()-2 ) )
           return false;
  
     stem.str = "";

     for ( int i=0; i<word.length()-suffix.length(); i++ )
         stem.str += word.charAt( i );
     tmp = stem.str;

     for ( int i=0; i<suffix.length(); i++ )
         tmp += suffix.charAt( i );

     if ( tmp.compareTo( word ) == 0 )
        return true;
     else
        return false;
  }

  private boolean vowel( char ch, char prev ) {
     switch ( ch ) {
        case 'a': case 'e': case 'i': case 'o': case 'u': 
          return true;
        case 'y': {

          switch ( prev ) {
            case 'a': case 'e': case 'i': case 'o': case 'u': 
              return false;

            default: 
              return true;
          }
        }
        
        default : 
          return false;
     }
  }

  private int measure( String stem ) {
    
    int i=0, count = 0;
    int length = stem.length();

    while ( i < length ) {
       for ( ; i < length ; i++ ) {
           if ( i > 0 ) {
              if ( vowel(stem.charAt(i),stem.charAt(i-1)) )
                 break;
           }
           else {  
              if ( vowel(stem.charAt(i),'a') )
                break; 
           }
       }

       for ( i++ ; i < length ; i++ ) {
           if ( i > 0 ) {
              if ( !vowel(stem.charAt(i),stem.charAt(i-1)) )
                  break;
              }
           else {  
              if ( !vowel(stem.charAt(i),'?') )
                 break;
           }
       } 
      if ( i < length ) {
         count++;
         i++;
      }
    } //while
    
    return(count);
  }

  private boolean containsVowel( String word ) {

     for (int i=0 ; i < word.length(); i++ )
         if ( i > 0 ) {
            if ( vowel(word.charAt(i),word.charAt(i-1)) )
               return true;
         }
         else {  
            if ( vowel(word.charAt(0),'a') )
               return true;
         }
        
     return false;
  }

  private boolean cvc( String str ) {
     int length=str.length();

     if ( length < 3 )
        return false;
    
     if ( (!vowel(str.charAt(length-1),str.charAt(length-2)) )
        && (str.charAt(length-1) != 'w') && (str.charAt(length-1) != 'x') && (str.charAt(length-1) != 'y')
        && (vowel(str.charAt(length-2),str.charAt(length-3))) ) {

        if (length == 3) {
           if (!vowel(str.charAt(0),'?')) 
              return true;
           else
              return false;
        }
        else {
           if (!vowel(str.charAt(length-3),str.charAt(length-4)) ) 
              return true; 
           else
              return false;
        } 
     }   
  
     return false;
  }

  private String step1( String str ) {
 
     NewString stem = new NewString();

     if ( str.charAt( str.length()-1 ) == 's' ) {
        if ( (hasSuffix( str, "sses", stem )) || (hasSuffix( str, "ies", stem)) ){
           String tmp = "";
           for (int i=0; i<str.length()-2; i++)
               tmp += str.charAt(i);
           str = tmp;
        }
        else {
           if ( ( str.length() == 1 ) && ( str.charAt(str.length()-1) == 's' ) ) {
              str = "";
              return str;
           }
           if ( str.charAt( str.length()-2 ) != 's' ) {
              String tmp = "";
              for (int i=0; i<str.length()-1; i++)
                  tmp += str.charAt(i);
              str = tmp;
           }
        }  
     }

     if ( hasSuffix( str,"eed",stem ) ) {
           if ( measure( stem.str ) > 0 ) {
              String tmp = "";
              for (int i=0; i<str.length()-1; i++)
                  tmp += str.charAt( i );
              str = tmp;
           }
     }
     else {  
        if (  (hasSuffix( str,"ed",stem )) || (hasSuffix( str,"ing",stem )) ) { 
           if (containsVowel( stem.str ))  {

              String tmp = "";
              for ( int i = 0; i < stem.str.length(); i++)
                  tmp += str.charAt( i );
              str = tmp;
              if ( str.length() == 1 )
                 return str;

              if ( ( hasSuffix( str,"at",stem) ) || ( hasSuffix( str,"bl",stem ) ) || ( hasSuffix( str,"iz",stem) ) ) {
                 str += "e";
           
              }
              else {   
                 int length = str.length(); 
                 if ( (str.charAt(length-1) == str.charAt(length-2)) 
                    && (str.charAt(length-1) != 'l') && (str.charAt(length-1) != 's') && (str.charAt(length-1) != 'z') ) {
                     
                    tmp = "";
                    for (int i=0; i<str.length()-1; i++)
                        tmp += str.charAt(i);
                    str = tmp;
                 }
                 else
                    if ( measure( str ) == 1 ) {
                       if ( cvc(str) ) 
                          str += "e";
                    }
              }
           }
        }
     }

     if ( hasSuffix(str,"y",stem) ) 
        if ( containsVowel( stem.str ) ) {
           String tmp = "";
           for (int i=0; i<str.length()-1; i++ )
               tmp += str.charAt(i);
           str = tmp + "i";
        }
     return str;  
  }

  private String step2( String str ) {

     String[][] suffixes = { { "ational", "ate" },
                                    { "tional",  "tion" },
                                    { "enci",    "ence" },
                                    { "anci",    "ance" },
                                    { "izer",    "ize" },
                                    { "iser",    "ize" },
                                    { "abli",    "able" },
                                    { "alli",    "al" },
                                    { "entli",   "ent" },
                                    { "eli",     "e" },
                                    { "ousli",   "ous" },
                                    { "ization", "ize" },
                                    { "isation", "ize" },
                                    { "ation",   "ate" },
                                    { "ator",    "ate" },
                                    { "alism",   "al" },
                                    { "iveness", "ive" },
                                    { "fulness", "ful" },
                                    { "ousness", "ous" },
                                    { "aliti",   "al" },
                                    { "iviti",   "ive" },
                                    { "biliti",  "ble" }};
     NewString stem = new NewString();

     
     for ( int index = 0 ; index < suffixes.length; index++ ) {
         if ( hasSuffix ( str, suffixes[index][0], stem ) ) {
            if ( measure ( stem.str ) > 0 ) {
               str = stem.str + suffixes[index][1];
               return str;
            }
         }
     }

     return str;
  }

  private String step3( String str ) {

        String[][] suffixes = { { "icate", "ic" },
                                       { "ative", "" },
                                       { "alize", "al" },
                                       { "alise", "al" },
                                       { "iciti", "ic" },
                                       { "ical",  "ic" },
                                       { "ful",   "" },
                                       { "ness",  "" }};
        NewString stem = new NewString();

        for ( int index = 0 ; index<suffixes.length; index++ ) {
            if ( hasSuffix ( str, suffixes[index][0], stem ))
               if ( measure ( stem.str ) > 0 ) {
                  str = stem.str + suffixes[index][1];
                  return str;
               }
        }
        return str;
  }

  private String step4( String str ) {
        
     String[] suffixes = { "al", "ance", "ence", "er", "ic", "able", "ible", "ant", "ement", "ment", "ent", "sion", "tion",
                           "ou", "ism", "ate", "iti", "ous", "ive", "ize", "ise"};
     
     NewString stem = new NewString();
        
     for ( int index = 0 ; index<suffixes.length; index++ ) {
         if ( hasSuffix ( str, suffixes[index], stem ) ) {
           
            if ( measure ( stem.str ) > 1 ) {
               str = stem.str;
               return str;
            }
         }
     }
     return str;
  }

  private String step5( String str ) {

     if ( str.charAt(str.length()-1) == 'e' ) { 
        if ( measure(str) > 1 ) {/* measure(str)==measure(stem) if ends in vowel */
           String tmp = "";
           for ( int i=0; i<str.length()-1; i++ ) 
               tmp += str.charAt( i );
           str = tmp;
        }
        else
           if ( measure(str) == 1 ) {
              String stem = "";
              for ( int i=0; i<str.length()-1; i++ ) 
                  stem += str.charAt( i );

              if ( !cvc(stem) )
                 str = stem;
           }
     }
     
     if ( str.length() == 1 )
        return str;
     if ( (str.charAt(str.length()-1) == 'l') && (str.charAt(str.length()-2) == 'l') && (measure(str) > 1) )
        if ( measure(str) > 1 ) {/* measure(str)==measure(stem) if ends in vowel */
           String tmp = "";
           for ( int i=0; i<str.length()-1; i++ ) 
               tmp += str.charAt( i );
           str = tmp;
        } 
     return str;
  }

  private String stripPrefixes ( String str) {

     String[] prefixes = { "kilo", "micro", "milli", "intra", "ultra", "mega", "nano", "pico", "pseudo"};

     int last = prefixes.length;
     for ( int i=0 ; i<last; i++ ) {
         if ( str.startsWith( prefixes[i] ) ) {
            String temp = "";
            for ( int j=0 ; j< str.length()-prefixes[i].length(); j++ )
                temp += str.charAt( j+prefixes[i].length() );
            return temp;
         }
     }
     
     return str;
  }


  private String stripSuffixes( String str ) {

     str = step1( str );
     if ( str.length() >= 1 )
        str = step2( str );
     if ( str.length() >= 1 )
        str = step3( str );
     if ( str.length() >= 1 )
        str = step4( str );
     if ( str.length() >= 1 )
        str = step5( str );
 
     return str; 
  }


  public String stripAffixes( String str ) {

    str = str.toLowerCase();
    str = Clean(str);
  
    if (( str != "" ) && (str.length() > 2)) {
       str = stripPrefixes(str);

       if (str != "" ) 
          str = stripSuffixes(str);

    }   

    return str;
    } //stripAffixes


  public Porter()
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

	for(String word: stopsWordsLocal)
	{
		stopWords.add(word);
	}	

	} // constructor

	HashSet<String> stopWords;

} //class

