public class MeshFactory {
    public static Mesh createCube(double size) {
        double h = size / 2.0;

        Point3D[] vertices = {
            new Point3D(-h, -h, -h),
            new Point3D( h, -h, -h),
            new Point3D( h,  h, -h),
            new Point3D(-h,  h, -h),

            new Point3D(-h, -h,  h),
            new Point3D( h, -h,  h),
            new Point3D( h,  h,  h),
            new Point3D(-h,  h,  h)
        };

        SegmentRecord[] edges = {
            new SegmentRecord(0, 1),
            new SegmentRecord(1, 2),
            new SegmentRecord(2, 3),
            new SegmentRecord(3, 0),

            new SegmentRecord(4, 5),
            new SegmentRecord(5, 6),
            new SegmentRecord(6, 7),
            new SegmentRecord(7, 4),

            new SegmentRecord(0, 4),
            new SegmentRecord(1, 5),
            new SegmentRecord(2, 6),
            new SegmentRecord(3, 7)
        };

        return new Mesh(vertices, edges);
    }
}