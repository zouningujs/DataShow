package com.ujs.datashow2.pullrefresh;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.ujs.datashow2.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 两级下拉listview
 * @author zouning
 *
 */
public class MyListViewTwo extends ListView implements OnScrollListener {
    private static final String tag = "MyListView";
    /** 下拉显示一级菜单,还没有达到显示一级菜单的长度 */
    private final static int PULL_TO_LOAD_MENU = 1;
    /** 下拉长度超过菜单长度,显示菜单,下拉一级菜单长度够了,但还不到刷新的级别,同 RELEASE_TO_LOAD_MENU */
    private final static int PULL_To_REFRESH = 2;
    private final static int RELEASE_TO_LOAD_MENU = PULL_To_REFRESH;
    /** 下拉菜单完成,页面停在一级菜单界面 */
    private final static int LOAD_MENU_DONE = 3;
    /** 超过整个头部,放手刷新 */
    private final static int RELEASE_To_REFRESH = 4;
    /** 正在刷新 */
    private final static int REFRESHING = 5;
    /** 刷新完成 */
    private final static int DONE = 6;

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
    private int headContentWidth;
    private int headContentHeight;
    private int startY;
    private int firstItemIndex;
    private int state;
    private boolean isBack;
    private OnRefreshListener refreshListener;
    private boolean isRefreshable;

    // 一级下拉
    private int headFirstContentWidth;
    private int headFirstContentHeight;
    private RelativeLayout headFirstView;

    private Activity mContext = null;

    int i = 1;

    public MyListViewTwo(Context context) {
        super(context);
        if (context instanceof Activity) {
            mContext = (Activity) context;
        } else {
            throw new IllegalArgumentException("context not Activity");
        }
        init(context);
    }

    public MyListViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof Activity) {
            mContext = (Activity) context;
        } else {
            throw new IllegalArgumentException("context not Activity");
        }
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
        headContentWidth = headView.getMeasuredWidth();
        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();
        Log.v("@@@@@@", "width:" + headContentWidth + " height:"
                + headContentHeight);
        addHeaderView(headView, null, false);
        setOnScrollListener(this);

        // 第一级下拉菜单
        headFirstView = (RelativeLayout) headView
                .findViewById(R.id.head_message_filter);
        headFirstContentWidth = headFirstView.getMeasuredWidth();
        headFirstContentHeight = headFirstView.getMeasuredHeight();
        Log.v(tag, "headFirstContentWidth:" + headFirstContentWidth
                + " headFirstContentHeight:" + headFirstContentHeight);

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
                // 步骤1
                if (firstItemIndex == 0 && !isRecored) {
                    isRecored = true;
                    startY = (int) event.getY();
                    Log.v("@@@@@@", "ACTION_DOWN 这是第  " + i++ + "步" + 1);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 步骤2-3
                if (state != REFRESHING) {
                    if (state == DONE) {
                    }
                    if (state == PULL_TO_LOAD_MENU) {
                        // 下拉更新,长度还不够就不下拉了
                        state = DONE;
                        Log.v("@@@@@@",
                                "ACTION_UP PULL_TO_LOAD_MENU and changeHeaderViewByState()"
                                        + " 这是第  " + i++ + "步前" + 002);
                        changeHeaderViewByState();
                        Log.v("@@@@@@",
                                "ACTION_UP PULL_TO_LOAD_MENU and changeHeaderViewByState() "
                                        + "这是第  " + i++ + "步后" + 002);
                    }
                    if (state == PULL_To_REFRESH) {
                        // 下拉更新,长度还不够就不下拉了
                        state = LOAD_MENU_DONE;
                        Log.v("@@@@@@",
                                "ACTION_UP PULL_To_REFRESH and changeHeaderViewByState()"
                                        + " 这是第  " + i++ + "步前" + 2);
                        changeHeaderViewByState();
                        Log.v("@@@@@@",
                                "ACTION_UP PULL_To_REFRESH and changeHeaderViewByState() "
                                        + "这是第  " + i++ + "步后" + 2);
                    }
                    if (state == RELEASE_To_REFRESH) {
                        state = REFRESHING;
                        Log.v("@@@@@@",
                                "ACTION_UP RELEASE_To_REFRESH changeHeaderViewByState() "
                                        + "这是第  " + i++ + "步" + 3);
                        changeHeaderViewByState();
                        onRefresh();
                        Log.v("@@@@@@",
                                "ACTION_UP RELEASE_To_REFRESH changeHeaderViewByState()"
                                        + " 这是第  " + i++ + "步" + 3);
                    }
                }
                isRecored = false;
                isBack = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 步骤4-11
                int tempY = (int) event.getY();
                if (!isRecored && firstItemIndex == 0) {
                    isRecored = true;
                    startY = tempY;
                    Log.v("@@@@@@", "ACTION_MOVE 这是第  " + i++ + "步" + 4);
                }
                if (state != REFRESHING && isRecored) {
                    //列出各状态之间的转换,效率更高~箭头反转没有延迟
                    // add1 第一层菜单,其前一状态和后以状态
                    int heightDiff = tempY - startY;
                    int height = heightDiff / RATIO;

                    if (state == PULL_TO_LOAD_MENU) {
                        setSelection(0);
                        if (height >= headFirstContentHeight) {
                            state = PULL_To_REFRESH;
                            Log.v("@@@@@@", "changeHeaderViewByState "
                                    + "这是第  " + i++ + "步前" + 7);
                            changeHeaderViewByState();
                            Log.v("@@@@@@", "changeHeaderViewByState "
                                    + "这是第  " + i++ + "步后" + 7);
                        } else if (heightDiff <= 0) {
                            state = DONE;
                            changeHeaderViewByState();
                            Log.v("@@@@@@",
                                    "ACTION_MOVE changeHeaderViewByState PULL_To_REFRESH 2"
                                            + " 这是第  " + i++ + "步" + 8);
                        }
                    }

                    // add3
                    if (state == LOAD_MENU_DONE) {
                        if (height > headFirstContentHeight) {
                            state = PULL_To_REFRESH;
                            Log.v("@@@@@@",
                                    "ACTION_MOVE DONE changeHeaderViewByState "
                                            + "这是第  " + i++ + "步前" + 9);
                            changeHeaderViewByState();
                            Log.v("@@@@@@",
                                    "ACTION_MOVE DONE changeHeaderViewByState "
                                            + "这是第  " + i++ + "步后" + 9);
                        }
                    }
                    // add2
                    if (state == RELEASE_TO_LOAD_MENU) {
                        setSelection(0);
                        if ((height < headFirstContentHeight) && heightDiff > 0) {
                            state = PULL_TO_LOAD_MENU;
                            changeHeaderViewByState();
                            Log.v("@@@@@@", "changeHeaderViewByState() 这是第  "
                                    + i++ + "步" + 5);
                        } else if (heightDiff <= 0) {
                            state = DONE;
                            changeHeaderViewByState();
                            Log.v("@@@@@@",
                                    "ACTION_MOVE RELEASE_To_REFRESH 2  changeHeaderViewByState "
                                            + "这是第  " + i++ + "步" + 6);
                        }
                    }
                    // 4
                    if (state == RELEASE_To_REFRESH) {
                        setSelection(0);
                        if ((height < headContentHeight)
                                && height > headFirstContentHeight) {
                            state = PULL_To_REFRESH;
                            changeHeaderViewByState();
                            Log.v("@@@@@@", "changeHeaderViewByState() 这是第  "
                                    + i++ + "步" + 5);
                        } else if (heightDiff <= 0) {
                            state = DONE;
                            changeHeaderViewByState();
                            Log.v("@@@@@@",
                                    "ACTION_MOVE RELEASE_To_REFRESH 2  changeHeaderViewByState "
                                            + "这是第  " + i++ + "步" + 6);
                        }
                    }
                    // 2
                    if (state == PULL_To_REFRESH) {
                        setSelection(0);
                        if (height >= headContentHeight) {
                            state = RELEASE_To_REFRESH;
                            isBack = true;
                            Log.v("@@@@@@", "changeHeaderViewByState "
                                    + "这是第  " + i++ + "步前" + 7);
                            changeHeaderViewByState();
                            Log.v("@@@@@@", "changeHeaderViewByState "
                                    + "这是第  " + i++ + "步后" + 7);
                        } else if (height > headFirstContentHeight) {
                            state = RELEASE_TO_LOAD_MENU;
                            changeHeaderViewByState();
                            Log.v("@@@@@@",
                                    "ACTION_MOVE changeHeaderViewByState PULL_To_REFRESH 2"
                                            + " 这是第  " + i++ + "步" + 8);
                        }
                    }
                    if (state == DONE) {
                        if (heightDiff > 0) {
                            state = PULL_TO_LOAD_MENU;
                            Log.v("@@@@@@",
                                    "ACTION_MOVE DONE changeHeaderViewByState "
                                            + "这是第  " + i++ + "步前" + 9);
                            changeHeaderViewByState();
                            Log.v("@@@@@@",
                                    "ACTION_MOVE DONE changeHeaderViewByState "
                                            + "这是第  " + i++ + "步后" + 9);
                        }
                    }

                    // 方案2:箭头的反转延迟很大~pass
                    /*
                     * if(heightDiff <= 0){ state = DONE; }else { if (0 < height
                     * && height < headFirstContentHeight) { state =
                     * PULL_TO_LOAD_MENU; }else if ( headFirstContentHeight <=
                     * height && height < headContentHeight) { state =
                     * RELEASE_TO_LOAD_MENU; }else if (height >=
                     * headContentHeight) { state = RELEASE_To_REFRESH; isBack =
                     * true; } } setSelection(0); changeHeaderViewByState();
                     */

                    if (state == PULL_To_REFRESH || state == PULL_TO_LOAD_MENU) {
                        headView.setPadding(0, -1 * headContentHeight + height,
                                0, 0);
                        Log.v("@@@@@@", -1 * headContentHeight + height
                                + "ACTION_MOVE PULL_To_REFRESH 3  这是第  " + i++
                                + "步" + 10);
                    }
                    if (state == RELEASE_To_REFRESH
                            || state == PULL_TO_LOAD_MENU) {
                        headView.setPadding(0, height - headContentHeight, 0, 0);
                        Log.v("@@@@@@", "ACTION_MOVE PULL_To_REFRESH 4 这是第  "
                                + i++ + "步" + 11);
                    }
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void changeHeaderViewByState() {
        // 12-13
        switch (state) {
        case RELEASE_To_REFRESH:
            // 请释放 刷新
            arrowImageView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            tipsTextview.setVisibility(View.VISIBLE);
            lastUpdatedTextView.setVisibility(View.VISIBLE);
            arrowImageView.clearAnimation();
            arrowImageView.startAnimation(animation);
            tipsTextview.setText("请释放 刷新");
            Log.v("@@@@@@", "RELEASE_To_REFRESH 这是第  " + i++ + "步" + 12
                    + "请释放 刷新");
            break;
        case PULL_To_REFRESH:
            // 下拉刷新,下拉中
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
            Log.v("@@@@@@", "PULL_To_REFRESH 这是第  " + i++ + "步" + 13
                    + "  changeHeaderViewByState()");
            break;
        case REFRESHING:
            // 正在加载中
            headView.setPadding(0, 0, 0, 0);
            progressBar.setVisibility(View.VISIBLE);
            arrowImageView.clearAnimation();
            arrowImageView.setVisibility(View.GONE);
            tipsTextview.setText("正在加载中 ...");
            lastUpdatedTextView.setVisibility(View.VISIBLE);
            Log.v("@@@@@@", "REFRESHING 这是第  " + i++ + "步"
                    + "正在加载中 ...REFRESHING");
            break;
        case DONE:
            // 加载完毕
            headView.setPadding(0, -1 * headContentHeight, 0, 0);
            progressBar.setVisibility(View.GONE);
            arrowImageView.clearAnimation();
            arrowImageView.setImageResource(R.drawable.arrow);
            tipsTextview.setText("已经加载完毕- DONE ");
            lastUpdatedTextView.setVisibility(View.VISIBLE);
            Log.v("@@@@@@", "DONE 这是第  " + i++ + "步" + "已经加载完毕- DONE ");
            break;
        case PULL_TO_LOAD_MENU:
            break;
        case LOAD_MENU_DONE:
            headView.setPadding(0, headFirstContentHeight - headContentHeight,
                    0, 0);
            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    if (state == LOAD_MENU_DONE) {
                        mContext.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                Log.d(tag, "hideFirstMenuTask run");
                                state = DONE;
                                changeHeaderViewByState();

                            }
                        });
                    }
                }
            }, 5000);

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
        Log.v("@@@@@@", "onRefreshComplete() 被调用。。。");
    }

    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
            Log.v("@@@@@@", "onRefresh被调用，这是第  " + i++ + "步");
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