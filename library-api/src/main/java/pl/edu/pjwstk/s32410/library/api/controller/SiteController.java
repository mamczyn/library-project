package pl.edu.pjwstk.s32410.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.s32410.library.api.service.SiteService;
import pl.edu.pjwstk.s32410.library.shared.model.Site;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @GetMapping
    public List<Site> getAllSites() {
        return siteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable UUID id) {
        Optional<Site> site = siteService.findById(id);
        return site.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Site createSite(@RequestBody Site site) {
        return siteService.save(site);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Site> updateSite(@PathVariable UUID id, @RequestBody Site site) {
        if (!siteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        site.setId(id);
        return ResponseEntity.ok(siteService.save(site));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable UUID id) {
        if (!siteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        siteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name")
    public List<Site> getSitesByName(@RequestParam String name) {
        return siteService.findByName(name);
    }

    @GetMapping("/address")
    public List<Site> getSitesByAddress(@RequestParam String address) {
        return siteService.findByAddress(address);
    }

    @GetMapping("/phone-number")
    public List<Site> getSitesByPhoneNumber(@RequestParam String phoneNumber) {
        return siteService.findByPhoneNumber(phoneNumber);
    }
}
