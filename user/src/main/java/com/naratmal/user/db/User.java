package com.naratmal.user.db;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_seq")
    Long userSeq;
    @Column(name="user_email")
    String userEmail;
    @Column(name = "user_nickname")
    String userNickname;
    @Column(name = "user_name")
    String userName;
    @Column(name = "user_location")
    String userLocation;
}
