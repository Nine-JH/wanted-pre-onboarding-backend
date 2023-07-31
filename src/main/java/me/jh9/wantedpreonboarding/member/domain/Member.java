package me.jh9.wantedpreonboarding.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "members")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    protected Member() {
    }

    private Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Member createNewMember(String email, String password) {
        return new Member(null, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
