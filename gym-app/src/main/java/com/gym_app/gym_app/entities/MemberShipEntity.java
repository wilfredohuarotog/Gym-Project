package com.gym_app.gym_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "member_ships")
public class MemberShipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "member_ship_and_classes",
            joinColumns = @JoinColumn(name = "member_ship_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private List<ClassesEntity> classes;

}
