package by.vironit.training.basumatarau.messenger.model.meta;

import by.vironit.training.basumatarau.messenger.model.ContactEntry;
import by.vironit.training.basumatarau.messenger.model.Message;
import by.vironit.training.basumatarau.messenger.model.StatusInfo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StatusInfo.class)
public abstract class StatusInfo_ {

	public static volatile SingularAttribute<StatusInfo, Long> timeDelivered;
	public static volatile SingularAttribute<StatusInfo, Boolean> read;
	public static volatile SingularAttribute<StatusInfo, ContactEntry> contactEntry;
	public static volatile SingularAttribute<StatusInfo, Boolean> delivered;
	public static volatile SingularAttribute<StatusInfo, Long> id;
	public static volatile SingularAttribute<StatusInfo, Message> message;
	public static volatile SingularAttribute<StatusInfo, Long> timeRead;

	public static final String TIME_DELIVERED = "timeDelivered";
	public static final String READ = "read";
	public static final String CONTACT_ENTRY = "contactEntry";
	public static final String DELIVERED = "delivered";
	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String TIME_READ = "timeRead";

}

