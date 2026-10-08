public record Rotation3D(double x, double y, double z) {
    public Rotation3D add(Rotation3D other) {
        return new Rotation3D(
            this.x + other.x,
            this.y + other.y,
            this.z + other.z
        );
    }

    public Rotation3D multiply(double a) {
        return new Rotation3D(
            this.x * a,
            this.y * a,
            this.z * a
        );
    }
}