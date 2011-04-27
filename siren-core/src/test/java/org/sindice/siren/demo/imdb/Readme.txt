This is a simple demonstration of SIREn's ability to search columnar data.

I first met Renaud Delbru by chance  the Crane pub in Galway. He stood next to me in the crowded room he sipping his Guinness, I sipping mine, he chatting with his lovely tall brunette date, I with mine (yes, it was my wife). I picked up his unmistakable French accent and asked about how he came to be in Galway, he explained that he was working at DERI working on some advanced search features which might be useful for the semantic web. I asked him, was it really worth the effort, wasn't Lucene good enough? He explained that he was writing some advanced features for Lucene. I told him that I admired his entheusiasm but wondered could it ever come to much. That was about two years ago.

Renaud and I work together now on the DI2 team at DERI. Last week he asked me that if I had a chance would I have a look at the SIREn features he is preparing for an upcoming release. He said I would be playing an important role.

An important role - I would be a star at last. Never having accepted a gig acting in the same feature as Sylvester Stallone, and I wondered had Renaud got my credentials mixed up with someone else. I set to find out. I began by looking at Stallone's page on IMDB, and one by one following the links to each movie, then looking at the list of actors. This was slow: there had to be a better way. After several hours downloading the dblp files and trying unsuccessfully to load these into a mysql database, I settled on using writing a perl script to fetch the required information using IMDB::Film and IMDB::Persons and wrote the data to flat files, one per film. While the data was downloading I wrote a simple java application to read flat files and for each movie create a document with fields for title, url and each actor, director, writer. Conceptually the document looked like this:

title: Mandingo
director: Richard Fleischer
writer: Kyle Onstott
writer: Jack Kirkland 
actor: Susan George
actor: Perry King
actor: Richard Ward (I)
actor: Brenda Sykes
actor: Ken Norton
actor: Lillian Hayman
actor: Roy Poole (I)
actor: Ji-Tu Cumbuka
actor: Ben Masters
actor: Paul Benedict
actor: Ray Spruell
actor: Louis Turenne
actor: Duane Allen (I)
actor: Beatrice Winde

Once all the data was loaded I was able to write a simple query for actor:stallone AND actor:fuller, and voila, I was deligted to find a William Fuller mentioned as an actor in "The Complete History of the Philadelphia Eagles", http://www.imdb.com/title/tt0443735/. Lucene is great.

Lucene is great for simple queries. But what if you want to query something more complicated - like something more than the names of the actors. What about searching some tabular data rather than just key:value pairs? Consider the actors, writers, directors of a movie, for example. I want to search for a Stallone film with an actor named Margot born in Canada, and an actor named Brad born in Florida and a director born in September. This is possible to do in a rather complex SQL query, but not in Lucene. Until now.

Enter left stage Renaud and the SIREn SIREn plugin for Lucene.

Renaud guided me through formatting my IMDB data into tuple fields, format based on ntriples, with the difference that it's a bit more flexible than <subject> <predicate> object. After a couple of iterations I settled on using the a four-column format (Column1=type, Column2=name, column3=birthDate, column4=birthPlace).

<title> "Mandingo" .
<director> "Richard Fleischer" "8 December 1916" "Brooklyn, New York City, New York, USA" .
<writer> "Kyle Onstott" " " "" .
<writer> "Jack Kirkland" "15 July 1901" "St. Louis, Missouri, USA" .
<actor> "Susan George" "26 July 1950" "London, England, UK" .
<actor> "Perry King" "30 April 1948" "Alliance, Ohio, USA" .
<actor> "Richard Ward (I)" "15 March 1915" "Glenside, Pennsylvania, USA" .
<actor> "Brenda Sykes" "25 June 1949" "Shreveport, Louisiana, USA" .
<actor> "Ken Norton" "9 August 1943" "Jacksonville, Illinois, USA" .
<actor> "Lillian Hayman" "17 July 1922" "Baltimore, Maryland, USA" .
<actor> "Roy Poole (I)" "31 March 1924" "San Bernardino, California, USA" .
<actor> "Ji-Tu Cumbuka" "4 March 1942" "Montgomery County, Alabama, USA" .
<actor> "Ben Masters" "6 May 1947" "Corvallis, Oregon, USA" .
<actor> "Paul Benedict" "17 September 1938" "Silver City, New Mexico, USA" .
<actor> "Ray Spruell" "4 November 1924" "Louisiana, USA" .
<actor> "Louis Turenne" "26 November 1933" "Montréal, Québec, Canada" .
<actor> "Duane Allen (I)" "21 October 1937" "" .
<actor> "Beatrice Winde" "5 January 1924" "Chicago, Illinois, USA" .

To load the files into Lucene I used a writer with having SIREn's TupleAnalyzer rather than a StandardAnalyzer as the default:
    Directory dir = new RAMDirectory();
    IndexWriter writer = new IndexWriter(dir, new TupleAnalyzer(), MaxFieldLength.UNLIMITED);
    
Then for each movie document, I loaded the tuples from the file into a single string and added them to the index:
 Document doc = new Document();
 doc.add(new Field(DEFAULT_FIELD,tupleContent, Store.NO, Field.Index.ANALYZED_NO_NORMS));
 writer.addDocument(doc);
 
 There is no parser yet for the tuple queries, so I coded the query together manually: rembember my goal is to find a Stallone film with an actor named Margot born in Canada, and an actor named Brad born in Florida and a director born in September.  Here's the method returning the query:
 
   public Query getStaloneFilmMargotFromCanadaBradFromFloridaDirectorBornSeptemberQuery() {
  
    // Actor Margot born in Canada:
    final SirenTupleQuery tq1 = new SirenTupleQuery();
    tq1.add(termInCell("actor", COLUMN_TUPLE_TYPE), SirenTupleClause.Occur.MUST);
    tq1.add(termInCell("margot", COLUMN_PERSON_NAME), SirenTupleClause.Occur.MUST);
    tq1.add(termInCell("canada", COLUMN_BIRTH_PLACE), SirenTupleClause.Occur.MUST);

    // Actor Brad born in Florida:
    final SirenTupleQuery tq2 = new SirenTupleQuery();
    tq2.add(termInCell("actor", COLUMN_TUPLE_TYPE), SirenTupleClause.Occur.MUST);
    tq2.add(termInCell("brad", COLUMN_PERSON_NAME), SirenTupleClause.Occur.MUST);
    tq2.add(termInCell("florida", COLUMN_BIRTH_PLACE), SirenTupleClause.Occur.MUST);
  
    // Director born in September:
    final SirenTupleQuery tq3 = new SirenTupleQuery();
    tq3.add(termInCell("director", COLUMN_TUPLE_TYPE), SirenTupleClause.Occur.MUST);
    tq3.add(termInCell("september", COLUMN_BIRTH_DATE), SirenTupleClause.Occur.MUST);
    
    // A Stallone film:
    final SirenTupleQuery tq4 = new SirenTupleQuery();
    tq4.add(termInCell("stallone", COLUMN_ANY), SirenTupleClause.Occur.MUST);

    // Combine the four tuple queries with a Lucene boolean query
    final BooleanQuery q = new BooleanQuery();
    q.add(tq1, Occur.MUST);
    q.add(tq2, Occur.MUST);
    q.add(tq3, Occur.MUST);
    q.add(tq4, Occur.MUST);

    return q;
  }

Here's the helper method termInCell:
 
 private SirenCellQuery termInCell(String term, int cell) {
	final SirenCellQuery scq = new SirenCellQuery();
	scq.add(new SirenTermQuery(new Term(DEFAULT_FIELD, term)), SirenCellClause.Occur.MUST);
	if(cell != COLUMN_TUPLE_ANY){
		scq.setConstraint(cell);
	}
	return scq;
}

And finally, we run the query:
Query q = getStaloneFilmMargotFromCanadaBradFromFloridaDirectorBornSeptemberQuery();
IndexSearcher searcher = new IndexSearcher(dir);
ScoreDoc[] results = searcher.search(q, null, 10).scoreDocs;

Wow! It works, my query is matched by the film "Terror in the Aisles" http://www.imdb.com/title/tt0088249/.

Well Renaud, what can I say but, "well done". The SIREn plugin is a powerful feature that represents a great step forward for Lucene. I can think of some great use cases for movie and sports trivia easily represented in tabular data, and I'm sure there will be many use cases coming from business and enterprise.

I'm really looking forward to your query language syntax, and beyond that to a future release with some even more powerful transient join features.

That's a great job.
 
 

    
    