package example.com.githubissues.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.githubissues.R;
import example.com.githubissues.entities.Issue;

/**
 * Created by shahbaz on 14/05/17.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {

    private final LayoutInflater mInflator;
    private List<Issue> mIssueList;

    public DataAdapter(LayoutInflater inflator) {
        mInflator = inflator;
        mIssueList = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mInflator.inflate(R.layout.issue_row, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.mTextViewTitle.setText(mIssueList.get(position).getTitle());
        String id = mIssueList.get(position).getNumber().toString();
        holder.mTextViewId.setText(id);
        holder.mTextViewCreator.setText(mIssueList.get(position).getUser().getLogin());
    }

    @Override
    public int getItemCount() {
        return mIssueList.size();
    }

    public void addIssues(List<Issue> issues) {
        mIssueList.clear();
        mIssueList.addAll(issues);
        notifyDataSetChanged();
    }

    public void clearIssues() {
        mIssueList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView mTextViewTitle;
        TextView mTextViewId;
        TextView mTextViewCreator;

        public Holder(View v) {
            super(v);
            mTextViewTitle = (TextView) v.findViewById(R.id.title);
            mTextViewId = (TextView) v.findViewById(R.id.issue_id);
            mTextViewCreator = (TextView) v.findViewById(R.id.creator_name);
        }
    }
}