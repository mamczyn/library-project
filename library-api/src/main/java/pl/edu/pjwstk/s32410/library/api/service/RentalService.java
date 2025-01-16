
package pl.edu.pjwstk.s32410.library.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s32410.library.api.repository.RentalRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findById(UUID id) {
        return rentalRepository.findById(id);
    }

    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteById(UUID id) {
        rentalRepository.deleteById(id);
    }
}
