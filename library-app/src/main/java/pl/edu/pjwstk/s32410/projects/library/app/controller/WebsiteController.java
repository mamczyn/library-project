package pl.edu.pjwstk.s32410.projects.library.app.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.projects.library.app.service.LibraryService;

@Controller
public class WebsiteController {
	@Autowired
	private LibraryService service;
	
    @GetMapping("/")
    public String getSearchPage(@RequestParam(value = "search", required = false) String search, Model model) {
        if (search == null) search = "";

        List<Book> books = service.searchBooks(search);
        
        books.forEach((book) -> {
        	System.out.println(book.getTitle() + " " + book.getDescription());
        	book.getAuthors().forEach((author) -> {
        		System.out.println(author.getName() + " " + author.getSurname());
        	});
        	System.out.println();
        });
        
        Map<UUID, Integer> storageCount = service.getBooksStorageCount(books.stream()
        											.map((book) -> book.getId())
        											.collect(Collectors.toSet()));
        
        model.addAttribute("search", search);
        model.addAttribute("results", books);
        model.addAttribute("storage", storageCount);

        return "index";
    }
}
