package com.epam.internship.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
