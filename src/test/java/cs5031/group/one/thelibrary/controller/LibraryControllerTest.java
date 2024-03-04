package cs5031.group.one.thelibrary.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cs5031.group.one.thelibrary.model.Member;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryControllerTest {

        @Autowired
        TestRestTemplate restTemplate;

        @BeforeEach
        void setUp() {

        }

        /**
         * Test case to test the end-point for creating a new member.
         */
        @Test
        @DirtiesContext
        void shouldCreateANewMember() {
                Member newMember = new Member(null, "Mathew Lewis",
                                "University of St Andrews, Walter Bower House Eden Campus Main Street, Guard Bridge Fife, KY16 0US",
                                "mathew.lewis@st-andrews.ac.uk");
                ResponseEntity<Void> createResponse = restTemplate
                                .postForEntity("/library/member", newMember, Void.class);

                assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                URI locationOfNewMember = createResponse.getHeaders().getLocation();
                ResponseEntity<String> getResponse = restTemplate
                                .getForEntity(locationOfNewMember, String.class);
                assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

                DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
                Number id = documentContext.read("@.memberId");
                String email = documentContext.read("@.emailAddress");

                assertThat(id).isNotNull();
                assertThat(email).isEqualTo("mathew.lewis@st-andrews.ac.uk");
        }

        /**
         * Test case to test the end-point for getting a new member.
         */
        @Test
        @DirtiesContext
        void shoutReturnAMemberWhenDataIsSaved() {
                ResponseEntity<String> response = restTemplate
                                .getForEntity("/library/member/5", String.class);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

                DocumentContext documentContext = JsonPath.parse(response.getBody());
                Number memberId = documentContext.read("$.memberId");
                assertThat(memberId).isEqualTo(5);

                String email = documentContext.read("$.emailAddress");
                assertThat(email).isEqualTo("user.five@st-andrews.ac.uk");
        }

        /**
         * Test case to test the end-point for creating a new member.
         */
        @Test
        @DirtiesContext
        void shouldNotCreateAMemberThatAlreadyExist() {
                Member newMember = new Member(null, "Mathew Lewis",
                                "University of St Andrews, Walter Bower House Eden Campus Main Street, Guard Bridge Fife, KY16 0US",
                                "mathew.lewis@st-andrews.ac.uk");
                ResponseEntity<Void> createResponse = restTemplate
                                .postForEntity("/library/member", newMember, Void.class);

                assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                ResponseEntity<Void> createDuplicateResponse = restTemplate
                                .postForEntity("/library/member", newMember, Void.class);

                assertThat(createDuplicateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DirtiesContext
        void shouldDeleteAnExistingMember() {
                ResponseEntity<Void> response = restTemplate
                                .exchange("/library/member/5", HttpMethod.DELETE, null, Void.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        @DirtiesContext
        void shouldNotDeleteAMemberThatDoesNotExist() {
                ResponseEntity<Void> deleteResponse = restTemplate
                                .exchange("/library/member/6", HttpMethod.DELETE, null, Void.class);
                assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        @DirtiesContext
        void shouldDeleteAnExistingMemberByEmailAddress() {
                ResponseEntity<Void> response = restTemplate
                                .exchange("/library/member/user.three@st-andrews.ac.uk", HttpMethod.DELETE, null,
                                                Void.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        @DirtiesContext
        void shouldReturnAnExistingMember() {
                ResponseEntity<String> getResponse = restTemplate
                                .getForEntity("/library/member/4", String.class);
                assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        @DirtiesContext
        void shouldReturnAllLibraryMembersWhenListIsRequested() {
                ResponseEntity<String> response = restTemplate
                                .getForEntity("/library/member/", String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

                DocumentContext documentContext = JsonPath.parse(response.getBody());
                int memberCount = documentContext.read("$.length()");
                assertThat(memberCount).isEqualTo(5);

                JSONArray ids = documentContext.read("$..emailAddress");
                assertThat(ids).containsExactlyInAnyOrder("user.one@st-andrews.ac.uk", "user.two@st-andrews.ac.uk",
                                "user.three@st-andrews.ac.uk", "user.four@st-andrews.ac.uk",
                                "user.five@st-andrews.ac.uk");
        }
}