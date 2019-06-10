package by.vironit.training.basumatarau.instantMessengerApp.controller;

public class ViewResolver {

    private static final String VIEW_ROOT_FOLDER = "/WEB-INF/";
    private static final String PREFIX = ".jsp";
    private static ViewResolver instance;

    private ViewResolver(){}

    public static ViewResolver getInstance(){
        if(instance == null){
            synchronized (ViewResolver.class){
                if(instance == null){
                    instance = new ViewResolver();
                }
            }
        }
        return instance;
    }

    public String resolve(String viewName){
        return VIEW_ROOT_FOLDER + viewName + PREFIX;
    }
}
