package pl.exam.app.examapp.database.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "ExamEvent")
@Table(name = "exam_events")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude="exam")
public class ExamEvent
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "opened", nullable=false)
	private Boolean opened = false;
	
	@Column(name = "started", nullable=false)
	private Boolean started = false;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable=true)
	private Date startDate;
	
	@Column(name = "ended", nullable=false)
	private Boolean ended = false;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable=true)
	private Date endDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable= false)
	private Date creationDate = new Date(); 
	
	@ManyToOne
	@JoinColumn(name = "exam_id")
	private Exam exam;
	
	@ManyToMany
	@JoinTable(name="user_exam_events", 
		joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="exam_event_id", referencedColumnName="id"))
	private Collection<User> users;
}
