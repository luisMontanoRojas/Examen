package com.ucbcba.books.services;


import com.ucbcba.books.entities.Author;
import com.ucbcba.books.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService{

    AuthorRepository authorRepository;

    @Autowired
    @Qualifier(value = "authorRepository")
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public Iterable<Author> listAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Integer id) {

        authorRepository.deleteById(id);
    }
}
