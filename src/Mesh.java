public class Mesh {
    public record SegmentRecord(int vertexA, int vertexB) {}

    private Vector3D[] vertices;
    private SegmentRecord[] edges;

    public Mesh(Vector3D[] vertices, SegmentRecord[] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public int getEdgeCount() {
        return edges.length;
    }

    public Vector3D getEdgeVertexA(int index) {
        int vertIdx = this.edges[index].vertexA();
        return vertices[vertIdx];
    }

    public Vector3D getEdgeVertexB(int index) {
        int vertIdx = this.edges[index].vertexB();
        return vertices[vertIdx];
    }
}