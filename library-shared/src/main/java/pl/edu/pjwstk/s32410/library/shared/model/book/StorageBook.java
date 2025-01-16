package pl.edu.pjwstk.s32410.library.shared.model.book;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pjwstk.s32410.library.shared.model.Site;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class StorageBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Site site;

    @ManyToOne
    private Book reference;
}