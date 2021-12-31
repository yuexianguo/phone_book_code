package com.phone.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phone.book.R;
import com.phone.book.bean.Tree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/12/31
 */
public class DeptTreeAdapter extends RecyclerView.Adapter<DeptTreeAdapter.ViewHolder> {
    ArrayList<Tree> treeList=new ArrayList<>();
    Context context ;
    Tree pin;
    public DeptTreeAdapter(Context context, Tree tree) {
        this.context  =  context;
        treeList.addAll(tree.child);
    }

    @NonNull
    @Override
    public DeptTreeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_dept_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeptTreeAdapter.ViewHolder holder, int position) {
        Tree tree = treeList.get(position);
        if(!treeList.get(position).child.isEmpty()){
            holder.tag.setText(treeList.get(position).tag?"-":"+");
        }else{
            holder.tag.setText("    ");
        }
        holder.node.setText(tree.node);
        holder.itemView.setOnClickListener(view->{
            int pos = holder.getLayoutPosition();
            pin = treeList.get(pos);
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
        Stack<Tree> stack = new Stack<>();
        stack.push(treeList.get(pos));
        int count=0;
        while (!stack.isEmpty()){
            for (Tree tree:stack.pop().child) {
                if(tree.tag){stack.push(tree);}
                count++;
            }
        }
        for(int i=0;i<count;i++){
            treeList.remove(pos+1);
        }
    }

    private void expand(int pos) {
        treeList.addAll(pos+1,treeList.get(pos).child);
    }

    @Override
    public int getItemCount() {
        return treeList.size();
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
