package edu.wgu.jameswatsonabm2.Assessment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import edu.wgu.jameswatsonabm2.Model.Data;
import edu.wgu.jameswatsonabm2.R;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Assessment> listAssessments;

    public AssessmentAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (listAssessments != null) {
            Assessment current = listAssessments.get(position);
            holder.textTitle.setText(current.getTitle());
            SimpleDateFormat formatDate = new SimpleDateFormat("MMM dd", Locale.getDefault());
            if (current.getStartDate() != null) {
                holder.textStartDate.setText(formatDate.format(current.getStartDate()));
            }
            if (current.getEndDate() != null) {
                holder.textEndDate.setText(formatDate.format(current.getEndDate()));
            }
            if (current.getStartDate() == null || current.getEndDate() == null) {
                holder.textHyphen.setText("");
            }
            String typeString;
            String typeStringLong;
            if (current.getType() == 0) {
                typeString = "";
                typeStringLong = "";
            } else if (current.getType() == 1) {
                typeString = "O";
                typeStringLong = "OBJECTIVE";
            } else {
                typeString = "P";
                typeStringLong = "PERFORMANCE";
            }
            holder.textLabelVar.setText(typeString);
            holder.textLabel.setText(typeStringLong);
        }
    }

    public void setData(List<Assessment> assessments) {
        listAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (listAssessments != null) {
            return listAssessments.size();
        } else {
            return 0;
        }
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final TextView textStartDate;
        private final TextView textEndDate;
        private final TextView textLabelVar;
        private final TextView textLabel;
        private final TextView textHyphen;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textStartDate = itemView.findViewById(R.id.textStartDate);
            textEndDate = itemView.findViewById(R.id.textEndDate);
            textLabelVar = itemView.findViewById(R.id.textLabelVar);
            textLabel = itemView.findViewById(R.id.textLabel);
            textHyphen = itemView.findViewById(R.id.textHyphen);
            itemView.setOnClickListener(view -> {
                Assessment current = listAssessments.get(getAdapterPosition());
                Data.setCurrentAssessment(current);
                Data.setCurrentCourse(Data.getCourse(current.getCourseId()));
                Data.setIsTermAssigned(true);
                System.out.println("starting assessmentmain");
                context.startActivity(new Intent(context, AssessmentDetailsActivity.class));
            });
        }
    }
}