package models.categoryModels;
import com.google.gson.annotations.JsonAdapter;
import services.encoders.ItemCategoryJsonAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Class encapsulating a single category
 * Containing serie, type and a brand
 */

@JsonAdapter(ItemCategoryJsonAdapter.class)
public class ItemCategory implements Comparable<ItemCategory> {

    private int id;
    private ItemSeries series;
    private ItemType type;
    private ItemBrand brand;

    /**
     * Constructor of a category
     * @param id Id
     * @param series Serie
     * @param type Type
     * @param brand Brand
     */
    public ItemCategory(int id, ItemSeries series, ItemType type, ItemBrand brand) {
        this.id = id;
        this.series = series;
        this.type = type;
        this.brand = brand;
    }

    /**
     * Alternate constructor
     * @param series Serie
     * @param type Type
     * @param brand Brand
     */
    public ItemCategory(ItemSeries series, ItemType type, ItemBrand brand) {
        this(0, series, type, brand);
    }

    /**
     * Alternate constructor, with strings, for simplicity, used in tests
     * @param id
     * @param series
     * @param type
     * @param brand
     */
    public ItemCategory(int id, String series, String type, String brand) {
        this(id, new ItemSeries(0, series), new ItemType(0, type), new ItemBrand(0, brand));
    }

    /**
     * Creates item fresh instance of ItemCategory from Strings;
     * @param series name of the item series (model)
     * @param type type of the item, i.e TV, Laptop
     * @param brand brand of the given item i.e Samsung
     */
    public ItemCategory(String series, String type, String brand) {
        this(0, new ItemSeries(0, series), new ItemType(0, type), new ItemBrand(0, brand));
    }

    /**
     * @return ID of a category
     */
    public int getId() { return id; }

    /**
     * @param id ID of a category
     */
    public void setId(int id){
        this.id = id;
    }
    /**
     * @return Serie of a category
     */
    public ItemSeries getSeries() {
        return series;
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
    /**
     * Compares an itemCategory to another one by comparing serie, type and a brand, in that order
     */
    public int compareTo(ItemCategory o) {
        int cur = getSeries().getName().compareTo(o.getSeries().getName());
        if(cur != 0) return cur;
        cur = getType().getName().compareTo(o.getType().getName());
        if(cur != 0) return cur;
        return getBrand().getName().compareTo(o.getBrand().getName());
    }

    @Override
    /**
     * String representation of a category
     */
    public String toString() {
        return "Serie : " + series.toString() + ", Type : " + type.toString() + ", Brand : " + brand.toString() + "\n";
    }

    @Override
    /**
     * Equality of two itemCategories
     */
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof ItemCategory)) return false;
        return series.equals(((ItemCategory) o).getSeries()) && brand.equals(((ItemCategory) o).getBrand()) && type.equals(((ItemCategory) o).getType());
    }

    /**
     * @param set ResultSet taken from database
     * @return New ItemCategory parsed from set
     */
    public static ItemCategory parseCategory(ResultSet set) throws SQLException {
        return new ItemCategory(
                set.getInt(1),
                new ItemSeries(set.getInt(1), set.getString(2)),
                new ItemType(set.getInt(3), set.getString(6)),
                new ItemBrand(set.getInt(4), set.getString(8))
        );
    }
}
