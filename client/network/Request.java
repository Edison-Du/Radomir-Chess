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

    private static char SEPARATOR_START = '{';
    private static char SEPARATOR_END = '}';

    private String type;
    private ArrayList<String> parameters;

    public Request(String type) throws InvalidRequestException {
        if (validateString(type)) {
            this.type = type;
            parameters = new ArrayList<>();
        } else {
            throw new InvalidRequestException("Invalid characters used in request type. You must not use '" + SEPARATOR_START + "' or '" + SEPARATOR_END + "'.");
        }
    }

    public String getType() {
        return this.type;
    }

    public boolean addParam(String param) {
        // Make sure param does not have curly brackets
        if (validateString(param)) {
            parameters.add(param);
            return true;
        }
        return false;
    }

    public String getParam(int number) {
        if (number < 0 || number >= parameters.size()) {
            return null;
        }
        return parameters.get(number);
    }

    public int getNumParams() {
        return parameters.size();
    }

    public boolean validateString(String str) {
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            
            if ( (currentChar == SEPARATOR_START) || (currentChar == SEPARATOR_END) ) {
                return false;
            }
        }
        return true;
    }

    public String getText() {
        StringBuilder text = new StringBuilder();

        text.append(SEPARATOR_START);
        text.append(this.type);
        text.append(SEPARATOR_END);

        for (String param : parameters) {
            text.append(SEPARATOR_START);
            text.append(param);
            text.append(SEPARATOR_END);
        }

        return text.toString();
    }

    // Null if invalid request
    public static Request parse(String requestText){
        // Stores index in the string for the last curly bracket
        int lastIndex = -1;
        ArrayList<String> params = new ArrayList<>();

        for (int i = 0; i < requestText.length(); i++) {

            char currentChar = requestText.charAt(i);

            // There are characters not inside the curly brackets, invalid.
            if (lastIndex == -1 && currentChar != SEPARATOR_START) {
                return null;
            
            // Double starting separators
            } else if (lastIndex != -1 && currentChar == SEPARATOR_START) {
                return null;

            } else if (currentChar == SEPARATOR_START) {
                lastIndex = i;

            } else if (currentChar == SEPARATOR_END) {
                params.add(requestText.substring(lastIndex+1, i));
                lastIndex = -1;

            }
        }

        if (params.size() == 0) {
            return null;

        }
        try {
            Request request = new Request(params.get(0));

            for (int i = 1; i < params.size(); i++) {
                request.addParam(params.get(i));
            }

            return request;

        } catch (InvalidRequestException e) {
            System.out.println("Invalid request");
            e.printStackTrace();
            return null;
        }
    }
}
