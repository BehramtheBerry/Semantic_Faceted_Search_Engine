/**
 * 
 */
package facetStr;

/**
 * @author DELL
 *
 */
public class FacetName {
	
	private String id;
	private String name;
	private String label;
	
	public FacetName() {
		
	}
	
	public FacetName(String name) {
		this.name = name;
		if(this.label == null) {
			String[] tmp = name.split("/");
			this.label = tmp[tmp.length - 1];
		}
	}
	
	public FacetName(String name, String label) {
		this.name = name;
		this.label = label;
		if(this.label == null) {
			String[] tmp = name.split("/");
			this.label = tmp[tmp.length - 1];
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
		if(this.label == null) {
			String[] tmp = name.split("/");
			this.label = tmp[tmp.length - 1];
		}
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
