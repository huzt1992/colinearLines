import java.util.Arrays;

public class FastCollinearPoints {

    private Point sortedPoint;
    private int count = 0;
    private LineSegment[] segments = new LineSegment[1];//??? linesegment length to be determined

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        // corner case
        mergeSort(points);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Null Input");
        }


        //Sorting to deliver line segment

        int initLen = points.length;
        while (points.length > 3) {

            Arrays.sort(points, points[0].slopeOrder());
            boolean colLinear = false;
            int jBegin = 0;
            int jEnd = 0;

            for (int j = 3; j < points.length; j++) {

                if (!colLinear && points[j].slopeTo(points[0]) == points[j - 1].slopeTo(points[0]) &&
                        points[j - 1].slopeTo(points[0]) == points[j - 2].slopeTo(points[0])) {
                    jBegin = j - 2;
                    colLinear = true;
                    if (j == points.length - 1) {
                        colLinear = false;
                        

                        jEnd = j;
                        AddSegment(points, jBegin, jEnd, points[0]);

                    }

                } else if (colLinear && points[j].slopeTo(points[0]) != points[j - 1].slopeTo(points[0])) {
                    colLinear = false;
                    jEnd = j - 1;
                    AddSegment(points, jBegin, jEnd, points[0]);

                } else if (colLinear && j == points.length - 1) {
                    colLinear = false;
                    jEnd = j;
                    AddSegment(points, jBegin, jEnd, points[0]);
                }

            }
            points = Splice(points, 0, 0);
        }
    }


    private static Point[] Splice(Point[] points, int jBegin, int jEnd) {
        Point[] splicedPoints = new Point[points.length - (jEnd - jBegin + 1)];
        for (int i = 0; i < splicedPoints.length; i++) {
            if (i < jBegin) splicedPoints[i] = points[i];
            else splicedPoints[i] = points[jEnd - jBegin + 1 + i];
        }
        return splicedPoints;
    }


    //*** Sorting the provided points
    private static void mergeSort(Point[] points) {
        int lo = 0;
        int hi = points.length - 1;
        Point[] aux = AuxPoints(points);
        sort(points, aux, lo, hi);
    }


    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j] == null && aux[i] == null) a[k] = aux[j++];
            else if (aux[j] == null) a[k] = aux[i++];
            else if (aux[i] == null) a[k] = aux[j++];
            else if ((aux[j].compareTo(aux[i]) < 0)) a[k] = aux[j++];
            else if ((aux[j].compareTo(aux[i]) == 0)) throw new IllegalArgumentException("repeting point!");
            else a[k] = aux[i++];
        }
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static Point[] AuxPoints(Point[] points) {
        Point[] aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            aux[i] = points[i];
        }
        return aux;
    }
    //*** Sorting the provided points


    private void AddSegment(Point[] points, int jBegin, int jEnd, Point p) {
        int length = jEnd - jBegin + 2;
        Point[] segPoints = new Point[length];
        for (int i = 0; i < length - 1; i++) {
            segPoints[i] = points[jBegin + i];
        }
        segPoints[length - 1] = p;

        mergeSort(segPoints);
        LineSegment colLinearSegment = new LineSegment(segPoints[0], segPoints[length - 1]);
        if (count > segments.length - 1) {
            segments = Resize(segments);
        }
        segments[count++] = colLinearSegment;
    }


    // the number of line segments
    public int numberOfSegments() {
        return count;
    }

    private LineSegment[] Resize(LineSegment[] seg) {
        int length = seg.length;
        LineSegment[] newSeg = new LineSegment[length * 2];
        for (int i = 0; i < length; i++) {
            newSeg[i] = seg[i];
        }
        return newSeg;
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

