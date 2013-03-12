package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Student
 *
 */
@Entity
@Table(name="t_student")

public class Student implements Serializable {

	
	private int id;
	private String name;
	private List<Mark> marks ;
	private static final long serialVersionUID = 1L;
	public Student() {
	}   
	
	public Student(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id    
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@OneToMany(mappedBy="student" , cascade=CascadeType.REMOVE )
	public List<Mark> getMarks() {
		if(marks==null){
			marks=new ArrayList<Mark>();
			
		}
		return marks;
	}

	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}

	

	
	
   
}
