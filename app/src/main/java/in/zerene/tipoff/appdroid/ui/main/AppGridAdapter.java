package in.zerene.tipoff.appdroid.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import in.zerene.tipoff.R;

public class AppGridAdapter extends BaseAdapter {

    List<AppObject> list;
    Context context;

    public AppGridAdapter(Context context,List<AppObject> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v;
        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            v = layoutInflater.inflate(R.layout.item_app,viewGroup,false);
        }else{
            v = view;
        }
        TextView name = v.findViewById(R.id.nameAppItem);
        name.setText(list.get(i).getName());
        ImageView icon = v.findViewById(R.id.imgAppItem);
        icon.setImageDrawable(list.get(i).getIcon());

        final Snackbar snackbar = Snackbar.make(viewGroup,"",Snackbar.LENGTH_LONG);
        /*v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                snackbar.setAction("app info", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startApplicationDetailsActivity(list.get(i).getPackageName());
                            }
                        })
                        *//*
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        })*//*
                .show();
                return true;
            }
        });*/

        return v;
    }


}
