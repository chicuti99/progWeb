package br.ufes.inf.labes.circular.core.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class User_ extends br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport_ {

	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.User#password
	 **/
	public static volatile SingularAttribute<User, String> password;
	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.User#role
	 **/
	public static volatile SingularAttribute<User, Role> role;
	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.User#name
	 **/
	public static volatile SingularAttribute<User, String> name;
	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.User#email
	 **/
	public static volatile SingularAttribute<User, String> email;

	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String NAME = "name";
	public static final String EMAIL = "email";

}

