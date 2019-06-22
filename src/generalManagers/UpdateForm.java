package generalManagers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This object will be passed to updateManager, which will make updates in a database
 */
public class UpdateForm {

    private String tableName;
    private int id;
    private List<Pair> list;

    /**
     * @param tableName Name of a table
     * @param id Id of an object
     */
    public UpdateForm(String tableName, int id) {
        this.tableName = tableName;
        this.id = id;
        list = new ArrayList<>();
    }

    /**
     * @return Id of an object
     */
    public int getId() {
        return id;
    }

    /**
     * @return Name of a table
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Adds a new update to our list
     * @param column Name of a column
     * @param newValue New value
     */
    public void addUpdate(String column, Object newValue) {
        list.add(new Pair(column,newValue));
    }

    /**
     * @return Iterator of updates
     */
    public Iterator<Pair> getUpdates() {
        return list.iterator();
    }


    /**
     * Inner class Pair, encapsulating a single column To update -> value
     */
    public class Pair {

        private String columnName;
        private Object newValue;

        /**
         * @param columnName Name of a column
         * @param newValue New value
         */
        public Pair(String columnName, Object newValue) {
            this.columnName = columnName;
            this.newValue = newValue;
        }

        /**
         * @return Name of a column
         */
        public String getColumnName() {
            return columnName;
        }

        /**
         * @return New value
         */
        public Object getNewValue() {
            return newValue;
        }
    }
}
