package edu.wgu.jameswatsonabm2.Course;

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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Course> listCourses;

    public CourseAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return listCourses.get(position).getStatus();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 3:
                itemView = layoutInflater.inflate(R.layout.card_view_completed, parent, false);
                break;
            case 4:
                itemView = layoutInflater.inflate(R.layout.card_view_dropped, parent, false);
                break;
            default:
                itemView = layoutInflater.inflate(R.layout.card_view, parent, false);
        }
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (listCourses != null) {
            Course current = listCourses.get(position);
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
            String countAssessments = Long.toString(Data.getCourseAssessments(current).size());
            holder.textLabelVar.setText(countAssessments);
            holder.textLabel.setText("ASSESSMENTS");
        }
    }

    public void setData(List<Course> courses) {
        listCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (listCourses != null) {
            return listCourses.size();
        } else {
            return 0;
        }
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final TextView textStartDate;
        private final TextView textEndDate;
        private final TextView textLabelVar;
        private final TextView textLabel;
        private final TextView textHyphen;

        private CourseViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textStartDate = itemView.findViewById(R.id.textStartDate);
            textEndDate = itemView.findViewById(R.id.textEndDate);
            textLabelVar = itemView.findViewById(R.id.textLabelVar);
            textLabel = itemView.findViewById(R.id.textLabel);
            textHyphen = itemView.findViewById(R.id.textHyphen);
            itemView.setOnClickListener(view -> {
                Course current = listCourses.get(getAdapterPosition());
                Data.setCurrentCourse(current);
                Data.setCurrentTerm(Data.getTerm(current.getTermId()));
                Data.setIsCourseAssigned(true);
                context.startActivity(new Intent(context, CourseDetailsActivity.class));
            });
        }
    }
}