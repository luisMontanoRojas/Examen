package com.ucbcba.books.services;

import com.ucbcba.books.entities.Author;
import com.ucbcba.books.entities.BookCategory;

public interface AuthorService {

    Iterable<Author> listAllAuthors();
    void saveAuthor(Author author);
    void deleteAuthor(Integer id);
}
