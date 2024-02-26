package cs5031.group.one.thelibrary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the controller of the application.
 */
@RestController
public class LibraryController {

    /**
     * This is a default/basic home controller of the application.
     * @return Return a string "Welcome to the library!".
     */
    @GetMapping("/")
    public String home () {
        return "Welcome to the library!";
    }
}
