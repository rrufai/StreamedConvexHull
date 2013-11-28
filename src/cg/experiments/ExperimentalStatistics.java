package cg.experiments;

import cg.geometry.primitives.impl.Point2D;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * @author rrufai
 */
@Aspect
public class ExperimentalStatistics {
	private int comparisons;
	
        /**
         *
         */
        @Pointcut("call(* cg.common.comparators.RadialComparator.ccw(..))")
	public void compare() {
	}

        /**
         *
         */
        @Pointcut("execution(public static void cg.experiments.SmoothedAnalysis.main(..))")
	public void mainHeader() {		
	}
	
        /**
         *
         */
        @Pointcut("call(private * cg.experiments.SmoothedAnalysis.runWorstCaseInstance(..))")
	public void worstCaseInstance() {		
	}
	
        /**
         *
         */
        @Pointcut("call(private * cg.experiments.SmoothedAnalysis.runPertubations(..))")
	public void perturbations() {		
	}
	
        /**
         *
         */
        @Before("mainHeader()")
	public void header(){
		System.out.println("Size \t\t\t Worst-case instance \t\t Perturbed (Expected)");
		System.out.println("--------------------------------------------------------------");
	}
	
        /**
         *
         * @param jp
         */
        @After("worstCaseInstance()")
	public void resetCounter(JoinPoint jp){
		List<Point2D> ps = (List<Point2D>)jp.getArgs()[0];
		int size = ps.size();
//		for(Point2D.Double point : ps){
//			System.out.println(point.getX() + ", " + point.getY() + ";");
//		}
		System.out.println();
		System.out.print(size + "\t\t\t" + comparisons + "\t\t");
		comparisons = 0;
	}
	
        /**
         *
         * @param jp
         */
        @After("perturbations()")
	public void logPertubations(JoinPoint jp){
		int repetitions = (Integer) jp.getArgs()[1];

		System.out.println((1.0 * comparisons)/repetitions);
		comparisons = 0;
	}
	
        /**
         *
         */
        @After("compare()")
	public void logComparison() {
		comparisons++;		
	}
}