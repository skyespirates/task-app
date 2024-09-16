package com.skyes.taskapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.skyes.taskapp.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
