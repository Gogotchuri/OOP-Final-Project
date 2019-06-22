package services;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestValidator {
    //RULES!
    //Required rule when it's true, means: parameter must be included
    private static final String REQUIRED_REGEX = "required";
    private static final String TYPE_REGEX = "type:(text|number|email|url|boolean|integer|double)";
    //Number size if passed while not numeric, this is ignored!
    private static final String MAX_SIZE_REGEX = "max_num:-?[0-9]+";
    private static final String MIN_SIZE_REGEX = "min_num:-?[0-9]+";
    //String Length, number of characters
    private static final String MAX_LENGTH_REGEX = "max_len:[0-9]+";
    private static final String MIN_LENGTH_REGEX = "min_len:[0-9]+";

    //Standard regular expression from urlregex.com
    private static final String URL_REGEX = "^(https|http|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    //RFC 5322 Official Standard for email regular expression
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-" +
            "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|" +
            "[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(" +
            "?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    //Boolean regex
    private static final String BOOLEAN_REGEX = "^(true|false)";
    private static final String INTEGER_REGEX = "-?[0-9]+";
    private static final String DOUBLE_REGEX = "-?[0-9]+.?[0-9]+";

    private  Map<String, List<String>> rules;
    private HttpServletRequest request;

    //Parsed parameters
//    private Map<String, Object> parsed;
    private List<String> errors;

    public RequestValidator(HttpServletRequest request, Map<String, List<String>> rules){
        this.rules = rules;
        this.request = request;
//        this.parsed = new HashMap<>(); Maybe next time return
        errors = new ArrayList<>();
        validate();
    }

    public boolean failed(){
        return !errors.isEmpty();
    }

    public List<String> getErrors(){
        return errors;
    }

    private void validate() {
        List<String> param_rules;
        for(String key : rules.keySet()){
            String param = request.getParameter("key");
            param_rules = rules.get(key);
            for(String rule : param_rules){
                rule = rule.toLowerCase();
                if(rule.matches(REQUIRED_REGEX)) checkRequired(key, param);
                else if(param == null) continue;
                else if(rule.matches(TYPE_REGEX)) checkType(key, param, rule);
                else if(rule.matches(MAX_SIZE_REGEX)) checkMaxSize(key, param, rule);
                else if(rule.matches(MIN_SIZE_REGEX)) checkMinSize(key, param, rule);
                else if(rule.matches(MAX_LENGTH_REGEX)) checkMaxLength(key, param, rule);
                else if(rule.matches(MIN_LENGTH_REGEX)) checkMinLength(key, param, rule);
            }

        }
    }

    private void checkMinLength(String key, String param, String rule) {
        int min_length = Integer.parseInt(rule.substring(rule.indexOf(':')+1));

        if(param.length() < min_length)
            errors.add("Parameter: " + key + "should be longer than " + (min_length - 1) + "characters! given: (" + param + ")");

    }

    private void checkMaxLength(String key, String param, String rule) {
        int max_length = Integer.parseInt(rule.substring(rule.indexOf(':')+1));

        if(param.length() > max_length)
            errors.add("Parameter: " + key + "should be shorter than " + (max_length + 1) + "characters! given: (" + param + ")");

    }

    private void checkMinSize(String key, String param, String rule) {
        if(!param.matches(DOUBLE_REGEX)) return;
        double min_size = Double.parseDouble(rule.substring(rule.indexOf(':')+1));
        double num = Double.parseDouble(param);
        if(num < min_size)
            errors.add("Parameter: "+ key + "Should be bigger number than "+(min_size-1)+". given: ("+param+")");

    }

    private void checkMaxSize(String key, String param, String rule) {
        if(!param.matches(DOUBLE_REGEX)) return;
        double max_size = Double.parseDouble(rule.substring(rule.indexOf(':')+1));
        double num = Double.parseDouble(param);
        if(num > max_size)
            errors.add("Parameter: "+ key + "Should be smaller number than "+(max_size+1)+". given: ("+param+")");
    }

    //text|number|email|url|boolean|integer|double
    private void checkType(String key, String param, String rule) {
        String type = rule.substring(rule.indexOf(":")+1).toLowerCase();
        switch (type){
            case "number":
            case "double":
                checkDouble(key, param);
                break;
            case "integer":
                checkInteger(key, param);
                break;
            case "boolean":
                checkBoolean(key, param);
                break;
            case "url":
                checkUrl(key, param);
                break;
            case "email":
                checkEmail(key, param);
                break;
        }
    }

    private void checkRequired(String key, String param) {
        if(param == null)
            errors.add("Parameted: "+key+" must be included, but none given!");
    }

    private void checkUrl(String key, String url){
        if(!url.toLowerCase().matches(URL_REGEX))
            errors.add("Parameter: "+key+" is supposed to be an URL, but isn't! given: ("+url+")");
    }

    private void checkEmail(String key, String email){
        if(!email.toLowerCase().matches(EMAIL_REGEX))
            errors.add("Parameter: "+key+" is supposed to be an email, but isn't! given: ("+email+")");;
    }

    private void checkBoolean(String key, String bool){
        if(!bool.toLowerCase().matches(BOOLEAN_REGEX))
            errors.add("Parameter: "+key+" is supposed to be a Boolean, but isn't! given: ("+bool+")");
    }

    private void checkInteger(String key, String integer){
        if(!integer.matches(INTEGER_REGEX))
            errors.add("Parameter: "+key+" is supposed to be an integer, but isn't! given: ("+integer+")");
    }

    private void checkDouble(String key, String d){
        if(!d.matches(DOUBLE_REGEX))
            errors.add("Parameter: "+key+" is supposed to be a number, but isn't! given: ("+d+")");
    }

}
