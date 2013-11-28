/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.pointsequences;

import cg.common.smoothedanalysis.PerturbationModel;
import cg.common.smoothedanalysis.UniformNoisePerturbationModel;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AbstractPointSequence<T extends Point> extends AbstractList<T> implements PointSequence<T> {
    private final List<T> pointSequence;
    private PerturbationModel perturbationModel;

    protected AbstractPointSequence(List<T> pointSequence){
        this.pointSequence = new ArrayList<>(pointSequence);
    }
    
    @Override
    public T get(int index) {
        return pointSequence.get(index);
    }

    @Override
    public int size() {
        return pointSequence.size();
    }

    @Override
    public Geometry<T> getPointSeqence() {
        return (Geometry<T>) new Polygon2D<>(pointSequence);
    }

    @Override
    public Geometry<T> shuffle() {
        Collections.shuffle(pointSequence);
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

    private PerturbationModel getPerturbationModel() {
        if(perturbationModel == null){
            perturbationModel = new UniformNoisePerturbationModel();
        }
        
        return perturbationModel;
    }

}
