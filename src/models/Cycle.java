package models;
import java.sql.Timestamp;

public class Cycle {

    private int id, statusId;
    private Timestamp createdAt, updatedAt;

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
}