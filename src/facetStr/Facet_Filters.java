/**
 * 
 */
package facetStr;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

/**
 * @author DELL
 *
 */
@Configuration
public class Facet_Filters {
	private Map<String, List<String>> FacetName_Values;
	
	//private String[] FacetNames = {"Type", "Publisher"};

	private String[] Title_Value;
	private String[] Type_Value;
	private String[] Publisher_Value;
	private String[] Keyword_Value;
	private String[] URL_Value;	
	private String[] Licence_Value;
	private String[] Description_Value;
	/**
	 * @return the uRL_Value
	 */
	public String[] getURL_Value() {
		return URL_Value;
	}

	/**
	 * @param uRL_Value the uRL_Value to set
	 */
	public void setURL_Value(String[] uRL_Value) {
		URL_Value = uRL_Value;
	}
	
	/**
	 * @return the title_Value
	 */
	public String[] getTitle_Value() {
		return Title_Value;
	}

	/**
	 * @param title_Value the title_Value to set
	 */
	public void setTitle_Value(String[] title_Value) {
		Title_Value = title_Value;
	}



	/**
	 * @return the keyword_Value
	 */
	public String[] getKeyword_Value() {
		return Keyword_Value;
	}

	/**
	 * @param keyword_Value the keyword_Value to set
	 */
	public void setKeyword_Value(String[] keyword_Value) {
		Keyword_Value = keyword_Value;
	}

	/**
	 * @return the publisher_Value
	 */
	public String[] getPublisher_Value() {
		return Publisher_Value;
	}

	/**
	 * @param publisher_Value the publisher_Value to set
	 */
	public void setPublisher_Value(String[] publisher_Value) {
		Publisher_Value = publisher_Value;
	}

	/**
	 * @return the type_Value
	 */
	public String[] getType_Value() {
		return Type_Value;
	}

	/**
	 * @param type_Value the type_Value to set
	 */
	public void setType_Value(String[] type_Value) {
		Type_Value = type_Value;
	}

	/**
	 * @return the facetName_Values
	 */
	public Map<String, List<String>> getFacetName_Values() {
		return FacetName_Values;
	}

	/**
	 * @param facetName_Values the facetName_Values to set
	 */
	public void setFacetName_Values(Map<String, List<String>> facetName_Values) {
		FacetName_Values = facetName_Values;
	}

	/**
	 * @return the licence_Value
	 */
	public String[] getLicence_Value() {
		return Licence_Value;
	}

	/**
	 * @param licence_Value the licence_Value to set
	 */
	public void setLicence_Value(String[] licence_Value) {
		Licence_Value = licence_Value;
	}

	/**
	 * @return the description_Value
	 */
	public String[] getDescription_Value() {
		return Description_Value;
	}

	/**
	 * @param description_Value the description_Value to set
	 */
	public void setDescription_Value(String[] description_Value) {
		Description_Value = description_Value;
	}   

}
