/**
 * 
 */
package facetStr;

/**
 * @author DELL
 *
 */
public class FacetVal {
	
	private String id;
	private String type;
	private String label;
	private String object;
	
	public FacetVal() {
		
	}
	
	public FacetVal(String object, String type) {
		this.object = object;
		this.type = type;
		if(this.label == null) {
			this.label = makeLabel(object);
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

	/**
	 * @return the object
	 */
	public String getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(String object) {
		this.object = object;
		if(this.label == null) {
			this.label = makeLabel(object);
		}
	}

	/**
	 * @param object2
	 * @return
	 */
	private String makeLabel(String object2) {
		String[] tmp1 = object2.split("/");
		String[] tmp2 = tmp1[tmp1.length - 1].split("#");
		return tmp2[tmp2.length - 1];
	}
	

}
