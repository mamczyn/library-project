package pl.edu.pjwstk.s32410.library.shared.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private StorageBook book;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Employee employee;

    @NotNull
    private Date rentalStart;
    
    @NotNull
    private Date rentalEnd;
    
    private Date returnDate;
}
