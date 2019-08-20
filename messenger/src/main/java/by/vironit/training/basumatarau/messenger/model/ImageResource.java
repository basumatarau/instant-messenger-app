package by.vironit.training.basumatarau.messenger.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue(value = MessageResource.IMAGE_RESOURCE)
public class ImageResource extends MessageResource {

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    public ImageResource(){}

    protected ImageResource(ImageResourceBuilder builder){
        super(builder);
        width = builder.width;
        height = builder.height;
    }

    public static class ImageResourceBuilder
            extends MessageResourceBuilder<ImageResource, ImageResourceBuilder>{
        public ImageResourceBuilder(){}

        private Integer width;
        private Integer height;

        public ImageResourceBuilder width(Integer width){
            this.width = width;
            return this;
        }

        public ImageResourceBuilder height(Integer height){
            this.height = height;
            return this;
        }

        @Override
        public ImageResource build() throws InstantiationException {
            buildDataIntegrityCheck();
            return new ImageResource(this);
        }

        @Override
        protected void buildDataIntegrityCheck() throws InstantiationException {
            super.buildDataIntegrityCheck();
            if(width == null || height == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for " +
                                getClass().getName() +
                                " object instantiation");
            }
        }
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ImageResource that = (ImageResource) o;
        return Objects.equals(width, that.width) &&
                Objects.equals(height, that.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width, height);
    }
}
