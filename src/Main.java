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
        KeyInput input = new KeyInput();
        canvas.addKeyListener(input);
        canvas.setFocusable(true);
        canvas.requestFocus();

        // initializing the scene
        Scene scene = new Scene();
        scene.addMesh(MeshFactory.createCube(2));

        // initializing the camera
        Camera camera = new Camera();
        camera.setPosition(0.3, -0.5, -5);

        // canvas buffer strategy
        canvas.createBufferStrategy(2);
        BufferStrategy strategy = canvas.getBufferStrategy();

        // main render loop
        while (frame.isDisplayable()) {
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