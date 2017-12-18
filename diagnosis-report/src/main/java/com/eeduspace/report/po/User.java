package com.eeduspace.report.po;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by zhuchaowei on 2017/3/14.
 */
@Entity
@Table(name = "edu_test_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long id;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "phone")
    private String phone;

}
