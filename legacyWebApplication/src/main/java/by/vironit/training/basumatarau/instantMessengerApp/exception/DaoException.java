package by.vironit.training.basumatarau.instantMessengerApp.exception;

public class DaoException extends Exception{
    public DaoException(){
        super();
    }

    public DaoException(Exception e){
        super(e);
    }

    public DaoException(String message, Exception e){
        super(message, e);
    }
}
