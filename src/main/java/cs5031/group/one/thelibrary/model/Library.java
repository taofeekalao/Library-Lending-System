package cs5031.group.one.thelibrary.model;

import org.springframework.data.annotation.Id;

/**
 * This is the record class representing the library.
 * @param libraryId
 * @param name
 */
public record Library(@id Long libraryId, String name) {
}
