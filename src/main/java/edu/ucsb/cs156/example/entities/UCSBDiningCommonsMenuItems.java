package edu.ucsb.cs156.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ucsbdiningcommonsmenuitems")
public class UCSBDiningCommonsMenuItems {
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private long id; 
  private String diningCommonsCode;
  private String name;
  private String station;

}