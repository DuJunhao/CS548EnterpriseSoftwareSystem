package edu.stevens.cs548.clinic.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-27T15:13:25.261-0500")
@StaticMetamodel(Provider.class)
public class Provider_ {
	public static volatile SingularAttribute<Provider, Long> NPI;
	public static volatile SingularAttribute<Provider, String> name;
	public static volatile SingularAttribute<Provider, String> spec;
	public static volatile ListAttribute<Provider, Treatment> treatments;
	public static volatile SingularAttribute<Provider, Long> id;
}
