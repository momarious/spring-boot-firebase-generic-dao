package com.example.dao;

import java.util.concurrent.ExecutionException;

import com.example.model.Student;

public interface StudentDao extends GenericDao<Student> {

	Student findByClasse(String name)  throws InterruptedException, ExecutionException;

}
