package pl.edu.pjwstk.s32410.library.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.edu.pjwstk.s32410.library.shared.model.book.Author;

public interface AuthorRepository  extends JpaRepository<Author, UUID> {

}
