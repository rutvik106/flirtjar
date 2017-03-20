package instagram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import instagram.apimodels.RecentMedia;
import instagram.viewholder.VHInstaPhoto;

/**
 * Created by rutvik on 3/20/2017 at 4:28 PM.
 */

public class InstaPhotosAdapter extends RecyclerView.Adapter
{

    final Context context;

    final List<RecentMedia.DataBean.ImagesBean> itemList;

    public InstaPhotosAdapter(final Context context)
    {
        this.context = context;
        itemList = new ArrayList<>();
    }

    public void addItem(RecentMedia.DataBean.ImagesBean item)
    {
        itemList.add(item);
        notifyItemInserted(itemList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return VHInstaPhoto.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        VHInstaPhoto.bind((VHInstaPhoto) holder, itemList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

}
