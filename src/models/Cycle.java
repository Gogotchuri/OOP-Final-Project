package models;
import java.sql.Timestamp;
import java.util.*;

public class Cycle {

    private int id, statusId;
    private Timestamp createdAt, updatedAt;
    /**
     * Map representing structure of a cycle
     * Key is Id of a user
     * Value is Deal, that user is interested in
     */
    private Map<Integer, Deal> map;

    /**
     * Constructor of a cycle
     * @param id Id of a cycle
     * @param statusId Status of a cycle
     * @param createdAt Time of creation
     * @param updatedAt Time of update
     */
    public Cycle(int id, int statusId, Timestamp createdAt, Timestamp updatedAt){
        this.id = id;
        this.statusId = statusId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        map = new LinkedHashMap<>();
    }

    /**
     * Constructs a cycle object
     * @param deals List of deals making up a cycle, already sorted
     */
    public Cycle(List<Deal> deals) {
        for (int i = 0; i < deals.size(); i++)
            map.put(deals.get(i).getId(), deals.get((i + 1) % deals.size()));
    }

    /**
     * @param userId Id of a user
     * @return Deal that user is interested in
     */
    public Deal getWantedDealByUser(int userId) {
        return map.getOrDefault(userId, null);
    }

    /**
     * @return Iterator of ID's and deals
     */
    public Iterator<Map.Entry<Integer, Deal>> getCycleInfo() {
        return map.entrySet().iterator();
    }

    /**
     * @return Id of a cycle
     */
    public int getId() { return id; }

    /**
     * @return StatusId of a cycle
     */
    public int getStatus_id() { return statusId; }

    /**
     * @return Time of creation
     */
    public Timestamp getUpdated_at() { return updatedAt; }

    /**
     * @return Time of update
     */
    public Timestamp getCreated_at() { return createdAt; }

    /**
     * @param o Passed cycle
     * @return Whether two cycles are equal or not
     */
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Cycle)) return false;
        return id == ((Cycle) o).getId();
    }
}