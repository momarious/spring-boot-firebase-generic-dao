package com.example.dao;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.example.firebase.FirebaseInitializer;
import com.example.model.Student;

@Repository
public class StudentDaoImpl  extends GenericDaoImpl<Student> implements StudentDao  {

    @Autowired
    private FirebaseInitializer db;

    @Override
    public String getCollectionName() {
        return "student";
    }

    @Override
    public Student findByClasse(String name) throws InterruptedException, ExecutionException {
        CollectionReference collectionReference = db.getFirebase().collection(getCollectionName());
        ApiFuture<QuerySnapshot> querySnapshot = collectionReference.get();
        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            Student student = doc.toObject(Student.class);
            if (student.getClasse().equals(name)) {
                return student;
            }
        }
        return null;
    }
   
}
