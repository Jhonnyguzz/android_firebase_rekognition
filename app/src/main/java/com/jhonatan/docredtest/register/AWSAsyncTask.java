package com.jhonatan.docredtest.register;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class AWSAsyncTask extends AsyncTask<Image, Void, Boolean> {

    private AWSCredentials credentials;
    private AmazonRekognition amazonRekognition;

    private static final String ACCESS_KEY = "YOUR ACCESS ID";
    private static final String SECRET_KEY = "YOUR SECRET KEY";

    private void init() {
        credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        amazonRekognition = new AmazonRekognitionClient(credentials);
        amazonRekognition.setRegion(Region.getRegion(Regions.US_EAST_1));
    }

    @Override
    protected void onPreExecute() {
        init();
    }

    @Override
    protected Boolean doInBackground(Image... images) {

        CompareFacesRequest compareFacesRequest = new CompareFacesRequest();

        compareFacesRequest.setSourceImage(images[0]);
        compareFacesRequest.setTargetImage(images[1]);

        CompareFacesResult faces = amazonRekognition.compareFaces(compareFacesRequest);
        if(faces.getFaceMatches().size()>0) {
            for(CompareFacesMatch face: faces.getFaceMatches()) {
                System.err.println("Similarity: " + face.getSimilarity());
                Log.i("SIMILAR", "Son similares en un "+face.getSimilarity()+"%");
            }
            return faces.getFaceMatches().get(0).getSimilarity() > 70;
        }else {
            System.err.println("Error comparando las dos fotos, intenta otra vez");
            return false;
        }
    }

}
