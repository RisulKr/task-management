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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String roleName;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> users;
}
