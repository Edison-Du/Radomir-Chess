package server;

import java.util.ArrayList;

import config.MessageTypes;

/**
 * [Message.java]
 * A class representing a message that is sent between server
 * and client, with a single type and any number of parameters.
 * Can be converted and parsed from plain text.
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */

public class Message {

    // Separators used when converting to/from plain text
    private static char SEPARATOR_START = '{';
    private static char SEPARATOR_END = '}';

    private String type;
    private ArrayList<String> parameters;

    /**
     * Message
     * Creates a message of specified type
     * @param type the type of message
     */
    public Message(String type) {
        if (validateString(type)) {
            this.type = type;
            parameters = new ArrayList<>();
        } else {
            this.type = MessageTypes.UNDEFINED;
        }
    }

    /**
     * getType
     * Getter for the message type
     * @return the message type
     */
    public String getType() {
        return this.type;
    }

    /**
     * addParam
     * Adds a parameter to the message
     * @param param the parameter to add
     * @return whether or not the parameter is valid
     */
    public boolean addParam(String param) {
        if (validateString(param)) {
            parameters.add(param);
            return true;
        }
        return false;
    }

    /**
     * getParam
     * Gets a parameter at a certain index
     * @param number the parameter index
     * @return whether or not the specified index exists
     */
    public String getParam(int number) {
        if (number < 0 || number >= parameters.size()) {
            return null;
        }
        return parameters.get(number);
    }

    /**
     * getNumParams
     * Gets the number of parameters
     * @return the number of parameters
     */
    public int getNumParams() {
        return parameters.size();
    }

    /**
     * validateString
     * Checks if a string has any separators used when converting
     * a message to/from plain text, this is to prevent any
     * injection errors/attacks.
     * @param str the string to validate
     * @return whether or not the string is valid
     */
    public boolean validateString(String str) {

        // Loop through the string and see if there are any separator characters
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            
            if ( (currentChar == SEPARATOR_START) || (currentChar == SEPARATOR_END) ) {
                return false;
            }
        }
        return str == null ? false : true;
    }

    /**
     * getText
     * Converts the message into plain text which can be sent
     * between the server and client
     * @return the plain text representation of the message
     */
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

    /**
     * parse
     * Parses a message object from plain text
     * @param messageText the plain text to parse
     * @return the message object, or null if it could not parse correctly
     */
    public static Message parse(String messageText){

        // -1 denotes that the current index is not part of a type or parameter
        int lastIndex = -1;
        ArrayList<String> values = new ArrayList<>();
        Message message;

        /**
         * Make sure each item in the message is contained within separators,
         * otherwise the message is invalid
         */
        for (int i = 0; i < messageText.length(); i++) {

            char currentChar = messageText.charAt(i);
            
            // Current chracter is not inside a separator
            if (lastIndex == -1 && currentChar != SEPARATOR_START) {
                return null;

            // Starting seperator appears twice before an ending separator has
            } else if (lastIndex != -1 && currentChar == SEPARATOR_START) {
                return null;

            // Mark the index where the start of the current value (type/param) is located
            } else if (currentChar == SEPARATOR_START) {
                lastIndex = i;

            // When reaching the end, add the current value (type/param) to the list of values
            } else if (currentChar == SEPARATOR_END) {
                values.add(messageText.substring(lastIndex+1, i));
                lastIndex = -1;
            }
        }

        if (values.isEmpty() || messageText == null) {
            return null;
        }

        // Construct parsed message to return
        message = new Message(values.get(0));
        for (int i = 1; i < values.size(); i++) {
            message.addParam(values.get(i));
        }
        return message;
    }
}