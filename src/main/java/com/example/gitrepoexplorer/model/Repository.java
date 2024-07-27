package com.example.gitrepoexplorer.model;

import lombok.Data;
import java.util.List;

@Data
public class Repository {
    private String name;
    private Owner owner;
    private boolean fork;
    private List<Branch> branches;
}
