/**
 * 
 */
package facetStr;

import java.util.List;
import org.springframework.context.annotation.Configuration;


/**
 * @author DELL
 *
 */
@Configuration
public class Dataset {
	
//	private DataSetID IID;
	private String ID;
//	private Snippet id;
//	private Map<FacetName, FacetVal> facetValueMap = new HashMap<FacetName, FacetVal>();
//	private Map<String, List<String>> facetMap = new HashMap<String, List<String>>();
	private String Title;
	private String Description;
	private String AccessURL;
	private String Type;
	private String Publisher;
	private String Licence;
	private List<String> Keyword;
	
	
	
	public boolean equals(final Dataset o) {
	    if (this.getID() == o.getID()) return true;
	    if (o == null || this.getClass() != o.getClass()) return false;

	    final Dataset news = (Dataset) o;

	    return this.getID() == news.getID();

	}



	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}



	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}



	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}



	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}



	/**
	 * @return the accessURL
	 */
	public String getAccessURL() {
		return AccessURL;
	}



	/**
	 * @param accessURL the accessURL to set
	 */
	public void setAccessURL(String accessURL) {
		AccessURL = accessURL;
	}



	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}



	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		Type = type;
	}



	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return Publisher;
	}



	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		Publisher = publisher;
	}



	/**
	 * @return the licence
	 */
	public String getLicence() {
		return Licence;
	}



	/**
	 * @param licence the licence to set
	 */
	public void setLicence(String licence) {
		Licence = licence;
	}



	/**
	 * @return the keyword
	 */
	public List<String> getKeyword() {
		return Keyword;
	}



	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(List<String> keyword) {
		Keyword = keyword;
	}



	/**
	 * @param iD
	 * @param title
	 * @param description
	 * @param accessURL
	 * @param type
	 * @param publisher
	 * @param licence
	 * @param keyword
	 */
	public Dataset(String iD, String title, String description, String accessURL, String type, String publisher,
			String licence, List<String> keyword) {
		super();
		ID = iD;
		Title = title;
		Description = description;
		AccessURL = accessURL;
		Type = type;
		Publisher = publisher;
		Licence = licence;
		Keyword = keyword;
	}

	
	public Dataset() {
		
	}
	
	
}
