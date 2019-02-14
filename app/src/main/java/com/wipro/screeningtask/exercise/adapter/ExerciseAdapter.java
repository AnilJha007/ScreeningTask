package com.wipro.screeningtask.exercise.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wipro.screeningtask.R;
import com.wipro.screeningtask.databinding.ItemExerciseListBinding;
import com.wipro.screeningtask.exercise.pojo.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private List<Exercise> exerciseList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        ItemExerciseListBinding itemExerciseListBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_exercise_list, viewGroup, false);
        return new ViewHolder(itemExerciseListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindDataWithView(position);
    }

    @Override
    public int getItemCount() {
        return exerciseList == null ? 0 : exerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemExerciseListBinding itemExerciseListBinding;

        ViewHolder(ItemExerciseListBinding itemExerciseListBinding) {
            super(itemExerciseListBinding.getRoot());
            this.itemExerciseListBinding = itemExerciseListBinding;
        }

        private void bindDataWithView(int position) {
            Exercise exercise = exerciseList.get(position);
            itemExerciseListBinding.setExercise(exercise);
        }
    }


    // setting data to adapter
    public void setData(List<Exercise> newInfoList) {

        if (exerciseList != null)
            exerciseList.clear();
        else
            exerciseList = new ArrayList<>();

        exerciseList.addAll(newInfoList);
        notifyDataSetChanged();
    }
}
