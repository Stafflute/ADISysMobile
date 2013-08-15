package business.entity;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 15/08/13
 * Time: 01:14
 * To change this template use File | Settings | File Templates.
 */
public class Accelerometro {
    private double x;
    private double y;
    private double z;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
