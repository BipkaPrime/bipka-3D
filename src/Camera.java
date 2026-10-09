public class Camera {
    public static final double MIN_FOV = Math.PI / 6;
    public static final double MAX_FOV = 5 * Math.PI / 6;

    private Vector3D position = new Vector3D(0, 0, 0);
    private Rotation3D rotation = Rotation3D.IDENTITY;
    private double verticalFoV = Math.PI / 3;

    public Vector3D getPosition() {
        return this.position;
    }

    public Rotation3D getRotation() {
        return this.rotation;
    }

    public double getVerticalFoV() {
        return this.verticalFoV;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public void setRotation(Rotation3D rotation) {
        this.rotation = rotation;
    }

    public void setVerticalFoV(double fov) {
        this.verticalFoV = Math.clamp(fov, MIN_FOV, MAX_FOV);
    }

    public void rotateLocal(Vector3D axis, double angle) {
        Rotation3D delta = new Rotation3D(axis, angle);
        this.rotation = delta.compose(this.rotation);
    }

    public void moveLocal(Vector3D direction) {
        this.position = this.position.add(this.rotation.apply(direction));
    }
}