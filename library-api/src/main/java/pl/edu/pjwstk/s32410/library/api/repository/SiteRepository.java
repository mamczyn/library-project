package pl.edu.pjwstk.s32410.library.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Site;
import java.util.UUID;

public interface SiteRepository extends JpaRepository<Site, UUID> {
}