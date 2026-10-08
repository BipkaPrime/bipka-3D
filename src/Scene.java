import java.util.ArrayList;

public class Scene {
    private ArrayList<Mesh> meshes = new ArrayList<Mesh>();

    public void addMesh(Mesh mesh) {
        this.meshes.add(mesh);
    }

    public int getMeshCount() {
        return this.meshes.size();
    }

    public Mesh getMeshByIdx(int index) {
        return this.meshes.get(index);
    }
}