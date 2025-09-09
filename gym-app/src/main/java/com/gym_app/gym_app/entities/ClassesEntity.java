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
@Data
@Builder
@Table(name = "class")
public class ClassesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "schedule_id")
    private List<ScheduleEntity> schedule;

    private Long capacity;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private CoachEntity coach;

    @OneToMany(mappedBy = "classes")
    private List<RegistrationEntity> registrationEntityList;
}
