//package com.axecom.smartweight.ui.activity.datasummary;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ExpandableListView;
//import android.widget.TextView;
//import com.axecom.smartweight.R;
//import com.advertising.SysApplication;
//import com.axecom.smartweight.bean.ReportResultBean;
//import com.axecom.smartweight.my.adapter.DetailsAdapter;
//import com.axecom.smartweight.my.entity.OrderInfo;
//import com.axecom.smartweight.my.entity.dao.OrderInfoDao;
//import com.luofx.newclass.printer.MyBasePrinter;
//import com.luofx.utils.DateUtils;
//import com.luofx.utils.match.MyMatch;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//
///**
// * 资料 模块
// * 待定
// * Created by Administrator on 2018/9/19.
// */
//public class DetailsFragment extends Fragment implements View.OnClickListener, DetailsAdapter.IMyItemOnclick {
//
//    /**
//     * ViewPager控件，承载各Fragment的容器
//     */
//    private Context context;
//    private SysApplication sysApplication;
//    private ExpandableListView lvSearch;
//    private List<OrderInfo> data;
//    private Date time;
//    private TextView tvMonth;
//    private TextView tvTotalMoney;
//    private String date;
//    private DetailsAdapter adapter;
//    private float totalMoney;
//
//    private Calendar calendar;
//    private int year;
//    private int month;
//    private OrderInfoDao orderInfoDao;
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = null;
//        sysApplication = (SysApplication) getActivity().getApplication();
//        context = getContext();
//        if (inflater != null) {
//            view = inflater.inflate(R.layout.fragment_details_summary, container, false);
//            orderInfoDao = new OrderInfoDao(context);
//            initView(view);
//            initHandler();
//            getData(true);
//
//            /* initListeners(view);*/
//        }
//        return view;
//    }
//
//
//    private MyBasePrinter myBasePrinter;
//
//    public void initView(View view) {
//        calendar = Calendar.getInstance();
//        time = calendar.getTime();
////        int year = calendar.get(Calendar.YEAR);
////        int month = calendar.get(Calendar.MONTH);
//
//        tvMonth = view.findViewById(R.id.tvMonth);
//        tvTotalMoney = view.findViewById(R.id.tvTotalMoney);
//        date = DateUtils.getYYMMDD(time);
//        tvMonth.setText(date);
//
//        view.findViewById(R.id.btnBefore).setOnClickListener(this);
//        view.findViewById(R.id.btnNext).setOnClickListener(this);
//
//
////        view.findViewById(R.id.btnPrinter).setOnClickListener(this);
//
//
//        myBasePrinter = sysApplication.getPrint();
//        lvSearch = view.findViewById(R.id.lvSearch);
//        data = new ArrayList<>();
//
//        adapter = new DetailsAdapter(context, data);
//        adapter.setMyItemOnclick(this);
////        adapter.setMyItemOnClickListener(this);
////        adapter.setMyItemOnclick(this);
////        adapter.setMyItemOnClickListener(this);
//        lvSearch.setAdapter(adapter);
//        lvSearch.setGroupIndicator(null);//这个是去掉父级的箭头
//        lvSearch.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                return true;  //返回true,表示不可点击
//            }
//        });
//    }
//
//    @Override
//    public void myItemGroupClick(int groupPosition, OrderInfo orderInfo) {
//
//        myBasePrinter.printOrder(sysApplication.getThreadPool(), orderInfo);
//    }
//
//    private void getData(final boolean isFirst) {
//        sysApplication.getThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
////                setData();
//                List<OrderInfo> list = orderInfoDao.queryByDay(date, false);
//                totalMoney = 0.00f;
//
//                for (OrderInfo orderInfo : list) {
//                    orderInfo.setOrderItem(new ArrayList<>(orderInfo.getOrderBeans()));
////                    int dayIndex = orderInfo.getDay() - 1;
////                    ReportResultBean resultBean = data.get(dayIndex);
////                    resultBean.setAll_num(resultBean.getAll_num() + 1);
////                    resultBean.setTotal_amount(resultBean.getTotal_amount() + Float.valueOf(orderInfo.getTotalamount()));
////                    resultBean.setTotal_weight(resultBean.getTotal_weight() + Float.valueOf(orderInfo.getTotalweight()));
//
//                    totalMoney += Float.valueOf(orderInfo.getTotalamount());
//                }
//                data.clear();
//                data.addAll(list);
//
//                handler.sendEmptyMessage(914);
//            }
//        });
//    }
//
//
////    /**
////     * 测试 数据功能
////     */
////    private void test() {
////        // TODO 数据接口  使用功能
////    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnBefore:
//                calendar.add(Calendar.DATE, -1); //向
//                time = calendar.getTime();
//                date = DateUtils.getYYMMDD(time);
//                tvMonth.setText(date);
//                tvTotalMoney.setText("0.00");
//                getData(false);
////                getReportsList(currentDay, typeVal + "", --currentPage <= 1 ? (currentPage = 1) + "" : --currentPage + "", pageNum + "");
//                break;
//            case R.id.btnNext:
//                calendar.add(Calendar.DATE, 1); //向
//                time = calendar.getTime();
//                date = DateUtils.getYYMMDD(time);
//                tvMonth.setText(date);
//                tvTotalMoney.setText("0.00");
//                getData(false);
////                getReportsList(currentDay, typeVal + "", --currentPage <= 1 ? (currentPage = 1) + "" : --currentPage + "", pageNum + "");
//                break;
////            case R.id.btnPrinter://打印机
////                //TODO  打印机
//////                sysApplication.getPrint()
////
////
////                break;
//            default:
//                break;
//
//        }
//    }
//
//    private Handler handler;
//
//    private void initHandler() {
//        handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 914:
//                        adapter.notifyDataSetChanged();
//                        for (int i = 0; i < adapter.getGroupCount(); i++) {
//                            lvSearch.expandGroup(i);
//                        }
//                        tvTotalMoney.setText(MyMatch.accurate2(totalMoney) + "元");
//                        break;
//                }
//                return false;
//            }
//        });
//    }
//
//
//    public class MonthAdapter extends BaseAdapter {
//        private Context context;
//        private List<ReportResultBean> list;
//
//        public MonthAdapter(Context context, List<ReportResultBean> list) {
//            this.context = context;
//            this.list = list;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return list.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            MonthAdapter.ViewHolder holder;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(context).inflate(R.layout.data_summary_item, null);
//                holder = new MonthAdapter.ViewHolder();
//                holder.timeTv = convertView.findViewById(R.id.data_item_time_tv);
//                holder.countTv = convertView.findViewById(R.id.data_item_count_tv);
//                holder.weightTv = convertView.findViewById(R.id.data_item_weight_tv);
//                holder.grandTotalTv = convertView.findViewById(R.id.data_item_grand_total_tv);
//                holder.incomeTv = convertView.findViewById(R.id.data_item_income_tv);
//                convertView.setTag(holder);
//            } else {
//                holder = (MonthAdapter.ViewHolder) convertView.getTag();
//            }
//            ReportResultBean item = list.get(position);
//            holder.timeTv.setText(item.getTimes());
//            holder.countTv.setText(item.getAll_num() + "");
//            holder.incomeTv.setText(item.getTotal_amount() + "");
//            holder.grandTotalTv.setText(item.getTotal_amount() + "");
//            holder.weightTv.setText(item.getTotal_weight() + "");
//            return convertView;
//        }
//
//        class ViewHolder {
//            TextView timeTv;
//            TextView countTv;
//            TextView weightTv;
//            TextView grandTotalTv;
//            TextView incomeTv;
//        }
//    }
//
//}
