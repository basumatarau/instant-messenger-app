package by.vironit.training.basumatarau.instantMessengerApp.dao.criteria;

public final class LookUpCriteria {

    private LookUpCriteria() {
    }

    public enum User{
        NICKNAME, EMAIL
    }

    public enum Role{
        NAME
    }

    public enum PrivateMessage{
        AUTHOR, TIMESENT, CONTACT, ADDRESSEE
    }

    public enum Contact{
        OWNER, PERSON
    }
}
