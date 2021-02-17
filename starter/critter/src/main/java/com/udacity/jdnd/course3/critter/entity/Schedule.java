package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Schedule {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToMany(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY)
  @JoinTable(name = "employee_schedule",
  joinColumns = {@JoinColumn(name = "schedule_id")},
  inverseJoinColumns = {@JoinColumn(name = "employee_id")})
  private List<Employee> employee;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name = "schedule_pet",
      joinColumns = {@JoinColumn(name = "schedule_id")},
      inverseJoinColumns = {@JoinColumn(name = "pet_id")}
  )
  private List<Pet> pet;
  private LocalDate date;

  @ElementCollection(targetClass = EmployeeSkill.class)
  @CollectionTable(name = "activities", joinColumns = @JoinColumn(name = "schedule_id"))
  @Enumerated(EnumType.STRING)
  private Set<EmployeeSkill> activities;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name="schedule_customer",
      joinColumns = {@JoinColumn(name = "schedule_id")},
      inverseJoinColumns = {@JoinColumn(name = "customer_id")}
  )
  private List<Customer> customer;

}
