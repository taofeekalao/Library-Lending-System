package cs5031.group.one.thelibrary;

import cs5031.group.one.thelibrary.controller.LibraryControllerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * A basic Library test suite approach was used to provide the capability of
 * segregating the tests into groups.
 */

@Suite
@SelectClasses({
                TheLibraryApplicationTests.class,
                LibraryMVCTests.class,
                LibraryControllerTests.class,
                LibraryControllerTest.class })

public class LibraryTestSuite {

}
