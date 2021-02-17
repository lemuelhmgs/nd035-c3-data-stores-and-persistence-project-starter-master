package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String name;

  @ElementCollection(targetClass = EmployeeSkill.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name="employee_skill", joinColumns =
  @JoinColumn(name = "employee_id"))
  private Set<EmployeeSkill> skills;

  @ElementCollection(targetClass = DayOfWeek.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name="days_available",
                    joinColumns = @JoinColumn(
                        name="employee_id"
                    ))
  private Set<DayOfWeek> daysAvailable;

  @ManyToMany(mappedBy = "employee")
  private List<Schedule> schedules;


}
