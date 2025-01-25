
package pl.edu.pjwstk.s32410.library.api.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import pl.edu.pjwstk.s32410.library.api.service.AuthorService;
import pl.edu.pjwstk.s32410.library.api.service.BookService;
import pl.edu.pjwstk.s32410.library.api.service.CategoryService;
import pl.edu.pjwstk.s32410.library.api.service.StorageBookService;
import pl.edu.pjwstk.s32410.library.shared.json.JsonUtility;
import pl.edu.pjwstk.s32410.library.shared.model.Author;
import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.library.shared.model.Category;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;

@RestController
public class ScraperController {

    @Autowired
    private BookService bookService;
    
    @Autowired
    private StorageBookService storageService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/scrape")
    public String scrapeData(@RequestParam String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements scriptElements = doc.select("script");
            Element scriptElement = null;

            for (Element element : scriptElements) {
                if (element.html().contains("window.insider_object.listing")) {
                    scriptElement = element;
                    break;
                }
            }

            if (scriptElement == null) {
                return "No script tag containing 'window.insider_object.listing' found.";
            }

            String json = scriptElement.html().split("window.insider_object.listing=")[1].split("};")[0] + "}";

            ObjectMapper objectMapper = new ObjectMapper();
            Listing listing = objectMapper.readValue(json, Listing.class);
            
            int scrapedBookCount = 0;
            
            for (Item item : listing.getItems()) {
            	System.out.println(JsonUtility.toJson(item));
            	
            	try {
	                Book book = new Book();
	                book.setTitle(item.getName());
	                book.setImages(Collections.singletonList(item.getProductImageUrl()));
	                book.setDescription("The description could not be scraped!");
	                book.setPublicationYear(0);
	                book.setIsbn(item.getCustom().getIsbn());
	                book.setLanguage("Polish");
	                book.setPublisher(item.getCustom().getBrand());
	
	                Set<Author> authors = new HashSet<>();
	                
	                Author author = new Author();
	                author.setName(item.getCustom().getAuthor().split(" ")[0]);
	                
	                if (item.getCustom().getAuthor().split(" ").length > 1) {
	                    author.setSurname(item.getCustom().getAuthor().split(" ")[1]);
	                }
	
	                List<Author> existingAuthors = authorService.findBySurname(author.getSurname());
	                
	                if (!existingAuthors.isEmpty()) author = existingAuthors.get(0);
	                else author = authorService.save(author);
	                
	                authors.add(author);
	                book.setAuthors(new ArrayList<>(authors));
	
	                Set<Category> categories = new HashSet<>();
	                for (String categoryName : item.getTaxonomy()) {
	                    Category category = new Category();
	                    category.setName(categoryName);
	
	                    List<Category> existingCategories = categoryService.findByName(category.getName());
	                    if (!existingCategories.isEmpty()) {
	                        category = existingCategories.get(0);
	                    } else {
	                        category = categoryService.save(category);
	                    }
	                    categories.add(category);
	                }
	                book.setCategories(new ArrayList<>(categories));
	
	                Book result = bookService.save(book);
	                
	                Random rand = new Random();

	                int n = 6;
	                int toStore = (int)(Math.random() * n);
	                
	                for(int i = 0; i < toStore; i++) {
	                	storageService.save(new StorageBook(null, result));
	                }
	                scrapedBookCount++;
            	} catch(Exception e) { 
            		System.out.println("There was an error saving scraped book %s : %s".formatted(item.getName(), e.getMessage())); 
            	};
            }

            return "Data scraped and saved successfully! (Scraped %s books)".formatted(scrapedBookCount);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unexpected error occurred: " + e.getMessage();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Listing {
        @JsonProperty("items")
        private List<Item> items;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Item {
    	@JsonProperty("product_image_url") private String productImageUrl;
        private String id;
        private String name;
        private List<String> taxonomy;
        private String currency;
        private String url;
        private Custom custom;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Custom {
        private String shortName;
        private String unitLowest30dPrice;
        private String ean;
        private String isbn;
        private String tag;
        private String author;
        private String brand;
        private String category1;
        private String category2;
        private String category3;
        private String releaseDate;
        private String lifeStage;
        private String bestseller;
        private String recommended;
        private String patronate;
        private String availability;
        private String timeToShip;
        private int timeToShipDays;
        private String condition;
        private double reviewsAvg;
        private int reviewsCnt;
        private String format;
        private String seria;
        private String gaClientId;
        private String gaSessionId;
        private String gaSessionCount;
    }
}
