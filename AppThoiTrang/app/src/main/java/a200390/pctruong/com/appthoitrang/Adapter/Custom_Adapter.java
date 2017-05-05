package a200390.pctruong.com.appthoitrang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import a200390.pctruong.com.appthoitrang.Activity_DS_Detail;
import a200390.pctruong.com.appthoitrang.DAO.XuLyDuLieu;
import a200390.pctruong.com.appthoitrang.DTO.AoNamNu;
import a200390.pctruong.com.appthoitrang.R;

/**
 * Created by PCTRUONG on 2/11/2017.
 */

public class Custom_Adapter extends RecyclerView.Adapter<Custom_Adapter.ViewHolder_Adapter> {
    Context context;
    int layout;
    ArrayList<AoNamNu> arrayList;
    public Custom_Adapter(int layout, Context context, ArrayList<AoNamNu> arrayList) {
        this.layout = layout;
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public ViewHolder_Adapter onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.item_custom,parent,false);
        return new ViewHolder_Adapter(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder_Adapter holder, final int position) {
        holder.imageView.setImageBitmap(arrayList.get(position).getAnhNho());
        holder.txt_ten.setText(arrayList.get(position).getTen());
        holder.txt_gia.setText(arrayList.get(position).getGia() + " VND");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder_Adapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt_ten,txt_gia;
        public ViewHolder_Adapter(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.img_anh_nho);
            txt_ten= (TextView) itemView.findViewById(R.id.txt_ten_sp);
            txt_gia= (TextView) itemView.findViewById(R.id.txt_gia_sp);


        }
    }
}
