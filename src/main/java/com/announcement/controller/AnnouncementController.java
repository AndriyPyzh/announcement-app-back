package com.announcement.controller;

import com.announcement.entity.Announcement;
import com.announcement.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping(path = "", params = {"page", "size"})
    public Page<Announcement> getAnnouncements(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<Announcement> resultPage = announcementService.getAll(page, size);

        if (page > resultPage.getTotalPages()) {
            throw new IndexOutOfBoundsException();
        }
        return resultPage;
    }

    @GetMapping("/{id}")
    public Announcement getAnnouncement(@PathVariable Long id) {
        return announcementService.getById(id);
    }

    @GetMapping(path = "/{id}/similar", params = {"size"})
    public List<Announcement> getSimilarAnnouncements(@PathVariable Long id,
                                                      @RequestParam(name = "size", defaultValue = "3") Integer size) {
        return announcementService.getSimilarAnnouncements(id,size);
    }

    @PostMapping("")
    public void createAnnouncement(@RequestBody Announcement announcement) {
        announcementService.createAnnouncement(announcement);
    }

    @DeleteMapping("/{id}")
    public void deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void deleteAnnouncement(@PathVariable Long id,
                                   @RequestBody Announcement announcement) {
        announcementService.updateById(announcement, id);
    }

}
