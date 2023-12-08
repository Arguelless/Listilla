package com.arguelles.listilla;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Model: Record (intents=puntuació, nom)
    class Record {
        public int intents;
        public String nom;

        public Record(int _intents, String _nom ) {
            intents = _intents;
            nom = _nom;
        }
    }
    private List<String> nombres = Arrays.asList("Manolo", "Pepe", "Laura", "Ana", "Carlos", "Elena", "David", "Sara", "Juan", "María");
    private List<String> apellidos = Arrays.asList("Gómez", "Pérez", "Fernández", "López", "Martínez", "García", "Ruiz", "Sánchez", "Díaz", "Rodríguez");

    private int[] imagenes = {R.drawable.crow, R.drawable.death, R.drawable.mummy, R.drawable.pumpkin, R.drawable.vampire};
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialitzem model
        records = new ArrayList<Record>();
        Random random = new Random();

        // Afegim alguns exemples
        for (int i = 0; i < 10; i++) {
            int randomIntents = random.nextInt(100) + 1; // Número aleatorio entre 1 y 100
            String randomNombre = getRandomNombreCompleto();
            records.add(new Record(randomIntents, randomNombre));
        }

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                int imagenAleatoria = obtenerImagenAleatoria();
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                ((ImageView) convertView.findViewById(R.id.icon)).setImageResource(imagenAleatoria);
                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<3;i++) {

                    int randomIntents = random.nextInt(100) + 1;
                    String randomNombre = getRandomNombreCompleto();
                    records.add(new Record(randomIntents, randomNombre));
                }
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });
    }
    private String getRandomNombreCompleto() {
        Collections.shuffle(nombres);
        Collections.shuffle(apellidos);

        String nombre = nombres.get(0);
        String apellido = apellidos.get(0);

        return nombre + " " + apellido;
    }
    private int obtenerImagenAleatoria() {
        // Genera un índice aleatorio dentro del rango de la matriz de imágenes
        Random random = new Random();
        int indiceAleatorio = random.nextInt(imagenes.length);

        // Devuelve la identificación de recurso de la imagen seleccionada
        return imagenes[indiceAleatorio];
    }
}