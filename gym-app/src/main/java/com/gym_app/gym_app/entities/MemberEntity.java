package com.gym_app.gym_app.entities;

import com.gym_app.gym_app.entities.emuns.MemberStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table (name = "members")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String dni;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agreement_id")
    private AgreementEntity agreement;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<RegistrationEntity> registrations;
}
