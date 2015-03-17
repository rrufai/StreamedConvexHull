/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.convexchain;

import cg.common.Utilities;
import cg.common.comparators.RadialComparator;
import cg.geometry.primitives.Point;
import com.rits.cloning.Cloner;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class ConvexChainImpl<K extends Point> extends LinkedList<K> implements ConvexChain<K> {

    public ConvexChainImpl(List<K> chain) {
        super(chain);
    }

    public ConvexChainImpl() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTangentPoint(K newPoint) throws IllegalArgumentException {
        int k = this.size();

        while (k > -1 && counterClockwise(this.predecessor(k), this.get(k), newPoint)) {
            k--;
        }

        return k;
    }

    private boolean counterClockwise(K l, K r, K p) {
        return RadialComparator.<K>relativeCCW(l, r, p) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInterior(K newPoint) {
        ConvexChain<K> chain = new ConvexChainImpl<>();
        chain.add(newPoint);
        return !this.isEmpty() && this.dominates(chain);
    }

    /**
     * Deletes the given chain and returns the index from which it was deleted,
     * or -1 if it is not found.
     * 
     * @param chain
     * @return returns the index from which it was deleted, or -1, if chain not found.
     */
    @Override
    public int delete(ConvexChain<K> chain) {
        int i = -1;
        if (!chain.isEmpty()) {
            i = this.indexOf(chain.get(0));
            boolean removeAll = this.removeAll(chain);
        }
        return i;
    }

    @Override
    public void insertBefore(K newPoint, K existingPoint) {
        this.add(this.indexOf(existingPoint), newPoint);
    }

    @Override
    public void insertAfter(K newPoint, K existingPoint) {
        this.add(this.indexOf(existingPoint) + 1, newPoint);
    }

    @Override
    public boolean add(K p){
        ConvexChain<K> trivialChain = new ConvexChainImpl<>();
        trivialChain.addFirst(p);
        ConvexChain<K> evictions = this.insert(trivialChain);
        
        return evictions.isEmpty();
    }
    
    @Override
    public ConvexChain<K> insert(ConvexChain<K> chain) throws IllegalArgumentException {
        ConvexChain<K> evictedChain = new ConvexChainImpl();
        if (!chain.isEmpty()) {
            if (this.isEmpty()) {
                this.addAll(chain);
            } else if (size() == 1 && chain.size() == 1) {
                if (Utilities.isMonotonic(getFirst(), chain.getFirst())) {
                    this.addAll(chain);
                } else if (Utilities.isMonotonic(chain.getFirst(), getFirst())) {
                    this.addAll(0, chain);
                } else {
                    evictedChain.addAll(this);
                    this.clear();
                    this.addAll(chain);
                }
            } else if (chain.dominates(this)) {
                evictedChain = new ConvexChainImpl(
                        this.subList(0, this.size()));

                if (!evictedChain.isEmpty()) {
                    this.subList(0, this.size()).clear();
                }

                for (K p : chain) {
                    this.addLast(p);
                }
            } else {
                int tangent = getTangentPoint(chain.getFirst()) + 1;

                if (tangent < this.size()) {
                    evictedChain = new ConvexChainImpl(
                            this.subList(tangent, this.size()));
                }

                if (!evictedChain.isEmpty()) {
                    this.subList(tangent, this.size()).clear();
                }

                for (K p : chain) {
                    this.addLast(p);
                }
            }
        }
        return evictedChain;
    }

    /*
     * Returns true if this chain dominates the given chain, i.e. the given chain lies within the convex closure of this chain.
     * 
     * @param 
     */
    @Override
    public boolean dominates(ConvexChain<K> chain) {
        return chain.isEmpty() || getFirst().getX() < chain.getFirst().getX() && getLast().getY() > chain.getLast().getY();
    }

    @Override
    public Entry<ConvexChain<K>, ConvexChain<K>> split(int i) {
        if (this.isEmpty() || i < 0) {
            return null;
        }

        assert i > -1 && i <= size();
        if (i > -1 && i <= size()) {
            ConvexChain<K> lChain = new ConvexChainImpl<>(this.subList(0, i));
            ConvexChain<K> rChain = new ConvexChainImpl<>(this.subList(i, this.size()));

            return new AbstractMap.SimpleEntry<>(lChain, rChain);
        } else {
            throw new IllegalArgumentException(this.getClass() + ": Index " + i + " is not within range (0 ... " + (size() - 1) + ")");
        }
    }

    @Override
    public K getRightSentinel() {
        if (size() > 0) {
            int i = size() - 1;
            K rightSentinel = new Cloner().deepClone(this.get(i));
            rightSentinel.setLocation(Double.MAX_VALUE, rightSentinel.getY());
            return rightSentinel;
        } else {
            return (K) RIGHT_SENTINEL;
        }
    }

    /**
     *
     * @param chain
     * @return
     */
    @Override
    public K getLeftSentinel() {
        if (size() > 0) {
            K leftSentinel = new Cloner().deepClone(get(0));
            leftSentinel.setLocation(leftSentinel.getX(), -0.5 * Double.MAX_VALUE);
            return leftSentinel;
        } else {
            return (K) LEFT_SENTINEL;
        }
    }

    @Override
    public K predecessor(int j) {
        return this.get(j - 1);
    }

    @Override
    public K successor(int j) {
        return this.get(j + 1);
    }

    @Override
    public K get(int i) {
        return i < 0 ? this.getLeftSentinel() : (i > size() - 1 ? this.getRightSentinel() : super.get(i));
    }

    private int goLeft(int i, ConvexChain<K> chain) {
//        int j = chain.size() - 1;
//        while (j > 0 && counterClockwise(this.get(i), chain.get(j), chain.predecessor(j))) {
//            --j;
//            while (i > 0 && counterClockwise(this.get(i), chain.get(j), this.predecessor(i))) {
//                --i;
//            }
//        }

//        while (i > 0 && counterClockwise(this.get(i), chain.get(j), this.predecessor(i))) {
//            while (j > 0 && counterClockwise(this.get(i), chain.get(j), chain.predecessor(j))) {
//                --j;
//            }
        while (i > 0 && counterClockwise(this.get(i), chain.getFirst(), this.predecessor(i))) {
            --i;
        }
        return i;
    }
}