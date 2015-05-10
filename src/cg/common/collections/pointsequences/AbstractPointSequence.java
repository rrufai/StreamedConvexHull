/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.pointsequences;

import cg.common.smoothedanalysis.PerturbationModel;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractPointSequence<T extends Point> extends AbstractList<T> implements PointSequence<T> {

    private final List<T> pointSequence;
    private PerturbationModel perturbationModel;
    int filledSize;
    int capacity;

    protected AbstractPointSequence(List<T> pointSequence) {
        this.pointSequence = new ArrayList<>(pointSequence);
        filledSize = pointSequence.size();
        capacity = filledSize;
    }

    protected AbstractPointSequence(int capacity) {
        this.pointSequence = new ArrayList<>(capacity);
        filledSize = 1;
        this.capacity = capacity;
    }

    @Override
    public T get(int index) {
        if (index > capacity - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index > filledSize - 1) {
                for (int i = filledSize - 1; i < index + 1; i++) {
                    pointSequence.add(i, newPoint());                    
                }
                filledSize = index + 1;
            }
        }

        return pointSequence.get(index);
    }

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public Geometry<T> getPointSeqence() {
        return (Geometry<T>) new Polygon2D<>(pointSequence);
    }

    @Override
    public Geometry<T> shuffle() {
        if (size() == filledSize) {
            Collections.shuffle(pointSequence);
        } else{
            fillSequence();
        }
        
        return this.getPointSeqence();
    }

    @Override
    public Geometry<T> perturb() {
        return this.perturb(getPerturbationModel());
    }

    @Override
    public Geometry<T> perturb(PerturbationModel perturbationModel) {
        perturbationModel.perturb(this.pointSequence);

        return this.getPointSeqence();
    }

    /**
     * Returns this sequence\'s perturbation model.
     *
     * @return
     */
    protected PerturbationModel getPerturbationModel() {
        return perturbationModel;
    }

    /**
     * Sets this sequence\'s perturbation model.
     *
     * @param perturbationModel
     */
    @Override
    public void setPerturbationModel(PerturbationModel perturbationModel) {
        this.perturbationModel = perturbationModel;
    }

    @Override
    public void fillSequence() {
        for(int i = filledSize; i < size(); i++){
            get(i);
        }
    }
}
