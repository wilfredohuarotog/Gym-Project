package com.gym_app.gym_app.entities;

import com.gym_app.gym_app.entities.emuns.MemberShipName;
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
@Table(name = "member_ship")
public class MemberShipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(value = EnumType.STRING)
    private MemberShipName name;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "member_ship_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private List<ClassesEntity> classes;

}
