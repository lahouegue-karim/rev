package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Mark;
import model.Module;
import model.Prof;
import model.Student;

@Stateless
public class UniversityService implements UniversityServiceRemote {
	@PersistenceContext
	private EntityManager em;

	public UniversityService() {
	}


	public void assignProfToModule(List<Prof> profs, Module module) {
		for (Prof prof : profs) {
			module.addProf(prof);
		}
		em.merge(module);
	}

	public void create(Student student) {
		em.persist(student);
	}

	public void create(Module module) {
		em.persist(module);
	}

	public void create(Prof prof) {
		em.persist(prof);
	}

	public void assignMark(Student student, Module module, Date date,
			String type, int mark) {
		Mark aMark = new Mark(student, module, date, type, mark);
		em.persist(aMark);
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentsByProf(Prof prof) {
		return em.createQuery("select distinct s from Student s join s.marks mark join mark.module mod where :p member of mod.profs").setParameter("p", prof).getResultList();
	}

	public void removeMark(Student student, Module module, Date date) {
		Mark aMark =  new Mark(student, module, date, "", 0);
		em.remove(em.merge(aMark));
	}


	@SuppressWarnings("unchecked")
	public List<Student> findStudentsByProfAlternative(Prof prof) {
		String jpql = "select distinct stu from Mark mar ,Module mod  join mar.student stu join mar.module mod  where :prof member of mod.profs ";
		Query query = em.createQuery(jpql);
		query.setParameter("prof", prof);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Prof> findProfsByStudent(Student student) {
		
		return em.createQuery("select distinct p from Prof p join p.module mod join mod.marks mar where mar.student=:s").setParameter("s", student).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Prof> findProfsByStudentAlternative(Student student) {
		//"select distinct p from Prof p, Mark mar join mar.module mod where mar.student=:s and p.module=mod"
		//"select distinct mod.profs from Module mod, Mark mar join mar.module mod where mar.student=:s"
		return em.createQuery("select distinct p from Prof p, Mark mar join mar.module mod where mar.student=:s and p.module=mod").setParameter("s", student).getResultList();
	}

}
