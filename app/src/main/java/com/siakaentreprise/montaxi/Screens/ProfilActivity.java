package com.siakaentreprise.montaxi.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.siakaentreprise.montaxi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import soup.neumorphism.NeumorphButton;

public class ProfilActivity extends AppCompatActivity {

    NeumorphButton param_propos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ImageView mycomment;

        mycomment = findViewById(R.id.mycomment);
        param_propos = findViewById(R.id.param_propos);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("nom", "oan");
        Log.d("teste11", "");

        db.collection("acteurs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("teste", document.getId() + " => " + document.getData());
                                Toast.makeText(getApplicationContext(), " 2 "+document.getId(), Toast.LENGTH_LONG).show();

                                String mytext = document.getData().toString();

                                //Initialiser multi format writer
                                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();


                                try {
                                    //Initialiser bit matrix
                                    BitMatrix bitMatrix = multiFormatWriter.encode(mytext, BarcodeFormat.QR_CODE,
                                            200, 200);

                                    //Initialiser BarCode
                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                                    //Initialiser Bitmap
                                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                                    //Set bitmap on view image
                                    mycomment.setImageBitmap(bitmap);

                                    //Inialiser input manager
                                   /* InputMethodManager manager = (InputMethodManager) getSystemService(
                                            Context.INPUT_METHOD_SERVICE
                                    );
*/
                                    //Hide Soft
                                } catch (WriterException e) {
                                    e.printStackTrace();
                                }

                            }
                        } else {
                            Log.w("teste", "Error getting documents.", task.getException());
                        }
                    }
                });

        param_propos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("acteurs")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("teste1", document.getId() + " => " + document.getData());
                                        Toast.makeText(getApplicationContext(), " 1 "+document.getId(), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Log.w("teste1", "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
                /*db.collection("Users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });
            }*/
        });
        // Add a new document with a generated ID


    }
}