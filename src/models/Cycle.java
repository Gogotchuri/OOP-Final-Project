
package models;

import managers.DealsManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cycle implements Comparable<Cycle>{

    /* Comments means default values
     * if user does not initializes it. */

    private int cycleID;                      // 0
    private ProcessStatus.Status cycleStatus; // null
    private List<Deal> deals;                 // null


    /**
     * Main Constructor of a Cycle.
     * @param cycleID - ID of a Cycle in DB
     * @param cycleStatus - Process Status of a Cycle
     * @param deals - Set of Deals which contains Cycle
     */
    public Cycle(int cycleID, ProcessStatus.Status cycleStatus, List<Deal> deals) {
        this.cycleID = cycleID;
        this.cycleStatus = cycleStatus;
        this.deals = deals;
    }

    /**
     * Constructor with only ID
     * @param cycleID - ID of a Cycle in DB
     */
    public Cycle(int cycleID) {
        this(cycleID, null, null);
    }

    /**
     * Constructor of a Cycle.
     * Initializes Cycle with:
     * 1) Uninitialized Cycle ID
     * 2) Uninitialized Cycle Status
     * 3) Initialized Set of Deals
     * @param deals - Set of Deals which contains Cycle
     */
    public Cycle(List<Deal> deals) {
        this(0, null, deals);
    }


    /**
     * @return ID of a Cycle.
     *         If returned -1 that means that
     *         Cycle's ID is not initialized yet.
     */
    public int getCycleID() { return cycleID; }


    /**
     * Updates cycle ID
     * @param cycleID - ID of a Cycle in DB
     */
    public void setCycleID(int cycleID) {
        this.cycleID = cycleID;
    }


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
    public List<Deal> getDeals() { return deals; }


    /**
     * @return Iterator of deals
     * @throws NullPointerException if deals in not initialized
     */
    public Iterator<Deal> getDealsIterator() {
        if(deals == null) return null;
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
     * find users deals
     * @param userId ID of user, whose deals are looked for
     * @return List<Deal> deals of given user in this cycle
     */
    public List<Deal> getUserDeals(int userId){
        return DealsManager.getUsersDealsByCycleId(userId, cycleID);
    }


    /**
     * !!! Cycle ID must be Initialized for correct comparing !!!
     * @param other - Passed Cycle
     * @return Whether two Cycles are equal or not
     */
    @Override public boolean equals(Object other) {

        if (this == other) return true;
        if (!(other instanceof Cycle)) return false;

        Cycle otherCycle = (Cycle) other;

        return cycleID == otherCycle.cycleID;
    }

    /**
     * @param cycle - Passed Cycle
     * @return Comparison of Cycles
     */
    @Override public int compareTo(Cycle cycle) {
        if (cycle.cycleID > this.cycleID)
            return -1;
        if (cycle.cycleID < this.cycleID)
            return 1;
        return 0;
    }

}
