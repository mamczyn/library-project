package pl.edu.pjwstk.s32410.projects.library.app.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

import pl.edu.pjwstk.s32410.library.shared.json.JsonUtility;
import pl.edu.pjwstk.s32410.library.shared.model.Author;
import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.library.shared.model.Category;
import pl.edu.pjwstk.s32410.library.shared.model.Customer;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;
import pl.edu.pjwstk.s32410.library.shared.model.Image;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.DataInputContainer;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.exception.DataInputException;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.impl.NumberInput;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.impl.SelectInput;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.impl.TextInput;
import pl.edu.pjwstk.s32410.projects.library.app.service.LibraryService;

@Controller
public class WebsiteController {
	@Autowired
	private LibraryService service;
	
	@GetMapping("/forms")
    public String getFormsPage(Model model) {
		HashMap<String, DataInputContainer> forms = new HashMap<>();
		
		forms.put("author", getAuthorForm());
		forms.put("category", getCategoryForm());
		forms.put("image", getImageForm());
		forms.put("book", getBookForm());
		forms.put("customer", getCustomerForm());
		forms.put("employee", getEmployeeForm());
		forms.put("rental", getRentalForm());
		
		model.addAttribute("forms", forms);
		
        return "forms";
    }
	
	@PostMapping("/forms")
    public String getFormsPageSubmit(@RequestParam Map<String, String> data, Model model) {
		HashMap<String, DataInputContainer> forms = new HashMap<>();
		
		forms.put("author", getAuthorForm());
		forms.put("category", getCategoryForm());
		forms.put("image", getImageForm());
		forms.put("book", getBookForm());
		forms.put("customer", getCustomerForm());
		forms.put("employee", getEmployeeForm());
		forms.put("rental", getRentalForm());
		
		String id = data.get("id");
		
		DataInputContainer form = forms.get(id);
		
		try { 
			String dataId = form.checkAndSend(data); 
			model.addAttribute("message", "Pomyslnie wprowadzono wartosc o ID: " + dataId);
		}
		catch(Exception e) {
			model.addAttribute("message", e.getMessage());
		}
		
		forms.put("author", getAuthorForm());
		forms.put("category", getCategoryForm());
		forms.put("image", getImageForm());
		forms.put("book", getBookForm());
		forms.put("customer", getCustomerForm());
		forms.put("employee", getEmployeeForm());
		forms.put("rental", getRentalForm());
		
		model.addAttribute("forms", forms);
		
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
    	DataInputContainer form = new DataInputContainer("Dodaj Ksiazke") {

			@Override
			public String OnDataCorrectSubmission(Map<String, String> data) {
				Book book = new Book();
				
				Author author = new Author();
				author.setId(UUID.fromString(data.get("author")));
				
				Category category = new Category();
				category.setId(UUID.fromString(data.get("category")));
				
				book.setTitle(data.get("title"));
				book.setIsbn(data.get("isbn"));
				book.setAuthors(List.of(author));
				book.setCategories(List.of(category));
				book.setImages(List.of(data.get("image")));
				
				String response = service.postJSONToAPI("books", book);
				
				book = JsonUtility.fromJson(response, Book.class);
				
				int booksToStorage = 0;
				
				try {
					booksToStorage = Integer.valueOf(data.get("count"));
				} catch(Exception e) {}
				
				for(int i = 0; i < booksToStorage; i++) {
					StorageBook sb = new StorageBook();
					sb.setReference(book);
					service.postJSONToAPI("storage-books", sb);
				}
	
				return book.getId().toString();
			}
    		
    	};
		
    	Map<String, String> authors = service.getAuthors().stream()
    			.collect(Collectors.toMap(a -> a.getId().toString(),
    					a -> a.getName() + " " + a.getSurname()));
    	
    	Map<String, String> categories = service.getCategories().stream()
    			.collect(Collectors.toMap(c -> c.getId().toString(),
    					c -> c.getName()));
    	
    	
		form.add("title", new TextInput("Tytul", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Tytul ksiazki nie moze byc pusty!");
		}));
		
		form.add("author", new SelectInput("Autor", (v) -> {
			if(v == null || v.trim().equals("default")) throw new DataInputException("Autor ksiazki musi byc wybrany!");
		}, authors));
		
		form.add("category", new SelectInput("Kategoria", (v) -> {
			if(v == null || v.trim().equals("default")) throw new DataInputException("Kategoria ksiazki musi byc wybrana!");
		}, categories));
		
		form.add("image", new TextInput("Link Obrazka"));
		
		form.add("isbn", new TextInput("ISBN"));
		
		form.add("count", new NumberInput("Ilosc Egzemplarzy"));
		
		return form;
    }
    
    private DataInputContainer getRentalForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Wypozyczenie") {

    		@Override
    		public String OnDataCorrectSubmission(Map<String, String> data) {
    		    Rental rental = new Rental();
    		    
    		    StorageBook book = new StorageBook();
    		    book.setId(UUID.fromString(data.get("book")));
    		    
    		    Customer customer = new Customer();
    		    customer.setId(UUID.fromString(data.get("customer")));
    		    
    		    Employee employee = new Employee();
    		    employee.setId(UUID.fromString(data.get("employee")));
    		    
    		    LocalDate startDate = null;
    		    LocalDate endDate = null;
    		    try {
    		        startDate = LocalDate.parse(data.get("start"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    		        endDate = LocalDate.parse(data.get("end"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    		    } catch (Exception e) {
    		        throw new DataInputException("Niepoprawny format daty!");
    		    }
    		    
    		    rental.setBook(book);
    		    rental.setCustomer(customer);
    		    rental.setEmployee(employee);
    		    rental.setRentalStart(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    		    rental.setRentalEnd(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    		    
    		    String response = service.postJSONToAPI("rentals", rental);
    		    rental = JsonUtility.fromJson(response, Rental.class);
    		    
    		    return rental.getId().toString();
    		}
    		
    	};
		
    	Map<String, String> books = service.getStorageBooks().stream()
    			.collect(Collectors.toMap(b -> b.getId().toString(),
    					b -> "%s [%s]".formatted(b.getReference().getTitle(), b.getId())));
    	
    	Map<String, String> customers = service.getCustomers().stream()
    			.collect(Collectors.toMap(c -> c.getId().toString(),
    					c -> c.getName() + " " + c.getSurname()));
    	
    	Map<String, String> employees = service.getEmployees().stream()
    			.collect(Collectors.toMap(e -> e.getId().toString(),
    					e -> e.getName() + " " + e.getSurname()));
    	
		form.add("book", new SelectInput("Ksiazka", (v) -> {
			if(v == null || v.trim().equals("default")) throw new DataInputException("Ksiazka musi byc wybrana!");
		}, books));
		
		form.add("customer", new SelectInput("Klient", (v) -> {
			if(v == null || v.trim().equals("default")) throw new DataInputException("Klient musi byc wybrany!");
		}, customers));
		
		form.add("employee", new SelectInput("Pracownik", (v) -> {
			if(v == null || v.trim().equals("default")) throw new DataInputException("Pracownik musi byc wybrany!");
		}, employees));
		
		form.add("start", new TextInput("Start (DD.MM.YYYY)", (v) -> {
			if(v == null || v.trim().equals("") ) throw new DataInputException("Data startu wypozyczenia nie moze byc pusta!");
			
			try { LocalDate.parse(v, DateTimeFormatter.ofPattern("dd.MM.yyyy")); } 
			catch(Exception e) { throw new DataInputException("Data startu jest nieprawidlowa!"); }
		
		}));
		
		form.add("end", new TextInput("Koniec (DD.MM.YYYY)", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Data konca wypozyczenia nie moze byc pusta!");
			
			try { LocalDate.parse(v, DateTimeFormatter.ofPattern("dd.MM.yyyy")); } 
			catch(Exception e) { throw new DataInputException("Data konca jest nieprawidlowa!"); }
		}));
		
		return form;
    }
    
    private DataInputContainer getCategoryForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Kategorie") {

			@Override
			public String OnDataCorrectSubmission(Map<String, String> data) {
			    Category category = new Category();
			    category.setName(data.get("name"));
			    
			    String response = service.postJSONToAPI("categories", category);
			    category = JsonUtility.fromJson(response, Category.class);
			    
			    return category.getId().toString();
			}

    		
    	};
		
		form.add("name", new TextInput("Nazwa", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Nazwa kategorii nie moze byc pusta!");
		}));
		
		return form;
    }
    
    private DataInputContainer getImageForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Zdjecie") {

    		@Override
    		public String OnDataCorrectSubmission(Map<String, String> data) {

    		    Image image = new Image();

    		    image.setBase64Data(data.get("image"));
    		    
    		    String response = service.postJSONToAPI("images", image);
    		    image = JsonUtility.fromJson(response, Image.class);
    		    
    		    return "http://localhost:8080/images/" + image.getId().toString();
    		}
    		
    	};
		
		form.add("image", new TextInput("Obrazek (Base64)", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Base64 obrazka nie moze byc pusty!");
		}));
		
		return form;
    }
    
    private DataInputContainer getEmployeeForm() {
    	DataInputContainer form = new DataInputContainer("Dodaj Pracownika") {

    		@Override
    		public String OnDataCorrectSubmission(Map<String, String> data) {
    		    Employee employee = new Employee();
    		    employee.setName(data.get("name"));
    		    employee.setSurname(data.get("surname"));
    		    employee.setToken(data.get("token"));
    		    employee.setEmail(data.get("email"));
    		    employee.setPhoneNumber(data.get("phone"));
    		    
    		    String response = service.postJSONToAPI("employees", employee);
    		    employee = JsonUtility.fromJson(response, Employee.class);
    		    
    		    return employee.getId().toString();
    		}

    		
    	};
		
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
    	DataInputContainer form = new DataInputContainer("Dodaj Klienta") {

    		@Override
    		public String OnDataCorrectSubmission(Map<String, String> data) {
    		    Customer customer = new Customer();
    		    customer.setName(data.get("name"));
    		    customer.setSurname(data.get("surname"));
    		    customer.setEmail(data.get("email"));
    		    customer.setPhoneNumber(data.get("phone"));
    		    
    		    String response = service.postJSONToAPI("customers", customer);
    		    customer = JsonUtility.fromJson(response, Customer.class);
    		    
    		    return customer.getId().toString();
    		}

    		
    	};
		
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
    	DataInputContainer form = new DataInputContainer("Dodaj Autora") {

    		@Override
    		public String OnDataCorrectSubmission(Map<String, String> data) {
    		    Author author = new Author();
    		    author.setName(data.get("name"));
    		    author.setSurname(data.get("surname"));
    		    
    		    // Make the API call to save the author
    		    String response = service.postJSONToAPI("authors", author);
    		    author = JsonUtility.fromJson(response, Author.class);
    		    
    		    return author.getId().toString();
    		}
    		
    	};
		
		form.add("name", new TextInput("Imie", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Imie autora nie moze byc puste!");
		}));
		
		form.add("surname", new TextInput("Nazwisko", (v) -> {
			if(v == null || v.trim().equals("")) throw new DataInputException("Nazwisko autora nie moze byc puste!");
		}));
		
		return form;
    }
}
