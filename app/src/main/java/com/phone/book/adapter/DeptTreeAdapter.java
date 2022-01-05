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
    ArrayList<DeptTree> treeList = new ArrayList<>();
    ArrayList<DeptTree> originTreeList = new ArrayList<>();
    Context context;
    DeptTree pin;
    private int preSelectPos = 0;

    public DeptTreeAdapter(Context context, ArrayList<DeptTree> list) {
        this.context = context;
        treeList.clear();
        treeList.addAll(list);
        originTreeList.clear();
        originTreeList.addAll(list);
    }

    public void setNewList(ArrayList<DeptTree> list) {
        treeList.clear();
        treeList.addAll(list);
        originTreeList.clear();
        originTreeList.addAll(list);
    }

    @NonNull
    @Override
    public DeptTreeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_dept_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeptTreeAdapter.ViewHolder holder, int position) {
        if (preSelectPos == position) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.item_select_bg));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.transparent));
        }
        DeptTree tree = treeList.get(position);
        if (!tree.child.isEmpty()) {
            if (tree.level == 1) {
                holder.tag.setText(tree.tag ? "-" : "+");
            } else {
                String expandEmpty = getExpandEmpty(tree.level);
                holder.tag.setText(tree.tag ? expandEmpty + "-" : expandEmpty + "+");
            }
        } else {
            if (tree.level == 1) {
                String expandEmpty = getExpandEmpty(tree.level);
                holder.tag.setText(expandEmpty);
            } else {
                String expandEmpty = getExpandEmpty(tree.level) + "  ";
                holder.tag.setText(expandEmpty);
            }

        }
        holder.node.setText(tree.name);
        holder.itemView.setOnClickListener(view -> {
            int pos = holder.getLayoutPosition();
            pin = treeList.get(pos);
            pin.tag = !pin.tag;
            preSelectPos = pos;
            if (pin.tag) {
                expand(pos);
            } else {
                fold(pos);
            }
            notifyDataSetChanged();
        });
    }

    public DeptTree getCurrentDept() {
        return treeList.size() > preSelectPos ? treeList.get(preSelectPos) : null;
    }

    private String getExpandEmpty(Integer level) {
        String baseEmpty = "    ";
        StringBuilder target = new StringBuilder();
        if (level > 1) {
            for (int i = 0; i < level - 1; i++) {
                target.append(baseEmpty);
            }
        } else {
            target.append("  ");
        }

        return target.toString();
    }

    private void fold(int pos) {
        Stack<DeptTree> stack = new Stack<>();
        DeptTree deptTree = treeList.get(pos);

        stack.push(deptTree);
        int count = 0;
        while (!stack.isEmpty()) {
            for (DeptTree tree : stack.pop().child) {
                if (tree.tag) {
                    stack.push(tree);
                }
                count++;
            }
        }

        if (treeList.size() > pos + 1) {
            for (int i = 0; i < count; i++) {
                treeList.remove(pos + 1);
            }
        }

    }

    private void expand(int pos) {
        for (DeptTree deptTree : treeList.get(pos).child) {
            deptTree.tag = false;
        }
        treeList.addAll(pos + 1, treeList.get(pos).child);
    }

    @Override
    public int getItemCount() {
        return treeList.size();
    }

    public void setOriginDeptPosition() {
        preSelectPos = 0;
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
