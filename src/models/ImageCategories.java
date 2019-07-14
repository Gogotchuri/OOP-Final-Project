package models;

public class ImageCategories {

    /**
     * Enum representing category of an image, profile image, or a featured image
     */
    public enum ImageCategory {
        PROFILE("profile",1),
        FEATURED("featured", 2);

        private String name;
        private int id;

        /**
         * @param name Name of category
         * @param id ID of category
         */
        ImageCategory(String name, int id) {
            this.name = name;
            this.id = id;
        }

        /**
         * @return Name of an imageCategory
         */
        public String getName() {
            return name;
        }

        /**
         * @return ID of an imageCategory
         */
        public int getId() {
            return id;
        }
    }

    /**
     * @param statusID ID of a status
     * @return ImageCategory with passed id
     */
    public static ImageCategory getCategoryByID(int statusID) {

        ImageCategory[] array = ImageCategory.values();

        for (ImageCategory cat : array)
            if (cat.getId() == statusID)
                return cat;

        return null;
    }
}
