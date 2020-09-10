package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){   // public에서는 사람들한테 호출되어 질 수 있으니까 protected로 ( JPA 스펙상 엔티티나 임베디드 타입은 기본 생성자를 PUBLIC 또는 PROTECTED로 )
    }

    public Address(String city, String street, String zipcode) {    // setter 대신 생성자로.
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
