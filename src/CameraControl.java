import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CameraControl extends KeyAdapter {
    private static final double MOVE_SPEED = 1;
    private static final double ROTATION_SPEED = 1;

    private static final Vector3D X_AXIS = new Vector3D(1, 0, 0);
    private static final Vector3D Y_AXIS = new Vector3D(0, 1, 0);
    private static final Vector3D Z_AXIS = new Vector3D(0, 0, 1);

    private boolean moveForward;
    private boolean moveBackward;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private boolean rotateCW;
    private boolean rotateCCW;
    private boolean rotateUp;
    private boolean rotateDown;
    private boolean rotateLeft;
    private boolean rotateRight;
    

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.moveForward = true;
            case KeyEvent.VK_S -> this.moveBackward = true;
            case KeyEvent.VK_A -> this.moveLeft = true;
            case KeyEvent.VK_D -> this.moveRight = true;
            case KeyEvent.VK_SPACE -> this.moveUp = true;
            case KeyEvent.VK_SHIFT -> this.moveDown = true;
            case KeyEvent.VK_E -> this.rotateCW = true;
            case KeyEvent.VK_Q -> this.rotateCCW = true;
            case KeyEvent.VK_UP -> this.rotateUp = true;
            case KeyEvent.VK_DOWN -> this.rotateDown = true;
            case KeyEvent.VK_LEFT -> this.rotateLeft = true;
            case KeyEvent.VK_RIGHT -> this.rotateRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.moveForward = false;
            case KeyEvent.VK_S -> this.moveBackward = false;
            case KeyEvent.VK_A -> this.moveLeft = false;
            case KeyEvent.VK_D -> this.moveRight = false;
            case KeyEvent.VK_SPACE -> this.moveUp = false;
            case KeyEvent.VK_SHIFT -> this.moveDown = false;
            case KeyEvent.VK_E -> this.rotateCW = false;
            case KeyEvent.VK_Q -> this.rotateCCW = false;
            case KeyEvent.VK_UP -> this.rotateUp = false;
            case KeyEvent.VK_DOWN -> this.rotateDown = false;
            case KeyEvent.VK_LEFT -> this.rotateLeft = false;
            case KeyEvent.VK_RIGHT -> this.rotateRight = false;
        }
    }

    public void update(Camera camera, double deltaTime) {
        int dx = (this.moveRight ? 1 : 0) - (this.moveLeft ? 1 : 0);
        int dy = (this.moveUp ? 1 : 0) - (this.moveDown ? 1 : 0);
        int dz = (this.moveForward ? 1 : 0) - (this.moveBackward ? 1 : 0);
        int roll = (this.rotateCCW ? 1 : 0) - (this.rotateCW ? 1 : 0);
        int yaw = (this.rotateRight ? 1 : 0) - (this.rotateLeft ? 1 : 0);
        int pitch = (this.rotateDown ? 1 : 0) - (this.rotateUp ? 1 : 0);

        double angle = deltaTime * ROTATION_SPEED;
        if (pitch != 0) {
            camera.rotateLocal(X_AXIS, pitch * angle);
        }
        if (yaw != 0) {
            camera.rotateLocal(Y_AXIS, yaw * angle);
        }
        if (roll != 0) {
            camera.rotateLocal(Z_AXIS, roll * angle);
        }

        double length = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (length > 0) {
            double scale = MOVE_SPEED * deltaTime / length;
            camera.moveLocal(new Vector3D(
                dx * scale,
                dy * scale,
                dz * scale
            ));
        }
    }
}