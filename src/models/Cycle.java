
package models;

import java.util.Iterator;
import java.util.Set;

public class Cycle {


    /* Comments means default values
     * if user does not initializes it. */

    private int cycleID;                      // -1
    private ProcessStatus.Status cycleStatus; // null
    private Set<Deal> deals;                  // null


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
     * Constructor of a Cycle.
     * Initializes Cycle with:
     * 1) Uninitialized Cycle ID
     * 2) Uninitialized Cycle Status
     * 3) Initialized Set of Deals
     * @param deals - Set of Deals which contains Cycle
     */
    public Cycle(Set<Deal> deals) {
        this(-1, null, deals);
    }


    /**
     * @return ID of a Cycle
     *         If returned -1 that means that
     *         Cycle's ID is not initialized yet.
     */
    public int getCycleID() { return cycleID; }


    /**
     * @return Cycle's Process Status
     *         If returned null that means that
     *         Cycle's Status is not initialized yet.
     */
    public ProcessStatus.Status getCycleStatus() { return cycleStatus; }


    /**
     * @return Set of Deals which contains Cycle
     *         If returned null that means that
     *         Cycle's Set of Deals is not initialized yet.
     */
    public Set<Deal> getDeals() { return deals; }


    /**
     * @return Iterator of deals
     * @throws NullPointerException if deals in not initialized
     */
    public Iterator<Deal> getDealsIterator() {
        return deals.iterator();
    }


    /**
     * Adds Deal into the Set of Deals
     * @param deal - Deal to add into Set of Deals
     * @throws NullPointerException if deals in not initialized
     */
    public void addDeal(Deal deal) {
        deals.add(deal);
    }


    /**
     * @param other - Passed Cycle
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
