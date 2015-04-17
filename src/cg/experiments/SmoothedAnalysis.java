/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.experiments;

import cg.common.collections.pointsequences.ConcentricRandomPointSequence;
import cg.common.collections.pointsequences.PointSequence;
import cg.common.smoothedanalysis.PerturbationModel;
import cg.common.smoothedanalysis.UniformNoisePerturbationModel;
import cg.convexhull.approximate.streaming.StreamedConvexHull;
import cg.convexhull.exact.ConvexHull;
import cg.convexhull.exact.impl.AndrewsMonotoneChain;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.impl.Point2D;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rrufai
 */
public class SmoothedAnalysis {

    private static boolean sysout = false;
    private final PerturbationModel<Point2D, Double> perturbationModel;
    private PrintStream out;

    /**
     *
     */
    public SmoothedAnalysis() { // perturbation model == uniform
        this(new UniformNoisePerturbationModel(), System.out);
    }

    /**
     *
     * @param perturbationModel
     */
    public SmoothedAnalysis(
            PerturbationModel<Point2D, Double> perturbationModel, //gaussian or uniform
            PrintStream printStream) {

        this.perturbationModel = perturbationModel;
        this.out = printStream;
    }

    PointSequence<Point2D> generateAdversarialPointset(int size, double radius) {
        PointSequence<Point2D> pointSequence = new ConcentricRandomPointSequence<>(size, radius);
        pointSequence.setPerturbationModel(perturbationModel);

        return pointSequence;
    }

    List<Point2D> generatePerturbation(List<Point2D> pointset) {
        return perturbationModel.perturb(pointset);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        double radius = 1000.0;
        double windowWidthRatio = 0.25;
        int repetitions = 3;
        int numberOfPoints = 35;
        int dataSizes = 100;
        //int skip = numberOfPoints / 2;
        int experiments = 3;            
        PerturbationModel<Point2D, Double> model =
                new UniformNoisePerturbationModel();
        model.setParameter("windowWidthRatio", windowWidthRatio);
        model.setParameter("radius", radius);
        SmoothedAnalysis analysis = new SmoothedAnalysis(model, getPrintStream("experiments/error.txt"));
        for (int d = 0; d < dataSizes; d++) {
            for (int i = 0; i < experiments; i++) {
                for (int budget = 3; budget < Math.log(numberOfPoints); budget++) {
                    analysis.runPertubations(radius, numberOfPoints, budget, repetitions, "diameter");
                    analysis.runPertubations(radius, numberOfPoints, budget, repetitions, "area");
                }
                System.out.print("*");
            }
            numberOfPoints *= 1.2;
            System.out.print(".");
        }
    }

    private void runPertubations(double radius, int size, int budget, int repetitions, String type) {
        PointSequence<Point2D> pointSequence = generateAdversarialPointset(size, radius);
        ComparisonResult aggregateComparisonResult = null;
        Geometry<Point2D> perturbedSet = pointSequence.getPointSeqence();
        for (int i = 0; i < repetitions; i++) {
            ComparisonResult comparisonResult = runComparison(budget, perturbedSet, type);
            if (aggregateComparisonResult == null) {
                aggregateComparisonResult = comparisonResult;
            }
            if (type.equals("diameter")) {
                if (comparisonResult.getRelativeDiameterDiff() > aggregateComparisonResult.getRelativeDiameterDiff()) {
                    aggregateComparisonResult.updateDiameter(comparisonResult);
                }
            } else {
                if (comparisonResult.getRelativeAreaDiff() > aggregateComparisonResult.getRelativeAreaDiff()) {
                    aggregateComparisonResult.updateArea(comparisonResult);
                }
            }

            perturbedSet = pointSequence.perturb();
        }

        out.println(aggregateComparisonResult);
    }

    private static class ComparisonResult {

        String type;
        int setSize;
        int hullsize;
        int trueHullsize;
        int budget;
        double trueDiameter;
        double streamedDiameter;
        double trueArea;
        double streamedArea;

        double getDiameterDiff() {
            return trueDiameter - streamedDiameter;
        }

        double getRelativeDiameterDiff() {
            return 1.0 - streamedDiameter / trueDiameter;
        }

        double getAreaDiff() {
            return trueArea - streamedArea;
        }

        double getRelativeAreaDiff() {
            return 1.0 - streamedArea / trueArea;
        }

        double[] getErrorModels() {
            return new double[]{1.0 / Math.sqrt(budget), 1.0 / budget, 1.0 / (budget * budget), 1.0 / Math.pow(budget, 3.0), (trueHullsize - budget) / Math.pow(budget, 3.0)};
        }

        void updateDiameter(ComparisonResult comparisonResult) {
            this.type = "diameter";
            this.hullsize = comparisonResult.hullsize;
            this.trueHullsize = comparisonResult.trueHullsize;
            this.trueDiameter = comparisonResult.trueDiameter;
            this.streamedDiameter = comparisonResult.streamedDiameter;
        }

        void updateArea(ComparisonResult comparisonResult) {
            this.type = "area";
            this.hullsize = comparisonResult.hullsize;
            this.trueHullsize = comparisonResult.trueHullsize;
            this.trueArea = comparisonResult.trueArea;
            this.streamedArea = comparisonResult.streamedArea;
        }

        @Override
        public String toString() {
            Formatter formatter = new Formatter();
            return formatter.format("%s \t%d \t%d \t%d \t%d \t%07.3f \t%07.3f \t%07.3f \t%07.3f \t%07.3f \t%07.3f \t%07.3f \t%07.3f \t%07.3f \t%07.3f \t%07.3f \t%07.3f", type, setSize, hullsize, trueHullsize, budget, trueDiameter, streamedDiameter, getDiameterDiff(), getRelativeDiameterDiff(), trueArea, streamedArea, getAreaDiff(), getRelativeAreaDiff(), getErrorModels()[0], getErrorModels()[1], getErrorModels()[2], getErrorModels()[3]).toString();
        }
    };

    private ComparisonResult runComparison(int budget, Geometry<Point2D> geometry, String type) {
        ConvexHull<Point2D> streamedHull = new StreamedConvexHull<>(budget);

        Geometry<Point2D> streamedHullGeometry = streamedHull.compute(geometry);

        ConvexHull<Point2D> trueHull = new AndrewsMonotoneChain<>();
        Geometry<Point2D> trueHullGeometry = trueHull.compute(geometry);
        ComparisonResult comparisonResult = new ComparisonResult();
        comparisonResult.type = type;
        comparisonResult.setSize = geometry.getVertices().size();
        comparisonResult.budget = budget;
        comparisonResult.trueHullsize = trueHullGeometry.getVertices().size();
        comparisonResult.hullsize = streamedHullGeometry.getVertices().size();
        comparisonResult.trueDiameter = trueHullGeometry.getDiameter();
        comparisonResult.streamedDiameter = streamedHullGeometry.getDiameter();
        comparisonResult.trueArea = trueHullGeometry.getArea();
        comparisonResult.streamedArea = streamedHullGeometry.getArea();

        return comparisonResult;
    }

    private static PrintStream getPrintStream(String fileName) {
        PrintStream out = null;
        try {
            out = new PrintStream(fileName);
            if (sysout) {
                out = System.out;
            }

            out.println("type        \tsize \thullsize \ttrueHullsize \tbudget \ttrueDiameter \tstrDiameter \tdiameterDiff \trelDiamDiff \ttrueArea \tstreamedArea \tareaDiff \trelAreaDiff \terrorModel1 \terrorModel2 \terrorModel3 \terrorModel4");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SmoothedAnalysis.class.getName()).log(Level.SEVERE, null, ex);
        }

        return out;
    }
}