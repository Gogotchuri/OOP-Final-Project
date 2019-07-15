
package events;

import managers.CategoryManager;
import managers.CycleManager;
import managers.DealsManager;
import models.Cycle;
import models.Deal;
import models.categoryModels.ItemCategory;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DealCyclesFinder extends Thread {

    private static final int CYCLE_MAX_LENGTH = 4;

    /**
     * Deal which should be checked
     * for new cycles.
     */
    private Deal deal;


    /**
     * Protects us from duplicating
     * cycles in Data Base.
     */
    private static Lock lock = new ReentrantLock();


    /**
     * Private helper structure
     * for linking deals
     * to save cycle property
     */
    private static class LinkedDeal {
        Deal deal;
        LinkedDeal linkedTo;
        LinkedDeal(Deal deal, LinkedDeal linkedTo) {
            this.deal = deal;
            this.linkedTo = linkedTo;
        }
    }


    /**
     * Main constructor,
     * for getting parameters,
     * before starting run() method.
     * @param deal - Deal which's Cycles we are going to insert
     */
    public DealCyclesFinder(Deal deal) {
        this.deal = deal;
    }


    /**
     * Updates, creates new cycles
     * associated with passed deal.
     */
    @Override public void run() {

        Queue<LinkedDeal> currBreadth = new LinkedList<>(),
                           nextBreadth = new LinkedList<>();

        int currBreadthIndex = 0;
        currBreadth.add(new LinkedDeal(deal, null));

        while (currBreadthIndex++ < CYCLE_MAX_LENGTH &&
                !currBreadth.isEmpty()) {

            /* Iterate over currBreadth */
            while (!currBreadth.isEmpty()) {
                LinkedDeal linkedDeal = currBreadth.poll();

                if (dealMakesCycle(linkedDeal.deal)) {
                    Cycle cycle = getCycle(linkedDeal);

                    lock.lock();
                    try {
                        if (!CycleManager.containsDB(cycle))
                            CycleManager.addCycleToDB(cycle);
                    }
                    catch (SQLException e) { e.printStackTrace(); }
                    lock.unlock();
                }

                // Just for optimization
                if (currBreadthIndex == CYCLE_MAX_LENGTH)
                    continue;

                List<Deal> clients = DealsManager.getClients(linkedDeal.deal.getDealID());

                for (Deal clientDeal : clients)
                    if (!pathContainsDeal(linkedDeal, clientDeal)) {
                        LinkedDeal clientLinkedDeal = new LinkedDeal(clientDeal, linkedDeal);
                        nextBreadth.add(clientLinkedDeal);
                    }
            }

            Queue<LinkedDeal> temp = currBreadth;
            currBreadth = nextBreadth;
            nextBreadth = temp; // Empty Queue
        }
    }


    /**
     * @param deal - Deal Object
     * @return true if and only if:
     *         Passed deal != this.deal and
     *         deal.wantedItemCategories == this.deal.ownedItemCategories
     */
    private boolean dealMakesCycle(Deal deal) {

        if (deal.equals(this.deal))
            return false;

        /*
         l1 -> Parameter deal, wanted item categories.
         l2 -> this.deal, owned item categories.
         */
        List<ItemCategory> l1 = deal.getOwnedItemCategories(),
                            l2 = this.deal.getWantedCategories();

        return CategoryManager.listsEqualsIgnoreOrder(l1, l2);
    }


    /**
     * @param linkedDeal - LinkedDeal Object
     * @return Cycle made with this.deal
     */
    private Cycle getCycle(LinkedDeal linkedDeal) {
        List<Deal> cycleDeals = new ArrayList<>(CYCLE_MAX_LENGTH);

        while (linkedDeal != null) {
            cycleDeals.add(linkedDeal.deal);
            linkedDeal = linkedDeal.linkedTo;
        }

        return new Cycle(cycleDeals);
    }


    /**
     * @param startLinkedDeal - LinkedDeal Object
     * @param deal - Deal Object
     * @return false iff:
     *         Path from startLinkedDeal before null
     *         does not contains equal Deal of passed 'dea;'
     */
    private boolean pathContainsDeal(LinkedDeal startLinkedDeal, Deal deal) {
        while (startLinkedDeal != null) {
            if (startLinkedDeal.deal.equals(deal))
                return true;
            startLinkedDeal = startLinkedDeal.linkedTo;
        }
        return false;
    }

}
