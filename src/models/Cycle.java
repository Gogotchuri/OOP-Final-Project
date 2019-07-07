
package models;

import java.util.Iterator;
import java.util.Set;

public class Cycle {


    private int cycleID;
    private ProcessStatus.Status cycleStatus;
    private Set<Deal> deals;


    /**
     * Mains Constructor of a Cycle.
     * @param cycleID - ID of a Cycle in DB
     * @param cycleStatus - Process Status of a Cycle
     * @param deals - Set of Deals which contains Cycle
     */
    public Cycle(int cycleID, ProcessStatus.Status cycleStatus, Set<Deal> deals) {
        this.cycleID = cycleID;
        this.cycleStatus = cycleStatus;
        this.deals = deals;
    }


    /**
     * Constructor.
     * @param deals - Set of Deals which contains Cycle
     */
    public Cycle(Set<Deal> deals) {
        /* -1 means and null means that, cycleID and cycleStatus
         * are not initialized yet. */
        this(-1, null, deals);
    }


    /**
     * @return ID of a Cycle
     */
    public int getCycleID() { return cycleID; }


    /**
     * @return Cycle's Process Status
     */
    public ProcessStatus.Status getCycleStatus() { return cycleStatus; }


    /**
     * @return Set of Deals which contains Cycle
     */
    public Set<Deal> getDeals() { return deals; }


    /**
     * @return Iterator of 'deals'
     */
    public Iterator<Deal> getDealsIterator() {
        return deals.iterator();
    }


    /**
     * @param other Passed Cycle
     * @return Whether two Cycles are equal or not
     */
    @Override public boolean equals(Object other) {

        if (this == other) return true;
        if (!(other instanceof Cycle)) return false;

        Cycle otherCycle = (Cycle) other;

        if (cycleID != -1 && cycleID == otherCycle.cycleID)
            return true;

        return deals.equals(otherCycle.deals);
    }

}
