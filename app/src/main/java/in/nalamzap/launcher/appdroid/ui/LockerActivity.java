package in.nalamzap.launcher.appdroid.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.nalamzap.launcher.databinding.ActivityLockerBinding;
import in.nalamzap.launcher.databinding.LockerItemViewBinding;

public class LockerActivity extends AppCompatActivity {
    ActivityLockerBinding bind;

    FirebaseFirestore db;
    List<DocumentSnapshot> docList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityLockerBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        db = FirebaseFirestore.getInstance();
        db.collection("locker").orderBy("for").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null){
                    if(!value.isEmpty()){
                        docList = value.getDocuments();
                        setList();
                    }
                }
            }
        });

        bind.addBtnLKR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.newCLLKR.setVisibility(View.VISIBLE);
                bind.newCLLKR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bind.newCLLKR.setVisibility(View.GONE);
                    }
                });
            }
        });

        bind.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FOR = bind.forT.getText().toString().trim();
                String email = bind.emailT.getText().toString().trim();
                String pass = bind.passT.getText().toString().trim();
                String desc = bind.descT.getText().toString().trim();

                Map<String,String> data = new HashMap<>();
                if(FOR.isEmpty()){
                    bind.forTIL.setError("empty!");
                }else if(email.isEmpty()){
                    bind.emailTIL.setError("empty!");
                }else if(pass.isEmpty()){
                    bind.passTIL.setError("empty!");
                }else{
                    data.put("for",FOR);
                    data.put("email",email);
                    data.put("pass",pass);
                    if(!desc.isEmpty()) data.put("desc",desc);
                    bind.forT.setText("");
                    bind.emailT.setText("");
                    bind.passT.setText("");
                    bind.descT.setText("");
                    db.collection("locker").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            bind.newCLLKR.setVisibility(View.GONE);
                        }
                    });
                }

            }
        });
        bind.forT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                bind.forTIL.setError(null);
            }
        });
        bind.emailT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                bind.emailTIL.setError(null);
            }
        });
        bind.passT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                bind.passTIL.setError(null);
            }
        });
        bind.searchLKR.setOnSearchClickListener(onSearchClicked);
        bind.searchLKR.setOnCloseListener(onCloseListener);
    }

    private void setList() {

        bind.recyclerLKR.setLayoutManager(new LinearLayoutManager(this));
        bind.recyclerLKR.setAdapter(adapter);
    }

    RecyclerView.Adapter<ViewHolder> adapter = new RecyclerView.Adapter<ViewHolder>() {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LockerItemViewBinding lockerItem = LockerItemViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new ViewHolder(lockerItem.getRoot(),lockerItem);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DocumentSnapshot doc = docList.get(holder.getAdapterPosition());
            holder.lockerItem.forAppLItem.setText(doc.getString("for"));
            holder.lockerItem.emailLItem.setText(doc.getString("email"));
            holder.lockerItem.passwordLItem.setText(doc.getString("pass"));
        }

        @Override
        public int getItemCount() {
            return docList.size();
        }
    };

    static class ViewHolder extends RecyclerView.ViewHolder {
        LockerItemViewBinding lockerItem;
        public ViewHolder(@NonNull View itemView, LockerItemViewBinding lockerItem) {
            super(itemView);
            this.lockerItem = lockerItem;
        }
    }

    View.OnClickListener onSearchClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bind.addBtnLKR.setVisibility(View.GONE);
            bind.headerLKR.setVisibility(View.GONE);
        }
    };

    SearchView.OnCloseListener onCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            bind.addBtnLKR.setVisibility(View.VISIBLE);
            bind.headerLKR.setVisibility(View.VISIBLE);
            return false;
        }
    };
}