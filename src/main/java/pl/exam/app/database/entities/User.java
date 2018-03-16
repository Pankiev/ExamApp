package pl.exam.app.database.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name="User")
@Table(name = "users")
@Data
@EqualsAndHashCode(of = "id")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nickname", unique=true, nullable=false)
	private String nickname;
	
	@Column(name = "password", nullable=false)
	private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable= false)
	private Date creationDate = new Date(); 
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<>();
}
