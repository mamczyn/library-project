package pl.edu.pjwstk.s32410.library.api.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pjwstk.s32410.library.shared.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByTitle(String title);
    List<Book> findByPublicationYear(int year);
    List<Book> findByIsbn(String isbn);
    List<Book> findByLanguage(String language);
    List<Book> findByPublisher(String publisher);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    List<Book> findByTitleContaining(String title);

    @Query("SELECT b FROM Book b WHERE b.description LIKE %:description%")
    List<Book> findByDescriptionContaining(String description);

    @Query("SELECT b FROM Book b WHERE :category MEMBER OF b.categories")
    List<Book> findByCategory(String category);

    @Query("SELECT b FROM Book b WHERE :author MEMBER OF b.authors")
    List<Book> findByAuthor(String author);
}
