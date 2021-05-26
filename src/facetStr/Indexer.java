/**
 * 
 */
package facetStr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

/**
 * @author DELL
 *
 */
public class Indexer {

	protected static MultiValuedMap<String, String> map = new HashSetValuedHashMap<>();

	public void addDataToSearchMap(String id, String text, String delimiter) {
		String [] words = cleanText(text).split(delimiter);
		for(String word : words)
			if(!word.isEmpty())
				map.put(word, id);
	}

	public static Collection<String> getIds(String keyword){
		return map.get(keyword.toLowerCase());
	}
	
	public static void iterateUsingIteratorAndEntry() {
//		List<Dataset> dataset = new ArrayList<>();
	    Iterator<Map.Entry<String, String>> iterator = map.entries().iterator();
	    while (iterator.hasNext()) {
	        Map.Entry<String, String> entry = iterator.next();
	        System.out.println(entry.getKey() + ":" + entry.getValue());
	    }
	}

	public static<K, V> void iterate(MultiValuedMap<K, V> multiValuedMap)
	{
		for (K key: multiValuedMap.keySet()) {
			System.out.println(key + ": " + multiValuedMap.get(key));
		}
	}

	/**
	 * @param text
	 * @return
	 */
	private String cleanText(String text) {
		return text.toLowerCase();
	}
}
