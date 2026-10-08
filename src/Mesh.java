record SegmentRecord(int vertexA, int vertexB) {}

public class Mesh {
    private Point3D[] vertices;
    private SegmentRecord[] edges;

    public Mesh(Point3D[] vertices, SegmentRecord[] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public int getEdgeCount() {
        return edges.length;
    }

    public Point3D getEdgeVertexA(int index) {
        int vertIdx = this.edges[index].vertexA();
        return vertices[vertIdx];
    }

    public Point3D getEdgeVertexB(int index) {
        int vertIdx = this.edges[index].vertexB();
        return vertices[vertIdx];
    }
}