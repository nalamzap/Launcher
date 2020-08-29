package in.tipoff.appdroid.ui.main;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import in.tipoff.appdroid.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class AppListFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    ConstraintLayout selLayout;
    Button appInfo,appUninstall;
    TextView appName;
    AppObject selected;
    int n;
    List<AppObject> list;
    AppGridAdapter adapter;


    public static AppListFragment newInstance() {
        AppListFragment fragment = new AppListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        list = getAppList();
        View root = inflater.inflate(R.layout.fragment_apps, container, false);
        GridView gridView = root.findViewById(R.id.appGrid);
        selLayout = root.findViewById(R.id.selectLayout);
        appInfo = root.findViewById(R.id.button3);
        appUninstall = root.findViewById(R.id.button4);
        appName = root.findViewById(R.id.selectedAppName);

        gridView.setColumnWidth((int)getResources().getDimension(R.dimen.appWidth));
        adapter = new AppGridAdapter(getContext(),list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent launchApp = getContext().getPackageManager().getLaunchIntentForPackage(list.get(i).getPackageName());
                if(launchApp!=null)
                    getContext().startActivity(launchApp);
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = list.get(i);
                n=3;
                appName.setText(selected.getName());
                appUninstall.setText(String.format("Uninstall %s?",selected.getName()));
                selLayout.setVisibility(View.VISIBLE);
                return true;
            }
        });

        selLayout.setOnClickListener(onClickListener);
        appInfo.setOnClickListener(onClickListener);
        appUninstall.setOnClickListener(onClickListener);



        return root;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.selectLayout:
                    selLayout.setVisibility(View.GONE);
                    break;
                case R.id.button3:
                    startApplicationDetailsActivity();
                    selLayout.setVisibility(View.GONE);
                    break;
                case R.id.button4:
                    appUninstall();
                    selLayout.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void appUninstall() {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + selected.getPackageName()));
        startActivity(intent);
    }

    private void startApplicationDetailsActivity() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + selected.getPackageName()));
        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        startActivityForResult(intent,123);
    }

    private List<AppObject> getAppList() {
        List<AppObject> list = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> untreatedAppList = getContext().getApplicationContext().getPackageManager().queryIntentActivities(intent,0);

        for(ResolveInfo untreatedApp : untreatedAppList){
            AppObject appObject = new AppObject(untreatedApp.activityInfo.loadLabel(getContext().getApplicationContext().getPackageManager()).toString(),
                    untreatedApp.activityInfo.packageName,
                    untreatedApp.activityInfo.loadIcon(getContext().getApplicationContext().getPackageManager()));
            if(!list.contains(appObject))list.add(appObject);
        }

        Collections.sort(list, new Comparator<AppObject>() {
            @Override
            public int compare(AppObject appObject, AppObject t1) {
                return appObject.getName().compareTo(t1.getName());
            }
        });
        return  list;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                list.remove(selected);
                Toast.makeText(getContext(), selected.getName().concat(" uninstalled"), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }else if (resultCode == RESULT_FIRST_USER) {
                Toast.makeText(getContext(), "can't uninstall system apps", Toast.LENGTH_SHORT).show();
            }
        }
    }
}