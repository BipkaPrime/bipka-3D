record Vector3D(double x, double y, double z) {
    public Vector3D normalize() {
        double norm = Math.sqrt(
            this.x * this.x +
            this.y * this.y +
            this.z * this.z
        );

        if (norm == 0.0) {
            throw new ArithmeticException(
                "Cannot normalize zero vector"
            );
        }

        return new Vector3D(
            this.x / norm,
            this.y / norm,
            this.z / norm
        );
    }

    public Vector3D add(Vector3D other) {
        return new Vector3D(
            this.x + other.x,
            this.y + other.y,
            this.z + other.z
        );
    }

    public Vector3D subtract(Vector3D other) {
        return new Vector3D(
            this.x - other.x,
            this.y - other.y,
            this.z - other.z
        );
    }
}