package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;


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

  public Customer saveCustomer(Customer customer){

    if(!isCustomerNew(customer.getName())){
      throw new ObjectNotFoundException("Name already exist in DB");
    }


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
  //need to check if this ok or need to return null
    return customerRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
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

}
