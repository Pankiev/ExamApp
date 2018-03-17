package pl.exam.app.database.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name="Role")
@Table(name = "roles")
@Data
@EqualsAndHashCode(of = "id") 
public class Role
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Authority> authorities;
	
}