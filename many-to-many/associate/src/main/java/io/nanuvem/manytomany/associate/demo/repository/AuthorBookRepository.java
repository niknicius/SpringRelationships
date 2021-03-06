package io.nanuvem.manytomany.associate.demo.repository;

import io.nanuvem.manytomany.associate.demo.core.repository.BaseRepository;
import io.nanuvem.manytomany.associate.demo.model.AuthorBook;
import org.springframework.data.jpa.repository.Query;

public interface AuthorBookRepository extends BaseRepository<AuthorBook, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM author_book WHERE author_id = ?1 and book_id = ?2")
    AuthorBook findAuthorBookByAuthorAndBook(Long authorId, Long bookId);

}
