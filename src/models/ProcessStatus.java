package models;

public class ProcessStatus {

    public enum Status {
        ONGOING("ongoing", 1),
        COMPLETED("completed", 2),
        WAITING("waiting", 3); //when i.e cycle isn't accepted yet
        private String name;
        private int id;
        Status(String name, int id){
            this.name = name;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static Status getStatusByID(int statusID) {

        Status[] statuses = Status.values();

        for (Status status : statuses)
            if (status.getId() == statusID)
                return status;

        return null;
    }
}

