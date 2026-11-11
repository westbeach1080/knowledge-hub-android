package com.knowledgehub.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
  private final int bg=Color.rgb(12,18,30), panel=Color.rgb(24,34,51), white=Color.rgb(236,242,250), muted=Color.rgb(157,173,195), accent=Color.rgb(84,194,232);
  private LinearLayout results; private EditText search; private String category="Alla"; private boolean favoritesOnly=false;
  private SharedPreferences prefs;
  private final String[][] items={
    {"Git", "Utveckling", "Versionshantering och samarbete", "https://git-scm.com/doc"},
    {"GitHub Docs", "Utveckling", "Repositories, pull requests och Actions", "https://docs.github.com/"},
    {"Android Developers", "Android", "Officiell Android-dokumentation", "https://developer.android.com/"},
    {"Kotlin", "Android", "Programmeringsspråket Kotlin", "https://kotlinlang.org/docs/home.html"},
    {"OWASP Top 10", "Säkerhet", "Webbsäkerhet och riskförståelse", "https://owasp.org/www-project-top-ten/"},
    {"OWASP Cheat Sheets", "Säkerhet", "Praktiska riktlinjer för säkrare kod", "https://cheatsheetseries.owasp.org/"},
    {"MDN Web Docs", "Webb", "HTML, CSS och JavaScript", "https://developer.mozilla.org/"},
    {"web.dev", "Webb", "Webbprestanda och tillgänglighet", "https://web.dev/"},
    {"Linux Documentation", "Linux", "Dokumentation för Linux-kärnan", "https://docs.kernel.org/"},
    {"Ubuntu Documentation", "Linux", "Guider för Ubuntu", "https://documentation.ubuntu.com/"},
    {"Python Docs", "Utveckling", "Python-referens och handledningar", "https://docs.python.org/3/"},
    {"The Book of Secret Knowledge", "Samling", "Originalprojektets samlade resurser", "https://github.com/trimstray/the-book-of-secret-knowledge"}
  };
  @Override public void onCreate(Bundle b){super.onCreate(b);prefs=getSharedPreferences("bookmarks",MODE_PRIVATE);build();}
  private GradientDrawable shape(int color,int radius){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(radius);return d;}
  private TextView text(String s,int sp,int color,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(sp);t.setTextColor(color);if(bold)t.setTypeface(null,Typeface.BOLD);return t;}
  private void build(){
    ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);scroll.setBackgroundColor(bg);
    LinearLayout root=new LinearLayout(this);root.setOrientation(1);root.setPadding(22,35,22,30);scroll.addView(root);
    TextView eyebrow=text("DEVELOPER LIBRARY",12,accent,true);root.addView(eyebrow);
    TextView title=text("Knowledge Hub",30,white,true);root.addView(title);
    TextView sub=text("Lär dig utveckling, Android och säkerhet. Spara dina favoriter.",14,muted,false);sub.setPadding(0,8,0,24);root.addView(sub);
    search=new EditText(this);search.setSingleLine(true);search.setTextColor(white);search.setHintTextColor(muted);search.setTextSize(15);search.setHint("Sök resurser...");search.setPadding(20,12,20,12);search.setBackground(shape(panel,18));root.addView(search);
    HorizontalScrollView hs=new HorizontalScrollView(this);hs.setHorizontalScrollBarEnabled(false);LinearLayout chips=new LinearLayout(this);chips.setPadding(0,18,0,18);hs.addView(chips);root.addView(hs);
    String[] cats={"Alla","Android","Utveckling","Säkerhet","Webb","Linux","Samling","★ Sparade"};
    for(String c:cats){TextView chip=text(c,13,white,false);chip.setPadding(19,12,19,12);LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-2,-2);cp.setMargins(0,0,9,0);chips.addView(chip,cp);chip.setBackground(shape(panel,35));chip.setOnClickListener(v->{favoritesOnly=c.equals("★ Sparade");category=favoritesOnly?"Alla":c;for(int i=0;i<chips.getChildCount();i++){View x=chips.getChildAt(i);x.setBackground(shape(x==chip?Color.rgb(28,105,132):panel,35));}refresh();});}
    results=new LinearLayout(this);results.setOrientation(1);root.addView(results);
    TextView footer=text("Utvalda länkar • Öppnas i webbläsaren\nInspirerad av trimstray/the-book-of-secret-knowledge (MIT)",12,muted,false);footer.setPadding(0,25,0,10);root.addView(footer);
    search.addTextChangedListener(new TextWatcher(){public void beforeTextChanged(CharSequence s,int a,int c,int f){}public void onTextChanged(CharSequence s,int a,int b,int c){refresh();}public void afterTextChanged(Editable e){}});
    setContentView(scroll);refresh();
  }
  private void refresh(){results.removeAllViews();String q=search.getText().toString().toLowerCase(Locale.ROOT).trim();int count=0;
    for(String[] item:items){String id=item[3];boolean saved=prefs.getBoolean(id,false);if(!category.equals("Alla")&&!category.equals(item[1]))continue;if(favoritesOnly&&!saved)continue;if(!(item[0]+" "+item[1]+" "+item[2]).toLowerCase(Locale.ROOT).contains(q))continue;count++;
      LinearLayout card=new LinearLayout(this);card.setOrientation(1);card.setPadding(19,18,19,17);card.setBackground(shape(panel,20));LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.setMargins(0,0,0,13);results.addView(card,lp);
      TextView tag=text(item[1].toUpperCase(Locale.ROOT),11,accent,true);card.addView(tag);
      TextView name=text(item[0],19,white,true);name.setPadding(0,7,0,6);card.addView(name);
      TextView desc=text(item[2],14,muted,false);card.addView(desc);
      LinearLayout actions=new LinearLayout(this);actions.setGravity(Gravity.CENTER_VERTICAL);actions.setPadding(0,14,0,0);card.addView(actions);
      TextView open=text("Öppna resurs  ↗",14,accent,true);actions.addView(open,new LinearLayout.LayoutParams(0,-2,1));open.setOnClickListener(v->{try{startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(id)));}catch(Exception e){Toast.makeText(this,"Ingen webbläsare tillgänglig",Toast.LENGTH_SHORT).show();}});
      TextView star=text(saved?"★ Sparad":"☆ Spara",14,saved?accent:muted,true);actions.addView(star);star.setOnClickListener(v->{prefs.edit().putBoolean(id,!prefs.getBoolean(id,false)).apply();refresh();});
    }
    if(count==0){TextView empty=text("Inga resurser matchar sökningen.",15,muted,false);empty.setPadding(0,25,0,0);results.addView(empty);}
  }
}
