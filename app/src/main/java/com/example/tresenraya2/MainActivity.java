package com.example.tresenraya2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;

    // Representación de jugadores
    // 0 - X
    // 1 - O
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // Significado de los estados:
    // 0 - X
    // 1 - O
    // 2 - Null
    // poner todas las posiciones ganadoras en un array 2D
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;

    // se llama a esta función cada vez que un jugador
    // pulsa sobre una posición vacía del grid
    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        // la función de resetear el juego se invoca
        // si alguien gana o todas las posiciones del grid están llenas
        if (!gameActive) {
            gameReset(view);
        }

        // si la imagen pulsada está vacía
        if (gameState[tappedImage] == 2) {
            // incrementa el contador
            // después de cada tap
            counter++;

            // comprueba si es la última posición
            if (counter == 9) {
                // resetea el juego
                gameActive = false;
            }

            // marca esta posición
            gameState[tappedImage] = activePlayer;

            // efecto de movimiento a la imagen
            img.setTranslationY(-1000f);

            // cambia el jugador activo
            // de 0 a 1 o de 1 a 0
            if (activePlayer == 0) {
                // set de la imagen de X
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);

                // cambio de estado
                status.setText("Turno de O - Pulsa para jugar");
            } else {
                // set de la imagen de O
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);

                // cambio de estado
                status.setText("Turno de X - Pulsa para jugar");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        // Comprueba si ha ganado algún jugador
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                flag = 1;

                // Alguien ha ganado. Buscar quién
                String winnerStr;

                // llamada a la función para resetear el juego
                gameActive = false;
                if (gameState[winPosition[0]] == 0) {
                    winnerStr = "X ha ganado";
                } else {
                    winnerStr = "O ha ganado";
                }
                // actualiza la barra de estado para anunciar el ganador
                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);
            }
        }
        // actualiza el estado si hay empate
        if (counter == 9 && flag == 0) {
            TextView status = findViewById(R.id.status);
            status.setText("Empate");
        }
    }

    // resetea el juego
    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        // elimina todas las imágenes dentro del grid
        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("Turno de X - Pulsa para jugar");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

