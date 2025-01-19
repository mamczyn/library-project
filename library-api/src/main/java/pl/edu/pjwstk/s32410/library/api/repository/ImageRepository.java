package pl.edu.pjwstk.s32410.library.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.edu.pjwstk.s32410.library.shared.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
}
