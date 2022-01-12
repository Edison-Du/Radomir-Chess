package network;

import java.util.ArrayList;

/**
 * Requests have types and parameters, when turned into text,
 * they look like this:
 * {type}{param1}{param2}{param3}...
 * 
 * As such, the type and param cannot contain curly brackets.
 */

public class Request {

    private static char ELEMENT_HEAD = '{';
    private static char ELEMENT_TAIL = '}';

    private String type;
    private ArrayList<String> parameters;

    public Request(String type) throws InvalidRequestException {
        if (validateString(type)) {
            this.type = type;
            parameters = new ArrayList<>();
        } else {
            throw new InvalidRequestException("Invalid characters used in request type. You must not use '" + ELEMENT_HEAD + "' or '" + ELEMENT_TAIL + "'.");
        }
    }

    public boolean addParam(String param) {
        // Make sure param does not
        if (validateString(param)) {
            parameters.add(param);
            return true;
        }
        return false;
    }

    public boolean validateString(String str) {
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            
            if ( (currentChar == ELEMENT_HEAD) || (currentChar == ELEMENT_TAIL) ) {
                return false;
            }
        }
        return true;
    }

    public String getText() {
        StringBuilder text = new StringBuilder();

        text.append(ELEMENT_HEAD);
        text.append(this.type);
        text.append(ELEMENT_TAIL);

        for (String param : parameters) {
            text.append(ELEMENT_HEAD);
            text.append(param);
            text.append(ELEMENT_TAIL);
        }

        return text.toString();
    }
}
