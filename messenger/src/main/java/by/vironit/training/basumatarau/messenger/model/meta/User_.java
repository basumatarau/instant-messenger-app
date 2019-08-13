package by.vironit.training.basumatarau.messenger.model.meta;

import by.vironit.training.basumatarau.messenger.model.ContactEntry;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.model.User.UserRole;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, UserRole> role;
	public static volatile SingularAttribute<User, String> nickName;
	public static volatile SingularAttribute<User, Boolean> isEnabled;
	public static volatile SetAttribute<User, ContactEntry> contactEntries;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> passwordHash;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String ROLE = "role";
	public static final String NICK_NAME = "nickName";
	public static final String IS_ENABLED = "isEnabled";
	public static final String CONTACT_ENTRIES = "contactEntries";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String PASSWORD_HASH = "passwordHash";

}

