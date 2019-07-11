package models.categoryModels;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

//Class encapsulating a single category
public class ItemCategory implements Comparable<ItemCategory> {

    private int id;
    private ItemSerie serie;
    private ItemType type;
    private ItemBrand brand;

    /**
     * Constructor of a category
     * @param id Id
     * @param serie Serie
     * @param type Type
     * @param brand Brand
     */
    public ItemCategory(int id, ItemSerie serie, ItemType type, ItemBrand brand) {
        this.id = id;
        this.serie = serie;
        this.type = type;
        this.brand = brand;
    }

    /**
     * Alternate constructor
     * @param serie Serie
     * @param type Type
     * @param brand Brand
     */
    public ItemCategory(ItemSerie serie, ItemType type, ItemBrand brand) {
        this(-1, serie, type, brand);
    }

    public int getId() { return id; }

    /**
     * @return Serie of a category
     */
    public ItemSerie getSerie() {
        return serie;
    }

    /**
     * @return Type of a category
     */
    public ItemType getType() {
        return type;
    }

    /**
     * @return Brand of a category
     */
    public ItemBrand getBrand() {
        return brand;
    }

    @Override
    public int compareTo(ItemCategory o) {
        int cur = getSerie().getName().compareTo(o.getSerie().getName());
        if(cur != 0) return cur;
        cur = getType().getName().compareTo(o.getType().getName());
        if(cur != 0) return cur;
        return getBrand().getName().compareTo(o.getBrand().getName());
    }

    @Override
    public String toString() {
        return "Serie : " + serie.toString() + ", Type : " + type.toString() + ", Brand : " + brand.toString() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof ItemCategory)) return false;
        return serie.equals(((ItemCategory) o).getSerie()) && brand.equals(((ItemCategory) o).getBrand()) && type.equals(((ItemCategory) o).getType());
    }

    /**
     * @param set ResultSet taken from database
     * @return New ItemCategory parsed from set
     */
    public static ItemCategory parseCategory(ResultSet set) throws SQLException {
        return new ItemCategory(
                set.getInt(1),
                new ItemSerie(set.getInt(1), set.getString(2)),
                new ItemType(set.getInt(3), set.getString(6)),
                new ItemBrand(set.getInt(4), set.getString(8))
        );
    }
}
