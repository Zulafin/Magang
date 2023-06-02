package com.example.magang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RiwayatAdapter extends ArrayAdapter<ItemRiwayat> {
    private Context context;
    private int resource;
    private List<ItemRiwayat> riwayatList;

    public RiwayatAdapter(Context context, int resource, List<ItemRiwayat> riwayatList) {
        super(context, resource, riwayatList);
        this.context = context;
        this.resource = resource;
        this.riwayatList = riwayatList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textNoResi = convertView.findViewById(R.id.textNoResi);
            viewHolder.textNamaBarang = convertView.findViewById(R.id.textNamaBarang);
            viewHolder.textTanggal = convertView.findViewById(R.id.textTanggal);
            viewHolder.textJam = convertView.findViewById(R.id.textJam);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemRiwayat itemRiwayat = riwayatList.get(position);

        viewHolder.textNoResi.setText(itemRiwayat.getNoResi());
        viewHolder.textNamaBarang.setText(itemRiwayat.getNamaBarang());
        viewHolder.textTanggal.setText(itemRiwayat.getTanggal());
        viewHolder.textJam.setText(itemRiwayat.getJam());

        return convertView;
    }

    static class ViewHolder {
        TextView textNoResi;
        TextView textNamaBarang;
        TextView textTanggal;
        TextView textJam;
    }
}
