package com.theakj.expertcep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ExpertAnswer extends AppCompatActivity {

//    TextView detailDesc, detailTitle;
//    TextView detailLang;
    ImageView detailImage;
//    String key = "";
//    String imageUrl = "";
    String reqdoc="";
    String detailLang="";

    Button updateBtn;
    EditText answer;
    FirebaseFirestore database;
    String ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_answer);

        updateBtn=findViewById(R.id.updateBtn);
        answer=findViewById(R.id.answer);
        database= FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailLang=bundle.getString("Language").toString();
            System.out.println(detailLang);
//            detailDesc.setText(bundle.getString("Description"));
//            detailTitle.setText(bundle.getString("Title"));
//           detailLang.setText(bundle.getString("Language"));
//            key = bundle.getString("Key");
//            imageUrl = bundle.getString("Image");
//            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ans=answer.getText().toString();
               // getDocumentPath();
                updateAnswer();
                startActivity(new Intent(ExpertAnswer.this,DetailActivity.class));
            }
        });
    }

    private void updateAnswer() {
        database.collection("users").document("pb").update("dataLang",ans+"").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ExpertAnswer.this, "Answer Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ExpertAnswer.this, "Not Updated....task is unsuccecful", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ExpertAnswer.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void getDocumentPath() {
//        // Get reference to the collection
//        CollectionReference collectionRef = database.collection("users");
//
//        // Create a query to filter documents based on field and value
//        Query query = collectionRef.whereEqualTo("dataLang", detailLang + "");
//
//        // Execute the query
//        query.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                // Iterate through the result set
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    // Get the document reference and extract the path
//                    DocumentReference documentReference = document.getReference();
//                    String documentPath = documentReference.getPath();
//                    reqdoc = documentPath;
//                    // Print or use the document path as needed
//                    System.out.println("Document Path: " + documentPath);
//                }
//            } else {
//                // Handle errors
//                Exception exception = task.getException();
//                if (exception != null) {
//                    exception.printStackTrace();
//                }
//            }
//        });
//    }

//    public void updateAnswer() {
////        database.collection("users").whereEqualTo("dataLang",detailLang+"").get()
////                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////                    @Override
////                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                        if (task.isSuccessful()) {
////                            for (QueryDocumentSnapshot document : task.getResult()) {
//////                                reqdoc = (document.getId() + " => " + document.getData()).toString();
////                                DocumentReference documentReference = document.getReference();
////                                String documentPath = documentReference.getPath().toString();
////                                reqdoc=documentPath+"";
////                                System.out.println(reqdoc);
////                                Toast.makeText(ExpertAnswer.this, reqdoc+"", Toast.LENGTH_SHORT).show();
////                            }
////                        }
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        Toast.makeText(ExpertAnswer.this, "document error", Toast.LENGTH_SHORT).show();
////                    }
////                });
//
//    }
}