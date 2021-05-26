/**
 * 
 */
package facetStr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DELL
 *
 */
public class FacetValueToDataset {
	
	private Map<FacetVal, ArrayList<Dataset>> map = new HashMap<FacetVal, ArrayList<Dataset>>();

	public FacetValueToDataset() {
		
	}
	
	

	/**
	 * @param map
	 */
	public FacetValueToDataset(Map<FacetVal, ArrayList<Dataset>> map) {
		super();
		this.map = map;
	}



	/**
	 * @return the map
	 */
	public Map<FacetVal, ArrayList<Dataset>> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<FacetVal, ArrayList<Dataset>> map) {
		this.map = map;
	}
	
	
	

}
