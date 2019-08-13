package by.vironit.training.basumatarau.messenger.model.meta;

import by.vironit.training.basumatarau.messenger.model.ChatRoom;
import by.vironit.training.basumatarau.messenger.model.Subscription;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChatRoom.class)
public abstract class ChatRoom_ {

	public static volatile SetAttribute<ChatRoom, Subscription> subscriptions;
	public static volatile SingularAttribute<ChatRoom, String> name;
	public static volatile SingularAttribute<ChatRoom, Boolean> isPublic;
	public static volatile SingularAttribute<ChatRoom, Long> timeCreated;
	public static volatile SingularAttribute<ChatRoom, Long> id;

	public static final String SUBSCRIPTIONS = "subscriptions";
	public static final String NAME = "name";
	public static final String IS_PUBLIC = "isPublic";
	public static final String TIME_CREATED = "timeCreated";
	public static final String ID = "id";

}

