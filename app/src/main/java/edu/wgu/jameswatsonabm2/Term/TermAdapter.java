package edu.wgu.jameswatsonabm2.Term;

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

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Term> listTerms;

    public TermAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (listTerms != null) {
            Term current = listTerms.get(position);
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
            String countCourses = Integer.toString(Data.getTermCourses(current).size());
            holder.textLabelVar.setText(countCourses);
            holder.textLabel.setText("COURSES");
        }
    }

    public void setData(List<Term> terms) {
        listTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (listTerms != null) {
            return listTerms.size();
        } else {
            return 0;
        }
    }

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final TextView textStartDate;
        private final TextView textEndDate;
        private final TextView textLabelVar;
        private final TextView textLabel;
        private final TextView textHyphen;

        private TermViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textStartDate = itemView.findViewById(R.id.textStartDate);
            textEndDate = itemView.findViewById(R.id.textEndDate);
            textLabelVar = itemView.findViewById(R.id.textLabelVar);
            textLabel = itemView.findViewById(R.id.textLabel);
            textHyphen = itemView.findViewById(R.id.textHyphen);
            itemView.setOnClickListener(view -> {
                Term current = listTerms.get(getAdapterPosition());
                Data.setCurrentTerm(current);
                Data.setIsTermAssigned(true);
                context.startActivity(new Intent(context, TermDetailsActivity.class));
            });
        }
    }
}