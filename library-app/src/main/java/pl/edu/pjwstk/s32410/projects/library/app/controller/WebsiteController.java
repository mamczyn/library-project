package pl.edu.pjwstk.s32410.projects.library.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.DataInputContainer;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.exception.DataInputException;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.impl.SelectInput;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.impl.TextInput;
import pl.edu.pjwstk.s32410.projects.library.app.service.LibraryService;

@Controller
public class WebsiteController {
	@Autowired
	private LibraryService service;
	
	@GetMapping("/data")
    public String getDataPage(@RequestParam(value = "message", required = false) String message, Model model) {
		
		
        return "data";
    }
	
	@GetMapping("/forms")
    public String getFormsPage(@RequestParam(value = "resource", required = false) String resource, Model model) {
		HashMap<String, DataInputContainer> forms = new HashMap<>();
		
		forms.put("author", getAuthorForm());
		
		model.addAttribute("forms", forms);
		
        return "forms";
    }
	
	@PostMapping("/forms")
    public String getFormsPageSubmit(String resource, Model model) {
		HashMap<String, DataInputContainer> forms = new HashMap<>();
		
		forms.put("author", getAuthorForm());
		
		
		
        return "forms";
    }
	
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

        return "search";
    }
    
    private DataInputContainer getBookForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Ksiazke");
		
		form.add("title", new TextInput("Tytul", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Tytul ksiazki nie moze byc pusty!");
		}));
		
		form.add("author", new SelectInput("Autor", (v) -> {
			if(v == null || v.trim().equals("default")) throw new DataInputException("Autor ksiazki musi byc wybrany!");
		}));
		
		form.add("category", new SelectInput("Kategoria", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Kategoria ksiazki musi byc wybrana!");
		}));
		
		form.add("image", new TextInput("Link Obrazka"));
		
		form.add("isbn", new TextInput("ISBN"));
		
		return form;
    }
    
    private DataInputContainer getRentalForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Wypozyczenie");
		
		form.add("start", new TextInput("Start", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Data startu wypozyczenia nie moze byc pusta!");
		}));
		
		form.add("end", new TextInput("Koniec", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Data konca wypozyczenia nie moze byc pusta!");
		}));
		
		form.add("book", new SelectInput("Ksiazka", (v) -> {
			if(v == null || v.trim().equals("default")) throw new DataInputException("Ksiazka musi byc wybrana!");
		}));
		
		form.add("customer", new SelectInput("Klient", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Klient musi byc wybrany!");
		}));
		
		form.add("employee", new SelectInput("Pracownik", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Pracownik musi byc wybrany!");
		}));
		
		return form;
    }
    
    private DataInputContainer getCategoryForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Kategorie");
		
		form.add("name", new TextInput("Nazwa", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Nazwa kategorii nie moze byc pusta!");
		}));
		
		return form;
    }
    
    private DataInputContainer getImageForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Zdjecie");
		
		form.add("image", new TextInput("Obrazek", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Base64 obrazka nie moze byc pusty!");
		}));
		
		return form;
    }
    
    private DataInputContainer getEmployeeForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Pracownika");
		
		form.add("name", new TextInput("Imie", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Imie pracownika nie moze byc puste!");
		}));
		
		form.add("surname", new TextInput("Nazwisko", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Nazwisko pracownika nie moze byc puste!");
		}));
		
		form.add("token", new TextInput("Token", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Token pracownika nie moze byc pusty!");
		}));
		
		form.add("email", new TextInput("Email", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Email pracownika nie moze byc pusty!");
		}));
		
		form.add("phone", new TextInput("Telefon", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Telefon pracownika nie moze byc pusty!");
		}));
		
		return form;
    }
    
    private DataInputContainer getCustomerForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Klienta");
		
		form.add("name", new TextInput("Imie", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Imie klienta nie moze byc puste!");
		}));
		
		form.add("surname", new TextInput("Nazwisko", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Nazwisko klienta nie moze byc puste!");
		}));
		
		form.add("email", new TextInput("Email", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Email klienta nie moze byc pusty!");
		}));
		
		form.add("phone", new TextInput("Telefon", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Telefon klienta nie moze byc pusty!");
		}));
		
		return form;
    }
    
    private DataInputContainer getAuthorForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Autora");
		
		form.add("name", new TextInput("Imie", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Imie autora nie moze byc puste!");
		}));
		
		form.add("surname", new TextInput("Nazwisko", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Nazwisko autora nie moze byc puste!");
		}));
		
		return form;
    }
}
