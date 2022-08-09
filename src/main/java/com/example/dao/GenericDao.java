package com.example.dao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface GenericDao<T> {

	T save(T object) throws InterruptedException, ExecutionException;

	T find(String id) throws InterruptedException, ExecutionException;

	List<T> findAll() throws InterruptedException, ExecutionException;

	void delete(String id) throws InterruptedException, ExecutionException;

	T update(String id,T object) throws InterruptedException, ExecutionException;

	abstract String getCollectionName();
}
