package servlets;

public abstract class RoutingConstants {
    //Public routes
    public static final String HOME = "/home";
    public static final String DEALS = "/deals";
    public static final String SINGLE_DEAL = "/deals/show";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String PUBLIC_PROFILE = "/users/show";

    //User routes
    public static final String USER_CHATS= "/user/chats";
    public static final String USER_SINGLE_CHAT = "/user/chats/show"; //FOR API
    public static final String USER_DEALS = "/user/deals";
    public static final String USER_SINGLE_DEAL = "/user/deals/show";
    public static final String USER_DEAL_CREATE = "/user/deals/create";
    public static final String USER_DEAL_CONFIG = "/user/deals/config";
    public static final String USER_CYCLES = "/user/deals/cycles";
    public static final String USER_SINGLE_CYCLE = "/user/deals/cycles/show";
    public static final String USER_EDIT = "/user/edit";
    public static final String LOGOUT = "/logout";

    //Public API
    public static final String ITEM_CATEGORIES = "/items/categories";

    //User API
    /**
     * Supports
     * GET - returns all user items
     * DELETE - with param id deletes item
     * PUT - with param id, updates given item resource
     */
    public static final String USER_ITEMS= "/user/items";
    /**
     * supports
     * GET - with param id, returns json for single item object
     * */
    public static final String USER_SINGLE_ITEM = "/user/items/show"; //FOR API


}
