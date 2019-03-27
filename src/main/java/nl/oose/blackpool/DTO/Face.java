package nl.oose.blackpool.DTO;

public class Face {
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean toBlur;
    private ChildDTO child;

    public Face() {
    }

    public Face(float x, float y, float width, float height, boolean toBlur, ChildDTO child) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.toBlur = toBlur;
        this.child = child;
    }

    public Face(int x, int y, int width, int height, boolean b) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.toBlur = b;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isToBlur() {
        return toBlur;
    }
}
