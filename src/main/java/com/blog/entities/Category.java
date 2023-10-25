package com.blog.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId ;
    @Column(name = "title" , nullable = false , length = 100)
    private String categoryTitle ;
    @Column(name = "description")
    private String categoryDescription ;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Post> posts ;

}
