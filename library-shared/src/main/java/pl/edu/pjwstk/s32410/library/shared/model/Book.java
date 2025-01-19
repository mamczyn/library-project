package pl.edu.pjwstk.s32410.library.shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    @ManyToMany
    private List<Author> authors = new ArrayList<>();

    @NotNull
    private String title = "Unknown";
    
    private String description = "Unknown";
    private int publicationYear = 0;
    private String isbn = "Unknown";
    private String language = "English";
    private String publisher = "Unknown";
    private List<String> images;
}