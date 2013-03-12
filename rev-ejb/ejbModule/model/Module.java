package model;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Module
 *
 */
@Entity
@Table(name="t_module")

public class Module implements Serializable {

	   
	
	private int id;
	private String name;
	private List<Prof> profs;
	private List<Mark> marks;
	private static final long serialVersionUID = 1L;

	public Module() {
		super();
	} 
	
	
	public Module(int id, String name) {
		super();
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


	@OneToMany(mappedBy="module",cascade = CascadeType.MERGE)
	public List<Prof> getProfs() {
		if(profs == null)
			profs = new ArrayList<Prof>();
		return profs;
	}


	public void setProfs(List<Prof> profs) {
		this.profs = profs;
	}
   
	public void addProf(Prof prof){
	   prof.setModule(this );
	   this.getProfs().add(prof);
   }
	@OneToMany(mappedBy="module",cascade=CascadeType.REMOVE)
	public List<Mark> getMarks() {
		if (marks==null){
			marks=new ArrayList<Mark>();
			
		}
				
		return marks;
	}

	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}


	@Override
	public String toString() {
		return "Module [id=" + id + ", name=" + name + "]";
	}
	
	
}
