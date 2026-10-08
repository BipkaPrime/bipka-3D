import java.awt.Graphics;
import java.awt.Color;

public class Renderer {
    private static final double PROJECTION_PLANE_DISTANCE = 1.0;
    private static final Color LINE_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    public static void render(
        Scene scene,
        Camera camera,
        int imageWidth,
        int imageHeight,
        Graphics g
    ) {
        // required camera parameters
        Point3D cameraPos = camera.getPosition();
        Rotation3D cameraRotation = camera.getRotation();

        // creating projection plane
        ProjectionPlane plane = new ProjectionPlane(
            imageWidth,
            imageHeight,
            PROJECTION_PLANE_DISTANCE,
            camera.getVerticalFoV()
        );

        // drawing background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, imageWidth, imageHeight);

        // drawing all edges for all meshes
        g.setColor(LINE_COLOR);
        int meshCount = scene.getMeshCount();
        for (int meshIdx = 0; meshIdx < meshCount; meshIdx++) {
            Mesh mesh = scene.getMeshByIdx(meshIdx);
            int edgeCount = mesh.getEdgeCount();
            for (int edgeIdx = 0; edgeIdx < edgeCount; edgeIdx++) {
                drawEdge(
                    g,
                    plane,
                    mesh.getEdgeVertexA(edgeIdx),
                    mesh.getEdgeVertexB(edgeIdx),
                    cameraPos,
                    cameraRotation
                );
            }
        }
    }

    private static ProjectionPlane.ProjectionPoint projectPoint(
        ProjectionPlane plane,
        Point3D point,
        Point3D cameraPos,
        Rotation3D cameraRotation
    ) {
        Point3D inCameraCoords = point.subtract(cameraPos);
        inCameraCoords = inCameraCoords.rotateAroundOrigin(
            cameraRotation.multiply(-1)
        );
        return plane.projectPoint(inCameraCoords);
    }

    private static void drawEdge(
        Graphics g,
        ProjectionPlane plane,
        Point3D vertexA,
        Point3D vertexB,
        Point3D cameraPos,
        Rotation3D cameraRotation
    ) {
        // Projecting both vertices on the projection plane
        ProjectionPlane.ProjectionPoint projectedA = projectPoint(
            plane,
            vertexA,
            cameraPos,
            cameraRotation
        );
        if (projectedA == null) return;
        ProjectionPlane.ProjectionPoint projectedB = projectPoint(
            plane,
            vertexB,
            cameraPos,
            cameraRotation
        );
        if (projectedB == null) return;

        // Clipping both vertices to projection plane bounds
        projectedA = plane.clipToBounds(projectedA, projectedB);
        if (projectedA == null) return;
        projectedB = plane.clipToBounds(projectedB, projectedA);
        if (projectedB == null) return;

        // Converting points to image coordinates
        ProjectionPlane.ImagePoint imageA = plane.toImageCoords(projectedA);
        ProjectionPlane.ImagePoint imageB = plane.toImageCoords(projectedB);
        // drawing the line between the points
        g.drawLine(imageA.x(), imageA.y(), imageB.x(), imageB.y());
    }
}