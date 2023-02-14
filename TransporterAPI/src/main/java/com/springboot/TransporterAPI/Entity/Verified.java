package com.springboot.TransporterAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="verification_data")
@AllArgsConstructor
@NoArgsConstructor
public class Verified {
    @Id
    private String reference_id;
    private String name;
    private String address;
    //@OneToOne(mappedBy = "verified")
    //private Transporter transporter;

}
