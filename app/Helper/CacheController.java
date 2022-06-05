package Helper;
import model.Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheController {

    HashMap<String, List<Repositories>> cache = new HashMap<String, List<Repositories>>();
    public Map<String, List<Repositories>> get_cache(){
        return cache;
    }

    public void setCache(String keyword, List<Repositories> repositories){
        cache.put(keyword, repositories);
    }

}
