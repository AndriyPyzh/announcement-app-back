package com.announcement.repository;

import com.announcement.entity.Announcement;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AnnouncementFullTextSearch implements SearchRepository<Announcement> {

    private EntityManager entityManager;

    @Autowired
    public AnnouncementFullTextSearch(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Announcement> findByParams(String searchParameters, Integer limit) {

        FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(entityManager);

        QueryBuilder tweetQb = fullTextEm.getSearchFactory().buildQueryBuilder().forEntity(Announcement.class).get();

        Query fullTextQuery = tweetQb.keyword().onField("title").andField("description").matching(searchParameters).createQuery();

        return (List<Announcement>) fullTextEm.createFullTextQuery(fullTextQuery).getResultList()
                .stream().limit(limit).collect(Collectors.toList());
    }

}
