package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Embeddable
public class MarkPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5910827450213732350L;
	private int idStudent;
	private int idModule;
	private Date date;
	
	public MarkPK() {
	}
	public MarkPK(int idStudent, int idModule, Date date) {
		super();
		this.idStudent = idStudent;
		this.idModule = idModule;
		this.date = date;
	}
	@Column(name="student_fk")
	public int getIdStudent() {
		return idStudent;
	}
	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}
	@Column(name="module_fk")
	public int getIdModule() {
		return idModule;
	}
	public void setIdModule(int idModule) {
		this.idModule = idModule;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + idModule;
		result = prime * result + idStudent;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarkPK other = (MarkPK) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (idModule != other.idModule)
			return false;
		if (idStudent != other.idStudent)
			return false;
		return true;
	}
	

}
