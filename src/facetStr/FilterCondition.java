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
public class FilterCondition {
	
	Map<FacetName, List<String>> filters = new HashMap<FacetName, List<String>>();
	
	List<FacetName> fNameList = new ArrayList<FacetName>();
	List<FacetVal> fValList = new ArrayList<FacetVal>();
	
	public FilterCondition() {
		
	}
	
	

	/**
	 * @param filters
	 * @param fNameList
	 * @param fValList
	 */
	public FilterCondition(Map<FacetName, List<String>> filters, List<FacetName> fNameList, List<FacetVal> fValList) {
		super();
		this.filters = filters;
		this.fNameList = fNameList;
		this.fValList = fValList;
	}
	
	public FilterCondition(Map<FacetName, List<String>> filters)
	{
		this.filters= filters;
	}



	/**
	 * @return the filters
	 */
	public Map<FacetName, List<String>> getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(Map<FacetName, List<String>> filters) {
		this.filters = filters;
	}

	/**
	 * @return the fNameList
	 */
	public List<FacetName> getfNameList() {
		return fNameList;
	}

	/**
	 * @param fNameList the fNameList to set
	 */
	public void setfNameList(List<FacetName> fNameList) {
		this.fNameList = fNameList;
	}

	/**
	 * @return the fValList
	 */
	public List<FacetVal> getfValList() {
		return fValList;
	}

	/**
	 * @param fValList the fValList to set
	 */
	public void setfValList(List<FacetVal> fValList) {
		this.fValList = fValList;
	}
	
	
	

	
}
