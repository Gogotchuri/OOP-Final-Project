package models.categoryModels;

import models.Item;

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
}
