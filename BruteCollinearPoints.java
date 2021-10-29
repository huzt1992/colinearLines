public class BruteCollinearPoints {

    private Point[] temp = new Point[4];
    private LineSegment[] segments = new LineSegment[1];//??? linesegment length to be determined
    private int count = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        // corner case
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Null Input");
        }


        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])) {
                        for (int h = k + 1; h < points.length; h++) {
                            if (points[j].slopeTo(points[k]) == points[k].slopeTo(points[h])) {

                                temp[0] = points[i];
                                temp[1] = points[j];
                                temp[2] = points[k];
                                temp[3] = points[h];
                                PointSort(temp);
//                                for (Point p : temp) {
//                                    System.out.print(p + " ");
//                                }
//                                System.out.println();
                                LineSegment colLinearSegment = new LineSegment(temp[0], temp[3]);
                                if (count > segments.length - 1) {
                                    segments = Resize(segments);
                                    System.out.println(segments.length);
                                }
                                segments[count++] = colLinearSegment;
                            }
                        }
                    }
                }
            }
        }
    }


    private LineSegment[] Resize(LineSegment[] seg) {
        int length = seg.length;
        LineSegment[] newSeg = new LineSegment[length * 2];
        for (int i = 0; i < length; i++) {
            newSeg[i] = seg[i];
        }
        return newSeg;
    }

    private void PointSort(Point[] tempP) {
        for (int i = 0; i < temp.length; i++) {
            for (int j = i; j > 0; j--) {
                if (tempP[j].compareTo(tempP[j - 1]) < 0) {
                    Point swap = tempP[j];
                    tempP[j] = tempP[j - 1];
                    tempP[j - 1] = swap;
                } else if (tempP[j].compareTo(tempP[j - 1]) == 0) {
                    throw new IllegalArgumentException("repeating point!");
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return count;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] newSeg = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            newSeg[i] = segments[i];
        }
        return newSeg;
    }

}
