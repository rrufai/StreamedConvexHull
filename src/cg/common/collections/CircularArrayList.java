/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Raimi Rufai
 */
public class CircularArrayList<E extends Comparable> extends ArrayList<E> {

    public CircularArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public CircularArrayList() {
    }

    public CircularArrayList(Collection<? extends E> c) {
        super(c);
    }
    

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }

        if (this.size() != ((List) o).size()) {
            return false;
        }

        ListComparator comparator = new ListComparator();
        List aList = (List) this.clone();
        List bList = new ArrayList((List)o);
        
        for(int i = 0; i < aList.size() - 1; i++){
            if(comparator.compare(aList, bList) == 0){
                return true;
            }else if(comparator.compare(aList, bList) < 0){
                Collections.rotate(aList, 1);
            }else {
                Collections.rotate(bList, 1);
            }
        }

        return false;
    }

 

    private class ListComparator implements Comparator<List<E> > {

        @Override
        public int compare(List<E> aList, List<E> bList) {
            int retValue = 0;

            if (aList.size() == bList.size()) {
                ListIterator<E> e1 = aList.listIterator();
                ListIterator<E> e2 = bList.listIterator();
                while (e1.hasNext() && e2.hasNext()) {
                    E o1 = e1.next();
                    E o2 = e2.next();
                    if (o1 != null && o2 != null) {
                        return o1.compareTo(o2);
                    }else{
                        return o1 == null ? -1 : 1;
                    }
                }
            } else {
                retValue = Integer.signum(aList.size() - bList.size());
            }
            return retValue;
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (E elem : this) {
            if (elem != null) {
                hash += elem.hashCode();
            }
        }
        return hash;
    }
}
