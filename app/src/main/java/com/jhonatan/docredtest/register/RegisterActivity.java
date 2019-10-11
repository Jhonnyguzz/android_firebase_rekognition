package com.jhonatan.docredtest.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.jhonatan.docredtest.MainActivity;
import com.jhonatan.docredtest.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView imageView;
    TextInputEditText email1;
    TextInputEditText pass1;
    TextInputEditText pass2;

    Bitmap selfie;
    Bitmap cc;

    FirebaseVisionFaceDetectorOptions highAccuracyOpts;

    int source = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageView = findViewById(R.id.picture);
        email1 = findViewById(R.id.email_register);
        pass1 = findViewById(R.id.pass_register1);
        pass2 = findViewById(R.id.pass_register2);
    }


    public void createUser(View view) {

        if(selfie!=null && cc!=null && email1!=null && pass1!=null && pass2!=null) {
            if(pass1.getText().toString().equals(pass2.getText().toString())) {
                //TODO: Validar email
                Toast.makeText(getApplicationContext(), "Validando con AWS Rekognition", Toast.LENGTH_LONG).show();
                if(callRekognition()){
                    //TODO: Registrar al usuario
                    callFirebaseRegister(email1.getText().toString(), pass2.getText().toString());
                }else {
                    Toast t = Toast.makeText(getApplicationContext(), "Las fotos no coinciden", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        }



        //ML AA Kit by Google
        /*if(selfie!=null && cc!=null) {
            highAccuracyOpts =
                    new FirebaseVisionFaceDetectorOptions.Builder()
                            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                            .enableTracking()
                            .build();

            FirebaseVisionImage mySelfie = FirebaseVisionImage.fromBitmap(selfie);

            FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                    .getVisionFaceDetector(highAccuracyOpts);

            Task<List<FirebaseVisionFace>> result =
                    detector.detectInImage(mySelfie)
                            .addOnSuccessListener(
                                    new OnSuccessListener<List<FirebaseVisionFace>>() {
                                        @Override
                                        public void onSuccess(List<FirebaseVisionFace> faces) {
                                            Toast.makeText(getApplicationContext(), "Te ves lindo en la selfie", Toast.LENGTH_SHORT).show();

                                            for (FirebaseVisionFace face: faces) {
                                                System.err.println("Tracking id:" + face.getTrackingId());
                                            }
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "No se pudo detectar el rostro de Selfie", Toast.LENGTH_SHORT).show();
                                        }
                                    });

        }*/
    }

    private void callFirebaseRegister(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "El usuario se registro con éxito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "El usuario no se registro.", Toast.LENGTH_SHORT).show();
                }
            }
            //TODO: OnFail, More Actions

        });
    }

    public boolean callRekognition() {

        Image image1 = new Image();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        cc.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
        image1.withBytes(ByteBuffer.wrap(stream1.toByteArray()));

        Image image2 = new Image();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        selfie.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
        image2.withBytes(ByteBuffer.wrap(stream2.toByteArray()));

        AWSAsyncTask awsAsyncTask = new AWSAsyncTask();
        awsAsyncTask.execute(image1, image2);

        try {
            return awsAsyncTask.get();
        } catch (ExecutionException e) {
            System.err.println("Error de ejecucion - Intentalo de nuevo");
            return false;
        } catch (InterruptedException e) {
            System.err.println("Interrupcion del hilo - Intentalo de nuevo");
            return false;
        }
    }

    public void takeSelfie(View view) {
        //Selfie
        source = 1;
        Intent intentSelfie = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentSelfie.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentSelfie, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void takeFotoCC(View view) {
        //CC
        source = 2;
        Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentFoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentFoto, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if(source==1) {
                selfie = (Bitmap) extras.get("data");
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }else if(source==2){
                cc = (Bitmap) extras.get("data");
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }else {
                System.err.println("Error enviado datos");
            }
        }
    }
}
