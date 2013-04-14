/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @param <K>
 * @author rrufai
 */
public class ReverseIterator<K> implements Iterator {

    ListIterator<K> base;

    /**
     *
     * @param list
     */
    public ReverseIterator(List list) {
        this.base = list.listIterator(list.size());
    }

    /**
     *
     * @param collection
     */
    public ReverseIterator(Collection collection) {
        List list = new ArrayList(collection);
        this.base = list.listIterator(list.size());
    }

    @Override
    public boolean hasNext() {
        return this.base.hasPrevious();
    }

    @Override
    public K next() {
        return base.previous();
    }

    @Override
    public void remove() {
        base.remove();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList();

        list.add("abcd");
        list.add("defg");
        list.add("bcde");
        list.add("vwxyz");

        for (Iterator iter = list.iterator(); iter.hasNext();) {
            System.out.println(iter.next());
        }

        System.out.println("\n reversed: ");
        for (Iterator iter = new ReverseIterator(list); iter.hasNext();) {
            System.out.println(iter.next());
        }
    }
}
