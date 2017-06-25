package ro.cipex.android.stamper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cbadescu on 6/25/17.
 */

public class ShapeList implements Iterable<Shape>{
    private static ShapeList sShapeList;
    private List<Shape> mShapes;

    public static ShapeList get() {
        if (null == sShapeList) {
            sShapeList = new ShapeList();
        }
        return sShapeList;
    }

    private ShapeList() {
        mShapes = new ArrayList<>();
    }

    public void add(Shape s) {
        mShapes.add(s);
    }

    public void clear() {
        mShapes.clear();
    }

    @Override
    public Iterator<Shape> iterator() {
        return new ShapeListIterator();
    }

    public void undo() {
        if (! mShapes.isEmpty()) {
            mShapes.remove(mShapes.size() - 1);
        }
    }

    class  ShapeListIterator implements Iterator<Shape> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < mShapes.size();
        }

        @Override
        public Shape next() {
            return mShapes.get(index++);
        }
    }
};
