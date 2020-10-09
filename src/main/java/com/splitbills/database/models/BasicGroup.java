package com.splitbills.database.models;

import javax.persistence.Column;

public class BasicGroup extends Group{
    @Column(nullable = false)
    private String name;

}
