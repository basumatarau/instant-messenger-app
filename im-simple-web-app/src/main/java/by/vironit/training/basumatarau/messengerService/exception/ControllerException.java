package by.vironit.training.basumatarau.messengerService.exception;

public class ControllerException extends Exception {
    public ControllerException(){
        super();
    }

    public ControllerException(Exception e){
        super(e);
    }

    public ControllerException(String message, Exception e){
        super(message, e);
    }

    public ControllerException(String message){
        super(message);
    }
}
