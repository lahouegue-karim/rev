package model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Mark
 *
 */
@Entity
@Table(name="t_mark")

public class Mark implements Serializable {

	private MarkPK pk;
	private String type;
	private int mark;
	private Student student;
	private Module module;
	private static final long serialVersionUID = 1L;

	public Mark() {
	}
	
	public Mark(Student student, Module module, Date date, String type, int mark){
		this.getPk().setIdModule(module.getId());
		this.getPk().setIdStudent(student.getId());
		this.getPk().setDate(date);
		this.mark = mark;
		this.type = type;
		this.student = student;
		this.module = module;
	}
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}   
	public int getMark() {
		return this.mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}   
	@ManyToOne
	@JoinColumn(name="student_fk",insertable=false,updatable=false)
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	@ManyToOne
	@JoinColumn(name="module_fk",insertable=false,updatable=false)
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	@EmbeddedId
	public MarkPK getPk() {
		if (pk==null)
			 pk = new MarkPK();
		return pk;
	}
	public void setPk(MarkPK pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "Mark [type=" + type + ", mark=" + mark + "]";
	}
	
	
   
}
