package pl.edu.pjwstk.s32410.projects.library.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.projects.library.app.service.SearchService;

@Controller
public class WebsiteController {
	@Autowired
	private SearchService searchService;
	
    @GetMapping("/")
    public String getSearchPage(@RequestParam(value = "search", required = false) String search, Model model) {
        if (search == null) search = "";

        List<Book> books = searchService.searchBooks(search);
        
        books.forEach((book) -> {
        	System.out.println(book.getTitle() + " " + book.getDescription());
        	book.getAuthors().forEach((author) -> {
        		System.out.println(author.getName() + " " + author.getSurname());
        	});
        	System.out.println();
        });
        
        model.addAttribute("search", search);
        model.addAttribute("results", books);

        return "index";
    }
}
