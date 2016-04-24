package com.hackerkernel.ondew.parser;

import com.hackerkernel.ondew.extras.Keys;
import com.hackerkernel.ondew.model.SimplePojo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class container helper methods to parse json
 */
public class JsonParser {
    public static SimplePojo SimpleParser(String response) throws JSONException {
        JSONObject jo = new JSONObject(response);
        SimplePojo current = new SimplePojo();
        current.setReturned(jo.getBoolean(Keys.COM_RETURN));
        current.setMesssage(jo.getString(Keys.COM_MESSAGE));
        return current;
    }
}
