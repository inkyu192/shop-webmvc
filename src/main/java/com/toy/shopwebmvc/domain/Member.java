package com.toy.shopwebmvc.domain;

import com.toy.shopwebmvc.constant.Role;
import com.toy.shopwebmvc.dto.request.MemberSaveRequest;
import com.toy.shopwebmvc.dto.request.MemberUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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

    public static Member createMember(MemberSaveRequest memberSaveRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.account = memberSaveRequest.account();
        member.password = passwordEncoder.encode(memberSaveRequest.password());
        member.name = memberSaveRequest.name();
        member.role = memberSaveRequest.role();
        member.address = Address.createAddress((memberSaveRequest.city()), memberSaveRequest.street(), memberSaveRequest.zipcode());

        return member;
    }

    public void updateMember(MemberUpdateRequest memberUpdateRequest, PasswordEncoder passwordEncoder) {
        if (StringUtils.hasText(memberUpdateRequest.password())) this.name = passwordEncoder.encode(memberUpdateRequest.password());
        if (StringUtils.hasText(memberUpdateRequest.name())) this.name = memberUpdateRequest.name();
        if (!ObjectUtils.isEmpty(memberUpdateRequest.role())) this.role = memberUpdateRequest.role();
        this.address.updateAddress((memberUpdateRequest.city()), memberUpdateRequest.street(), memberUpdateRequest.zipcode());
    }
}