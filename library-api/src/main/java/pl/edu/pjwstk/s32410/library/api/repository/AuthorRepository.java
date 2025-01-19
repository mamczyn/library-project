package pl.edu.pjwstk.s32410.library.api.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pjwstk.s32410.library.shared.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    List<Author> findByName(String name);
    List<Author> findBySurname(String surname);
    List<Author> findByNationality(String nationality);
    List<Author> findByBiographyContaining(String keyword);

    @Query("SELECT a FROM Author a WHERE a.name LIKE %:name% OR a.surname LIKE %:name%")
    List<Author> findByNameOrSurname(String name);

    @Query("SELECT a FROM Author a WHERE a.nationality = :nationality")
    List<Author> findByExactNationality(String nationality);

    @Query("SELECT a FROM Author a WHERE a.biography LIKE %:keyword%")
    List<Author> findByBiographyKeyword(String keyword);
}
