
package events;

import managers.CycleManager;
import managers.DealsManager;
import models.Category;
import models.Cycle;
import models.Deal;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DealCyclesFinder extends Thread {

    private static final int CYCLE_MAX_LENGTH = 4;

    /**
     Deal which should be checked
     for new cycles.
     */
    private Deal deal;

    /**
     Protects us from duplicating
     cycles in Data Base.
     */
    private static Lock lock = new ReentrantLock();

    /**
     Private helper structure
     for linking deals
     to save cycle property
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
     Main constructor,
     for getting parameters,
     before starting run() method.
     */
    public DealCyclesFinder(Deal deal) {
        this.deal = deal;
    }

    /**
     Updates, creates new cycles
     associated with passed deal.
     */
    @Override public void run() {

        Queue<LinkedDeal> currBreadth = new LinkedList<>(),
                           nextBreadth = new LinkedList<>();

        Set<Deal> usedDeals = new HashSet<>();

        int currBreadthIndex = 0;
        currBreadth.add(new LinkedDeal(this.deal, null));
        usedDeals.add(this.deal);

        while (currBreadthIndex++ < CYCLE_MAX_LENGTH &&
                !currBreadth.isEmpty()) {

            /* Iterate over currBreadth */
            while (!currBreadth.isEmpty()) {
                LinkedDeal linkedDeal = currBreadth.poll();

                if (dealMakesCycle(linkedDeal.deal)) {
                    Cycle cycle = getCycle(linkedDeal);

                    lock.lock();
                    if (!CycleManager.containsDB(cycle))
                        CycleManager.addCycleToDB(cycle);
                    lock.unlock();
                }

                // Just for optimization
                if (currBreadthIndex == CYCLE_MAX_LENGTH)
                    continue;

                List<Deal> clients = DealsManager.getClients(new Deal(linkedDeal.deal));

                for (Deal clientDeal : clients) // Returns at least empty list
                    if (!usedDeals.contains(clientDeal)) {
                        usedDeals.add(clientDeal);

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
     Returns true if and only if:
     Passed deal != this.deal and
     deal.wantedItemCategories == this.deal.ownedItemCategories
     */
    private boolean dealMakesCycle(Deal deal) {

        if (deal.equals(this.deal))
            return false;

        /*
         l1 -> Parameter deal, wanted item categories.
         l2 -> this.deal, owned item categories.
         */
        List<Category> l1 = deal.getWantedItemCategories(),
                        l2 = this.deal.getOwnedItemCategories();

        return Category.listsEqualsIgnoreOrder(l1, l2);
    }

    /**
     Returns cycle made with this.deal
     */
    private Cycle getCycle(LinkedDeal linkedDeal) {
        List<Deal> cycleDeals = new ArrayList<>(CYCLE_MAX_LENGTH);

        while (linkedDeal != null) {
            cycleDeals.add(new Deal(linkedDeal.deal));
            linkedDeal = linkedDeal.linkedTo;
        }

        return new Cycle(cycleDeals);
    }
}
