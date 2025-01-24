package pl.edu.pjwstk.s32410.projects.library.app.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.projects.library.app.config.AppConfig;

@Service
public class SearchService {
	
	@Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppConfig apiConfig;

    public List<Book> searchBooks(final String query) {
    	String json = restTemplate.getForObject(apiConfig.getApiBaseUrl() + "books", String.class);
        
        Gson gson = new Gson();
        
        List<Book> books = gson.fromJson(json, new TypeToken<List<Book>>() {});
        
        return (query == null) ? books : books.stream().filter(book -> book.containsInTitle(query) ||
				                                 book.containsInDescription(query) ||
				                                 book.getAuthors().stream().anyMatch((author) -> author.containsInNameAndSurname(query)))
                						.collect(Collectors.toList());
    }
	
}
