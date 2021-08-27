package com.user.phone.directory.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.user.phone.directory.enums.PhoneEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "phone")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Phone {

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

  @Column(name = "phoneName")
  private String phoneName;

  @Column (name = "phoneModel")
  private PhoneEnum phoneModel;

  @Column (name = "phoneNumber")
  private String phoneNumber;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="user_id", nullable=false)
  private User user;

}


