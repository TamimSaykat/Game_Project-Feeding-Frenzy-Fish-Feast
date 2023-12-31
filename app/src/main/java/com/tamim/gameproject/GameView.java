package com.tamim.gameproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private int X = 10;
    private int Y;
    private int Fspeed;
    private int canvasWidth, canvasHeight;
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    private int yellowX, yellowY, yellowSpeed = 18;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 28;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 28;
    private Paint redPaint = new Paint();

    private int score, lifeCounter;
    private boolean touch;

    public GameView(Context context) {
        super(context);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.hh111);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        // Yellow
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        // Green
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        // Red
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE); // Change text color to white
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD); // Change font style to bold
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        Y = 550;
        score = 0;
        lifeCounter = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);

        int minY = fish[0].getHeight();
        int maxY = canvasHeight - fish[0].getHeight() * 3;

        Y = X + Fspeed;

        if (Y < minY) {
            Y = minY;
        }
        if (Y > maxY) {
            Y = maxY;
        }

        Fspeed = Fspeed + 10; // Increase this value to make the fish move faster

        if (touch) {
            canvas.drawBitmap(fish[1], X, Y, null);
            touch = false;

        } else {
            canvas.drawBitmap(fish[0], X, Y, null);
        }

        // Yellow ball function
        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX, yellowY)) {
            score = score + 10;
            yellowX = -100;
        }

        if (yellowX < 0) {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

        // Green ball function
        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX, greenY)) {
            score = score + 20;
            greenX = -100;
        }

        if (greenX < 0) {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(greenX, greenY, 25, greenPaint);

        // Red Ball Function
        redX = redX - redSpeed;

        if (hitBallChecker(redX, redY)) {
            redX = -100;
            lifeCounter--;

            if (lifeCounter == 0) {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX < 0) {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        // Increase the size of the red ball
        canvas.drawCircle(redX, redY, 30, redPaint); // Increased the red ball size

        // Score Function
        canvas.drawText("My Score: " + score, 20, 60, scorePaint);

        // Red ball life decreasing function
        for (int i = 0; i < 3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < lifeCounter) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
    }

    public boolean hitBallChecker(int x, int y) {
        if (X < x && x < (X + fish[0].getWidth()) && Y < y && y < (Y + fish[0].getHeight())) {
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            Fspeed = -44;
        }

        return true;
    }
}


















