package in.nalamzap.launcher.appdroid.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import in.nalamzap.launcher.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment2 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


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
        View root = inflater.inflate(R.layout.fragment_main2, container, false);
        return root;
    }
}