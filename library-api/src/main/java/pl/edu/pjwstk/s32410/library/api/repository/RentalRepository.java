package pl.edu.pjwstk.s32410.library.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pjwstk.s32410.library.shared.model.Rental;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {
    List<Rental> findByCustomerId(UUID customerId);
    List<Rental> findByRentalStartBetween(Date startDate, Date endDate);
    List<Rental> findByEmployeeId(UUID employeeId);
    List<Rental> findByBookReferenceId(UUID referenceId);
    
    @Query("SELECT r FROM Rental r WHERE r.book.id = :bookId")
    List<Rental> findByBookId(UUID bookId);
    
    @Query("SELECT r FROM Rental r WHERE r.book.reference.title LIKE %:title%")
    List<Rental> findByBookTitle(String title);

    @Query("SELECT r FROM Rental r WHERE r.customer.name LIKE %:name% OR r.customer.surname LIKE %:name%")
    List<Rental> findByCustomerName(String name);

    @Query("SELECT r FROM Rental r WHERE r.employee.name LIKE %:name% OR r.employee.surname LIKE %:name%")
    List<Rental> findByEmployeeName(String name);

    @Query("SELECT r FROM Rental r WHERE :category MEMBER OF r.book.reference.categories")
    List<Rental> findByBookCategory(String category);

    @Query("SELECT r FROM Rental r WHERE :author MEMBER OF r.book.reference.authors")
    List<Rental> findByAuthorName(String author);

    @Query("SELECT r FROM Rental r WHERE r.returnDate IS NULL")
    List<Rental> findActiveRentals();

    @Query("SELECT r FROM Rental r WHERE r.returnDate IS NOT NULL")
    List<Rental> findCompletedRentals();

    @Query("SELECT r FROM Rental r WHERE r.rentalEnd < CURRENT_DATE")
    List<Rental> findOverdueRentals();
}
