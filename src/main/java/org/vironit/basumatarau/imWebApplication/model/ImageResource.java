package org.vironit.basumatarau.imWebApplication.model;

import java.util.Arrays;
import java.util.Objects;

public class ImageResource extends MsgResource {
    private Integer width;
    private Integer height;
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
