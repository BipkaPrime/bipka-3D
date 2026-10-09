import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Graphics;


public class Main {
    public static void main(String[] args) {
        // Creating a frame and a canvas
        Frame frame = new Frame("Bipka 3D");
        Canvas canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(800, 800));
        frame.add(canvas);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

        // adding a listener to window close event
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        // adding a listener for key presses
        CameraControl cameraControl = new CameraControl();
        canvas.addKeyListener(cameraControl);
        canvas.setFocusable(true);
        canvas.requestFocus();

        // initializing the scene
        Scene scene = new Scene();
        scene.addMesh(MeshFactory.createCube(2));

        // initializing the camera
        Camera camera = new Camera();
        camera.setPosition(new Vector3D(0, 0, -5));

        // canvas buffer strategy
        canvas.createBufferStrategy(2);
        BufferStrategy strategy = canvas.getBufferStrategy();

        // main render loop
        long lastFrameTime = System.nanoTime();
        while (frame.isDisplayable()) {
            long frameStart = System.nanoTime();
            double deltaTime = (frameStart - lastFrameTime) / 1e9;
            lastFrameTime = frameStart;
            cameraControl.update(camera, deltaTime);

            Graphics graphics = strategy.getDrawGraphics();
            Renderer.render(
                scene,
                camera,
                canvas.getWidth(),
                canvas.getHeight(),
                graphics
            );
            graphics.dispose();
            strategy.show();
        }
    }
}