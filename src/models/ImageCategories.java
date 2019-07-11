package models;

public class ImageCategories {

    public enum ImageCategory {
        PROFILE("profile",1),
        FEATURED("featured", 2);

        private String name;
        private int id;

        ImageCategory(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    public static ImageCategory getCategoryByID(int statusID) {

        ImageCategory[] array = ImageCategory.values();

        for (ImageCategory cat : array)
            if (cat.getId() == statusID)
                return cat;

        return null;
    }
}
