package pl.edu.pjwstk.s32410.projects.library.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.reflect.TypeToken;

import pl.edu.pjwstk.s32410.library.shared.json.JsonUtility;
import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;
import pl.edu.pjwstk.s32410.projects.library.app.config.AppConfig;

@Service
public class LibraryService {
	
	@Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppConfig apiConfig;

    public String getJSONFromAPI(String endpoint) {
    	return restTemplate.getForObject(apiConfig.getApiBaseUrl() + endpoint, String.class);
    }
    
    public List<Book> searchBooks(final String query) {
    	String json = getJSONFromAPI("books");
        
        List<Book> books = JsonUtility.fromJson(json, new TypeToken<List<Book>>() {});
        
        return (query == null) ? books : books.stream().filter(book -> book.containsInTitle(query) ||
				                                 book.containsInDescription(query) ||
				                                 book.getAuthors().stream().anyMatch((author) -> author.containsInNameAndSurname(query)))
                						.collect(Collectors.toList());
    }
    
    public Map<UUID, Integer> getBooksStorageCount(Set<UUID> booksId) {
    	String json = getJSONFromAPI("storage-books");
        
        List<StorageBook> books = JsonUtility.fromJson(json, new TypeToken<List<StorageBook>>() {});
    	
        Map<UUID, Integer> countMap = new HashMap<>();
        
        booksId.forEach(id -> countMap.put(id, 0));
        
        for(StorageBook book : books) {
        	UUID uuid = book.getReference().getId();
        	
        	if(!booksId.contains(uuid)) continue;
        	
        	int count = countMap.get(uuid);
        	
        	countMap.put(uuid, ++count);
        }
        
        return countMap;
    }
	
}
