/**
 * Used to handle rotations in the 3D space. Rotations are stored as unit
 * quaternions: https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation.
 * This approach is chosen because it's convenient to:
 * 1. Combine several such rotations into one.
 * 2. Such rotation can be easily inversed.
 * Rotation quaternions are expected to have unit norm. Small deviations
 * may occur due to floating-point rounding errors.
 * 
 * Any rotation in the 3D space around an origin can be represented by
 * 2 things: axis of rotation and an angle. [Euler's rotation theorem]
 * Consider axis = Vector3D(x, y, z) with a norm of 1 and angle = a.
 * A unit quaternion encodes an axis-angle rotation using the following
 * components:
 * w = cos(a / 2)
 * x = axis.x * sin(a / 2)
 * y = axis.y * sin(a / 2)
 * z = axis.z * sin(a / 2)
 */
public final class Rotation3D {
    public static final Rotation3D IDENTITY = new Rotation3D(1, 0, 0, 0);

    private final double w;
    private final double x;
    private final double y;
    private final double z;

    private Rotation3D(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a quaternion representing the rotation around
     * the given axis by a given angle respective to the origin.
     * @param axis axis of rotation, cannot be a zero vector
     * @param angle angle of rotation in radians
     * @return unit rotation quaternion
     */
    public Rotation3D(Vector3D axis, double angle) {
        axis = axis.normalize();
        double cos = Math.cos(angle / 2.0);
        double sin = Math.sin(angle / 2.0);
        this.w = cos;
        this.x = axis.x() * sin;
        this.y = axis.y() * sin;
        this.z = axis.z() * sin;
    }

    /**
     * Calculates the inverse rotation to "this". Assuming that
     * "this" is a unit quaternion.
     */
    public Rotation3D inverse() {
        return new Rotation3D(this.w, -this.x, -this.y, -this.z);
    }

    /**
     * Applies the rotation represented by this quaternion
     * to a given 3-dimensional vector.
     * @param v input vector to be rotated
     * @return vector resulting from the rotation of the given vector
     */
    public Vector3D apply(Vector3D v) {
        Rotation3D vQuaternion = new Rotation3D(0, v.x(), v.y(), v.z());
        Rotation3D result = this.multiply(vQuaternion).multiply(this.inverse());
        return new Vector3D(result.x, result.y, result.z);
    }

    /**
     * Calculates the quaternion resulting from applying 
     * "this" then "other" sequentially.
     */
    public Rotation3D compose(Rotation3D other) {
        return other.multiply(this);
    }

    /**
     * Calculates the Hamilton product of 2 quaternions: this * other
     */
    private Rotation3D multiply(Rotation3D other) {
        return new Rotation3D(
            this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z,
            this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y,
            this.w * other.y - this.x * other.z + this.y * other.w + this.z * other.x,
            this.w * other.z + this.x * other.y - this.y * other.x + this.z * other.w
        );
    }
}