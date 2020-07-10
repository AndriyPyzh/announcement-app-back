package com.announcement.service;

import com.announcement.entity.Announcement;
import com.announcement.repository.AnnouncementFullTextSearch;
import com.announcement.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementFullTextSearch announcementFullTextSearch;

    @Autowired
    public AnnouncementService(AnnouncementRepository announcementRepository, AnnouncementFullTextSearch announcementFullTextSearch) {
        this.announcementRepository = announcementRepository;
        this.announcementFullTextSearch = announcementFullTextSearch;
    }


    public Page<Announcement> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return announcementRepository.findAll(pageable);
    }

    public Announcement getById(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void createAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    public void deleteById(Long id) {
        announcementRepository.deleteById(id);
    }

    public void updateById(Announcement updateAnnouncement, Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        announcement.setTitle(updateAnnouncement.getTitle());
        announcement.setDescription(updateAnnouncement.getDescription());
        announcementRepository.save(announcement);
    }

    public List<Announcement> getSimilarAnnouncements(Long id, Integer size) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        String searchParams = announcement.getTitle() + " " + announcement.getDescription();

        return announcementFullTextSearch.findByParams(searchParams, size + 1)
                .stream().filter(x -> !x.getId().equals(id)).collect(Collectors.toList());
    }
}
