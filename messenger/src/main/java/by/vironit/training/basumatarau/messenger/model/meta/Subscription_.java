package by.vironit.training.basumatarau.messenger.model.meta;

import by.vironit.training.basumatarau.messenger.model.ChatRoom;
import by.vironit.training.basumatarau.messenger.model.Subscription;
import by.vironit.training.basumatarau.messenger.model.Subscription.ChatRoomPrivilege;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subscription.class)
public abstract class Subscription_ extends ContactEntry_ {

	public static volatile SingularAttribute<Subscription, Boolean> isEnabled;
	public static volatile SingularAttribute<Subscription, ChatRoomPrivilege> privilege;
	public static volatile SingularAttribute<Subscription, Long> enteredChat;
	public static volatile SingularAttribute<Subscription, ChatRoom> chatRoom;

	public static final String IS_ENABLED = "isEnabled";
	public static final String PRIVILEGE = "privilege";
	public static final String ENTERED_CHAT = "enteredChat";
	public static final String CHAT_ROOM = "chatRoom";

}

