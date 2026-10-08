/**
 * Projection plane is an imaginary plane in front of
 * the "camera", used for image rendering.
 * 
 * All calculations are carried out in camera coordinates:
 * camera is located at (0, 0, 0); facing positive Z; not rotated on any axis.
 */
public class ProjectionPlane {
    public static record Interval(double min, double max) {}
    public static record ProjectionPoint(double x, double y) {}
    public static record ImagePoint(int x, int y) {}

    // projection plane parameters
    private final double planeHalfHeight;
    private final double planeHalfWidth;
    private final double distance;
    private final double imageScale;

    public ProjectionPlane(
        int imageWidth,
        int imageHeight,
        double planeDistance,
        double verticalFoV
    ) {
        // projection plane parameters
        this.planeHalfHeight = (planeDistance * Math.tan(verticalFoV / 2));
        double aspectRatio = (double) imageWidth / imageHeight;
        this.planeHalfWidth = this.planeHalfHeight * aspectRatio;
        this.distance = planeDistance;
        this.imageScale = imageHeight / (2 * this.planeHalfHeight);
    }

    /**
     * Projects the given point on the projection plane.
     * Returns null if the point is closer to the camera than
     * the projection plane.
     * @param point point to be projected on the plane (camera coordinates)
     * @return projection coordinates or null
     */
    public ProjectionPoint projectPoint(Point3D point) {
        // if point is closer to camera then projection plane, null is returned
        if (point.z() < this.distance) return null;
        // calculating projection coordinates
        double scale = this.distance / point.z();
        double projectedX = point.x() * scale;
        double projectedY = point.y() * scale;
        return new ProjectionPoint(projectedX, projectedY);
    }

    /**
     * Finds the nearest point to input "point" on the segment
     * [point, target] such that it is in bounds of the projection plane.
     * @param point projection coordinates of a point to be clipped.
     * @param target used to determine clip direction.
     * @return Input point when it's already in bounds. Or nearest point
     * to input "point" in the direction of "target" that is in bounds.
     * Or null when no point on the segment [point, target] is in bounds.
     */
    public ProjectionPoint clipToBounds(
        ProjectionPoint point,
        ProjectionPoint target
    ) {
        // When the point is already in bounds, it is returned
        if (Math.abs(point.x()) <= this.planeHalfWidth &&
            Math.abs(point.y()) <= this.planeHalfHeight
        ) return point;

        Interval intervalX = getClipInterval(
            point.x(),
            target.x(),
            this.planeHalfWidth
        );
        Interval intervalY = getClipInterval(
            point.y(),
            target.y(),
            this.planeHalfHeight
        );
        
        if (intervalX == null || intervalY == null) return null;
        double t = Math.max(intervalX.min(), intervalY.min());
        double maxT = Math.min(intervalX.max(), intervalY.max());
        if (t > maxT) return null;

        return new ProjectionPoint(
            point.x() + t * (target.x() - point.x()),
            point.y() + t * (target.y() - point.y())
        );
    }

    /**
     * Converts projection coordinates to image coordinates.
     * This method can be called on points that are out of plane bounds.
     * @param point coordinates of projection point
     * @return image coordinates of input point (can be out of bounds)
     */
    public ImagePoint toImageCoords(ProjectionPoint point) {
        return new ImagePoint(
            (int) Math.round(((point.x() + this.planeHalfWidth) * this.imageScale)),
            (int) Math.round(((this.planeHalfHeight - point.y()) * this.imageScale))
        );
    }

    /**
     * Calculates the interval of t for which:
     * coordinate(t) = point + t * (target - point)
     * lies within [-halfLength, halfLength].
     * Note that we only care about the [0, 1] part of all t values.
     * @param point coordinate value of a ProjectionPoint (X or Y)
     * @param target coordinate value of a ProjectionPoint (X or Y)
     * @param halfLength used to determine what "in bounds" means
     * @return the interval of t within [0, 1], or null if no such interval exists
     */
    private Interval getClipInterval(
        double point,
        double target,
        double halfLength
    ) {
        double delta = target - point;
        if (delta == 0) {
            boolean pointInBounds = Math.abs(point) <= halfLength;
            // value of t does not affect coordinate on the axis
            return pointInBounds ? new Interval(0, 1) : null;
        } else {
            // values of t for which intersections occure
            double t1 = (-halfLength - point) / delta;
            double t2 = (halfLength - point) / delta;
            if (t1 > t2) {
                double temp = t1;
                t1 = t2;
                t2 = temp;
            }
            // interval does not intersect with [0, 1]
            if (t1 > 1 || t2 < 0) {
                return null;
            }
            return new Interval(
                Math.max(t1, 0),
                Math.min(t2, 1)
            );
        }
    }
}