package pl.edu.pjwstk.s32410.library.api.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pjwstk.s32410.library.shared.model.Site;

@Repository
public interface SiteRepository extends JpaRepository<Site, UUID> {
    List<Site> findByName(String name);
    List<Site> findByAddress(String address);
    List<Site> findByPhoneNumber(String phoneNumber);

    @Query("SELECT s FROM Site s WHERE s.name LIKE %:name%")
    List<Site> findByNameContaining(String name);

    @Query("SELECT s FROM Site s WHERE s.address LIKE %:address%")
    List<Site> findByAddressContaining(String address);

    @Query("SELECT s FROM Site s WHERE s.phoneNumber = :phoneNumber")
    List<Site> findByExactPhoneNumber(String phoneNumber);
}
