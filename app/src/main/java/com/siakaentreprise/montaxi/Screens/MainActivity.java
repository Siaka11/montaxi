package com.siakaentreprise.montaxi.Screens;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.zxing.common.StringUtils;
import com.siakaentreprise.montaxi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import soup.neumorphism.NeumorphButton;
import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphFloatingActionButton;

public class MainActivity extends AppCompatActivity {

    NeumorphButton scan_main;
    NeumorphFloatingActionButton menumain, shareinfo;
    TextView mycomment, nomduchauffeur, prenomduchauffeur, matriculecar;
    ImageView imgchauffeur, imgtaxi;
    LottieAnimationView addtocart;
    String[] agent;
    String myagent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menumain  = findViewById(R.id.menumain);
        scan_main = findViewById(R.id.scan_main);
        //mycomment = findViewById(R.id.mycomment);

        shareinfo  = findViewById(R.id.shareinfo);


        addtocart = findViewById(R.id.addtocart);
        scan_main = findViewById(R.id.scan_main);
        nomduchauffeur = findViewById(R.id.nomduchauffeur);
        prenomduchauffeur = findViewById(R.id.prenomduchauffeur);
        matriculecar = findViewById(R.id.matriculecar);

        imgchauffeur = findViewById(R.id.imgchauffeur);
        imgtaxi = findViewById(R.id.imgtaxi);

        //SetVisibility Text
        nomduchauffeur.setVisibility(View.GONE);
        prenomduchauffeur.setVisibility(View.GONE);
        matriculecar.setVisibility(View.GONE);

        //SetVisibility ImageView
        imgchauffeur.setVisibility(View.GONE);
        imgtaxi.setVisibility(View.GONE);

        //SetLoti
        addtocart.setVisibility(View.VISIBLE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("teste11", "message de la vrai raison de son");
        Toast.makeText(getApplicationContext(), " 2 ", Toast.LENGTH_LONG).show();


        shareinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "myvalue");
                i.putExtra(Intent.EXTRA_TEXT, myagent);
                startActivity(Intent.createChooser(i, "share"));
            }
        });


// Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("nom", "Ada");


// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("tester", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("tester", "Error adding document", e);
                    }
                });

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


        // Register the launcher and result handler
         /*final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if(result.getContents() == null) {
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    }
                });


        scan_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanOptions options = new ScanOptions();
                options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
                options.setPrompt("Scan a barcode");
                options.setCameraId(0);  // Use a specific camera of the device
                options.setBeepEnabled(true);
                options.setCaptureActivity(Capture.class);
                options.setOrientationLocked(false);
                options.setBarcodeImageEnabled(true);
                barcodeLauncher.launch(options);
            }
        });*/
        /*scan_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ScanActivity.class);
            }
        });*/

        scan_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);

                intentIntegrator.setPrompt("For flash");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setBeepEnabled(true);

                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

            }
        });

        menumain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentResult.getContents() != null){

            nomduchauffeur.setVisibility(View.VISIBLE);
            prenomduchauffeur.setVisibility(View.VISIBLE);
            matriculecar.setVisibility(View.VISIBLE);
            addtocart.setVisibility(View.GONE);
            imgchauffeur.setVisibility(View.VISIBLE);
            imgtaxi.setVisibility(View.VISIBLE);

            myagent = intentResult.getContents();
            agent = intentResult.getContents().split(",");

            //mycomment.setText(intentResult.getContents());
            Log.d("TAG", "onActivityResult: "+agent[1]);

            String[] nomsplit = agent[0].split("=");
            Log.d("TAG1", "onActivityResult: "+nomsplit[1]);

            String[] prenomsplit = agent[1].split("=");
            Log.d("TAG1", "onActivityResult: "+prenomsplit[1]);

            String[] matriculeplit = agent[2].split("=");
            String matriculeplit1 = matriculeplit[1];
            String matriculeplitfinal = matriculeplit1.substring(0,matriculeplit1.length() - 1);
            Log.d("TAG1", "onActivityResult: "+matriculeplitfinal);

            nomduchauffeur.setText(agent[0]);
            prenomduchauffeur.setText(agent[1]);
            matriculecar.setText(agent[2]);
           /* for(int i = 0; i < agent.length; i++){

            }*/

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this
            );

            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }else{
            Toast.makeText(getApplicationContext(), "Oups...you did not scan anything", Toast.LENGTH_LONG).show();
        }
    }
}