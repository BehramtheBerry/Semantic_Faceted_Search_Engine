/**
 * 
 */
package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import facetStr.Dataset;
import facetStr.FacetName;
import facetStr.FacetVal;
import facetStr.Facet_Filters;
import facetStr.Facet_Names;
import facetStr.Indexer;
import facetStr.Queries;
import facetStr.Response;
import facetStr.SesameStore;
import facetStr.StoreAdapter;

/**
 * @author DELL
 *
 */

@Service
@Repository
public class MainService {

	public static SesameStore store;
	private Indexer indexer;
	// List of FacetName and FacetValues to make the checkbox
	public static Map<String,List<String>> FacetName_Value = new HashMap<String, List<String>>();

	private static List<String> description_values;
	private static List<String> publisher_values;
	private static List<String> title_values;
	private static List<String> keyword_values;
	private static List<String> url_values;

	

	public MainService() throws URISyntaxException, IOException {
	
		Resource resource = new ClassPathResource("result001.nt");
		File file = resource.getFile();
		String path = file.getAbsolutePath();    
		String newPath = path.substring(0, 84) + "Result.json";

		try {
			store = setStore(new SesameStore());
			store.loadData(file); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	

		indexer = new Indexer();
		addDataToSearchIndex(indexer, "http://purl.org/dc/terms/title", store);
		addDataToSearchIndex(indexer, "http://purl.org/dc/terms/description", store);

	}

	private List<String> getNameOfProperies(){
		List<String> names = new ArrayList<>();

		return names;
	}
	
	/**
	 * @param index
	 * @param string
	 * @param store2
	 */
	private void addDataToSearchIndex(Indexer index, String string, SesameStore store2) {
		Set<String> uniqueIds = new HashSet<String>();
		String query = String.format("select ?x ?y where { ?x <%s> ?y . }", string);
		StoreAdapter tupleIterator = store.executeQuery(query);
		if (tupleIterator != null){
			tupleIterator.open();
			while (tupleIterator.hasNext()){
				try {
					if (!uniqueIds.contains(tupleIterator.getItem(0)))
						index.addDataToSearchMap(tupleIterator.getItem(0), tupleIterator.getItem(1), " ");
					uniqueIds.add(tupleIterator.getItem(0));
				} catch (Exception e){
					System.out.println("Corrupt data: " + e.getMessage());
				}
				tupleIterator.next();
			}
			tupleIterator.dispose();		
		}
	}


	/**
	 * @return
	 */
	/* for testing a facetfiler
	public static Response facetFilter(String keyword) {
		Response answer = new Response();

		Map<FacetName, List<String>> filters = new HashMap<FacetName, List<String>>();
		List<String> factValue1 = new ArrayList<>();
		factValue1.add("http://www.w3.org/ns/dcat#Informational");

		List<String> factValue2 = new ArrayList<>();
		factValue2.add("http://datahub.org/publisher/publisher_1");

		FacetName name1 = new FacetName();
		FacetName name2 = new FacetName();
		name1.setName("Type");
		name2.setName("Publisher");

		//one query is done so far

		filters.put(name1, factValue1);
		filters.put(name2, factValue2);



		//		FilterCondition fc = new FilterCondition(filters);
		List<Dataset> listOfDatasets = Search(filters, keyword);
		answer.setResults(listOfDatasets);


		return answer;
	}
	*/

	/* search function
	@SuppressWarnings("unlikely-arg-type")
	public static List<Dataset> Search(Map<FacetName, List<String>> filterMap, String keyword) {

		List<String> datasetids = getDatasetsIDsFromKeyword(keyword);
		List<Dataset> datasetList = getListDataset(datasetids);

		List<Dataset> results = new ArrayList<Dataset>();
		List<String> values = new ArrayList<>();

		int count;
		int totalQuery = filterMap.size();
		for(Dataset dataset : datasetList) {
			count = 0;
			{
				for (Map.Entry<FacetName,List<String>> filter : filterMap.entrySet())
				{
					System.out.println("FacetName = " + filter.getKey().getName() +
							", FacetValue = " + filter.getValue());

					dataset.printMap();

					List<String> valueOfDataset = dataset.getFacetMap().get(filter.getKey().getName());


					if( filter.getKey().getName()=="Publisher")
						System.out.println("DatasetId: "+dataset.getID() +"Value of Publisher: " +
								valueOfDataset.iterator());
					if( filter.getKey().getName()=="Type")
						System.out.println("DatasetId: "+dataset.getID() +"Value of Type: " +
								valueOfDataset.iterator());


					for(String filterValue: filter.getValue()) {
						// change the comparision methods
						if(valueOfDataset.size()!=0 && valueOfDataset!=null&& valueOfDataset.contains(filterValue))
						{
							count ++;
							if( filter.getKey().getName()=="Publisher")
								System.out.println("Sucess DatasetId: "+dataset.getID() +"/n Value of Publisher: " + valueOfDataset.iterator());

							if( filter.getKey().getName()=="Keyword")
								System.out.println("Sucess DatasetId: "+dataset.getID() +"/n Value of Type: " + valueOfDataset.iterator());

							System.out.println("dataset Id: " +dataset.getID() + "count: " + count );
						}
						else
							break;
					}



				}
				if(count==totalQuery)
					results.add(dataset);
			}
		}
		System.out.println("results size:" + results.size());
		return results;
	}
	*/


	public static Response datasetlist(String keyword) {
		Response answer = new Response();

		List<String> datasetids = getDatasetsIDsFromKeyword(keyword);
		List<Dataset> results = getListDataset(datasetids);

		answer.setResults(results);

		return answer;

	}

	/**
	 * @return
	 */
	private static List<FacetVal> getFirstFacetValues() {
		Set<String> facetIds = new HashSet<String>();
		facetIds.add("title_1");
		facetIds.add("title_2");
		facetIds.add("tilte_3");

		List<FacetVal> facetValues = new ArrayList<FacetVal>();
		//getFacetValueHierarchy(config, facetIds, new HashSet<String>(), true);
		Map<String, String> idLabelMap = getIdLabelMap(facetIds);

		for (FacetVal fValue : facetValues) {
			if (idLabelMap.containsKey(fValue.getObject()))
				fValue.setLabel(idLabelMap.get(fValue.getObject()));
		}
		return facetValues;
	}

	/**
	 * @param facetIds 
	 * @return
	 */
	private static Map<String, String> getIdLabelMap(Set<String> facetIds) {
		Map<String, String> multimap = new HashMap<String, String>();

		for (String fValue : facetIds) {
			multimap.put(fValue, "title_1");
			multimap.put(fValue, "title_2");
			multimap.put(fValue, "title_3");
		}

		return multimap;
	}

	/**
	 * @return
	 */
	public static List<String> getDatasetsIDsFromKeyword(String searchKey) {
		if (searchKey.isEmpty() || searchKey == null)
			return new ArrayList<String>();
		Set<String> allIds = new HashSet<String>();
		Set<String> intersectingIds = new HashSet<String>();
		String[] keywords = searchKey.split(" ");

		for (String keyword : keywords) {
			allIds.addAll(Indexer.getIds(keyword));
		}

		intersectingIds.addAll(allIds);

		for (String keyword : keywords) {
			intersectingIds.retainAll(Indexer.getIds(keyword));
		}

		if (intersectingIds.size() > 0)
			allIds = intersectingIds;

		if (allIds.size() > 1000) {
			List<String> ids = new ArrayList<String>();
			int i = 0;
			for (String id : allIds) {
				if (i < 1000)
					ids.add(id);
				i++;
			}

			return ids;
		} else
			return new ArrayList<String>(allIds);

	}


	/*
	//Umair did
	private static List<Dataset> getDatasets(List<String> datasetIDs) {

		List<Dataset> datasets = new ArrayList<Dataset>();
		List<String> type_values = new ArrayList<>();
		List<String> publisher_values = new ArrayList<>();
		for (String datasetid : datasetIDs) {
			Dataset dataset = new Dataset();
			dataset.setID(datasetid);			
			dataset.setUrl(getProperties(store, datasetid, "http://www.w3.org/ns/dcat#accessURL"));
			dataset.setDescription(getProperties(store, datasetid, "http://purl.org/dc/terms/description"));
			dataset.setTitle(getProperties(store, datasetid, "http://purl.org/dc/terms/title"));

			dataset.setType(getProperties(store, datasetid, "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"));
			dataset.setPublisher(getProperties(store, datasetid, "http://purl.org/dc/terms/publisher"));

			dataset.setKeywords(MainService.getListProperties(MainService.store, datasetid, "http://http://www.w3.org/ns/dcat#keyword"));


			if(! type_values.contains(dataset.getType()))
			{
				type_values.add(dataset.getType());
			}

			if(! publisher_values.contains(dataset.getPublisher()))
			{
				publisher_values.add(dataset.getPublisher());
			}
			//here we have to add map for each dataset which contains facetNames and facetValues
			dataset.setMap(createMap(dataset));
			dataset.setFacetMap(createFacetMap(dataset));

			datasets.add(dataset);

		}
		FacetName_Value.put("Type", type_values);
		FacetName_Value.put("Publisher",publisher_values);
		return datasets;
	}
	 */

	//Van did
	public static List<Dataset> getListDataset(List<String> datasetIds)
	{
		refeshFacetName_value();
		List<Dataset> datasets = new ArrayList<Dataset>();
		for (String datasetid : datasetIds) {
			Dataset dataset = new Dataset();
			System.out.println("datasetId: " + datasetid);
			dataset.setID(datasetid);
			dataset.setAccessURL(
					MainService.getProperties(MainService.store, datasetid, "http://www.w3.org/ns/dcat#accessURL"));
			dataset.setDescription(
					MainService.getProperties(MainService.store, datasetid, "http://purl.org/dc/terms/description"));
			dataset.setTitle(MainService.getProperties(MainService.store, datasetid, "http://purl.org/dc/terms/title"));

			dataset.setType(MainService.getProperties(MainService.store, datasetid,
					"http://www.w3.org/1999/02/22-rdf-syntax-ns#type"));
			dataset.setPublisher(
					MainService.getProperties(MainService.store, datasetid, "http://purl.org/dc/terms/publisher"));

			dataset.setKeyword(MainService.getListProperties(MainService.store, datasetid, "http://http://www.w3.org/ns/dcat#keyword"));


			dataset.setLicence(MainService.getProperties(MainService.store, datasetid, "http://purl.org/dc/terms/license"));
			dataset.setAccessURL(MainService.getProperties(MainService.store, datasetid, "http://www.w3.org/ns/dcat#accessURL"));


			// here we have to add map for each dataset which contains facetNames and
			// facetValues
			//dataset.setMap(MainService.createMap(dataset));
			
			//No need to use it
			//dataset.setFacetMap(MainService.createFacetMap(dataset));

			// set FacetName_Value for checklist
			setFacetName_Value(dataset);

			datasets.add(dataset);

		}
		
		extractFacetName_Value();
		return datasets;
	}

	/**
	 * 
	 */
	private static void extractFacetName_Value() {
		//CheckList
		FacetName_Value.put(Facet_Names.Keyword.toString(), keyword_values);
		FacetName_Value.put(Facet_Names.Publisher.toString(), publisher_values);
		FacetName_Value.put(Facet_Names.Title.toString(), title_values);
		//FacetName_Value.put(Facet_Names.Description.toString(), description_values);
		FacetName_Value.put(Facet_Names.AccessURL.toString(), url_values);

		makeUniqueFacetName_Value();
	}
	
	private static void refeshFacetName_value() {
		FacetName_Value = new HashMap<>();
		keyword_values = new ArrayList<>();
		publisher_values = new ArrayList<>();
		title_values = new ArrayList<>();
		url_values= new ArrayList<>();
		description_values = new ArrayList<>();
		
	}

	private static void setFacetName_Value(Dataset dataset)
	{

		if (!description_values.contains(dataset.getDescription()) &&! dataset.getDescription().isEmpty()) {
			description_values.add(dataset.getDescription());
			//System.out.println("Add Type value: "+ dataset.getType());
		}

		if (!publisher_values.contains(dataset.getPublisher())&& !dataset.getPublisher().isEmpty()) {
			publisher_values.add(dataset.getPublisher());
		}

		if (!title_values.contains(dataset.getTitle())&& !dataset.getTitle().isEmpty()) {
			title_values.add(dataset.getTitle());
		}

		if (dataset.getKeyword()!=null && dataset.getKeyword().size()!=0) {
			keyword_values.addAll(dataset.getKeyword());
		}
		
		if (dataset.getAccessURL()!=null && !dataset.getAccessURL().isEmpty()) {
			url_values.add(dataset.getAccessURL());
		}

	}
	
	private static void makeUniqueFacetName_Value()
	{
		Set<String> set = new HashSet<>(keyword_values);
		keyword_values.clear();
		keyword_values.addAll(set);
		
		Set<String> set1 = new HashSet<>(url_values);
		url_values.clear();
		url_values.addAll(set1);
		
		Set<String> set3 = new HashSet<>(publisher_values);
		publisher_values.clear();
		publisher_values.addAll(set3);
		
		Set<String> set4 = new HashSet<>(title_values);
		title_values.clear();
		title_values.addAll(set4);

	}

	public static Map<FacetName, FacetVal> createMap(Dataset d) {

		Map<FacetName, FacetVal> map =new HashMap<FacetName, FacetVal>();
		FacetName fName1 = new FacetName();
		FacetName fName2 = new FacetName();
		FacetName fName3 = new FacetName();
		fName1.setName("Title");
		fName2.setName("Type");
		fName3.setName("Publisher");

		FacetVal fVal1 = new FacetVal();
		FacetVal fVal2 = new FacetVal();
		FacetVal fVal3 = new FacetVal();

		if(!d.getTitle().isEmpty())
		{
			fVal1.setLabel(d.getTitle());
			map.put(fName1, fVal1);
		}

		if(!d.getType().isEmpty()) {
			fVal2.setLabel(d.getType());
			map.put(fName2, fVal2);
		}

		if(!d.getPublisher().isEmpty()) {
			fVal3.setLabel(d.getPublisher());
			map.put(fName3, fVal3);		
		}


		return map;
	}

	public static Map<String, List<String>> createFacetMap(Dataset d) {
		//todo automate 

		Map<String, List<String>> facetMap =new HashMap<String, List<String>>();

		FacetName fName1 = new FacetName();
		FacetName fName2 = new FacetName();
		FacetName fName3 = new FacetName();

		fName1.setName(Facet_Names.Title.toString());
		fName2.setName(Facet_Names.Keyword.toString());
		fName3.setName(Facet_Names.Publisher.toString());
		fName3.setName(Facet_Names.Description.toString());

		List<String> values;
		if(d.getKeyword()!=null && d.getKeyword()!=null) {

			facetMap.put(Facet_Names.Keyword.toString(), d.getKeyword());
		}

		if(!d.getPublisher().isEmpty()) {
			values = new ArrayList<>();
			values.add(d.getPublisher());
			facetMap.put(Facet_Names.Publisher.toString(), values);
		}

		if(!d.getTitle().isEmpty()) {
			values = new ArrayList<>();
			values.add(d.getTitle());
			facetMap.put(Facet_Names.Title.toString(), values);
		}

		if(!d.getDescription().isEmpty()) {
			values = new ArrayList<>();
			values.add(d.getDescription());
			facetMap.put(Facet_Names.Description.toString(), values);
		}

		return facetMap;
	}


	/**
	 * @return
	 */
	public static Map<FacetName, FacetVal> getMapProperties() {
		Map<FacetName, FacetVal> nameValue =new HashMap<FacetName, FacetVal>();

		FacetName fName1 = new FacetName();
		FacetName fName2 = new FacetName();
		FacetName fName3 = new FacetName();
		fName1.setName("Title");
		fName2.setName("Publisher");
		fName3.setName("DataType");
		FacetVal fVal1 = new FacetVal();
		FacetVal fVal2 = new FacetVal();
		FacetVal fVal3 = new FacetVal();
		fVal1.setId("val_1");

		fVal1.setLabel("title_1");
		fVal2.setLabel("Elsevier");
		fVal3.setLabel("Edu");

		nameValue.put(fName1, fVal1);
		nameValue.put(fName2, fVal2);
		nameValue.put(fName3, fVal3);


		return nameValue;
	}


	/**
	 * @return
	 */
	public static String getProperties(SesameStore store2, String id, String predicate) {
		String result = "";
		String query = String.format("SELECT ?z WHERE { <%s> <%s> ?z }", id, predicate);
		StoreAdapter tupleIterator = store2.executeQuery(query);
		if (tupleIterator != null) {
			tupleIterator.open();
			if (tupleIterator.hasNext()) {
				result = tupleIterator.getItem(0);
			}

			tupleIterator.dispose();
		}
		return result;
	}

	public static List<String> getListProperties(SesameStore store2, String id, String predicate) {
		List<String> list = new ArrayList<>();
		String query = String.format("SELECT ?z WHERE { <%s> <%s> ?z }", id, predicate);
		list= executeQuery(store2, query);
		return list;
	}


	public List<String> getTriples(){
		String query = String.format("select ?x ?y ?z where { ?x ?y ?z } Limit 50");
		List<String> triples = new ArrayList<String>();
		StoreAdapter rs = store.executeQuery(query);
		if(rs != null) {
			rs.open();
			while(rs.hasNext()) {
				triples.add(rs.getItem(0) +", " + rs.getItem(1) + ", " + rs.getItem(2));
				//				System.out.println(rs.getItem(0) +", " + rs.getItem(1) + ", " + rs.getItem(2));
				rs.next();
			}
			rs.dispose();
		}
		System.out.println("Processing is done...");
		return triples;
	}



	/**
	 * @param filter
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static List<Dataset> facetFilterByCheckList(Facet_Filters filter, List<Dataset> datasets) throws UnsupportedEncodingException {
		
		
		List<Dataset> answer = new ArrayList<Dataset>();

		Map<FacetName, List<String>> filters = new HashMap<FacetName, List<String>>();

		
		List<String> typeValues = new ArrayList<>();

		if(filter.getType_Value()!=null)
		{
			for(String v: filter.getType_Value())
			{
				typeValues.add(v);
			}
		}
		
		if(typeValues.size()!=0)
		{
			FacetName name1 = new FacetName();

			name1.setName(Facet_Names.Type.toString());
			filters.put(name1, typeValues);
		}

		List<String> publisherValues = new ArrayList<>();
		if(filter.getPublisher_Value()!=null)
		{
			for(String v: filter.getPublisher_Value())
			{
				publisherValues.add(v);
			}
			if(publisherValues.size()!=0)
			{
	
				FacetName name2 = new FacetName();
				name2.setName(Facet_Names.Publisher.toString());
				filters.put(name2, publisherValues);
			}
		}

		if(filter.getKeyword_Value()!=null)
		{
			List<String> keywordValues = new ArrayList<>();
	
			for(String v: filter.getKeyword_Value())
			{
				keywordValues.add(v);
			}
	
	
			if(keywordValues.size()!=0)
			{
				FacetName name3 = new FacetName();
	
				name3.setName(Facet_Names.Keyword.toString());
				filters.put(name3, keywordValues);
			}
		}
		
		if(filter.getTitle_Value()!=null)
		{
			List<String> values = new ArrayList<>();
	
			for(String v: filter.getTitle_Value())
			{
				values.add(v);
			}
	
	
			if(values.size()!=0)
			{
				FacetName name4 = new FacetName();
	
				name4.setName(Facet_Names.Title.toString());
				filters.put(name4, values);
			}
		}

		if(filter.getURL_Value()!=null)
		{
			List<String> values = new ArrayList<>();
	
			for(String v: filter.getURL_Value())
			{
				values.add(v);
			}
	
	
			if(values.size()!=0)
			{
				FacetName name5 = new FacetName();
	
				name5.setName(Facet_Names.AccessURL.toString());
				filters.put(name5, values);
			}
		}




		if(filters.size()!=0)
		{
			//answer.setResults(filter(filters, datasets));
			answer = filterByProperties(filters, datasets);
			System.out.println("Filter Results: "+ filters.size());
		}
		else
		{
			answer= datasets;
		}


		return answer;
	}
/* Filter use FacetMap in each dataset
	private static List<Dataset> filter(Map<FacetName, List<String>> filters, List<Dataset> datasets)
	{
		List<Dataset> results = new ArrayList<Dataset>();
		List<String> values = new ArrayList<>();

		int count;
		int totalQuery = filters.size();
		System.out.println("query count:" +totalQuery);
		for(Dataset dataset : datasets) {
			count = 0;
			{	//System.out.println("DatasetId: "+dataset.getID() );
				for (Map.Entry<FacetName,List<String>> filter : filters.entrySet())
				{
					//System.out.println("FacetName = " + filter.getKey().getName() +
					//		", FacetValue = " + filter.getValue());

					//					dataset.printMap();

					List<String> valueOfDataset = dataset.getFacetMap().get(filter.getKey().getName());


					if( filter.getKey().getName()=="Publisher") { if(valueOfDataset!=null) {
						for(String s: filter.getValue()) System.out.println("Filter Publisher: " + s);
					    for(String s: valueOfDataset) System.out.println("Publisher: " + s);
						
						
						
					} }

					if( filter.getKey().getName()=="Type") { if(valueOfDataset!=null) {

						for(String s: valueOfDataset) System.out.println("Type: " + s); } }



					for(String filterValue: filter.getValue()) {
						// change the comparision methods
						if(valueOfDataset!=null&& valueOfDataset.contains(filterValue))
						{
							count ++;
							if( filter.getKey().getName()=="Publisher")
								System.out.println("Sucess DatasetId: "+dataset.getID() +"/n Value of Publisher: " + valueOfDataset.iterator());

							if( filter.getKey().getName()=="Type")
								System.out.println("Sucess DatasetId: "+dataset.getID() +"/n Value of Type: " + valueOfDataset.iterator());

							System.out.println("dataset Id: " +dataset.getID() + "count: " + count );
						}
						else
							break;
					}


				}
				if(count==totalQuery)
					results.add(dataset);
			}
		}
		System.out.println("results size:" + results.size());

		for(Dataset d: results)
		{
			System.out.println("id:" + d.getID());
			System.out.println("type:" + d.getType());
			System.out.println("publisher:" + d.getPublisher());
		}
		return results;
	}
	*/
	
	private static List<Dataset> filterByProperties(Map<FacetName, List<String>> filters, List<Dataset> datasets)
	{
		refeshFacetName_value();
		List<Dataset> results = new ArrayList<Dataset>();
		List<String> values = new ArrayList<>();

		int count;
		int totalQuery = filters.size();
		System.out.println("query count:" +totalQuery);
		for(Dataset dataset : datasets) {
			count = 0;
			{	//System.out.println("DatasetId: "+dataset.getID() );
				for (Map.Entry<FacetName,List<String>> filter : filters.entrySet())
				{
					//System.out.println("FacetName = " + filter.getKey().getName() +
					//		", FacetValue = " + filter.getValue());

					//					dataset.printMap();
					List<String> filterValues = filter.getValue();
	
					if( filter.getKey().getName()==Facet_Names.Publisher.toString()) { 

						
						String valueOfDataset = dataset.getPublisher();
						for(String s: filter.getValue()) System.out.println("Filter Publisher: " + s);
						System.out.println("Publisher: " + valueOfDataset);
						if(filterValues.contains(valueOfDataset))
						{
							count++;
						}
						
					}
					else if( filter.getKey().getName()==Facet_Names.Type.toString()) {
						String s = dataset.getType();
						if(filterValues.contains(s))
						{
							count++;
						}
					}
					else if(filter.getKey().getName()==Facet_Names.Keyword.toString())
					{
						List<String> valueList= dataset.getKeyword();
						List<String> intersectElements = filterValues.stream()
								.filter(valueList :: contains)
								.collect(Collectors.toList());
						if(!intersectElements.isEmpty()) {
							count++;
						}
					}
					else if(filter.getKey().getName()==Facet_Names.Title.toString())
					{

						
						String valueOfDataset = dataset.getTitle();
						
						for(String s: filter.getValue()) System.out.println("Filter Publisher: " + s);
						System.out.println("Publisher: " + valueOfDataset);
						
						if(filterValues.contains(valueOfDataset))
						{
							count++;
						}
					}
					else if(filter.getKey().getName()==Facet_Names.Description.toString()) {
						String s = dataset.getDescription();
						if(filterValues.contains(s))
						{
							count++;
						}
					}
					else if(filter.getKey().getName()==Facet_Names.AccessURL.toString()) {
						String s = dataset.getAccessURL();
						if(filterValues.contains(s))
						{
							count++;
						}
					}


				}
				if(count==totalQuery)
				{
					results.add(dataset);
					//Todo: add value to FacetName_Value
					setFacetName_Value(dataset);
				}
			}
		}
		
		extractFacetName_Value();
		
		System.out.println("results size:" + results.size());

		for(Dataset d: results)
		{
			System.out.println("id:" + d.getID());
			System.out.println("type:" + d.getType());
			System.out.println("publisher:" + d.getPublisher());
		}
		return results;
	}
	



	/*-----------------parser rest api test -*/
	public static JSONArray onCallsAPI(String q1) throws IOException {
		
		String subQuery = URLEncoder.encode(q1, "UTF-8");

		HttpURLConnection conn = null;
		JSONObject responseJson = null;
		// 성공

		StringBuilder sb = new StringBuilder();

		try {

			URL url = new URL("http://localhost:5000/word?input=" + subQuery);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(false);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
			//  responseJson = new JSONObject(sb.toString());    

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("not JSON Format response");
			e.printStackTrace();
		}

		Object obj = null;
		try {
			JSONParser jsonParser = new JSONParser();
			obj = jsonParser.parse(sb.toString());
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (JSONArray)obj;

	}

	/*.....................*/
	@SuppressWarnings("unchecked")
	public static Queries getQueries(String UserQuery) throws IOException
	{
		Queries queries = new Queries();
		//Todo: Read the query from input
		Map<String, List<List<String>>> nestedMap = new HashMap<String, List<List<String>>>();




		//	JSONParser jsonParser = new JSONParser();

		/*  try (FileReader reader = new FileReader(jsonFile))
        {
            //Read JSON file
            Object obj = null;
			try {
				obj = jsonParser.parse(reader);
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 */
		JSONArray queryList = onCallsAPI(UserQuery);
		System.out.println("obj of json: "+queryList);

		List<List<String>> nestedListForQuery = new ArrayList<>();
		//Iterate over employee array
		for(int i = 0; i< queryList.size();i++) {
			JSONObject o = (JSONObject)queryList.get(i);
			JSONObject u = (JSONObject)o.get("keywordList");
			JSONArray r = (JSONArray)u.get("query");
			//System.out.println("value of I : "+i);

			if( r!=null)
			{
				for(int j = 0; j< r.size();j++) {

					JSONArray arr = (JSONArray)r.get(j);

					List<String> elements = new ArrayList<>();

					for(int k = 0; k < arr.size();k++) {

						elements.add(arr.get(k).toString());


					}
					nestedListForQuery.add(elements);
				} 
			}
		}

		if(nestedListForQuery.size()!=0)
			nestedMap.put("query", nestedListForQuery);


		List<List<String>> nestedListForDescription = new ArrayList<>();
		//Iterate over employee array
		for(int i = 0; i< queryList.size();i++) {
			JSONObject o = (JSONObject)queryList.get(i);
			JSONObject u = (JSONObject)o.get("keywordList");
			JSONArray r = (JSONArray)u.get("description");

			if(r!=null)
			{
				for(int j = 0; j< r.size();j++) {
					//            	r.forEach(a -> {   
					JSONArray arr = (JSONArray)r.get(j);

					List<String> elements = new ArrayList<>();

					for(int k = 0; k < arr.size();k++) {
						elements.add(arr.get(k).toString());
						//            			arr.forEach(e -> System.out.println("element: " + e));   

					}
					nestedListForDescription.add(elements);
				} 
			}
		}

		if(nestedListForDescription.size()!=0)
			nestedMap.put("Description", nestedListForDescription);

		List<List<String>> nestedListForTitle = new ArrayList<>();
		//Iterate over employee array
		for(int i = 0; i< queryList.size();i++) {
			JSONObject o = (JSONObject)queryList.get(i);
			JSONObject u = (JSONObject)o.get("keywordList");
			JSONArray r = (JSONArray)u.get("title");

			if(r!=null)
			{
				for(int j = 0; j< r.size();j++) {
					//            	r.forEach(a -> {   
					JSONArray arr = (JSONArray)r.get(j);
					System.out.println("query list: "+arr);
					List<String> elements = new ArrayList<>();

					for(int k = 0; k < arr.size();k++) {
						elements.add(arr.get(k).toString());
						//            			arr.forEach(e -> System.out.println("element: " + e));   

					}
					nestedListForTitle.add(elements);
				} 
			}
		}
		if(nestedListForTitle.size()!=0)
			nestedMap.put("Title", nestedListForTitle);


		/*
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		 */
		queries.setQueryList(nestedMap);


		return queries;
	}


	/*.....................*/
	public static Queries readJsonFile(String jsonFile) throws IOException, org.json.simple.parser.ParseException
	{
		Queries queries = new Queries();
		BufferedReader buff =  new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile), "UTF-8"));
		//Todo: Read the query from input
		String line = null;

		while((line = buff.readLine()) != null){

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(line);
			JSONObject jsonObj = (JSONObject) obj;
			System.out.println(obj);

			System.out.println((String)jsonObj.get("keywordList"));
		}


		return queries;
	}




	public static List<Dataset> getDatasetFromQuery(Queries queries)
	{

		List<Dataset> datasets = new ArrayList<>();
		List<String>datasetIds = new ArrayList<>();
		List<String> finalDatasetIds;

		Map<String, List<String>> sqlList = queries.getGeneratedSql();

		sqlList.forEach((k,v)->System.out.println("Key of Queries =: " + k + ", Queries: " + v));


		for (Map.Entry<String,List<String>> map : sqlList.entrySet())
		{
			finalDatasetIds = new ArrayList<>();
			//System.out.println("finalDatasetId: ");
			for(String query: map.getValue())
			{
				System.out.println("for one query: ");
				finalDatasetIds.addAll(executeQuery(MainService.store, query));
				
				System.out.println("results: ");
				
				for(String s: finalDatasetIds)
					System.out.println(s);

			}
			if(datasetIds.size()==0)
				datasetIds.addAll(finalDatasetIds);
			else
			{
				for(String id: finalDatasetIds)
				{
					if(!datasetIds.contains(id))
					{
						datasetIds.remove(id);
					}
				}
			}

		}

		
		Set<String> set = new HashSet<>(datasetIds);
		datasetIds.clear();
		datasetIds.addAll(set);
	
		System.out.println("List of datasetIds after quering");
		for(String s: datasetIds)
			System.out.println(s);
		
		datasets = getListDataset(datasetIds);
		System.out.println("filter dataset: ");
		System.out.println("results:  "+ datasetIds.size());
		for(Dataset d: datasets)
		{
			//System.out.println(d.getID());
		}
		return datasets;
	}



	public static List<String> executeQuery(SesameStore store2, String query) {

		List<String> list = new ArrayList<>();

		StoreAdapter tupleIterator = store2.executeQuery(query);

		if (tupleIterator != null) {
			tupleIterator.open();
			while (tupleIterator.hasNext())
			{	
				list.add(tupleIterator.getItem(0));
				tupleIterator.next();
			
			}
			tupleIterator.dispose();
		}

		return list;
	}


	/**
	 * @return the store
	 */
	public SesameStore getStore() {
		return store;
	}


	/**
	 * @param sesameStore the store to set
	 * @return 
	 */
	public SesameStore setStore(SesameStore sesameStore) {
		return MainService.store = sesameStore;
	}

}
