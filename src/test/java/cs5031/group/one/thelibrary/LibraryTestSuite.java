package cs5031.group.one.thelibrary;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
                TheLibraryApplicationTests.class,
                LibraryMVCTests.class,
                LibraryControllerTests.class })

public class LibraryTestSuite {

}
