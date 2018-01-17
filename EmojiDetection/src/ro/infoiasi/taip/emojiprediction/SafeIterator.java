package ro.infoiasi.taip.emojiprediction;
import com.runtimeverification.rvmonitor.java.rt.*;
import com.runtimeverification.rvmonitor.java.rt.ref.*;
import com.runtimeverification.rvmonitor.java.rt.table.*;
import com.runtimeverification.rvmonitor.java.rt.tablebase.AbstractIndexingTree;
import com.runtimeverification.rvmonitor.java.rt.tablebase.SetEventDelegator;
import com.runtimeverification.rvmonitor.java.rt.tablebase.TableAdopter.Tuple2;
import com.runtimeverification.rvmonitor.java.rt.ref.*;
import com.runtimeverification.rvmonitor.java.rt.ref.*;


public class SafeIterator<E> implements java.util.Iterator<E> {
    private java.util.Iterator<E> it;

    public SafeIterator(java.util.Iterator it){
        this.it = it;
    }

    public boolean hasNext() {
        rvm.HasNextRuntimeMonitor.hasnextEvent(it);
        return it.hasNext();
    }

    public E next(){
        rvm.HasNextRuntimeMonitor.nextEvent(it);
        return it.next();
    }

    public void remove(){
        it.remove();
    }
}
