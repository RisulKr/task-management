package com.epam.internship.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();
}
