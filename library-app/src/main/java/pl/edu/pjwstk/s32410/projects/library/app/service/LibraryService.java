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
import pl.edu.pjwstk.s32410.library.shared.model.Author;
import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.library.shared.model.Category;
import pl.edu.pjwstk.s32410.library.shared.model.Customer;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;
import pl.edu.pjwstk.s32410.library.shared.model.Image;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;
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
    
    public String postJSONToAPI(String endpoint, Object object) {
    	return restTemplate.postForObject(apiConfig.getApiBaseUrl() + endpoint, object, String.class);
    }
    
    public List<StorageBook> getStorageBooks() {
    	String json = getJSONFromAPI("storage-books");
        
        return JsonUtility.fromJson(json, new TypeToken<List<StorageBook>>() {});
    }
    
    public List<Image> getImages() {
    	String json = getJSONFromAPI("images");
        
        return JsonUtility.fromJson(json, new TypeToken<List<Image>>() {});
    }
    
    public List<Rental> getRentals() {
    	String json = getJSONFromAPI("rentals");
        
        return JsonUtility.fromJson(json, new TypeToken<List<Rental>>() {});
    }
    
    public List<Employee> getEmployees() {
    	String json = getJSONFromAPI("employees");
        
        return JsonUtility.fromJson(json, new TypeToken<List<Employee>>() {});
    }
    
    public List<Customer> getCustomers() {
    	String json = getJSONFromAPI("customers");
        
        return JsonUtility.fromJson(json, new TypeToken<List<Customer>>() {});
    }
    
    public List<Category> getCategories() {
    	String json = getJSONFromAPI("categories");
        
        return JsonUtility.fromJson(json, new TypeToken<List<Category>>() {});
    }
    
    public List<Author> getAuthors() {
    	String json = getJSONFromAPI("authors");
        
        return JsonUtility.fromJson(json, new TypeToken<List<Author>>() {});
    }
    
    public List<Book> getBooks() {
    	String json = getJSONFromAPI("books");
        
        return JsonUtility.fromJson(json, new TypeToken<List<Book>>() {});
    }
    
    public void AddAuthor() {
    	
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
