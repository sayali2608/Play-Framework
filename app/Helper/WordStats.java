package Helper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import controllers.RepositoryIssuesController.*;
/**
 * Class to perform word level statistics of the titles on the 
 * Repository Issues API.
 * 
 * @author Kirthana Senguttuvan
 * 
 *
 */
public class WordStats {
	/**
	 * 
	 * @param titles   titles passed from the api
	 * @return Returns words and corresponding counts as a Map object
	 */
	public Map<String, Integer> countWords(List<String> titles) {
		List<String> parts = new ArrayList();
		for(String title : titles) {
			if(!(title == null || title.isEmpty())){
				//SPLITTING THE TITLES INTO INDIVIDUAL WORDS FOR STATS
				String[] part = (title.split("[\\s+\",-.\\\\|!\\[\\]]"));
				for(String p : part)
					parts.add(p.toLowerCase());
			}
		}
		// PERFROMING COUNT ON WORDS
		Map<String, Integer> counts =
				parts.stream()
				.filter(i -> !i.isEmpty())
				.collect(
                    Collectors.toMap(
                        w -> w, w -> 1, Integer::sum));
		//SORTING THE RESULT IN DESCENDING ORDER AS PER THE REQUIREMENT
		return counts
				.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));
		}
}
