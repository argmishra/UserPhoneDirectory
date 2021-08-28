package com.user.phone.directory.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator",
      parameters = {
          @Parameter(
              name = "uuid_gen_strategy_class",
              value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
          )
      }
  )
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "userName")
  private String userName;

  @Column (name = "password")
  private String password;

  @Column (name = "emailAddress")
  private String emailAddress;

  @Column (name = "preferredPhoneNumber")
  private String preferredPhoneNumber;

  @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Phone> phones;

}
