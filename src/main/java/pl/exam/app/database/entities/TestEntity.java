package pl.exam.app.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import pl.exam.app.database.repositories.Identifiable;

@Data
@Entity(name = "TestEntity")
@Table(name = "test_entities")
public class TestEntity implements Identifiable<Integer>
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "testString", nullable = true)
	private String testString = "It works!";
}