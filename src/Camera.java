public class Camera {
    public static final double MIN_FOV = Math.PI / 6;
    public static final double MAX_FOV = 5 * Math.PI / 6;

    private Point3D position = new Point3D(0, 0, 0);
    private Rotation3D rotation = new Rotation3D(0, 0, 0);
    private double verticalFoV = Math.PI / 3;

    public Point3D getPosition() {
        return this.position;
    }

    public Rotation3D getRotation() {
        return this.rotation;
    }

    public double getVerticalFoV() {
        return this.verticalFoV;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public void setPosition(double x, double y, double z) {
        this.position = new Point3D(x, y, z);
    }

    public void setRotation(Rotation3D rotation) {
        this.rotation = rotation;
    }

    public void setRotation(double x, double y, double z) {
        this.rotation = new Rotation3D(x, y, z);
    }

    public void setVerticalFoV(double fov) {
        this.verticalFoV = Math.clamp(fov, MIN_FOV, MAX_FOV);
    }
}