
package pl.edu.pjwstk.s32410.library.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.s32410.library.shared.model.book.Book;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}