package models.categoryModels;

//Class encapsulating a single category
public class ItemCategory {

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
        this(serie,type,brand);
        this.id = id;
    }

    /**
     * Alternate constructor
     * @param serie Serie
     * @param type Type
     * @param brand Brand
     */
    public ItemCategory(ItemSerie serie, ItemType type, ItemBrand brand) {
        this.serie = serie;
        this.type = type;
        this.brand = brand;
    }

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
}
