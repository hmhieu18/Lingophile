package com.example.lingophile.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lingophile.Database.DataCenter;
import com.example.lingophile.Database.FirebaseManagement;
import com.example.lingophile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MyProfileActivity extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 71;
    private TextView emailTextView;
    private EditText fullNameEditText;
    private RatingBar ratingBar;
    private Button editProfileBtn, doneBtn;
    private ImageButton imageButton;
    private DataCenter dc = DataCenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initComponent();
        try {
            loadUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUser() throws IOException {
//        imageButton.setImageResource();
        ratingBar.setRating(dc.user.getRating());
        emailTextView.setText(dc.user.getEmail());
        fullNameEditText.setText(dc.user.getUsername());

        StorageReference mImageRef =
                FirebaseStorage.getInstance().getReference().child("avatar").child(dc.user.getUserID());
        final long ONE_MEGABYTE = 1024 * 1024;
        mImageRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        DisplayMetrics dm = new DisplayMetrics();
//                        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//                        imageView.setMinimumHeight(dm.heightPixels);
//                        imageView.setMinimumWidth(dm.widthPixels);
                        imageButton.setImageBitmap(bm);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void initComponent() {
        imageButton = findViewById(R.id.imageButton);
        emailTextView = findViewById(R.id.emailTextView);
        fullNameEditText = findViewById(R.id.fullnameEditText);
        ratingBar = findViewById(R.id.ratingBar2);
        ratingBar.setEnabled(false);
        editProfileBtn = findViewById(R.id.editProfileButton);
        doneBtn = findViewById(R.id.EditDoneBtn);
        fullNameEditText.setInputType(InputType.TYPE_NULL);
        imageButton.setEnabled(false);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButton.setEnabled(true);
                fullNameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                doneBtn.setVisibility(View.VISIBLE);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dc.user.setUsername(fullNameEditText.getText().toString());
                FirebaseManagement.getInstance().updateUsername(fullNameEditText.getText().toString());
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap = circleFit(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageButton.setImageBitmap(bitmap);

            FirebaseManagement.getInstance().uploadAvatar(bitmap);
        }
    }

    public Bitmap circleFit(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle((float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2),
                (float) (bitmap.getWidth() / 2.5), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        Bitmap _bmp = Bitmap.createScaledBitmap(output, 200, 200, false);
        return output;
    }
}