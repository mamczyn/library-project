package pl.edu.pjwstk.s32410.library.api.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pjwstk.s32410.library.shared.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByName(String name);
    List<Employee> findBySurname(String surname);
    List<Employee> findByEmail(String email);
    List<Employee> findByPhoneNumber(String phoneNumber);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name% OR e.surname LIKE %:name%")
    List<Employee> findByNameOrSurname(String name);

    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    List<Employee> findByExactEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.phoneNumber = :phoneNumber")
    List<Employee> findByExactPhoneNumber(String phoneNumber);
}
