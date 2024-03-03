package cs5031.group.one.thelibrary.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * This is the record class representing the member of the library.
 *
 * @param memberId     This is the member id and serves as the unique identifier of the member.
 * @param memberName         This is the name of the member.
 * @param address      This is the physical address of the member.
 * @param emailAddress This is the email address of the member.
 */
public record Member(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)Long memberId, String memberName, String address, String emailAddress) {
}
