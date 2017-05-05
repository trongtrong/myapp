package androi30_b.foody2.View;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import androi30_b.foody2.R;

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, FirebaseAuth.AuthStateListener{
    Button btnDangNhapGoogle;
    GoogleApiClient apiClient;
    public static int REQUEST_CODE_DANG_NHAP_GOOGLE = 99;
    public static int KIEM_TRA_PROVIDER_DANG_NHAP = 0;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_dang_nhap);
        firebaseAuth = FirebaseAuth.getInstance();

        btnDangNhapGoogle = (Button) findViewById(R.id.btnDangNhapGoogle);
        btnDangNhapGoogle.setOnClickListener(this);
        taoClientDangNhapGoogle();
    }
    private void taoClientDangNhapGoogle(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
         apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }
    private void dangNhapGoogle(GoogleApiClient apiClient){
        KIEM_TRA_PROVIDER_DANG_NHAP = 1;
        Intent iDangNhapGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(iDangNhapGoogle, REQUEST_CODE_DANG_NHAP_GOOGLE );
    }
    private void chungThucDangNHapFirebase(String tokenID){
        if (KIEM_TRA_PROVIDER_DANG_NHAP == 1){
            AuthCredential credential = GoogleAuthProvider.getCredential(tokenID, null);
            firebaseAuth.signInWithCredential(credential);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DANG_NHAP_GOOGLE){
            if (resultCode == RESULT_OK){
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
                String tokenID = signInAccount.getIdToken();
                chungThucDangNHapFirebase(tokenID);
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDangNhapGoogle :
                dangNhapGoogle(apiClient);
                break;
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            Toast.makeText(this, "đang nhap thành công", Toast.LENGTH_LONG).show();
        }else {

        }
    }
}
