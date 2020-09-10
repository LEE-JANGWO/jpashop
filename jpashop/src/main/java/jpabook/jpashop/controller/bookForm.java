package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class bookForm {  // 아이템 중 하나 일단 book

    private Long id;  // 상품에서 id값을 받는다. 상품수정하는 부분도 있기 떄문에

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;




}
