package com.example.montraqrlector.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montraqrlector.R;

import java.util.ArrayList;

public class AdapterSQLite extends RecyclerView.Adapter<AdapterSQLite.ViewHolder> {
    private Context context;
    private ArrayList lectura_id, qrscan_id, name_id, empresa_id, tel1_id, tel2_id,correo1_id, correo2_id, info_id, coment_id, agrx_id;


    public AdapterSQLite(Context context, ArrayList lectura_id, ArrayList qrscan_id, ArrayList name_id, ArrayList empresa_id, ArrayList tel1_id, ArrayList tel2_id, ArrayList correo1_id, ArrayList correo2_id, ArrayList info_id, ArrayList coment_id, ArrayList agrx_id) {
        this.context = context;
        this.lectura_id = lectura_id;
        this.qrscan_id = qrscan_id;
        this.name_id = name_id;
        this.empresa_id = empresa_id;
        this.tel1_id = tel1_id;
        this.tel2_id = tel2_id;
        this.correo1_id = correo1_id;
        this.correo2_id = correo2_id;
        this.info_id = info_id;
        this.coment_id = coment_id;
        this.agrx_id = agrx_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userdata,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lectura.setText(String.valueOf(lectura_id.get(position)));
        holder.qrscan.setText(String.valueOf(qrscan_id.get(position)));
        holder.Name.setText(String.valueOf(name_id.get(position)));
        holder.Empresa.setText(String.valueOf(empresa_id.get(position)));
        holder.Tel1.setText(String.valueOf(tel1_id.get(position)));
        holder.Tel2.setText(String.valueOf(tel2_id.get(position)));
        holder.Correo1.setText(String.valueOf(correo1_id.get(position)));
        holder.Correo2.setText(String.valueOf(correo2_id.get(position)));
        holder.Info.setText(String.valueOf(info_id.get(position)));
        holder.Comentarios.setText(String.valueOf(coment_id.get(position)));
        holder.Agrx.setText(String.valueOf(agrx_id.get(position)));



    }

    @Override
    public int getItemCount() {
        return lectura_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lectura, qrscan, Name, Empresa, Tel1, Tel2, Correo1, Correo2, Info, Comentarios, Agrx;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lectura = itemView.findViewById(R.id.lecturatext);
            qrscan = itemView.findViewById(R.id.qrscantext);
            Name = itemView.findViewById(R.id.nametext);
            Empresa = itemView.findViewById(R.id.empresatext);
            Tel1 = itemView.findViewById(R.id.tel1text);
            Tel2 = itemView.findViewById(R.id.tel2text);
            Correo1 = itemView.findViewById(R.id.correo1text);
            Correo2 = itemView.findViewById(R.id.correo2text);
            Info = itemView.findViewById(R.id.infotext);
            Comentarios = itemView.findViewById(R.id.comenttext);
            Agrx = itemView.findViewById(R.id.agrxtext);
        }
    }
}
