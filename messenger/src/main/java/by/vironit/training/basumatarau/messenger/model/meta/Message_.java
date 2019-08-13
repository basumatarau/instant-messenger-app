package by.vironit.training.basumatarau.messenger.model.meta;

import by.vironit.training.basumatarau.messenger.model.Message;
import by.vironit.training.basumatarau.messenger.model.MessageResource;
import by.vironit.training.basumatarau.messenger.model.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, MessageResource> resource;
	public static volatile SingularAttribute<Message, User> author;
	public static volatile SingularAttribute<Message, Long> timeSent;
	public static volatile SingularAttribute<Message, Long> id;
	public static volatile SingularAttribute<Message, String> body;

	public static final String RESOURCE = "resource";
	public static final String AUTHOR = "author";
	public static final String TIME_SENT = "timeSent";
	public static final String ID = "id";
	public static final String BODY = "body";

}

