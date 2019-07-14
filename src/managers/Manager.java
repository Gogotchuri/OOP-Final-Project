package managers;

import java.util.List;

/**
 * Generic manager class, used for manipulating database
 * T optimally should be model, representing an entry in the database
 * */
public interface Manager<T>{

    /**
     * Enumeration for every column of the table this manager is responsible for
     * */
    enum TableColumn{
        //Every table have Column ID
        ID("id", true);
        private String name;
        private boolean isUnique;
        /**
         * Constructs new enumeration for the table, with given name
         * and is unique flag
         * */
        TableColumn(String name, boolean isUnique){
            this.name = name;
            this.isUnique = isUnique;
        }

        public String getName() {
            return name;
        }

        public boolean isUnique() {
            return isUnique;
        }
    }

    /**
     * Stores given model in the database and returns stored instance id
     * Also sets id in the passed object model
     * @param model model to store in database
     * @return returns stored model id, or returns -1;
     */
    int store(T model);


    /**
     * Updates an already existing model in the database, if instance is found and successfully updated
     * returns true, otherwise false
     * @param model Model to update in the database
     * @return false if no instance with given model id has been found or error has occurred,
     *             otherwise returns true
     */
    boolean update(T model);

    /**
     * Finds and retrieves entry in the database with given model ID
     * if nothing found / fetching failed returns null
     * @param modelID id of the instance to retrieve from the database
     * @return returns @T object of the given id entry, if nothing found returns null
     */
    T get(int modelID);

    /**
     * Delete a model in the database
     * If model is deleted successfully returns true, otherwise false;
     * @param modelID to delete
     * @return If model is deleted successfully returns true, otherwise false;
     */
    boolean delete(int modelID);

    /**
     * Return all entries of the database
     * @return returns list of all entries for the given table in the database, if error occurs returns null
     */
    List<T> all();

    /**
     * Filters entries with given condition and returns all entries responding to condition
     * @param column Column to filter by
     * @param operator operator to compare column value with the given value
     * @param value value to compare to
     * @return Returns list of entries with given condition
     */
    List<T> where(TableColumn column, String operator, String value);


    /**
     * Returns true if any entry is present, where given column has a given value
     * @param column Column to check value from
     * @param value value to compare to
     * @return true if any entry appears
     */
    boolean contains(TableColumn column, String value);
}
