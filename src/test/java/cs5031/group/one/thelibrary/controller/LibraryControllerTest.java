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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        Member newMember = new Member(null, "Mathew Lewis", "University of St Andrews, Walter Bower House Eden Campus Main Street, Guard Bridge Fife, KY16 0US", "mathew.lewis@st-andrews.ac.uk");
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
    void shoutReturnAMemberWhenDataIsSaved () {
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
        Member newMember = new Member(null, "Mathew Lewis", "University of St Andrews, Walter Bower House Eden Campus Main Street, Guard Bridge Fife, KY16 0US", "mathew.lewis@st-andrews.ac.uk");
        ResponseEntity<Void> createResponse = restTemplate
                .postForEntity("/library/member", newMember, Void.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);


        ResponseEntity<Void> createDuplicateResponse = restTemplate
                .postForEntity("/library/member", newMember, Void.class);

        assertThat(createDuplicateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Test case to test the end-point for deleting a member using member's id.
     */
    @Test
    @DirtiesContext
    void shouldDeleteAnExistingMember() {
        ResponseEntity<Void> response = restTemplate
                .exchange("/library/member/5", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Test case to test the end-point to not delete a new that does not exist.
     */
    @Test
    @DirtiesContext
    void shouldNotDeleteAMemberThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/library/member/6", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Test case to test the end-point for deleting a member using member's email address.
     */
    @Test
    @DirtiesContext
    void shouldDeleteAnExistingMemberByEmailAddress() {
        ResponseEntity<Void> response = restTemplate
                .exchange("/library/member/user.three@st-andrews.ac.uk", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Test case to test the end-point for getting an existing member using member's id.
     */
    @Test
    @DirtiesContext
    void shouldReturnAnExistingMember() {
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/library/member/4", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test case to test the end-point for getting all members in the system.
     */
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
        assertThat(ids).containsExactlyInAnyOrder("user.one@st-andrews.ac.uk", "user.two@st-andrews.ac.uk", "user.three@st-andrews.ac.uk", "user.four@st-andrews.ac.uk", "user.five@st-andrews.ac.uk");
    }

    /**
     * Test case to test the end-point for updating a member's details (name and address).
     */
    @Test
    @DirtiesContext
    void shouldUpdateAnExistingMember() {
        Member memberUpdate = new Member(null, "Mathew Lewis",  null, null);
        Member memberUpdate1 = new Member(null, null, "Johnson S4 Fife Park, KY16 0US", null);
        HttpEntity<Member> request = new HttpEntity<>(memberUpdate);
        ResponseEntity<Void> response = restTemplate
                .exchange("/library/member/2", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/library/member/2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("@.memberId");
        String email = documentContext.read("@.emailAddress");

        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(2);
        assertThat(email).isEqualTo("user.two@st-andrews.ac.uk");
    }

    /**
     * Test case to test the end-point for updating a member's details (name and address).
     * It should not update a member that does not exist
     */
    @Test
    void shouldNotUpdateAMemberThatDoesNotExist() {
        Member unknownMember = new Member(null, "Mathew Lewis",  null, null);
        HttpEntity<Member> request = new HttpEntity<>(unknownMember);
        ResponseEntity<Void> response = restTemplate
                .exchange("/library/member/99999", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Test case to test the end-point for updating a member's details (name and address).
     * It should not update a member's email address.
     */
    @Test
    void shouldNotUpdateEmailAddressOfAMember() {
        Member memberWithEmailAddress = new Member(null, "Mathew Lewis",  null, "user.two@st-andrews.ac.uk");
        HttpEntity<Member> request = new HttpEntity<>(memberWithEmailAddress);
        ResponseEntity<Void> response = restTemplate
                .exchange("/library/member/2", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/library/member/2", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        String email = documentContext.read("@.emailAddress");
        assertThat(email).isEqualTo("user.two@st-andrews.ac.uk");
    }
}