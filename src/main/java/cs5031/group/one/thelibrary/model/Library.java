package cs5031.group.one.thelibrary.model;

import org.springframework.data.annotation.Id;

/**
 * This is the record class representing the library.
 *
 * @param libraryId This is the id of the library.
 * @param name      This is the name of the library.
 */
public record Library(@Id Long libraryId, String name) {
}
