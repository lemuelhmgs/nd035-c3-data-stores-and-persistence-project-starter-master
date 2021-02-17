package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

  @Autowired
  EmployeeRepository employeeRepository;

  public Employee saveEmployee(Employee employee){
    return employeeRepository.save(employee);
  }

  public Employee getEmployee(Long id){
    return employeeRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
  }

  public Employee updateEmployee(Set<DayOfWeek> daysAvaliable, Long id){
    Employee employee = new Employee();
   if(employeeRepository.findById(id).isPresent()){
     employee = employeeRepository.findById(id).get();
     employee.setDaysAvailable(daysAvaliable);
     employeeRepository.save(employee);

   } else {
     throw new ObjectNotFoundException();
   }
    return employee;
  }

  public List<Employee> getEmployeeForService(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek){
    List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(dayOfWeek);

    List<Employee> freeEmployees = new ArrayList<>();

    for(Employee employee: employees){
      if(employee.getSkills().containsAll(skills)) {
        freeEmployees.add(employee);
      }
    }

    return freeEmployees;
  }

}
