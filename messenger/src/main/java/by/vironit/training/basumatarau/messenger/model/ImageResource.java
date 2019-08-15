package by.vironit.training.basumatarau.messenger.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "imagemessageresources",
        schema = "instant_messenger_db_schema")
public class ImageResource extends MessageResource {

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "imagebin",
            columnDefinition = "bytea NOT NULL",
            nullable = false)
    private byte[] imageBin;

    public ImageResource(){}

    protected ImageResource(ImageResourceBuilder builder){
        super(builder);
        width = builder.width;
        height = builder.height;
        imageBin = builder.imageBin;
    }

    public static class ImageResourceBuilder
            extends MessageResourceBuilder<ImageResource, ImageResourceBuilder>{
        public ImageResourceBuilder(){}

        private Integer width;
        private Integer height;
        private byte[] imageBin;

        public ImageResourceBuilder width(Integer width){
            this.width = width;
            return this;
        }

        public ImageResourceBuilder height(Integer height){
            this.height = height;
            return this;
        }

        public ImageResourceBuilder imageBin(byte[] imageBin){
            this.imageBin = imageBin;
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
            if(width == null || height == null || imageBin == null){
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

    public byte[] getImageBin() {
        return imageBin;
    }

    public void setImageBin(byte[] imageBin) {
        this.imageBin = imageBin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ImageResource that = (ImageResource) o;
        return Objects.equals(width, that.width) &&
                Objects.equals(height, that.height) &&
                Arrays.equals(imageBin, that.imageBin);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), width, height);
        result = 31 * result + Arrays.hashCode(imageBin);
        return result;
    }
}
