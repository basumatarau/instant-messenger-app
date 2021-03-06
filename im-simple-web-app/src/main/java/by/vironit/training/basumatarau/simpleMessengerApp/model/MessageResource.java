package by.vironit.training.basumatarau.simpleMessengerApp.model;

import java.util.Objects;

public abstract class MessageResource {
    private Long id;
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageResource(){}

    protected MessageResource(MessageResourceBuilder builder){
        this.name = builder.name;
    }

    public abstract static class MessageResourceBuilder
            <R extends MessageResource, B extends MessageResourceBuilder<R, B>>{
        private String name;

        public MessageResourceBuilder(){}

        /**here and below: returned values are extensions of the base type "B"
         * (which is curiously recurred template impl.) */
        @SuppressWarnings("unchecked")
        public B name(String name){
            this.name = name;
            return ((B) this);
        }

        public abstract R build() throws InstantiationException;

        protected void buildDataIntegrityCheck() throws InstantiationException {
            if(name == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for " +
                                getClass().getName() +
                                " object instantiation");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageResource that = (MessageResource) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
