package com.excite.applab.spacesmash;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    RelativeLayout mLayout;
    Button restartButton;
    TextView gameOverText;
    TextView levelText;
    ArrayList<ImageButton> ships;
    ArrayList<ImageButton> aliens;

    int level = 1;
    int shipsTouched = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mLayout = new RelativeLayout(mContext);
        ships = new ArrayList<>();
        aliens = new ArrayList<>();

        //
        mLayout.setBackgroundColor(Color.parseColor("#000000"));

        gameOverText = new TextView(mContext);
        gameOverText.setText("Game Over");
        gameOverText.setTextColor(Color.parseColor("#FF0000"));
        gameOverText.setVisibility(View.INVISIBLE);
        gameOverText.setTextSize(20
        );
        final RelativeLayout.LayoutParams gameOverTextParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        gameOverTextParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gameOverTextParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mLayout.addView(gameOverText, gameOverTextParams);

        restartButton = new Button(mContext);
        restartButton.setText("Start");
        restartButton.setTextColor(Color.parseColor("#FFFFFF"));
        restartButton.setBackgroundColor(Color.parseColor("#00000000"));
        restartButton.setVisibility(View.INVISIBLE);
        RelativeLayout.LayoutParams restartButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        restartButtonParams.setMargins(0,0,dp2px(8),dp2px(8));
        restartButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        restartButtonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartButton.setVisibility(View.INVISIBLE);
                gameOverText.setVisibility(View.INVISIBLE);
                level = 1;
                shipsTouched = 0;
                levelText.setText("Level: " + level);
                newLevel(level);
            }
        });
        mLayout.addView(restartButton, restartButtonParams);

        levelText = new TextView(mContext);
        levelText.setText("Level: 1");
        levelText.setTextColor(Color.parseColor("#ffffff"));
        RelativeLayout.LayoutParams levelTextParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        levelTextParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        levelTextParams.setMargins(dp2px(8), 0, 0, dp2px(8));
        mLayout.addView(levelText, levelTextParams);

        //


        //mLayout = (RelativeLayout)findViewById(R.id.activity_main);

        newLevel(level);
        setContentView(mLayout);
    }

    void newLevel(int l){
        for (int i = 1; i <= l; i++){
            makeShip(mContext);
            makeAlien(mContext);
        }
    }

    void makeShip(Context context){
        final ImageButton newShip = new ImageButton(context);
        Random random = new Random();

        int edgeInPx = dp2px(96);
        int maxMarginLeft = getWindowManager().getDefaultDisplay().getWidth() - edgeInPx;
        int maxMarginTop = getWindowManager().getDefaultDisplay().getHeight() - 2*edgeInPx;
        int marginLeft = random.nextInt(maxMarginLeft);
        int marginTop = random.nextInt(maxMarginTop);

        newShip.setImageResource(R.drawable.space_ship);
        newShip.setScaleType(ImageView.ScaleType.FIT_XY);
        newShip.setBackgroundColor(Color.parseColor("#00000000"));
        newShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newShip.setVisibility(View.INVISIBLE);
                shipsTouched++;
                if (shipsTouched == level){
                    level++;
                    shipsTouched = 0;
                    levelText.setText("Level: " + level);
                    clearSpace();
                    newLevel(level);
                }
            }
        });

        RelativeLayout.LayoutParams shipLayoutParams = new RelativeLayout.LayoutParams(edgeInPx,edgeInPx);
        shipLayoutParams.setMargins(marginLeft,marginTop,0,0);
        newShip.setLayoutParams(shipLayoutParams);

        mLayout.addView(newShip);
        ships.add(newShip);
    }

    void makeAlien(Context context){
        ImageButton newAlien = new ImageButton(context);
        Random random = new Random();

        int edgeInPx = dp2px(96);
        int maxMarginLeft = getWindowManager().getDefaultDisplay().getWidth() - edgeInPx;
        int maxMarginTop = getWindowManager().getDefaultDisplay().getHeight() - edgeInPx;
        int marginLeft = random.nextInt(maxMarginLeft);
        int marginTop = random.nextInt(maxMarginTop);

        newAlien.setImageResource(R.drawable.green_alien);
        newAlien.setScaleType(ImageView.ScaleType.FIT_XY);
        newAlien.setBackgroundColor(Color.parseColor("#00000000"));
        newAlien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSpace();
                gameOverText.setVisibility(View.VISIBLE);
                restartButton.setVisibility(View.VISIBLE);
            }
        });

        RelativeLayout.LayoutParams alienLayoutParams = new RelativeLayout.LayoutParams(edgeInPx,edgeInPx);
        alienLayoutParams.setMargins(marginLeft,marginTop,0,0);
        newAlien.setLayoutParams(alienLayoutParams);

        mLayout.addView(newAlien);
        aliens.add(newAlien);
    }

    int dp2px(int dp){
        int px =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
        return px;
    }

    void clearSpace(){
        for (ImageButton ship : ships ){
            mLayout.removeView(ship);
        }
        ships.clear();
        for (ImageButton alien : aliens){
            mLayout.removeView(alien);
        }
        aliens.clear();
    }
}

