package ro.infoiasi.taip.emojiprediction;

import java.util.Iterator;
import java.util.Vector;

public class Test {
    public static void main(String[] args){
        Vector<Integer> v = new Vector<Integer>();

        v.add(1);
        v.add(2);
        v.add(4);
        v.add(8);

        Iterator it = v.iterator();
        SafeIterator i = new SafeIterator(it);
        int sum = 0;

        if(i.hasNext()){
            sum += (Integer)i.next();
            sum += (Integer)i.next();
            sum += (Integer)i.next();
                sum += (Integer)i.next();
            }

            System.out.println("sum: " + sum);
    }
}