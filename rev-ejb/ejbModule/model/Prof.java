package model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Prof
 *
 */
@Entity
@Table(name="t_prof")

public class Prof implements Serializable {

	
	private int id;
	private String name;
	private Module module;
	private static final long serialVersionUID = 1L;

	public Prof() {
		super();
	} 
	
	public Prof(int id, String name) {
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

	@ManyToOne
	@JoinColumn(name="module_fk")
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	@Override
	public String toString() {
		return "Prof [id=" + id + ", name=" + name + "]";
	}
	
	
   
}
