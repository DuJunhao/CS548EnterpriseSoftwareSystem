package edu.stevens.cs548.clinic.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-10-23T22:39:03.681-0400")
@StaticMetamodel(Treatment.class)
public class Treatment_ {
	public static volatile SingularAttribute<Treatment, Long> id;
	public static volatile SingularAttribute<Treatment, String> diagnosis;
	public static volatile SingularAttribute<Treatment, Patient> patient;
	public static volatile SingularAttribute<Treatment, Provider> provider;
	public static volatile SingularAttribute<Treatment, String> treatmentType;
}