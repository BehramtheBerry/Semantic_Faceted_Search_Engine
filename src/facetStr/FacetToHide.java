/**
 * 
 */
package facetStr;

import java.util.TreeSet;

/**
 * @author DELL
 *
 */
public class FacetToHide {
	
	private TreeSet<FacetName> facetToHide = new TreeSet<FacetName>();

	public FacetToHide() {
		
	}
	
	
	
	/**
	 * @param st
	 */
	public FacetToHide(TreeSet<FacetName> st) {
		super();
		this.facetToHide = st;
	}



	/**
	 * @return the st
	 */
	public TreeSet<FacetName> getSt() {
		return facetToHide;
	}

	/**
	 * @param st the st to set
	 */
	public void setSt(TreeSet<FacetName> st) {
		this.facetToHide = st;
	}
	
	

}
