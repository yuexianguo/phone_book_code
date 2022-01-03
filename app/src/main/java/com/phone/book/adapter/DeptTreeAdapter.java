package com.phone.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phone.book.R;
import com.phone.book.bean.DeptTree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/12/31
 */
public class DeptTreeAdapter extends RecyclerView.Adapter<DeptTreeAdapter.ViewHolder> {
    ArrayList<DeptTree> deptTreeList =new ArrayList<>();
    Context context ;
    DeptTree pin;
    public DeptTreeAdapter(Context context, DeptTree deptTree) {
        this.context  =  context;
        deptTreeList.addAll(deptTree.child);
    }

    @NonNull
    @Override
    public DeptTreeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_dept_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeptTreeAdapter.ViewHolder holder, int position) {
        DeptTree deptTree = deptTreeList.get(position);
        if(!deptTreeList.get(position).child.isEmpty()){
            holder.tag.setText(deptTreeList.get(position).tag?"-":"+");
        }else{
            holder.tag.setText("    ");
        }
        holder.node.setText(deptTree.name);
        holder.itemView.setOnClickListener(view->{
            int pos = holder.getLayoutPosition();
            pin = deptTreeList.get(pos);
            pin .tag = !pin.tag;
            if (pin.tag){
                expand(pos);
            }else{
                fold(pos);
            }
            notifyDataSetChanged();
        });
    }

    private void fold(int pos) {
        Stack<DeptTree> stack = new Stack<>();
        stack.push(deptTreeList.get(pos));
        int count=0;
        while (!stack.isEmpty()){
            for (DeptTree deptTree :stack.pop().child) {
                if(deptTree.tag){stack.push(deptTree);}
                count++;
            }
        }
        for(int i=0;i<count;i++){
            deptTreeList.remove(pos+1);
        }
    }

    private void expand(int pos) {
        deptTreeList.addAll(pos+1, deptTreeList.get(pos).child);
    }

    @Override
    public int getItemCount() {
        return deptTreeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView node;
        TextView tag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.adapter_item_dept_tag);
            node = itemView.findViewById(R.id.adapter_item_dept_name);
        }

    }
}
