package in.nalamzap.launcher.appdroid.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import in.nalamzap.launcher.R;
import in.nalamzap.launcher.appdroid.ui.LockerActivity;
import in.nalamzap.launcher.databinding.FragmentMain2Binding;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment2 extends Fragment {

    FragmentMain2Binding bind;
    FirebaseFirestore db;
    private ListenerRegistration listenerReg;

    public static PlaceholderFragment2 newInstance() {
        return new PlaceholderFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        bind = FragmentMain2Binding.inflate(inflater,container,false);
        db = FirebaseFirestore.getInstance();
        bind.passIET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bind.passIL.setError(null);
            }
        });
        bind.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listenerReg = db.collection("locker").document("credentials").addSnapshotListener(eventListener);
            }
        });
        return bind.getRoot();
    }

    EventListener<DocumentSnapshot> eventListener = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
            if(value!=null) {
                listenerReg.remove();
                String pass = bind.passIET.getText().toString();
                if(value.getString("pass").equals(pass)) lockerActivity();
                else {
                    bind.passIL.setError("wrong password!");
                }
            }
        }
    };

    private void lockerActivity() {
        Intent intent = new Intent(getContext(), LockerActivity.class);
        startActivity(intent);
    }
}