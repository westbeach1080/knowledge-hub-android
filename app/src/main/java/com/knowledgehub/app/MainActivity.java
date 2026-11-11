
package com.knowledgehub.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.view.View;
import android.widget.*;
import android.text.*;

public class MainActivity extends Activity {
    LinearLayout results;
    String[][] resources = {
        {"Android Developers", "Android",
         "https://developer.android.com"},
        {"GitHub", "Utveckling",
         "https://github.com"},
        {"MDN Web Docs", "Webbutveckling",
         "https://developer.mozilla.org"},
        {"Linux Documentation", "Linux",
         "https://docs.kernel.org"},
        {"OWASP", "Säkerhet",
         "https://owasp.org"}
    };

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(1);
        root.setPadding(24, 40, 24, 16);
        root.setBackgroundColor(Color.rgb(16, 20, 30));

        TextView title = new TextView(this);
        title.setText("Knowledge Hub");
        title.setTextSize(28);
        title.setTextColor(Color.WHITE);
        root.addView(title);

        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setHint("Sök resurser...");
        search.setHintTextColor(Color.LTGRAY);
        search.setTextColor(Color.WHITE);
        root.addView(search);

        ScrollView scroll = new ScrollView(this);
        results = new LinearLayout(this);
        results.setOrientation(1);
        scroll.addView(results);
        root.addView(scroll);

        setContentView(root);
        showResources("");

        search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {}

            public void onTextChanged(
                CharSequence s, int start, int before, int count) {
                showResources(s.toString());
            }

            public void afterTextChanged(Editable e) {}
        });
    }

    void showResources(String query) {
        results.removeAllViews();

        for (String[] item : resources) {
            String text = item[0] + " - " + item[1];

            if (!text.toLowerCase().contains(
                    query.toLowerCase())) continue;

            Button button = new Button(this);
            button.setText(text);
            button.setAllCaps(false);
            results.addView(button);

            button.setOnClickListener(v -> {
                Intent intent = new Intent(
                    Intent.ACTION_VIEW, Uri.parse(item[2]));
                startActivity(intent);
            });
        }
    }
}
