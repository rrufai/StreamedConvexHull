/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.smoothedanalysis;

import java.util.List;
import java.util.Map;

/**
 *
 * @param <K>
 * @param <V>
 * @author rrufai
 */
public interface PerturbationModel<K, V extends Number> {

    /**
     *
     * @param data
     * @return
     */
    List<K> perturb(List<K> data);

    /**
     *
     * @param data
     * @param parameters
     * @return
     */
    List<K> perturb(
            List<K> data, Map<String, V> parameters);

    /**
     *
     * @param name
     * @param value
     */
    void setParameter(String name, V value);

    /**
     *
     * @param name
     * @param value
     */
    void setParameter(String name, String value);
}
