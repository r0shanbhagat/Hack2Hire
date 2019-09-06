package com.example.myapplication.base;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    public List<?> itemArrayList;
    private OnLoadMoreListener onLoadMoreListener;
    private OnItemClickListener onItemClickListener;


    /**
     * Instantiates a new Abstract recycler adapter.
     *
     * @param itemArrayList the item array list
     */
    public BaseRecyclerAdapter(List<?> itemArrayList) {
        this.itemArrayList = itemArrayList;

    }

    @Override
    public T onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return setViewHolder(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        onBindData(holder, itemArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return null != itemArrayList && !itemArrayList.isEmpty() ? itemArrayList.size() : 0;
    }


    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Add items.
     *
     * @param itemAdd the saved card itemz
     */
    public void addItems(List<?> itemAdd) {
        itemArrayList = itemAdd;
        this.notifyDataSetChanged();
    }

    /**
     * Sets on load more listener.
     *
     * @param recyclerView        the recycler view
     * @param mOnLoadMoreListener the m on load more listener
     */
    public void setOnLoadMoreListener(RecyclerView recyclerView, OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    // isLoading = true;
                }

            }
        });
    }

    /**
     * Gets item.
     *
     * @param position the position
     * @return the item
     */
    public Object getItem(int position) {
        return itemArrayList.get(position);
    }

    /**
     * Gets Adapter ItemList.
     *
     * @return the item
     */
    public List getItemList() {
        return itemArrayList;
    }


    /**
     * Sets view holder.
     *
     * @param parent   the parent
     * @param viewType
     * @return the view holder
     */
    public abstract T setViewHolder(ViewGroup parent, int viewType);

    /**
     * On bind data.
     *
     * @param viewHolder the t
     * @param itemVal    the val
     */
    public abstract void onBindData(T viewHolder, Object itemVal);


    /**
     * The interface On load more listener.
     */
    public static abstract class OnLoadMoreListener {

        protected void onLoadMore() {
        }

        protected void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        }


    }

}
