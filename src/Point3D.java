public record Point3D(double x, double y, double z) {
    public Point3D add(Point3D other) {
        return new Point3D(
            this.x + other.x,
            this.y + other.y,
            this.z + other.z
        );
    }

    public Point3D subtract(Point3D other) {
        return new Point3D(
            this.x - other.x,
            this.y - other.y,
            this.z - other.z
        );
    }

    public Point3D multiply(double a) {
        return new Point3D(
            this.x * a,
            this.y * a,
            this.z * a
        );
    }

    public Point3D rotateAroundOrigin(Rotation3D rotation) {
        Point3D result = this;

        // Rotating around x-axis
        double rotationX = rotation.x();
        double cosX = Math.cos(rotationX);
        double sinX = Math.sin(rotationX);
        result = new Point3D(
            result.x,
            result.y * cosX - result.z * sinX,
            result.z * cosX + result.y * sinX
        );

        // Rotating around y-axis
        double rotationY = rotation.y();
        double cosY = Math.cos(rotationY);
        double sinY = Math.sin(rotationY);
        result = new Point3D(
            result.x * cosY + result.z * sinY,
            result.y,
            result.z * cosY - result.x * sinY
        );

        // rotation around z-axis
        double rotationZ = rotation.z();
        double cosZ = Math.cos(rotationZ);
        double sinZ = Math.sin(rotationZ);
        result = new Point3D(
            result.x * cosZ - result.y * sinZ,
            result.y * cosZ + result.x * sinZ,
            result.z
        );

        return result;
    }

    public Point3D rotateAroundOther(Rotation3D rotation, Point3D other) {
        Point3D result = this;
        // shifting the result to rotate it around origin
        result = result.subtract(other);
        result = result.rotateAroundOrigin(rotation);
        result = result.add(other);
        return result;
    }
}