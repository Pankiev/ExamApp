package pl.exam.app.persistence.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.exam.app.database.entities.Authority;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Role")
@Table(name = "roles")
@Data
@EqualsAndHashCode(of = "id")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Authority> authorities;

}
