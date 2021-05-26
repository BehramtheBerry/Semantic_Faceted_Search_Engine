/**
 * 
 */
package facetStr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DELL
 *
 */
public class Queries {


	private  Map<String, List<String>> queryMap = new HashMap<String, List<String>>();
	private  Map<String, List<List<String>>> queryList = new HashMap<String, List<List<String>>>();
	
	
	/**
	 * @return the queryList
	 */
	public Map<String, List<List<String>>> getQueryList() {
		
		
		return queryList;
	}



	private List<String> queries;
	
	private  Map<String, List<String>> generatedSql = new HashMap<String, List<String>>();
	
	/**
	 * @return the generatedSql
	 */
	public Map<String, List<String>> getGeneratedSql() {
		String query;
		List<List<String>> forquery= queryList.get(Facet_Names.query.toString());
		List<String> queries;
		if(forquery.size()!=0)
		{
			queries= new ArrayList<>();
			for(List<String> q: forquery)
			{
				
				query ="SELECT DISTINCT ?s "+
						  "WHERE { ?s ?p ?o ";
				for(String p: q)
				{
						  query+= ".FILTER regex(str(?o), "+"\'"+p+"\'"+")";
						 
				}
				query+=  " . }";
				queries.add(query);
			}
			generatedSql.put(Facet_Names.query.toString(), queries);
		}
		
		
		
		
		String titleQuery;
		List<List<String>> forTitle= queryList.get(Facet_Names.Title.toString());
		String predicate;
		if(forTitle!=null)
		{
			predicate= this.getPredicateFromKey(Facet_Names.Title.toString());
			List<String> titleQueries= new ArrayList<>();
			for(List<String> q : forTitle)
			{
				titleQuery ="SELECT DISTINCT ?s "+
						  "WHERE { ";
				for(String p:q)
				{
					titleQuery+=
						   "?s"
						  + predicate
						  +"";
					
					titleQuery+= "?o .FILTER regex(str(?o), "+"\'"+p+"\'"+")";
					
				}
				titleQuery +="."
						  + "  }";
				titleQueries.add(titleQuery);
			}
			generatedSql.put(Facet_Names.Title.toString(), titleQueries);
			
			
			}
		
		String descriptionQuery;
		List<List<String>> forDescription= queryList.get(Facet_Names.Title.toString());
		String predicateDes;
		if(forDescription!=null)
		{
			predicateDes= this.getPredicateFromKey(Facet_Names.Description.toString());
			List<String> descriptionQueries= new ArrayList<>();
			for(List<String> q : forDescription)
			{
				descriptionQuery ="SELECT DISTINCT ?s "+
						  "WHERE { ";
				for(String p:q)
				{
					descriptionQuery+=
						   "?s"
						  + predicateDes
						  +"";
					
					descriptionQuery+= "?o .FILTER regex(str(?o), "+"\'"+p+"\'"+")";
					
				}
				descriptionQuery +="."
						  + "  }";
				descriptionQueries.add(descriptionQuery);
			}
			generatedSql.put(Facet_Names.Description.toString(), descriptionQueries);
			
			
			}
		
		
		return generatedSql;
	}


	/**
	 * @param generatedSql the generatedSql to set
	 */
	public void setGeneratedSql(Map<String, List<String>> generatedSql) {
		
		this.generatedSql = generatedSql;
	}


	/**
	 * @return the queries
	 */
	public List<String> getQueries() {
		
		return queries;
	}


	/**
	 * @param queries the queries to set
	 */
	public void setQueries(List<String> queries) {
		//int queryCount = this.queryMap.size();
		String query;
		Object key;
		List<String> forquery= queryMap.get(Facet_Names.query.toString());
		
		if(forquery.size()!=0)
		{
			for(String q: forquery)
			{
				query ="SELECT ?s ?p"+
						  "WHERE { "
						  + "?s"
						  + "?p"
						  + "?o .FILTER regex(str(?o), "+q+") ."
						  + "  }";
				queries.add(query);
			}
		}
		
		List<String> forTitle= queryMap.get(Facet_Names.Title.toString());
		String predicate;
		if(forTitle.size()!=0)
		{
			predicate= this.getPredicateFromKey(Facet_Names.Title.toString());
			query ="SELECT ?s ?p"+
					  "WHERE { "
					  + "?s"
					  + predicate
					  +"";
			for(String q: forTitle)
			{
			
			query+= "?o .FILTER regex(str(?o), "+q+")";
				
			}
			query +="."
					  + " . }";
			queries.add(query);
		}
		
		List<String> forDescription= queryMap.get(Facet_Names.Description.toString());

		if(forDescription.size()!=0)
		{
			predicate= this.getPredicateFromKey(Facet_Names.Description.toString());
			predicate= this.getPredicateFromKey(Facet_Names.Title.toString());
			query ="SELECT ?s ?p"+
					  "WHERE { "
					  + "?s"
					  + predicate
					  +"";
			for(String q: forTitle)
			{
			
			query+= "?o .FILTER regex(str(?o), "+q+")";
				
			}
			query +="."
					  + " . }";
			queries.add(query);
		}
		
		this.queries = queries;
	}


	@SuppressWarnings("unused")
	private String getPredicateFromKey(String key)
	{
		String predicate;
		if(key==Facet_Names.Title.toString())
			predicate ="http://purl.org/dc/terms/title";
		if(key ==Facet_Names.Keyword.toString())
			predicate ="http://http://www.w3.org/ns/dcat#keyword";
		if(key==Facet_Names.query.toString())
			predicate ="?p";
		else
			predicate ="?p";
		return predicate;
	}


	/**
	 * @param nestedMap
	 */
	public void setQueryList(Map<String, List<List<String>>> nestedMap) {
		// TODO Auto-generated method stub
		this.queryList = nestedMap;
		
	}



	

}
