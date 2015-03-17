/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.pointsequences;

import cg.common.smoothedanalysis.PerturbationModel;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import java.util.List;

/**
 *
 * @author I827590
 */
public interface PointSequence<T extends Point> extends List<T>{
    Geometry<T> getPointSeqence();
    Geometry<T> shuffle();
    Geometry<T> perturb();
    Geometry<T> perturb(PerturbationModel perturbationModel);
    void setPerturbationModel(PerturbationModel<T, Double> perturbationModel);
}
