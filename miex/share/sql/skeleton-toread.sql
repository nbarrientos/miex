--[ Title ][ Base MySQL 4.1 database for MIEX ]--
--[ Desc ][ Creates a clean database from scratch ]---
--[ Author ][ Nacho Barrientos Arias <chipi@criptonita.com> ]--
--[ Date ][ March 2007 ]--
--[ License ][ GPL (see LICENSE) ]--

---
---> Here we go!, cleaning old DB...
---

DROP TABLE IF EXISTS wordwordpropdoc;
DROP TABLE IF EXISTS wordpropdoc;
DROP TABLE IF EXISTS doccat;

DROP TABLE IF EXISTS property;
DROP TABLE IF EXISTS document;

DROP TABLE IF EXISTS word;
DROP TABLE IF EXISTS collection;
DROP TABLE IF EXISTS doctype;
DROP TABLE IF EXISTS proptype;
DROP TABLE IF EXISTS category;

---
---> Creating 'single' tables
---

--- word = (@word_id, string)

CREATE TABLE word( word_id int NOT NULL AUTO_INCREMENT, string varchar(20) NOT NULL, PRIMARY KEY (word_id) ) TYPE = INNODB;

--- collection = (@collection_id, name)

CREATE TABLE collection( collection_id int NOT NULL AUTO_INCREMENT, name varchar(70) NOT NULL, PRIMARY KEY (collection_id) ) TYPE = INNODB;

--- doctype = (@doctype_id, string)

CREATE TABLE doctype( doctype_id int NOT NULL, string varchar(10) NOT NULL, PRIMARY KEY (doctype_id) ) TYPE = INNODB;

--- proptype = (@proptype_id, string)

CREATE TABLE proptype( proptype_id int NOT NULL, string varchar(30) NOT NULL, PRIMARY KEY (proptype_id) )TYPE = INNODB;

--- category = (@category_id, string)

CREATE TABLE category( category_id int NOT NULL AUTO_INCREMENT, string varchar(20) NOT NULL, PRIMARY KEY (category_id) ) TYPE = INNODB;

---
---> Relationship tables (1:M)
---

--- document = (@document_id, @_collection_id_, title, doctype)

CREATE TABLE document( document_id int NOT NULL, collection_id int NOT NULL, title varchar(150), doctype_id int NOT NULL, PRIMARY KEY (document_id,collection_id), FOREIGN KEY (collection_id) REFERENCES collection (collection_id), FOREIGN KEY (doctype_id) REFERENCES doctype (doctype_id) ) TYPE = INNODB;

---- property = (@property_id, string, description, _type_id_)

CREATE TABLE property( property_id int NOT NULL AUTO_INCREMENT, string varchar(15) NOT NULL, type_id int NOT NULL, description tinytext, PRIMARY KEY (property_id), FOREIGN KEY (type_id) REFERENCES proptype (proptype_id) ) TYPE = INNODB;

---
---> Relationship tables (N:M)
---

--- doccat = (@_category_id_, @_document_id, @collection_id_)

CREATE TABLE doccat( cat_id int NOT NULL, doc_id int NOT NULL, col_id int NOT NULL, PRIMARY KEY (cat_id,doc_id,col_id), FOREIGN KEY (cat_id) REFERENCES category (category_id), FOREIGN KEY (doc_id,col_id) REFERENCES document (document_id,collection_id) ) TYPE = INNODB;

--- wordpropdoc = (@_word_id_, @_property_id_, @_document_id, @collection_id_, times)

CREATE TABLE wordpropdoc( word_id int NOT NULL, prop_id int NOT NULL, doc_id int NOT NULL, col_id int NOT NULL, times int NOT NULL DEFAULT '1', fromTitle bool NOT NULL DEFAULT 'false', normalized bool NOT NULL DEFAULT 'false', PRIMARY KEY (word_id,prop_id,doc_id,col_id,fromTitle), FOREIGN KEY (word_id) REFERENCES word (word_id), FOREIGN KEY (prop_id) REFERENCES property (property_id), FOREIGN KEY (doc_id,col_id) REFERENCES document (document_id,collection_id) ) TYPE = INNODB;

--- wordwordpropdoc = (@_masterWord_id_, @_slaveWord_id_, @_property_id_, @_document_id, @collection_id_, times)

CREATE TABLE wordwordpropdoc( masterWord_id int NOT NULL, slaveWord_id int NOT NULL, prop_id int NOT NULL, doc_id int NOT NULL, col_id int NOT NULL, times int NOT NULL DEFAULT '1', fromTitle bool NOT NULL DEFAULT 'false', normalized bool NOT NULL DEFAULT 'false', PRIMARY KEY (masterWord_id,slaveWord_id,prop_id,doc_id,col_id,fromTitle), FOREIGN KEY (masterWord_id) REFERENCES word (word_id), FOREIGN KEY (slaveWord_id) REFERENCES word (word_id), FOREIGN KEY (prop_id) REFERENCES property (property_id),FOREIGN KEY (doc_id,col_id) REFERENCES document (document_id,collection_id) ) TYPE = INNODB;

---
---> Inserting static data
---

--- Document types

INSERT INTO doctype (doctype_id,string) VALUES ('1',"DOCTRAIN");
INSERT INTO doctype (doctype_id,string) VALUES ('2',"DOCTEST");

--- Properties types
---		Grammatical category (e.g.: NNP - Project)
---		Word relationship (e.g.: nn(Project,Debian)

INSERT INTO proptype (proptype_id,string) VALUES ('1',"GRAMMATICAL");
INSERT INTO proptype (proptype_id,string) VALUES ('2',"RELATIONSHIP");

--- Grammatical categories, extracted from:
--- http://bulba.sdsu.edu/jeanette/thesis/PennTags.html

INSERT INTO property (type_id,string,description) VALUES ('1','ADJP','Adjective Phrase');  
INSERT INTO property (type_id,string,description) VALUES ('1','ADVP','Adverb Phrase'); 
INSERT INTO property (type_id,string,description) VALUES ('1','CC','Coordinating conjunction');
INSERT INTO property (type_id,string,description) VALUES ('1','CD','Cardinal number');  
INSERT INTO property (type_id,string,description) VALUES ('1','CONJP','Conjunction Phrase');  
INSERT INTO property (type_id,string,description) VALUES ('1','DT','Determiner');  
INSERT INTO property (type_id,string,description) VALUES ('1','EX','Existential there');  
INSERT INTO property (type_id,string,description) VALUES ('1','FRAG','Fragment'); 
INSERT INTO property (type_id,string,description) VALUES ('1','FW','Foreign word');  
INSERT INTO property (type_id,string,description) VALUES ('1','IN','Preposition or subordinating conjunction');  
INSERT INTO property (type_id,string,description) VALUES ('1','INTJ','Interjection');  
INSERT INTO property (type_id,string,description) VALUES ('1','JJ','Adjective');  
INSERT INTO property (type_id,string,description) VALUES ('1','JJR','Adjective, comparative');  
INSERT INTO property (type_id,string,description) VALUES ('1','JJS','Adjective, superlative');  
INSERT INTO property (type_id,string,description) VALUES ('1','LS','List item marker');  
INSERT INTO property (type_id,string,description) VALUES ('1','LST','List marker');  
INSERT INTO property (type_id,string,description) VALUES ('1','MD','Modal');  
INSERT INTO property (type_id,string,description) VALUES ('1','NAC','Not a Constituent. used to show the scope of certain prenominal modifiers within an NP.');  
INSERT INTO property (type_id,string,description) VALUES ('1','NN','Noun, singular or mass');
INSERT INTO property (type_id,string,description) VALUES ('1','NNS','Noun, plural');  
INSERT INTO property (type_id,string,description) VALUES ('1','NNP','Proper noun, singular');
INSERT INTO property (type_id,string,description) VALUES ('1','NNPS','Proper noun, plural');  
INSERT INTO property (type_id,string,description) VALUES ('1','NP','Noun Phrase');  
INSERT INTO property (type_id,string,description) VALUES ('1','NX','Used within certain complex NPs to mark the head of the NP. Corresponds very roughly to N-bar level but used quite differently');  
INSERT INTO property (type_id,string,description) VALUES ('1','PDT','Predeterminer');  
INSERT INTO property (type_id,string,description) VALUES ('1','POS','Possessive ending');  
INSERT INTO property (type_id,string,description) VALUES ('1','PP','Prepositional Phrase');  
INSERT INTO property (type_id,string,description) VALUES ('1','PRN','Parenthetical');  
INSERT INTO property (type_id,string,description) VALUES ('1','PRP','Personal pronoun');  
INSERT INTO property (type_id,string,description) VALUES ('1','PRP$','Possessive pronoun');
INSERT INTO property (type_id,string,description) VALUES ('1','PRP-S','Prolog version of PRP$');
INSERT INTO property (type_id,string,description) VALUES ('1','PRT','Particle. Category for words that should be tagged RP.');   
INSERT INTO property (type_id,string,description) VALUES ('1','QP','Quantifier Phrase');  
INSERT INTO property (type_id,string,description) VALUES ('1','RB','Adverb');  
INSERT INTO property (type_id,string,description) VALUES ('1','RBR','Adverb, comparative');  
INSERT INTO property (type_id,string,description) VALUES ('1','RBS','Adverb, superlative');  
INSERT INTO property (type_id,string,description) VALUES ('1','RP','Particle');  
INSERT INTO property (type_id,string,description) VALUES ('1','RRC','Reduced Relative Clause');  
INSERT INTO property (type_id,string,description) VALUES ('1','S','simple declarative clause, i.e. one that is not introduced by a (possible empty) subordinating conjunction or a wh-word and that does not exhibit subject-verb inversion');  
INSERT INTO property (type_id,string,description) VALUES ('1','SBAR','Clause introduced by a (possibly empty) subordinating conjunction.');  
INSERT INTO property (type_id,string,description) VALUES ('1','SBARQ','Direct question introduced by a wh-word or a wh-phrase. Indirect questions and relative clauses should be bracketed as SBAR, not SBARQ.');  
INSERT INTO property (type_id,string,description) VALUES ('1','SINV','Inverted declarative sentence, i.e. one in which the subject follows the tensed verb or modal');  
INSERT INTO property (type_id,string,description) VALUES ('1','SQ','Inverted yes/no question, or main clause of a wh-question, following the wh-phrase in SBARQ.');  
INSERT INTO property (type_id,string,description) VALUES ('1','SYM','Symbol');  
INSERT INTO property (type_id,string,description) VALUES ('1','TO','to');  
INSERT INTO property (type_id,string,description) VALUES ('1','UCP','Unlike Coordinated Phrase.');   
INSERT INTO property (type_id,string,description) VALUES ('1','UH','Interjection');  
INSERT INTO property (type_id,string,description) VALUES ('1','VB','Verb, base form');  
INSERT INTO property (type_id,string,description) VALUES ('1','VBD','Verb, past tense');  
INSERT INTO property (type_id,string,description) VALUES ('1','VBG','Verb, gerund or present participle');  
INSERT INTO property (type_id,string,description) VALUES ('1','VBN','Verb, past participle');  
INSERT INTO property (type_id,string,description) VALUES ('1','VBP','Verb, non-3rd person singular present'); 
INSERT INTO property (type_id,string,description) VALUES ('1','VBZ','Verb, 3rd person singular present');  
INSERT INTO property (type_id,string,description) VALUES ('1','VP','Vereb Phrase');  
INSERT INTO property (type_id,string,description) VALUES ('1','WDT','Wh-determiner');  
INSERT INTO property (type_id,string,description) VALUES ('1','WHADJP','Wh-adjective Phrase. Adjectival phrase containing a wh-adverb, as in how hot.');  
INSERT INTO property (type_id,string,description) VALUES ('1','WHADVP','Wh-adverb Phrase. Introduces a clause with an NP gap. May be null (containing the 0 complementizer) or lexical, containing a wh-adverb such as how or why.');  
INSERT INTO property (type_id,string,description) VALUES ('1','WHNP','Wh-noun Phrase. Introduces a clause with an NP gap. May be null (containing the 0 complementizer) or lexical, containing some wh-word, e.g. who, which book, whose daughter, none of which, or how many leopards.');  
INSERT INTO property (type_id,string,description) VALUES ('1','WHPP','Wh-prepositional Phrase. Prepositional phrase containing a wh-noun phrase (such as of which or by whose authority) that either introduces a PP gap or is contained by a WHNP.');  
INSERT INTO property (type_id,string,description) VALUES ('1','WP','Wh-pronoun');  
INSERT INTO property (type_id,string,description) VALUES ('1','WP$','Possessive wh-pronoun'); 
INSERT INTO property (type_id,string,description) VALUES ('1','WP-S','Prolog version of WP$');  
INSERT INTO property (type_id,string,description) VALUES ('1','WRB','Wh-adverb');  
INSERT INTO property (type_id,string,description) VALUES ('1','X','Unknown, uncertain, or unbracketable. X is often used for bracketing typos and in bracketing the...the-constructions.');

-- EOF ;)
