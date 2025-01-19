package pl.edu.pjwstk.s32410.library.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s32410.library.api.repository.SiteRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Site;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    public Optional<Site> findById(UUID id) {
        return siteRepository.findById(id);
    }

    public Site save(Site site) {
        return siteRepository.save(site);
    }

    public void deleteById(UUID id) {
        siteRepository.deleteById(id);
    }

    public List<Site> findByName(String name) {
        return siteRepository.findByName(name);
    }

    public List<Site> findByAddress(String address) {
        return siteRepository.findByAddress(address);
    }

    public List<Site> findByPhoneNumber(String phoneNumber) {
        return siteRepository.findByPhoneNumber(phoneNumber);
    }
}
