package com.edu.ui;

import com.edu.Employee;
import com.edu.EmployeeRepository;
import org.omnifaces.util.Faces;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@RequestScoped
@Named
public class EmployeeController {
    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    EmployeeDatatable employeeDatatable;

    public void remove(Employee employee) {
        employeeRepository.remove(employee);
        employeeDatatable.refresh();
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage("Successfully deleted employee " + employee.getEmployeeNo()));
    }
}
