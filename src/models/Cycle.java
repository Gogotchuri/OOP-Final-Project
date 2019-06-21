package models;

import java.sql.Timestamp;

public class Cycle {

    private int id;
    private int status_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Cycle(int id, int status_id, Timestamp created_at, Timestamp updated_at){
        this.id = id;
        this.status_id = status_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId(){return id;}

    public int getStatus_id(){return status_id;}

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }
}
