/**
 * 
 */
package web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.SolrServerException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import facetStr.Dataset;
import facetStr.Facet_Filters;
import facetStr.Facet_Names;
import facetStr.Queries;
import facetStr.Response;
import service.MainService;

/**
 * @author DELL
 *
 */

@Controller
public class MyController {

	//	private final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	private final MainService mainService;
	private final Facet_Filters facet_Filters;
	List<Dataset> datasets = new ArrayList<Dataset>();

	@Autowired
	public MyController(MainService mService) throws IOException, ParseException {
		this.mainService = mService;
		this.facet_Filters = new Facet_Filters();
		//getInitalDataSet();


		//		MainService.readJsonFile("C:\\Users\\DELL\\eclipse-workspace\\Search5\\resources\\testJson.json");


	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Map<String, Object> model) {

		//		
		//		mainService.
		//		logger.debug("index() is executed!");

		//		model.put("title", mainService.getTitle(""));
		//		model.put("msg", mainService.getDesc());

		return "index2";
	}

	// List of FacetName and FacetValues to make the checkbox
	/*
	public static Map<String, List<String>> FacetName_Value = new HashMap<String, List<String>>();

	@SuppressWarnings({ "unlikely-arg-type" })
	public void getInitalDataSet() {

		List<String> datasetids= new ArrayList<String>();
		//Indexer.iterateUsingIteratorAndEntry();
		datasetids.addAll(MainService.getListOfDataset());

		//List<String> datasetids = MainService.getDatasetsIDsFromKeyword("Datahub");
		//System.out.println("# of dataset id:" + datasetids.size());

		//System.out.println("Begining of create a dataset");

		List<String> type_values = new ArrayList<>();
		List<String> publisher_values = new ArrayList<>();
		List<String> keyword_values = new ArrayList<>();

		for (String datasetid : datasetids) {
			Dataset dataset = new Dataset();
			List<String> keywords = new ArrayList<>();
			//System.out.println("datasetId: " + datasetid);
			dataset.setID(datasetid);
			dataset.setUrl(
					MainService.getProperties(MainService.store, datasetid, "http://www.w3.org/ns/dcat#accessURL"));
			dataset.setDescription(
					MainService.getProperties(MainService.store, datasetid, "http://purl.org/dc/terms/description"));
			dataset.setTitle(MainService.getProperties(MainService.store, datasetid, "http://purl.org/dc/terms/title"));

			dataset.setType(MainService.getProperties(MainService.store, datasetid,
					"http://www.w3.org/1999/02/22-rdf-syntax-ns#type"));
			dataset.setPublisher(
					MainService.getProperties(MainService.store, datasetid, "http://purl.org/dc/terms/publisher"));

			dataset.setKeywords(MainService.getListProperties(MainService.store, datasetid, "http://http://www.w3.org/ns/dcat#keyword"));

			//System.out.println("Type value: "+ dataset.getType());
			if (!type_values.contains(dataset.getType()) &&! dataset.getType().isEmpty()) {
				type_values.add(dataset.getType());
				//System.out.println("Add Type value: "+ dataset.getType());
			}

			if (!publisher_values.contains(dataset.getPublisher())&& !dataset.getPublisher().isEmpty()) {
				publisher_values.add(dataset.getPublisher());
				//System.out.println("Add publisher value: "+ dataset.getPublisher());
			}

			//System.out.println("keyword value: ");
			for(String key: dataset.getKeywords())
			{
				keyword_values.add(key);
				//System.out.println(key);
			}

			if (!keyword_values.contains(dataset.getKeywords().iterator()) && dataset.getKeywords().size()!=0) {

				List<String> keys = dataset.getKeywords();
				for(String key: keys)
				{
					keyword_values.add(key);
					//System.out.println("add key: "+key);
				}

			}

			// here we have to add map for each dataset which contains facetNames and
			// facetValues
			//dataset.setMap(MainService.createMap(dataset));
			dataset.setFacetMap(MainService.createFacetMap(dataset));

			datasets.add(dataset);

			//System.out.println("End of add a dataset");

		}
		FacetName_Value.put(Facet_Names.Keyword.toString(), keyword_values);
		FacetName_Value.put(Facet_Names.Publisher.toString(), publisher_values);
		FacetName_Value.put(Facet_Names.Type.toString(), type_values);

		System.out.println("#of Dataset:"+ datasets.size());

	}
	 */

	public List<Dataset> getDataSetByKeyword(String keyword) {

		List<String> datasetids= new ArrayList<String>();
		//Indexer.iterateUsingIteratorAndEntry();


		datasetids = MainService.getDatasetsIDsFromKeyword("keyword");

		List<Dataset> datasets=  MainService.getListDataset(datasetids);
		System.out.println("#of Dataset:"+ datasets.size());
		return datasets;

	}



	// Search by keyword
	@RequestMapping(path = "/datasetlist", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Response datasetlist(@RequestParam("keyword") String keyword) throws SolrServerException, IOException {

		Response results = MainService.datasetlist(keyword);


		/*
		 * List<Dataset> datasets = getDataSetByKeyword(keyword);
		 * 
		 * Response results = new Response(); results.setResults(datasets);
		 */

		return results;

	}


	/*
	 * @RequestMapping(path = "/facetFilter", method = RequestMethod.GET, produces =
	 * "application/json")
	 * 
	 * @ResponseBody public Response facetFilter(@RequestParam("keyword") String
	 * keyword) throws SolrServerException, IOException {
	 * 
	 * Response result = MainService.facetFilter(keyword);
	 * 
	 * return result;
	 * 
	 * }
	 */


	@RequestMapping(path = "/queries", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView queries(@RequestParam("query") String query) throws IOException {
		Response response = new Response();

		System.out.println(query);

		Queries queries = MainService.getQueries(query);
		datasets = MainService.getDatasetFromQuery(queries);
		response.setResults(datasets);
		response.setFacetName_Value(MainService.FacetName_Value);

		//Test Filter Done
		/*
		Facet_Filters filter = new Facet_Filters();
		 String[] Str = {"서울특별시 빅데이터담당관"};
		 System.out.println("publisher: "+Str[0]);
		 filter.setPublisher_Value(Str);

		 String[] Str1 = {"서울특별시_혼인종류 및 외국인 국적별 혼인 통계"};
		 filter.setTitle_Value(Str1);
		 System.out.println("title: "+Str1[0]);

		 //
		 System.out.println("Filter by checklist:");

		MainService.facetFilterByCheckList(filter, datasets);
		 */
		///
		//		return response;

		Facet_Filters filter = new Facet_Filters();
		ModelAndView modelAndView = new ModelAndView("redirect:/filter","command" ,filter);
		
		return modelAndView;

	}

	@RequestMapping(value = "/filterSubmit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ModelAndView filterSubmit(@ModelAttribute("SpringWeb")Facet_Filters filter, ModelMap model) throws UnsupportedEncodingException {

		System.out.println("submit function");
		model.addAttribute("Type_Value", filter.getType_Value());

		model.addAttribute("Publisher_Value", filter.getPublisher_Value());

		model.addAttribute("Keyword_value", filter.getKeyword_Value());

		model.addAttribute("URL_Value", filter.getURL_Value());

		model.addAttribute("Title_Value", filter.getTitle_Value());

		System.out.println("Publisher_Value: "+ filter.getPublisher_Value().length);

		for(String v: filter.getKeyword_Value()) { 
			System.out.println("selected value of keyword original: "+ v); 

		}
		for(String v: filter.getPublisher_Value()) {
			System.out.println("selected value of Publisher: "+ v); 

		}

		/*
		 String[] Str = {"http://datahub.org/publisher/n2"};
		 filter.setPublisher_Value(Str);

		 String[] Str1 = {"Car Emission Information"};
		 filter.setTitle_Value(Str1);
		 */


		datasets = MainService.facetFilterByCheckList(filter, datasets);


		Facet_Filters filter1 = new Facet_Filters();
		ModelAndView modelAndView = new ModelAndView("redirect:/filter","command" ,filter1);
		
		return modelAndView;
	}



	// Show dataset
	@ModelAttribute("datasetResults")
	public List<Dataset> datasetResults() {
		return datasets;
	}



	@ModelAttribute("titleValue")
	public List<String> titleValue() {

		List<String> titleValue =new ArrayList<>();
		System.out.println("FacetName"+Facet_Names.Title.toString());
		if(MainService.FacetName_Value.get(Facet_Names.Title.toString())!=null) {
			for(String value: MainService.FacetName_Value.get(Facet_Names.Title.toString()))
			{
				titleValue.add(value);
				//System.out.println("Typevalue :" + value);
			}
		}
		return titleValue;
	}

	@ModelAttribute("typeValue")
	public List<String> typeValue() {

		List<String> typeValue =new ArrayList<>();
		System.out.println("FacetName"+Facet_Names.Type.toString());
		if(MainService.FacetName_Value.get(Facet_Names.Type.toString())!=null) {
			for(String value: MainService.FacetName_Value.get(Facet_Names.Type.toString()))
			{
				typeValue.add(value);
				//System.out.println("Typevalue :" + value);
			}
		}
		return typeValue;
	}

	@ModelAttribute("urlValue")
	public List<String> urlValue() {

		List<String> urlValue =new ArrayList<>();
		System.out.println("FacetName"+Facet_Names.AccessURL.toString());
		if(MainService.FacetName_Value.get(Facet_Names.AccessURL.toString())!=null) {
			for(String value: MainService.FacetName_Value.get(Facet_Names.AccessURL.toString()))
			{
				urlValue.add(value);
				//System.out.println("Typevalue :" + value);
			}
		}
		return urlValue;
	}



	@ModelAttribute("keywordValue")
	public List<String> keywordValue() {

		List<String> keywords =new ArrayList<>();
		System.out.println("FacetName"+Facet_Names.Keyword.toString());
		if(MainService.FacetName_Value.get(Facet_Names.Keyword.toString())!=null) {
			for(String value: MainService.FacetName_Value.get(Facet_Names.Keyword.toString()))
			{
				keywords.add(value);
				//System.out.println("Typevalue :" + value);
			}
		}
		return keywords;
	}

	@ModelAttribute("publihserValue")
	public List<String> publihserValue() {

		List<String> publisherValue =new ArrayList<>();
		if(MainService.FacetName_Value.get(Facet_Names.Publisher.toString())!=null) {
			for(String value: MainService.FacetName_Value.get(Facet_Names.Publisher.toString()))
			{
				publisherValue.add(value);
				//System.out.println("Publishervalue :" + value);
			}
		}
		return publisherValue;
	}
	

	@RequestMapping(path = "/clear", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ModelAndView clear(@ModelAttribute("SpringWeb") Facet_Filters filter, ModelMap model) {

		//		filter.setKeyword_Value(null);
		//		filter.setType_Value(null);
		//		filter.setPublisher_Value(null);

//		Response response = new Response();
//		response.setResults(datasets);
//		response.setUser_filters(filter);
//		response.setFacetName_Value(MainService.FacetName_Value);

		//		Facet_Filters filter = new Facet_Filters();
		// user.setFavoriteFrameworks((new String []{"Spring MVC","Struts 2"}));

		ModelAndView modelAndView = new ModelAndView("index2");
		return modelAndView;
	}

	// Search by checkbox
	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ModelAndView Filters() {

		Facet_Filters filter = new Facet_Filters();
		// user.setFavoriteFrameworks((new String []{"Spring MVC","Struts 2"}));

		ModelAndView modelAndView = new ModelAndView("Search","command" ,filter);
		return modelAndView;
	}


}
