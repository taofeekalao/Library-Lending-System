package cs5031.group.one.thelibrary.model;

import org.springframework.data.annotation.Id;

/**
 * This is a record class representing the book item in the library.
 *
 * @param bookId   This is the unique id of the book item.
 * @param isbn     This is the ISBN of the book item.
 * @param title    This is the title of the book item.
 * @param author   This is the author of the book item.
 * @param quantityInLibrary This is the quantity of the book item in the library.
 * @param quantityCheckedOut This is the quantity of the book item in the library.
 */
public record Book(@Id Long bookId, String isbn, String title, String author, int quantityInLibrary,
                   int quantityCheckedOut) {
}
