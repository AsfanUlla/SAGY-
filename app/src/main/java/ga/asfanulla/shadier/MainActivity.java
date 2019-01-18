package ga.asfanulla.shadier;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ga.asfanulla.shadier.SubClass.SaveSharedPreference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        per();

       // SaveSharedPreference.clearUserData(this);
        if(SaveSharedPreference.getUserLang(this).isEmpty()){
            SaveSharedPreference.setUserLang(this,"en");
        }
    }

    private void per(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(this,MapsActivity.class);
            startActivity(i);
            finish();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           final String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent i = new Intent(this,MapsActivity.class);
                    i.putExtra("key", "null");
                    startActivity(i);
                    finish();
                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Permission Denied");
                    builder.setMessage("Grant Permission to Continue");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            per();
                        }
                    });

                    builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show(); }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
