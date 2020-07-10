package com.announcement.repository;

import java.util.List;

public interface SearchRepository<T> {
    List<T> findByParams(String searchParameters, Integer limit);
}