package pl.exam.app.persistence.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import pl.exam.app.persistence.role.Role;
import pl.exam.app.persistence.userexam.UserExam;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "User")
@Table(name = "users", uniqueConstraints =
@UniqueConstraint(columnNames = {"schoolClass", "idInClass"}))
@Data
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "schoolClass")
    private String schoolClass;

    @Column(name = "idInClass")
    private Integer idInClass;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false)
    private Date creationDate = new Date();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "key.user")
    private Set<UserExam> exams;
}
