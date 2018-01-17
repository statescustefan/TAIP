package rvm;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.lang.ref.*;
import com.runtimeverification.rvmonitor.java.rt.*;
import com.runtimeverification.rvmonitor.java.rt.ref.*;
import com.runtimeverification.rvmonitor.java.rt.table.*;
import com.runtimeverification.rvmonitor.java.rt.tablebase.AbstractIndexingTree;
import com.runtimeverification.rvmonitor.java.rt.tablebase.SetEventDelegator;
import com.runtimeverification.rvmonitor.java.rt.tablebase.TableAdopter.Tuple2;
import com.runtimeverification.rvmonitor.java.rt.tablebase.TableAdopter.Tuple3;
import com.runtimeverification.rvmonitor.java.rt.tablebase.IDisableHolder;
import com.runtimeverification.rvmonitor.java.rt.tablebase.IMonitor;
import com.runtimeverification.rvmonitor.java.rt.tablebase.DisableHolder;
import com.runtimeverification.rvmonitor.java.rt.tablebase.TerminatedMonitorCleaner;
import java.util.concurrent.atomic.AtomicInteger;

final class SafeFileWriterMonitor_Set extends com.runtimeverification.rvmonitor.java.rt.tablebase.AbstractMonitorSet<SafeFileWriterMonitor> {

	SafeFileWriterMonitor_Set(){
		this.size = 0;
		this.elements = new SafeFileWriterMonitor[4];
	}
	final void event_open(FileWriter f) {
		int numAlive = 0 ;
		for(int i = 0; i < this.size; i++){
			SafeFileWriterMonitor monitor = this.elements[i];
			if(!monitor.isTerminated()){
				elements[numAlive] = monitor;
				numAlive++;

				final SafeFileWriterMonitor monitorfinalMonitor = monitor;
				monitor.Prop_1_event_open(f);
				if(monitorfinalMonitor.Prop_1_Category_fail) {
					monitorfinalMonitor.Prop_1_handler_fail();
				}
				if(monitorfinalMonitor.Prop_1_Category_match) {
					monitorfinalMonitor.Prop_1_handler_match();
				}
			}
		}
		for(int i = numAlive; i < this.size; i++){
			this.elements[i] = null;
		}
		size = numAlive;
	}
	final void event_write(FileWriter f) {
		int numAlive = 0 ;
		for(int i = 0; i < this.size; i++){
			SafeFileWriterMonitor monitor = this.elements[i];
			if(!monitor.isTerminated()){
				elements[numAlive] = monitor;
				numAlive++;

				final SafeFileWriterMonitor monitorfinalMonitor = monitor;
				monitor.Prop_1_event_write(f);
				if(monitorfinalMonitor.Prop_1_Category_fail) {
					monitorfinalMonitor.Prop_1_handler_fail();
				}
				if(monitorfinalMonitor.Prop_1_Category_match) {
					monitorfinalMonitor.Prop_1_handler_match();
				}
			}
		}
		for(int i = numAlive; i < this.size; i++){
			this.elements[i] = null;
		}
		size = numAlive;
	}
	final void event_close(FileWriter f) {
		int numAlive = 0 ;
		for(int i = 0; i < this.size; i++){
			SafeFileWriterMonitor monitor = this.elements[i];
			if(!monitor.isTerminated()){
				elements[numAlive] = monitor;
				numAlive++;

				final SafeFileWriterMonitor monitorfinalMonitor = monitor;
				monitor.Prop_1_event_close(f);
				if(monitorfinalMonitor.Prop_1_Category_fail) {
					monitorfinalMonitor.Prop_1_handler_fail();
				}
				if(monitorfinalMonitor.Prop_1_Category_match) {
					monitorfinalMonitor.Prop_1_handler_match();
				}
			}
		}
		for(int i = numAlive; i < this.size; i++){
			this.elements[i] = null;
		}
		size = numAlive;
	}
}

class SafeFileWriterMonitor extends com.runtimeverification.rvmonitor.java.rt.tablebase.AbstractAtomicMonitor implements Cloneable, RVMObject {
	protected Object clone() {
		try {
			SafeFileWriterMonitor ret = (SafeFileWriterMonitor) super.clone();
			return ret;
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

	static int counter = 0;
	int writes = 0;

	static final int Prop_1_transition_open[] = {2, 3, 3, 3};;
	static final int Prop_1_transition_write[] = {3, 1, 1, 3};;
	static final int Prop_1_transition_close[] = {3, 0, 3, 3};;

	volatile boolean Prop_1_Category_fail = false;
	volatile boolean Prop_1_Category_match = false;

	private final AtomicInteger pairValue;

	SafeFileWriterMonitor() {
		this.pairValue = new AtomicInteger(this.calculatePairValue(-1, 0) ) ;

	}

	@Override public final int getState() {
		return this.getState(this.pairValue.get() ) ;
	}
	@Override public final int getLastEvent() {
		return this.getLastEvent(this.pairValue.get() ) ;
	}
	private final int getState(int pairValue) {
		return (pairValue & 3) ;
	}
	private final int getLastEvent(int pairValue) {
		return (pairValue >> 2) ;
	}
	private final int calculatePairValue(int lastEvent, int state) {
		return (((lastEvent + 1) << 2) | state) ;
	}

	private final int handleEvent(int eventId, int[] table) {
		int nextstate;
		while (true) {
			int oldpairvalue = this.pairValue.get() ;
			int oldstate = this.getState(oldpairvalue) ;
			nextstate = table [ oldstate ];
			int nextpairvalue = this.calculatePairValue(eventId, nextstate) ;
			if (this.pairValue.compareAndSet(oldpairvalue, nextpairvalue) ) {
				break;
			}
		}
		return nextstate;
	}

	final boolean Prop_1_event_open(FileWriter f) {
		{ //after
			this.writes = 0;
		}

		int nextstate = this.handleEvent(0, Prop_1_transition_open) ;
		this.Prop_1_Category_fail = nextstate == 3;
		this.Prop_1_Category_match = nextstate == 0;

		return true;
	}

	final boolean Prop_1_event_write(FileWriter f) {
		{ //  before
			this.writes ++;
		}

		int nextstate = this.handleEvent(1, Prop_1_transition_write) ;
		this.Prop_1_Category_fail = nextstate == 3;
		this.Prop_1_Category_match = nextstate == 0;

		return true;
	}

	final boolean Prop_1_event_close(FileWriter f) {
		{}

		int nextstate = this.handleEvent(2, Prop_1_transition_close) ;
		this.Prop_1_Category_fail = nextstate == 3;
		this.Prop_1_Category_match = nextstate == 0;

		return true;
	}

	final void Prop_1_handler_fail (){
		{
			System.out.println("write after close");
			this.reset();
		}

	}

	final void Prop_1_handler_match (){
		{
			System.out.println((++(counter))
			+ ":" + writes);
		}

	}

	final void reset() {
		this.pairValue.set(this.calculatePairValue(-1, 0) ) ;

		Prop_1_Category_fail = false;
		Prop_1_Category_match = false;
	}

	// RVMRef_f was suppressed to reduce memory overhead

	//alive_parameters_0 = [FileWriter f]
	boolean alive_parameters_0 = true;

	@Override
	protected final void terminateInternal(int idnum) {
		int lastEvent = this.getLastEvent();

		switch(idnum){
			case 0:
			alive_parameters_0 = false;
			break;
		}
		switch(lastEvent) {
			case -1:
			return;
			case 0:
			//open
			//alive_f
			if(!(alive_parameters_0)){
				RVM_terminated = true;
				return;
			}
			break;

			case 1:
			//write
			//alive_f
			if(!(alive_parameters_0)){
				RVM_terminated = true;
				return;
			}
			break;

			case 2:
			//close
			//alive_f
			if(!(alive_parameters_0)){
				RVM_terminated = true;
				return;
			}
			break;

		}
		return;
	}

	public static int getNumberOfEvents() {
		return 3;
	}

	public static int getNumberOfStates() {
		return 4;
	}

}

public final class SafeFileWriterRuntimeMonitor implements RVMObject {
	private static com.runtimeverification.rvmonitor.java.rt.map.RVMMapManager SafeFileWriterMapManager;
	static {
		SafeFileWriterMapManager = new com.runtimeverification.rvmonitor.java.rt.map.RVMMapManager();
		SafeFileWriterMapManager.start();
	}

	// Declarations for the Lock
	static final ReentrantLock SafeFileWriter_RVMLock = new ReentrantLock();
	static final Condition SafeFileWriter_RVMLock_cond = SafeFileWriter_RVMLock.newCondition();

	private static boolean SafeFileWriter_activated = false;

	// Declarations for Indexing Trees
	private static Object SafeFileWriter_f_Map_cachekey_f;
	private static SafeFileWriterMonitor SafeFileWriter_f_Map_cachevalue;
	private static final MapOfMonitor<SafeFileWriterMonitor> SafeFileWriter_f_Map = new MapOfMonitor<SafeFileWriterMonitor>(0) ;

	public static int cleanUp() {
		int collected = 0;
		// indexing trees
		collected += SafeFileWriter_f_Map.cleanUpUnnecessaryMappings();
		return collected;
	}

	// Removing terminated monitors from partitioned sets
	static {
		TerminatedMonitorCleaner.start() ;
	}
	// Setting the behavior of the runtime library according to the compile-time option
	static {
		RuntimeOption.enableFineGrainedLock(false) ;
	}

	public static final void openEvent(FileWriter f) {
		SafeFileWriter_activated = true;
		while (!SafeFileWriter_RVMLock.tryLock()) {
			Thread.yield();
		}

		CachedWeakReference wr_f = null;
		MapOfMonitor<SafeFileWriterMonitor> matchedLastMap = null;
		SafeFileWriterMonitor matchedEntry = null;
		boolean cachehit = false;
		if ((f == SafeFileWriter_f_Map_cachekey_f) ) {
			matchedEntry = SafeFileWriter_f_Map_cachevalue;
			cachehit = true;
		}
		else {
			wr_f = new CachedWeakReference(f) ;
			{
				// FindOrCreateEntry
				MapOfMonitor<SafeFileWriterMonitor> itmdMap = SafeFileWriter_f_Map;
				matchedLastMap = itmdMap;
				SafeFileWriterMonitor node_f = SafeFileWriter_f_Map.getNodeEquivalent(wr_f) ;
				matchedEntry = node_f;
			}
		}
		// D(X) main:1
		if ((matchedEntry == null) ) {
			if ((wr_f == null) ) {
				wr_f = new CachedWeakReference(f) ;
			}
			// D(X) main:4
			SafeFileWriterMonitor created = new SafeFileWriterMonitor() ;
			matchedEntry = created;
			matchedLastMap.putNode(wr_f, created) ;
		}
		// D(X) main:8--9
		final SafeFileWriterMonitor matchedEntryfinalMonitor = matchedEntry;
		matchedEntry.Prop_1_event_open(f);
		if(matchedEntryfinalMonitor.Prop_1_Category_fail) {
			matchedEntryfinalMonitor.Prop_1_handler_fail();
		}
		if(matchedEntryfinalMonitor.Prop_1_Category_match) {
			matchedEntryfinalMonitor.Prop_1_handler_match();
		}

		if ((cachehit == false) ) {
			SafeFileWriter_f_Map_cachekey_f = f;
			SafeFileWriter_f_Map_cachevalue = matchedEntry;
		}

		SafeFileWriter_RVMLock.unlock();
	}

	public static final void writeEvent(FileWriter f) {
		SafeFileWriter_activated = true;
		while (!SafeFileWriter_RVMLock.tryLock()) {
			Thread.yield();
		}

		CachedWeakReference wr_f = null;
		MapOfMonitor<SafeFileWriterMonitor> matchedLastMap = null;
		SafeFileWriterMonitor matchedEntry = null;
		boolean cachehit = false;
		if ((f == SafeFileWriter_f_Map_cachekey_f) ) {
			matchedEntry = SafeFileWriter_f_Map_cachevalue;
			cachehit = true;
		}
		else {
			wr_f = new CachedWeakReference(f) ;
			{
				// FindOrCreateEntry
				MapOfMonitor<SafeFileWriterMonitor> itmdMap = SafeFileWriter_f_Map;
				matchedLastMap = itmdMap;
				SafeFileWriterMonitor node_f = SafeFileWriter_f_Map.getNodeEquivalent(wr_f) ;
				matchedEntry = node_f;
			}
		}
		// D(X) main:1
		if ((matchedEntry == null) ) {
			if ((wr_f == null) ) {
				wr_f = new CachedWeakReference(f) ;
			}
			// D(X) main:4
			SafeFileWriterMonitor created = new SafeFileWriterMonitor() ;
			matchedEntry = created;
			matchedLastMap.putNode(wr_f, created) ;
		}
		// D(X) main:8--9
		final SafeFileWriterMonitor matchedEntryfinalMonitor = matchedEntry;
		matchedEntry.Prop_1_event_write(f);
		if(matchedEntryfinalMonitor.Prop_1_Category_fail) {
			matchedEntryfinalMonitor.Prop_1_handler_fail();
		}
		if(matchedEntryfinalMonitor.Prop_1_Category_match) {
			matchedEntryfinalMonitor.Prop_1_handler_match();
		}

		if ((cachehit == false) ) {
			SafeFileWriter_f_Map_cachekey_f = f;
			SafeFileWriter_f_Map_cachevalue = matchedEntry;
		}

		SafeFileWriter_RVMLock.unlock();
	}

	public static final void closeEvent(FileWriter f) {
		SafeFileWriter_activated = true;
		while (!SafeFileWriter_RVMLock.tryLock()) {
			Thread.yield();
		}

		CachedWeakReference wr_f = null;
		MapOfMonitor<SafeFileWriterMonitor> matchedLastMap = null;
		SafeFileWriterMonitor matchedEntry = null;
		boolean cachehit = false;
		if ((f == SafeFileWriter_f_Map_cachekey_f) ) {
			matchedEntry = SafeFileWriter_f_Map_cachevalue;
			cachehit = true;
		}
		else {
			wr_f = new CachedWeakReference(f) ;
			{
				// FindOrCreateEntry
				MapOfMonitor<SafeFileWriterMonitor> itmdMap = SafeFileWriter_f_Map;
				matchedLastMap = itmdMap;
				SafeFileWriterMonitor node_f = SafeFileWriter_f_Map.getNodeEquivalent(wr_f) ;
				matchedEntry = node_f;
			}
		}
		// D(X) main:1
		if ((matchedEntry == null) ) {
			if ((wr_f == null) ) {
				wr_f = new CachedWeakReference(f) ;
			}
			// D(X) main:4
			SafeFileWriterMonitor created = new SafeFileWriterMonitor() ;
			matchedEntry = created;
			matchedLastMap.putNode(wr_f, created) ;
		}
		// D(X) main:8--9
		final SafeFileWriterMonitor matchedEntryfinalMonitor = matchedEntry;
		matchedEntry.Prop_1_event_close(f);
		if(matchedEntryfinalMonitor.Prop_1_Category_fail) {
			matchedEntryfinalMonitor.Prop_1_handler_fail();
		}
		if(matchedEntryfinalMonitor.Prop_1_Category_match) {
			matchedEntryfinalMonitor.Prop_1_handler_match();
		}

		if ((cachehit == false) ) {
			SafeFileWriter_f_Map_cachekey_f = f;
			SafeFileWriter_f_Map_cachevalue = matchedEntry;
		}

		SafeFileWriter_RVMLock.unlock();
	}

}
