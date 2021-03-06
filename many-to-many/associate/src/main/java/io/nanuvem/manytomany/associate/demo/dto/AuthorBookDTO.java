package io.nanuvem.manytomany.associate.demo.dto;

import io.nanuvem.manytomany.associate.demo.dto.simple.AuthorSimpleDTO;
import io.nanuvem.manytomany.associate.demo.dto.simple.BookSimpleDTO;
import lombok.Data;

@Data
public class AuthorBookDTO {

    /**
     * id
     */
    private Long id;

    /**
     * Book.
     */
    private BookSimpleDTO book;
    /**
     * Author.
     */
    private AuthorSimpleDTO author;

}
