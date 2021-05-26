/**
 * 
 */
package facetStr;

import java.util.TreeSet;

/**
 * @author DELL
 *
 */
public class FacetToShow {
	
	private TreeSet<FacetName> facetToShow = new TreeSet<FacetName>();

	public FacetToShow() {
		
	}
	
	
	
	/**
	 * @param st
	 */
	public FacetToShow(TreeSet<FacetName> st) {
		super();
		this.facetToShow = st;
	}



	/**
	 * @return the st
	 */
	public TreeSet<FacetName> getSt() {
		return facetToShow;
	}

	/**
	 * @param st the st to set
	 */
	public void setSt(TreeSet<FacetName> st) {
		this.facetToShow = st;
	}

}
