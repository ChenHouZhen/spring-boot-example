package com.chenhz.neo4j.repository;import com.chenhz.neo4j.domain.Person;import org.springframework.data.neo4j.repository.Neo4jRepository;import org.springframework.data.repository.query.Param;import java.lang.reflect.ParameterizedType;import java.lang.reflect.Type;import java.util.Collection;/** */public interface PersonRepository extends Neo4jRepository<Person, Long>, CustomizedRepository<Person> {	Person findByfirstName(@Param("firstName") String firstName);	Collection<Person> findByfirstNameLike(@Param("firstName") String firstName);//	default public void myFindByfirstNameLike(@Param("firstName") String firstName) {//		this..   default void testMethod() {	   Class<?> myClass = this.getClass();	   System.out.println("1myClass:" + myClass);	   Type type = myClass.getGenericSuperclass();	   System.out.println("1type:" + type);	   System.out.println("1ParameterizedType:" + ParameterizedType.class);	   ParameterizedType pType = ((ParameterizedType) type);	   System.out.println("1pType:" + pType);	   Type firstType = pType.getActualTypeArguments()[0];	   System.out.println("1firstType:" + firstType);	}}