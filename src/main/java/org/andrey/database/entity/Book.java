package org.andrey.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String image;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String genre;

    private String description;

    @Column(nullable = false)
    private Integer yearOfPublish;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private Integer pricePaper;

    @Column(nullable = false)
    private Integer priceDigital;

    @Column(nullable = false)
    private Integer inStock;

    @Builder.Default
    @OneToMany(mappedBy = "book")
    private List<BookReview> reviews = new ArrayList<>();

}
