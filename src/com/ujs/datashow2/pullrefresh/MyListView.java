package com.ujs.datashow2.pullrefresh;

import java.util.Date;

import com.ujs.datashow2.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 带刷新的listview
 * 
 * @author zouning
 * 
 */
public class MyListView extends ListView implements OnScrollListener {

    /** 下拉过程中,下拉长度没有超过头长度 */
    private final static int PULL_To_REFRESH = 1;
    /** 超过整个头部,放手刷新 */
    private final static int RELEASE_To_REFRESH = 2;
    /** 正在刷新 */
    private final static int REFRESHING = 3;
    /** 刷新完成 */
    private final static int DONE = 4;

    private final static int RATIO = 3;
    private LayoutInflater inflater;
    private LinearLayout headView;
    private TextView tipsTextview;
    private TextView lastUpdatedTextView;
    private ImageView arrowImageView;
    private ProgressBar progressBar;

    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;
    private boolean isRecored;
    private int headContentHeight;
    private int startY;
    private int firstItemIndex;
    private int state;
    private boolean isBack;
    private OnRefreshListener refreshListener;
    private boolean isRefreshable;

    public MyListView(Context context) {
        super(context);
        init(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setCacheColorHint(context.getResources().getColor(R.color.transparent));
        inflater = LayoutInflater.from(context);
        headView = (LinearLayout) inflater.inflate(R.layout.head, null);
        arrowImageView = (ImageView) headView
                .findViewById(R.id.head_arrowImageView);
        arrowImageView.setMinimumWidth(70);
        arrowImageView.setMinimumHeight(50);
        progressBar = (ProgressBar) headView
                .findViewById(R.id.head_progressBar);
        tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView) headView
                .findViewById(R.id.head_lastUpdatedTextView);

        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();
        addHeaderView(headView, null, false);
        setOnScrollListener(this);

        animation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);

        state = DONE;
        isRefreshable = false;
    }

    public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
            int arg3) {
        firstItemIndex = firstVisiableItem;
    }

    public void onScrollStateChanged(AbsListView arg0, int arg1) {
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isRefreshable) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstItemIndex == 0 && !isRecored) {
                    isRecored = true;
                    startY = (int) event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                // 各种状态下松开手的少做
                if (state != REFRESHING) {
                    if (state == DONE) {
                    }
                    if (state == PULL_To_REFRESH) {
                        state = DONE;
                        changeHeaderViewByState();
                    }
                    if (state == RELEASE_To_REFRESH) {
                        state = REFRESHING;
                        changeHeaderViewByState();
                        onRefresh();
                    }
                }
                isRecored = false;
                isBack = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) event.getY();
                if (!isRecored && firstItemIndex == 0) {
                    isRecored = true;
                    startY = tempY;
                }
                // 各个状态的相邻状态变更,箭头变更无延迟
                if (state != REFRESHING && isRecored) {
                    int heightDiff = tempY - startY;
                    int height = heightDiff / RATIO;
                    if (state == RELEASE_To_REFRESH) {
                        setSelection(0);
                        if ((height < headContentHeight) && heightDiff > 0) {
                            state = PULL_To_REFRESH;
                            changeHeaderViewByState();
                        } else if (tempY - startY <= 0) {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                    }
                    if (state == PULL_To_REFRESH) {
                        setSelection(0);
                        if (height >= headContentHeight) {
                            state = RELEASE_To_REFRESH;
                            isBack = true;
                            changeHeaderViewByState();
                        } else if (tempY - startY <= 0) {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                    }
                    if (state == DONE) {
                        if (tempY - startY > 0) {
                            state = PULL_To_REFRESH;
                            changeHeaderViewByState();
                        }
                    }
                    if (state == PULL_To_REFRESH) {
                        headView.setPadding(0, -1 * headContentHeight + height,
                                0, 0);
                    }
                    if (state == RELEASE_To_REFRESH) {
                        headView.setPadding(0, height - headContentHeight, 0, 0);
                    }
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void changeHeaderViewByState() {
        switch (state) {
        case RELEASE_To_REFRESH:
            arrowImageView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            tipsTextview.setVisibility(View.VISIBLE);
            lastUpdatedTextView.setVisibility(View.VISIBLE);
            arrowImageView.clearAnimation();
            arrowImageView.startAnimation(animation);
            tipsTextview.setText("请释放 刷新");
            break;
        case PULL_To_REFRESH:
            progressBar.setVisibility(View.GONE);
            tipsTextview.setVisibility(View.VISIBLE);
            lastUpdatedTextView.setVisibility(View.VISIBLE);
            arrowImageView.clearAnimation();
            arrowImageView.setVisibility(View.VISIBLE);
            if (isBack) {
                isBack = false;
                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(reverseAnimation);
                tipsTextview.setText("isBack  is true ！！！");
            } else {
                tipsTextview.setText("isBack  is false ！！！");
            }
            break;
        case REFRESHING:
            headView.setPadding(0, 0, 0, 0);
            progressBar.setVisibility(View.VISIBLE);
            arrowImageView.clearAnimation();
            arrowImageView.setVisibility(View.GONE);
            tipsTextview.setText("正在加载中 ...REFRESHING");
            lastUpdatedTextView.setVisibility(View.VISIBLE);
            break;
        case DONE:
            headView.setPadding(0, -1 * headContentHeight, 0, 0);
            progressBar.setVisibility(View.GONE);
            arrowImageView.clearAnimation();
            arrowImageView.setImageResource(R.drawable.arrow);
            tipsTextview.setText("已经加载完毕- DONE ");
            lastUpdatedTextView.setVisibility(View.VISIBLE);
            break;
        }
    }

    public void setonRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        isRefreshable = true;
    }

    public interface OnRefreshListener {
        public void onRefresh();
    }

    public void onRefreshComplete() {
        state = DONE;
        lastUpdatedTextView.setText("已加载完成: " + new Date().toLocaleString());
        changeHeaderViewByState();
    }

    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public void setAdapter(BaseAdapter adapter) {
        lastUpdatedTextView.setText("this is in MyListView:"
                + new Date().toLocaleString());
        super.setAdapter(adapter);
    }
}