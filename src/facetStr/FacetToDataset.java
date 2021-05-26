/**
 * 
 */
package facetStr;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DELL
 *
 */
public class FacetToDataset {
	
	private Map<FacetName, FacetValueToDataset> FacetToDataset = new HashMap<FacetName, FacetValueToDataset>();
	
	public FacetToDataset() {
		
	}
	

	/**
	 * @param map
	 */
	public FacetToDataset(Map<FacetName, FacetValueToDataset> map) {
		super();
		this.FacetToDataset = map;
	}



	/**
	 * @return the map
	 */
	public Map<FacetName, FacetValueToDataset> getMap() {
		return FacetToDataset;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<FacetName, FacetValueToDataset> map) {
		this.FacetToDataset = map;
	}

	
	

}
