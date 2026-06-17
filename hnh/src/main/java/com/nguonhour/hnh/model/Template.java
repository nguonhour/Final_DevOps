package com.nguonhour.hnh.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    private String organizationName;

    private String layout;

    private String primaryColor;

    private String secondaryColor;

    private String textColor;

    private String tagline;
}