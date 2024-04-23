package br.ufes.inf.labes.circular.core.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(Workshop.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Workshop_ extends br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport_ {

	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.Workshop#acronym
	 **/
	public static volatile SingularAttribute<Workshop, String> acronym;
	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.Workshop#year
	 **/
	public static volatile SingularAttribute<Workshop, Integer> year;
	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.Workshop#name
	 **/
	public static volatile SingularAttribute<Workshop, String> name;
	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.Workshop
	 **/
	public static volatile EntityType<Workshop> class_;
	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.Workshop#reviewDeadline
	 **/
	public static volatile SingularAttribute<Workshop, LocalDate> reviewDeadline;
	
	/**
	 * @see br.ufes.inf.labes.circular.core.domain.Workshop#submissionDeadline
	 **/
	public static volatile SingularAttribute<Workshop, LocalDate> submissionDeadline;

	public static final String ACRONYM = "acronym";
	public static final String YEAR = "year";
	public static final String NAME = "name";
	public static final String REVIEW_DEADLINE = "reviewDeadline";
	public static final String SUBMISSION_DEADLINE = "submissionDeadline";

}

