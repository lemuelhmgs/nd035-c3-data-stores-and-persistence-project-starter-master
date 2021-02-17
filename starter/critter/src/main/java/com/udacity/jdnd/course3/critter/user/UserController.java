package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    PetService petService;
    @Autowired
    EmployeeService employeeService;


    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer savedCustomer = customerService.saveCustomer(convertCustomerDTOTOCustomerEntity(customerDTO));
        return convertCustomerToCustomerDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        List<Customer> customerList = customerService.getAllCustomers();
        List<CustomerDTO> customerDTO = customerList.stream()
            .map(UserController::convertCustomerToCustomerDTO)
            .collect(Collectors.toList());

        return customerDTO;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPetById(petId);
        CustomerDTO customerDTO = convertCustomerToCustomerDTO(customerService.getCustomerById(pet.getCustomer().getId()));
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());

        Employee  savedEmployee = employeeService.saveEmployee(convertEmployeeDtoToEmployee(employeeDTO));
        return convertEmployeeToEmployeeDto(savedEmployee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return  convertEmployeeToEmployeeDto(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
       employeeService.updateEmployee(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.getEmployeeForService(
            employeeDTO.getSkills(),employeeDTO.getDate().getDayOfWeek());
         return employees.stream().map(this::convertEmployeeToEmployeeDto).collect(Collectors.toList());
    }

    private static CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Long> petIds = new ArrayList<>();
        try {
            customer.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
            customerDTO.setPetIds(petIds);
        }catch (Exception e){
            System.out.println("customer "+customer.getId() + " does not have any pet");
        }
        return customerDTO;
    }

    private static Customer convertCustomerDTOTOCustomerEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }

    private  EmployeeDTO convertEmployeeToEmployeeDto(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDTO);
        return employeeDTO;
    }

    private static  Employee convertEmployeeDtoToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        return employee;
    }

}
