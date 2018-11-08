package com.edu;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Stateless
public class EmployeeRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Employee> queryAll() {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }

    public List<Employee> queryHiredAfter(Date hiredAfter) {
        TypedQuery<Employee> query = entityManager.
                createQuery("SELECT e FROM Employee e where e.hireDate >= :hiredAfter", Employee.class);
        query.setParameter("hiredAfter", hiredAfter);
        return query.getResultList();
    }

    public Employee find(Integer id) {
        return entityManager.find(Employee.class, id);
    }

    public Employee merge(Employee employee) {
        return entityManager.merge(employee);
    }

    public void persist(Employee employee) {
        entityManager.persist(employee);
    }

    public void remove(Employee employee) {
        Employee attached = find(employee.getEmployeeNo());
        entityManager.remove(attached);
    }
}
