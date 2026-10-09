public class MeshFactory {
    public static Mesh createCube(double size) {
        double h = size / 2.0;

        Vector3D[] vertices = {
            new Vector3D(-h, -h, -h),
            new Vector3D( h, -h, -h),
            new Vector3D( h,  h, -h),
            new Vector3D(-h,  h, -h),

            new Vector3D(-h, -h,  h),
            new Vector3D( h, -h,  h),
            new Vector3D( h,  h,  h),
            new Vector3D(-h,  h,  h)
        };

        Mesh.SegmentRecord[] edges = {
            new Mesh.SegmentRecord(0, 1),
            new Mesh.SegmentRecord(1, 2),
            new Mesh.SegmentRecord(2, 3),
            new Mesh.SegmentRecord(3, 0),

            new Mesh.SegmentRecord(4, 5),
            new Mesh.SegmentRecord(5, 6),
            new Mesh.SegmentRecord(6, 7),
            new Mesh.SegmentRecord(7, 4),

            new Mesh.SegmentRecord(0, 4),
            new Mesh.SegmentRecord(1, 5),
            new Mesh.SegmentRecord(2, 6),
            new Mesh.SegmentRecord(3, 7)
        };

        return new Mesh(vertices, edges);
    }
}