package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "imagemessageresources")
public class ImageResource extends MessageResource {

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "imagebin", nullable = false)
    private byte[] imageBin;

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
