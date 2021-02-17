package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;


import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class CustomerService {

  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  EmployeeRepository employeeRepository;

  public Customer saveCustomer(Customer customer){
//to support unit test
    //if(!isCustomerNew(customer.getName())){
    //  throw new ObjectNotFoundException("Name already exist in DB");
    //}
//

    return  customerRepository.save(customer);
  }

  public List<Customer> getAllCustomers(){
    List<Customer> customerList =  customerRepository.findAll();
    if(customerList.size()== 0){
      throw new ObjectNotFoundException("Customer table is empty");
    }
    return customerList;


  }



  public Customer getCustomerById(Long id){

    return customerRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
  }

  public List<Employee> findAllById(List<Long> employeeId){
    return employeeRepository.findAllById(employeeId);
  }

  private boolean isNullOrEmpty(String name) {
    if(name == null && name.isEmpty()){
      return true;
    } else {
      return false;
    }
  }

  private Boolean isCustomerNew(String name){
    Optional<Customer> customer = Optional.ofNullable(customerRepository.findByName(name));

    if(customer.isPresent()){
      return false;
    }
    else{
      return true;
    }
  }

  public Employee getEmployeeById(long employeeId) {
    return employeeRepository.findById(employeeId).orElseThrow(ObjectNotFoundException::new);
  }
}
