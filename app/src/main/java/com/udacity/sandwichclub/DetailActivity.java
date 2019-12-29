package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.LinearSystem;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView placeOfOrigin;
    TextView description;
    TextView ingredients;
    TextView alsoKnownAs;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        alsoKnownAs = findViewById(R.id.also_known_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private String CheckIfEmpty(String checkString) {
        if (!checkString.isEmpty()) {
            return checkString;
        } else {
            return "Not Known";
        }
    }

    private void populateUI(Sandwich sandwich) {

        placeOfOrigin.setText(CheckIfEmpty(sandwich.getPlaceOfOrigin()));
        description.setText(CheckIfEmpty(sandwich.getDescription()));

        ArrayList<String> alsoKnownAsArr = (ArrayList<String>) sandwich.getAlsoKnownAs();
        if (alsoKnownAsArr.size() != 0) {
            for (int i = 0; i < alsoKnownAsArr.size(); i++) {
                alsoKnownAs.append(sandwich.getAlsoKnownAs().get(i) + "\n");
            }
        } else {
            alsoKnownAs.append("Not Known");
        }

        ArrayList<String> ingredientsArr = (ArrayList<String>) sandwich.getIngredients();
        if (ingredientsArr.size() != 0) {
            for (int i = 0; i < ingredientsArr.size(); i++) {
                ingredients.append(sandwich.getIngredients().get(i) + "\n");
            }
        } else {
            ingredients.append("Not Known");
        }
    }
}
