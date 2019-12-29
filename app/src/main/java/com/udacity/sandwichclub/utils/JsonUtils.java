package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JSONObject sandwichJson = null;
        ArrayList<String> alsoKnownAsArr = new ArrayList<>();
        ArrayList<String> ingredientsArr = new ArrayList<>();
        try {
            sandwichJson = new JSONObject(json);
            JSONObject sandwichJsonByName = sandwichJson.getJSONObject("name");
            String mainName = sandwichJsonByName.getString("mainName");

            JSONArray sandwichJsonByNameKnown = sandwichJsonByName.getJSONArray("alsoKnownAs");
            for (int i = 0; i < sandwichJsonByNameKnown.length(); i++) {
                alsoKnownAsArr.add(sandwichJsonByNameKnown.getString(i));
            }
            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            String description = sandwichJson.getString("description");
            String image = sandwichJson.getString("image");
            JSONArray ingredients = sandwichJson.getJSONArray("ingredients");
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsArr.add(ingredients.getString(i));
            }
            Sandwich sandwichObj = new Sandwich(mainName, alsoKnownAsArr, placeOfOrigin, description, image, ingredientsArr);
            return sandwichObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
