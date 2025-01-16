package pl.edu.pjwstk.s32410.library.shared.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.pjwstk.s32410.library.shared.model.book.StorageBook;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Site site;

    @ManyToOne
    private StorageBook book;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Employee employee;
}