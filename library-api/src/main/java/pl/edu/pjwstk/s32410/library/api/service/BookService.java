
package pl.edu.pjwstk.s32410.library.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pl.edu.pjwstk.s32410.library.api.exceptions.author.AuthorNotFoundException;
import pl.edu.pjwstk.s32410.library.api.exceptions.category.CategoryNotFoundException;
import pl.edu.pjwstk.s32410.library.api.repository.BookRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Author;
import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.library.shared.model.Category;

@Service
public class BookService {
	@Autowired private CategoryService categoryService;
	@Autowired private AuthorService authorService;
    @Autowired private BookRepository books;

    public boolean exists(Book book) {
    	return book != null &&  existsById(book.getId());
    }
    
    public boolean existsById(UUID id) {
    	return books.existsById(id);
    }
    
    public List<Book> findAll() {
        return books.findAll();
    }

    @Cacheable(value = "book", key = "#id", unless = "#result == null")
    public Optional<Book> findById(UUID id) {
        return books.findById(id);
    }

    @CacheEvict(value = "book", key = "#book.id")
    public Book save(Book book) {
    	List<Author> authors = book.getAuthors();
    	List<Category> categories = book.getCategories();
    	
    	for(Author author : authors) {
    		if(!authorService.exists(author)) throw new AuthorNotFoundException();
    	}
    	
    	for(Category category : categories) {
    		if(!categoryService.exists(category)) throw new CategoryNotFoundException();
    	}
    			
        return books.save(book);
    }

    @CacheEvict(value = "book", key = "#id")
    public void deleteById(UUID id) {
        books.deleteById(id);
    }

    public List<Book> findByTitle(String title) {
        return books.findByTitle(title);
    }

    public List<Book> findByPublicationYear(int year) {
        return books.findByPublicationYear(year);
    }

    public List<Book> findByIsbn(String isbn) {
        return books.findByIsbn(isbn);
    }

    public List<Book> findByLanguage(String language) {
        return books.findByLanguage(language);
    }

    public List<Book> findByPublisher(String publisher) {
        return books.findByPublisher(publisher);
    }
}
