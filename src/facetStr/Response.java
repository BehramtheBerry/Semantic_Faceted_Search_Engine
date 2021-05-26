/**
 * 
 */
package facetStr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author DELL
 *
 */
public class Response {
	
	private List<Dataset> results;
	
	//private List<FacetName> facetName;
	//private List<FacetVal> facetVals;
	//private HashMap<String, Dataset> DatasetMap = new HashMap<String, Dataset>();
	//private int size;
	//private FacetName firstFacetName;
	private  Map<String,List<String>> FacetName_Value = new HashMap<String, List<String>>();
	
	private  Facet_Filters user_filters = new Facet_Filters();
	
	public Response() {
		
	}

	/**
	 * @return the results
	 */
	public List<Dataset> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<Dataset> results) {
		this.results = results;
	}


	/**
	 * @return the facetName_Value
	 */
	public Map<String, List<String>> getFacetName_Value() {
		return FacetName_Value;
	}

	/**
	 * @param facetName_Value the facetName_Value to set
	 */
	public void setFacetName_Value(Map<String, List<String>> facetName_Value) {
		FacetName_Value = facetName_Value;
	}

	/**
	 * @return the user_filters
	 */
	public Facet_Filters getUser_filters() {
		return user_filters;
	}

	/**
	 * @param user_filters the user_filters to set
	 */
	public void setUser_filters(Facet_Filters user_filters) {
		this.user_filters = user_filters;
	}
	
//
//	/**
//	 * @return the facetNaes
//	 */
//	public List<FacetName> getFacetNaes() {
//		return facetName;
//	}
//
//	/**
//	 * @param facetNaes the facetNaes to set
//	 */
//	public void setFacetNaes(List<FacetName> facetNaes) {
//		this.facetName = facetNaes;
//	}
//
//	/**
//	 * @return the facetVals
//	 */
//	public List<FacetVal> getFacetVals() {
//		return facetVals;
//	}
//
//	/**
//	 * @param facetVals the facetVals to set
//	 */
//	public void setFacetVals(List<FacetVal> facetVals) {
//		this.facetVals = facetVals;
//	}
//
//	/**
//	 * @return the datasetMap
//	 */
//	public HashMap<String, Dataset> getDatasetMap() {
//		return DatasetMap;
//	}
//
//	/**
//	 * @param datasetMap the datasetMap to set
//	 */
//	public void setDatasetMap(HashMap<String, Dataset> datasetMap) {
//		DatasetMap = datasetMap;
//	}
//
//	/**
//	 * @return the size
//	 */
//	public int getSize() {
//		return size;
//	}
//
//	/**
//	 * @param size the size to set
//	 */
//	public void setSize(int size) {
//		this.size = size;
//	}
//
//	/**
//	 * @return the firstFacetName
//	 */
//	public FacetName getFirstFacetName() {
//		return firstFacetName;
//	}
//
//	/**
//	 * @param firstFacetName the firstFacetName to set
//	 */
//	public void setFirstFacetName(FacetName firstFacetName) {
//		this.firstFacetName = firstFacetName;
//	}
//
//	/**
//	 * @param results
//	 * @param facetNaes
//	 * @param facetVals
//	 * @param datasetMap
//	 * @param size
//	 * @param firstFacetName
//	 */
//	public Response(List<Dataset> results, List<FacetName> facetNaes, List<FacetVal> facetVals,
//			HashMap<String, Dataset> datasetMap, int size, FacetName firstFacetName) {
//		super();
//		this.results = results;
//		this.facetName = facetNaes;
//		this.facetVals = facetVals;
//		DatasetMap = datasetMap;
//		this.size = size;
//		this.firstFacetName = firstFacetName;
//	}
//
//	/**
//	 * @return the facetName_Value
//	 */
//	public static Map<String,List<String>> getFacetName_Value() {
//		return FacetName_Value;
//	}
//
//	/**
//	 * @param facetName_Value the facetName_Value to set
//	 */
//	public static void setFacetName_Value(Map<String,List<String>> facetName_Value) {
//		FacetName_Value = facetName_Value;
//	}
	

}
