package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import model.Module;
import model.Prof;
import model.Student;

@Remote
public interface UniversityServiceRemote {

	void create(Student student);
	void create(Module module);
	void create(Prof prof);
	
	void assignProfToModule(List<Prof> profs, Module module);
	
	void assignMark(Student student, Module module, Date date, String type, int mark);
	void removeMark(Student student, Module module, Date date);
	
	List<Student> findStudentsByProf(Prof prof);
	List<Student> findStudentsByProfAlternative(Prof prof);
	
	List<Prof> findProfsByStudent(Student student);
	List<Prof> findProfsByStudentAlternative(Student student);
}
