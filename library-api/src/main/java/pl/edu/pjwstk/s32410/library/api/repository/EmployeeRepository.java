package pl.edu.pjwstk.s32410.library.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}