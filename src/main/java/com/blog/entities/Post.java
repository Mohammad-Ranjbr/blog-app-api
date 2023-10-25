package com.blog.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name = "post_title" , nullable = false , length = 100)
    private String title ;
    @Column(length = 10000)
    private String content ;
    private String imageName ;
    @CreationTimestamp
    private Date createdDate ;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category ;
    @ManyToOne
    private User user ;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments ;

}
