package com.toy.shopwebmvc.domain;

import com.toy.shopwebmvc.constant.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseDomain {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String account;
    private String password;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder(builderMethodName = "create")
    public Member(String account, String password, String name, Address address, Role role) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
    }

    @Builder(builderMethodName = "update", toBuilder = true)
    public Member(String password, String name, Address address, Role role) {
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
    }
}
