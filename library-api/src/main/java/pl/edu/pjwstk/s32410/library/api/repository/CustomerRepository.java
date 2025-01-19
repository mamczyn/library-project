package pl.edu.pjwstk.s32410.library.api.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pjwstk.s32410.library.shared.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByName(String name);
    List<Customer> findBySurname(String surname);
    List<Customer> findByEmail(String email);
    List<Customer> findByPhoneNumber(String phoneNumber);
    List<Customer> findBySiteId(UUID siteId);

    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:name% OR c.surname LIKE %:name%")
    List<Customer> findByNameOrSurname(String name);

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    List<Customer> findByExactEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
    List<Customer> findByExactPhoneNumber(String phoneNumber);
}
