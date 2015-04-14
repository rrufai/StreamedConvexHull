package cg.common.collections.convexchain;

import cg.common.Bits;
import cg.common.comparators.LexicographicComparator;
import cg.convexlayers.events.IntervalTree;
import cg.geometry.primitives.Point;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author rrufai@gmu.edu
 * @param <K>
 */
public class ConvexLayersIntervalTreeImpl<K extends Point> implements ConvexLayersIntervalTree<K> {
    /* Index is sorted by x-coordinate -- used to determine descent path in "interval" tree*/

    private Map<K, BitSet> pointToIndexMap;
    private IntervalTree<K> intervalTree;
    private int length;

    public ConvexLayersIntervalTreeImpl(List<K> pointset,
            LexicographicComparator<K> verticalComparator,
            LexicographicComparator<K> horizontalComparator) {

        Collections.sort(pointset, horizontalComparator);
        pointToIndexMap = new HashMap<>(pointset.size());
        length = (int) Math.ceil(Math.log(pointset.size()) / Math.log(2.0));
        for (int i = 0; i < pointset.size(); i++) {
            pointToIndexMap.put(pointset.get(i), Bits.convert(i, length));
            String binaryNumber = String.format("%" + length + "s", Integer.toBinaryString(i)).replace(' ', '0');
            Logger.getGlobal().log(Level.INFO, "{0}  \t: {1} \t: {2}", new Object[]{pointset.get(i), i, binaryNumber});
        }
        Logger.getGlobal().log(Level.INFO, "Point Rank Map: {0}", pointToIndexMap);
        intervalTree = new SimpleIntervalTreeImpl<>(null, this);
        Collections.sort(pointset, verticalComparator);
        for (K p : pointset) {
            intervalTree.insert(p);
        }
    }

    public ConvexLayersIntervalTreeImpl(List<K> pointset) {
        this(pointset,
                new LexicographicComparator(LexicographicComparator.Direction.BOTTOM_UP),
                new LexicographicComparator(LexicographicComparator.Direction.LEFT_TO_RIGHT));
    }

    public ConvexLayersIntervalTreeImpl() {
        this((List<K>) new ArrayList<K>());
    }

    /**
     *
     * @param point
     * @param level
     * @return
     */
    @Override
    public boolean goRight(K point, int level) {
        return pointToIndexMap.get(point).get(length - level);
    }

    @Override
    public void delete(K point) {
        getIntervalTree().delete(point);
    }

    @Override
    public List<K> extractRoot() {
        return getIntervalTree().extractHull();
    }

    @Override
    public boolean isEmpty() {
        return getIntervalTree().isEmpty();
    }

    @Override
    public String toString() {
        return getIntervalTree().toString();
    }

    /**
     * @return the intervalTree
     */
    public IntervalTree<K> getIntervalTree() {
        return intervalTree;
    }
}
