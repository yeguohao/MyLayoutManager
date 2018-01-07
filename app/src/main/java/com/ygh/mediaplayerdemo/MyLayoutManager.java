package com.ygh.mediaplayerdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MyLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int leftOffset = getPaddingLeft();
        int parentRight = getWidth() - getPaddingRight();
        for (int i = 0; i < state.getItemCount(); i++) {
            leftOffset += addChildView(leftOffset, i, recycler, false);

            if (leftOffset >= parentRight) break;
        }
    }

    private int addChildView(int left, int pos, RecyclerView.Recycler recycler, boolean rebase) {
        View child = recycler.getViewForPosition(pos);
        measureChild(child, 0, 0);

        int w = getDecoratedMeasuredWidth(child);
        int t = getPaddingTop();
        int r = left + w;
        int b = getHeight() - getPaddingBottom();

        if (rebase) {
            addView(child, 0);
        } else {
            addView(child);
        }
        layoutDecorated(child, left, t, r, b);
        return w;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    private int childOffset;

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dx == 0 || getChildCount() == 0) {
            return 0;
        }
        int realOffset = adjustOffset(dx, state);
        childOffset += realOffset;
        fill(recycler, realOffset);
        offsetChildrenHorizontal(-realOffset);
        return realOffset;
    }

    private int adjustOffset(int dx, RecyclerView.State state) {
        int totalWidth = state.getItemCount() * getChildAt(0).getWidth() - getWidth();
        if (totalWidth < 0) totalWidth = 0;

        int realOffset = dx;
        if (childOffset + realOffset < 0) {
            realOffset = -childOffset;
        } else if (childOffset + realOffset > totalWidth) {
            realOffset = totalWidth - childOffset;
        }
        return realOffset;
    }

    private void fill(RecyclerView.Recycler recycler, int dx) {
        View firstChild = getChildAt(0);
        View lastChild = getChildAt(getChildCount() - 1);

        if (firstChild.getRight() - dx < 0) {
            removeAndRecycleView(firstChild, recycler);
        } else if (lastChild.getLeft() - dx > getWidth()) {
            removeAndRecycleView(lastChild, recycler);
        }

        if (getChildCount() < 3) {
            if (dx > 0) {
                addChildView(lastChild.getRight(), getPosition(lastChild) + 1, recycler, false);
            } else if (dx < 0) {
                addChildView(firstChild.getLeft() - firstChild.getWidth(), getPosition(firstChild) - 1, recycler, true);
            }
        }
    }
}