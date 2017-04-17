package UI.utitlity;

import UI.myobjects.GraphicalNode;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 20, 2006
 * Time: 9:38:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class NthMinimum {
    /**
     * generates n th minimum of array A
     * @param A
     * @param p
     * @param r
     * @param i
     * @return index of nth minimum in A
     */
    public static int randomized_Select(GNodeWrapper [] A,int p,int r,int i){
        if (p==r){
            return p;
        }
        int q = randomized_Partition(A,p,r);
        int k = q - p +1;
        if (i==k){
            return q;
        }else if (i<k){
            return randomized_Select(A,p,q-1,i);
        }else {
            return randomized_Select(A,q+1,r,i-k);
        }
    }

    private static int randomized_Partition(GNodeWrapper[] A, int p, int r) {
        int i = (int)(Math.random()*(r-p+1))+p;
        exchange(A,i,r);
        return partition(A,p,r);
    }

    private static void exchange(GNodeWrapper [] A,int i,int j){
        GNodeWrapper temp = A[j];
        A[j] = A[i];
        A[i] = temp;
    }

    private static int partition(GNodeWrapper[] A, int p, int r) {
        GNodeWrapper x = A[r];
        int i = p -1;
        for (int j = p; j <r; j++){
            if (A[j].compareTo(x)<=0){
                i++;
                exchange(A,i,j);
            }
        }
        exchange(A,i+1,r);
        return i+1;
    }
}

