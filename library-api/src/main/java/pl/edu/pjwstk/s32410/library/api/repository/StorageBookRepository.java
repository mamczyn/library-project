package pl.edu.pjwstk.s32410.library.api.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;

@Repository
public interface StorageBookRepository extends JpaRepository<StorageBook, UUID> {
    List<StorageBook> findBySiteId(UUID siteId);
    List<StorageBook> findByReferenceId(UUID referenceId);

    @Query("SELECT sb FROM StorageBook sb WHERE sb.site.name LIKE %:siteName%")
    List<StorageBook> findBySiteName(String siteName);

    @Query("SELECT sb FROM StorageBook sb WHERE sb.reference.title LIKE %:title%")
    List<StorageBook> findByBookTitle(String title);

    @Query("SELECT sb FROM StorageBook sb WHERE sb.reference.isbn = :isbn")
    List<StorageBook> findByBookIsbn(String isbn);

    @Query("SELECT sb FROM StorageBook sb WHERE sb.reference.publisher LIKE %:publisher%")
    List<StorageBook> findByBookPublisher(String publisher);

    @Query("SELECT sb FROM StorageBook sb WHERE sb.reference.language = :language")
    List<StorageBook> findByBookLanguage(String language);
}
