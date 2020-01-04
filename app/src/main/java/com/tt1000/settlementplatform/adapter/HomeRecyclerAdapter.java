package com.tt1000.settlementplatform.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.bean.MainMenu;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private List<MainMenu> menuList;
    private int width;
    private int height;
    private OnAdapterItemClickListener listener;
    public HomeRecyclerAdapter(List<MainMenu> menuList, int width, int height) {
        this.menuList = menuList;
        this.width = width;
        this.height = height;
    }

    public void setListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home__item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageView.setImageResource(menuList.get(position).getResId());
        holder.textView.setText(menuList.get(position).getText());
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        params.width = (int) (width-60) / 2;
        params.height = height / 3;
        params.bottomMargin = 30;
//        params.
        holder.itemView.postInvalidate();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position, menuList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.grid_home_image);
            textView = itemView.findViewById(R.id.grid_home_text);
            ll = itemView.findViewById(R.id.grid_home_ll);
        }
    }
}
