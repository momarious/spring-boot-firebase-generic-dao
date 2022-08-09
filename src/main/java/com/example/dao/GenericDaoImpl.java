package com.example.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.firebase.FirebaseInitializer;
import com.example.model.FirebaseModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public abstract class GenericDaoImpl<T extends FirebaseModel> implements GenericDao<T> {

	@Autowired
	private FirebaseInitializer db;

	private Class<T> clazz;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDaoImpl() {
		ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
		this.clazz = (Class) genericSuperClass.getActualTypeArguments()[0];
	}

	public T save(T model) throws InterruptedException, ExecutionException {
		CollectionReference collection = db.getFirebase().collection(getCollectionName());
		DocumentReference document = collection.document();
		model.setId(document.getId());
		document.set(removeNullValues(model));
		return model;
	}

	public T find(String id) throws InterruptedException, ExecutionException {
		CollectionReference collection = db.getFirebase().collection(getCollectionName());
		DocumentReference documentReference = collection.document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot document = future.get();
		T model = null;
		if (document.exists()) {
			model = document.toObject(clazz);
		}
		return model;
	}

	public List<T> findAll() throws InterruptedException, ExecutionException {
		List<T> list = new ArrayList<T>();
		CollectionReference collection = db.getFirebase().collection(getCollectionName());
		ApiFuture<QuerySnapshot> querySnapshot = collection.get();
		for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
			T model = doc.toObject(clazz);
			list.add(model);
		}
		return list;
	}

	public T update(String id, T model) throws InterruptedException, ExecutionException {
		T value = find(id);
		CollectionReference collectionReference = db.getFirebase().collection(getCollectionName());
		DocumentReference documentReference = collectionReference.document(value.getId());
		model.setId(value.getId());
		documentReference.set(removeNullValues(model));
		return model;
	}

	public void delete(String id) throws InterruptedException, ExecutionException {
		CollectionReference collectionReference = db.getFirebase().collection(getCollectionName());
		DocumentReference documentReference = collectionReference.document(id);
		documentReference.delete();
	}

	private Map<String, Object> removeNullValues(T object) {
		Gson gson = new GsonBuilder().create();
		Map<String, Object> map = new Gson().fromJson(gson.toJson(object), new TypeToken<HashMap<String, Object>>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return map;
	}
}
