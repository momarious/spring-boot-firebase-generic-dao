package com.example.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.StudentDao;
import com.example.model.Student;

@RestController
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    @PostMapping
	public ResponseEntity<Student> save(@RequestBody Student student) throws InterruptedException, ExecutionException {
		return ResponseEntity.ok().body(studentDao.save(student));
	}

    @GetMapping
    public ResponseEntity<?> findAll() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok().body(studentDao.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findStudent(@PathVariable String id)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok().body(studentDao.find(id));
    }

    @PostMapping("{id}/edit")
    public ResponseEntity<?> edit(@PathVariable String id, @RequestBody Student student)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok().body(studentDao.update(id, student));
    }

    @GetMapping("classe/{classe}")
    public ResponseEntity<Student> findByClasse(@PathVariable String classe)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok().body(studentDao.findByClasse(classe));

    }
}
