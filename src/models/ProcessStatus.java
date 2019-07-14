package models;

public class ProcessStatus {

    /**
     * Enum representing a status of an object, ongoing, completed or waiting
     */
    public enum Status {
        ONGOING("ongoing", 1),
        COMPLETED("completed", 2),
        WAITING("waiting", 3); //when i.e cycle isn't accepted yet

        private String name;
        private int id;

        /**
         * @param name Name of a status
         * @param id ID of a status
         */
        Status(String name, int id){
            this.name = name;
            this.id = id;
        }

        /**
         * @return Id of a status
         */
        public int getId() {
            return id;
        }

        /**
         * @return Name of a status
         */
        public String getName() {
            return name;
        }
    }

    /**
     * @param statusID ID of a status
     * @return ProcessStatus by passed statusID
     */
    public static Status getStatusByID(int statusID) {

        Status[] statuses = Status.values();

        for (Status status : statuses)
            if (status.getId() == statusID)
                return status;

        return null;
    }
}

