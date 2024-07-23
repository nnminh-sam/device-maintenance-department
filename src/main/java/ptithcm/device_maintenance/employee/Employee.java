package ptithcm.device_maintenance.employee;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ptithcm.device_maintenance.helper.BaseEntity;
import ptithcm.device_maintenance.role.Role;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "citizen_id", nullable = false)
    private String citizenId;

    @Column(name = "dob", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
