package by.vironit.training.basumatarau.messenger.model.meta;

import by.vironit.training.basumatarau.messenger.model.PersonalContact;
import by.vironit.training.basumatarau.messenger.model.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersonalContact.class)
public abstract class PersonalContact_ extends ContactEntry_ {

	public static volatile SingularAttribute<PersonalContact, User> person;
	public static volatile SingularAttribute<PersonalContact, Boolean> isConfirmed;

	public static final String PERSON = "person";
	public static final String IS_CONFIRMED = "isConfirmed";

}

