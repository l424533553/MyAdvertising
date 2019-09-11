//package com.axecom.smartweight.ui.uiutils;
//
//import android.support.v7.widget.RecyclerView;
//import com.advertising.screen.myadvertising.R;
//import com.luofx.mvp.base.view.BaseActivity;
//
///**
// * 作者：罗发新
// * 时间：2019/9/4 0004    星期三
// * 邮件：424533553@qq.com
// * 说明：
// */
//
//
//    public class RvHuaDongActivity extends BaseActivity {
//        @BindView(R.id.rv_rvhuadong)
//        RecyclerView mRv;
//        private LinearLayoutManager mManager;
//
//        //此处等于setContentView
//        @Override
//        protected int getLayouRes() {
//            return R.layout.activity_rv_hua_dong;
//        }
//
//        //此处等于onCreate
//        @Override
//        protected void initData() {
//            List<String> list = new ArrayList<>();
//            for (int i = 0; i < 100; i++) {
//                list.add("position" + i);
//            }
//            mManager = new LinearLayoutManager(this);
//            mRv.setLayoutManager(mManager);
//            mRv.setAdapter(new MyAdapter(list));
//        }
//
//        @Override
//        protected void setListener() {
//        }
//
//        @OnClick({R.id.tv_rvhuadong_GuanXing_1, R.id.tv_rvhuadong_GuanXing_2, R.id.tv_rvhuadong_GuanXing_3,
//                R.id.tv_rvhuadong_ShanXian_1, R.id.tv_rvhuadong_ShanXian_2, R.id.tv_rvhuadong_ShanXian_3})
//        public void onViewClicked(View view) {
//            switch (view.getId()) {
//                case R.id.tv_rvhuadong_GuanXing_1:
//                    int position1 = (int) (Math.random() * 100);
//                    Toast.makeText(this, "滑到：" + position1, Toast.LENGTH_SHORT).show();
//                    LinearSmoothScroller s1 = new TopSmoothScroller(getActivity());
//                    s1.setTargetPosition(position1);
//                    mManager.startSmoothScroll(s1);
//                    break;
//                case R.id.tv_rvhuadong_GuanXing_2:
//                    LinearSmoothScroller s2 = new TopSmoothScroller(getActivity());
//                    s2.setTargetPosition(20);
//                    mManager.startSmoothScroll(s2);
//                    break;
//                case R.id.tv_rvhuadong_GuanXing_3:
//                    LinearSmoothScroller s3 = new TopSmoothScroller(getActivity());
//                    s3.setTargetPosition(99);
//                    mManager.startSmoothScroll(s3);
//                    break;
//                case R.id.tv_rvhuadong_ShanXian_1:
//                    int position2 = (int) (Math.random() * 100);
//                    Toast.makeText(this, "闪到：" + position2, Toast.LENGTH_SHORT).show();
//                    mManager.scrollToPositionWithOffset(position2, 0);
//                    break;
//                case R.id.tv_rvhuadong_ShanXian_2:
//                    mManager.scrollToPositionWithOffset(20, 0);
//                    break;
//                case R.id.tv_rvhuadong_ShanXian_3:
//                    mManager.scrollToPositionWithOffset(99, 0);
//                    break;
//            }
//        }
//
//        private class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {
//            private final List<String> mList;
//
//            public MyAdapter(List<String> list) {
//                mList = list;
//            }
//
//            @Override
//            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                LinearLayout ll = new LinearLayout(getActivity());
//                ll.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                ll.setOrientation(LinearLayout.VERTICAL);
//
//                AppCompatTextView tv = new AppCompatTextView(RvHuaDongActivity.this);
//                tv.setTextSize(30);
//                tv.setBackgroundColor(0xffeeeeee);
//                ll.addView(tv);
//
//                RecyclerView rv = new RecyclerView(getActivity());
//                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//                rv.setNestedScrollingEnabled(true);
//                rv.setAdapter(new ItemAdapter(new ArrayList<String>()));
//                ll.addView(rv, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                return new BaseViewHolder(ll);
//            }
//
//            @Override
//            public void onBindViewHolder(BaseViewHolder holder, int position) {
//                ViewGroup vg = (ViewGroup) holder.itemView;
//                TextView tv = (TextView) vg.getChildAt(0);
//                tv.setText(mList.get(position));
//
//                RecyclerView rv = (RecyclerView) vg.getChildAt(1);
//                ItemAdapter adapter = (ItemAdapter) rv.getAdapter();
//                adapter.mList.clear();
//                for (int i = 0; i < 6; i++) {
//                    adapter.mList.add("item" + i);
//                }
//                adapter.notifyDataSetChanged();//在bind时确定好数据
//            }
//
//            @Override
//            public int getItemCount() {
//                return mList.size();
//            }
//        }
//
//        private class ItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {
//            private final List<String> mList;
//
//            public ItemAdapter(List<String> list) {
//                mList = list;
//            }
//
//
//            @Override
//            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                AppCompatTextView tv = new AppCompatTextView(RvHuaDongActivity.this);
//                tv.setTextSize(30);
//                tv.setBackgroundColor(0xffeeeeee);
//                tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                return new BaseViewHolder(tv);
//            }
//
//            @Override
//            public void onBindViewHolder(BaseViewHolder holder, int position) {
//                TextView tv = (TextView) holder.itemView;
//                tv.setText(mList.get(position));
//                if (position >= mList.size() - 2) {
//                    tv.getLayoutParams().height = 600;
//                } else {
//                    tv.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                }
//                tv.setLayoutParams(tv.getLayoutParams());
//            }
//
//            @Override
//            public int getItemCount() {
//                return mList.size();
//            }
//        }
//
//
//
//
//}
