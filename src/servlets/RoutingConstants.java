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
    public static final String USER_SINGLE_CHAT = "/user/chats/show";
    public static final String USER_DEALS = "/user/deals";
    public static final String USER_SINGLE_DEAL = "/user/deals/show";
    public static final String USER_DEAL_CREATE = "/user/deals/create";
    public static final String USER_DEAL_CONFIG = "/user/deals/config";
    public static final String USER_CYCLES = "/user/deals/cycles";
    public static final String USER_SINGLE_CYCLE = "/user/deals/cycles/show";
    public static final String USER_EDIT = "/user/edit"; // TODO: Ilia url-ebi emtxveoda da es iyos ???
    public static final String LOGOUT = "/logout";

}
