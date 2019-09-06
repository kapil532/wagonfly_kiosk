package kartify_base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

import capture.Imageutils;

public class ProfilePrimeEditActivity extends BaseActivity implements OnClickListener, Imageutils.ImageAttachmentListener {

    Button btpic, btnup;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    private Uri fileUri;
    private Bitmap bitmap;
    private String file_name;
    ImageView edit;
    private TextView edit_std_num;
    private TextView edit_fullname;
    private TextView edit_mobile,name_fi;
    private TextView edit_address;
    private TextView edit_email;
    private Button save_profile;
    private RelativeLayout back;
    private ImageView rounde_image;
    private Toolbar mToolbar;
    private DisplayImageOptions options;
    Imageutils imageutils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_screen);

        imageutils = new Imageutils(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title =(TextView)findViewById(R.id.title);
        title.setText("Profile");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
           // window.setStatusBarColor(getResources().getColor(R.color.prime_primary_color));
        }
      //  ((TextView) findViewById(R.id.title)).setText("Profile");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  mToolbar.setBackgroundColor(getResources().getColor(R.color.prime_primary_color));
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });

        edit_fullname = (TextView) findViewById(R.id.edit_fullname);
        name_fi = (TextView) findViewById(R.id.name_fi);
        edit_fullname.setText(CommonClass.returnGenericData("name", "name", this));
        name_fi.setText(CommonClass.returnGenericData("name", "name", this));

        edit_mobile = (TextView) findViewById(R.id.edit_mobile);
        edit_mobile.setClickable(false);
        edit_mobile.setEnabled(false);

//        edit_address = (TextView) findViewById(R.id.edit_address);
//        edit_address.setText(CommonClass.returnGenericData("profile_add", "profile_add_reference", this));

        edit_email = (TextView) findViewById(R.id.edit_email);

        edit_email.setClickable(true);
        edit_email.setEnabled(true);
        edit_mobile.setText(CommonClass.returnGenericData(MOBILE, MOBILE_PREF, this));
        edit_email.setText(CommonClass.returnGenericData(EMAIL, EMAIL_PREF, this));



        save_profile = (Button) findViewById(R.id.save_profile);
        save_profile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                {

                    Is_Valid_Email(edit_email);

                    if (valid_email == null) {
                        edit_email.setError("Required");
                        return;
                    }
                    if (edit_mobile.getText().toString().length() == 0) {
                        edit_mobile.setError("Required");
                        return;
                    }

/*

                    if( !(CommonClass.returnGenericData("pic", "pic", ProfilePrimeEditActivity.this).length()>5))
                    {
                        Toast.makeText(ProfilePrimeEditActivity.this, "Please update your Picture!", Toast.LENGTH_LONG).show();
                        return;
                    }
*/

/*
                    JSONObject table = new JSONObject();
                    try {
*//*
                        table.put("uid", network.getDivrtUid());
                        table.put("divrt", net.getDivrtKey());
                        table.put("n", edit_fullname.getText().toString());
                        table.put("tn", edit_mobile.getText().toString());
                        table.put("me", edit_email.getText().toString());
                        table.put("as1", "");
                        table.put("vehicles", "" + edit_vehicle.getText().toString());*//*
                    } catch (JSONException e) {
                    }
                    PostData.call(table, CommonClass.FINAL_URL + "api/v3/user/" + "modify", mpost);*/
                }
            }
        });
        rounde_image = (ImageView) findViewById(R.id.rounde_image);
        rounde_image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                imageutils.imagepicker(1);
            }
        });


        updateProfile();


        customFontsChecks(edit_email);
        customFontsChecks(edit_fullname);
        customFontsChecks(save_profile);
        customFontsChecks(edit_mobile);


    }


    String valid_email;

    public void Is_Valid_Email(TextView edt) {

        if (edt.getText().toString() == null) {
            edt.setError("Required!!");
            valid_email = null;
        } else if (isEmailValid(edt.getText().toString()) == false) {
            edt.setError("Invalid Email Address");
            valid_email = null;
        } else {
            valid_email = edt.getText().toString();
        }

    }


    boolean isEmailValid(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    private void updateProfile() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_profile)
                .showImageForEmptyUri(R.drawable.ic_profile)
                .showImageOnFail(R.drawable.ic_profile)
                .cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();
        ImageLoader.getInstance().displayImage(CommonClass.returnGenericData("pic", "pic", this), rounde_image, options, null);

    }


    private void checkMedotryFields() {

    }





    private void clickpic() {
        // Check Camera
        if (getApplicationContext().getPackageManager().hasSystemFeature
                (PackageManager.FEATURE_CAMERA)) {
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        this.bitmap = file;
        this.file_name = filename;
        rounde_image.setImageBitmap(file);

        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imageutils.createImage(file, filename, path, false);
        new upload().execute();

    }

    String encode = "";

    @Override
    public void onClick(View view) {

    }

    private class upload extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            ByteArrayOutputStream bytArra = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytArra);
            encode = Base64.encodeToString(bytArra.toByteArray(), Base64.DEFAULT);
            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }






    private void saveImage(String path) {
        CommonClass.saveGenericData(path, "pic", "pic", this);
    }

}