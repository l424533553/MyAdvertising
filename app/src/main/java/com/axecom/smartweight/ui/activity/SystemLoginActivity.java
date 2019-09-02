//package com.axecom.smartweight.ui.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.text.Editable;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import com.axecom.smartweight.R;
//import com.axecom.smartweight.my.IConstants;
//import com.axecom.smartweight.ui.view.SoftKey;
//
//import java.lang.reflect.Method;
//
//public class SystemLoginActivity extends Activity implements View.OnClickListener, IConstants {
//
//
//    private EditText pwdEt;
//    private SoftKey softKey;
//    private Button doneBtn, backBtn;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.staff_member_login_activity);
//        setInitView();
//        initView();
//
//    }
//
//    public void setInitView() {
//        pwdEt = findViewById(R.id.staff_member_pwd_et);
//        pwdEt.requestFocus();
//        disableShowInput(pwdEt);
//        softKey = findViewById(R.id.staff_member_softkey);
//        doneBtn = findViewById(R.id.staff_member_done_btn);
//        backBtn = findViewById(R.id.staff_member_back_btn);
//
//        doneBtn.setOnClickListener(this);
//        backBtn.setOnClickListener(this);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }
//
//
//    public void initView() {
//        softKey.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String text = parent.getAdapter().getItem(position).toString();
//                setEditText(pwdEt, position, text);
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.staff_member_done_btn:
//                String password = pwdEt.getText().toString();
//                if (getString(R.string.Administrators_pwd).equals(password)) {
//                    Intent intentData = new Intent();
//                    setResult(RESULT_OK, intentData);
//                    this.finish();
//                }
//
//                break;
//            case R.id.staff_member_back_btn:
//                setResult(RESULT_CANCELED);
//                this.finish();
//                break;
//        }
//    }
//
//
//    public void disableShowInput(final EditText editText) {
//        if (android.os.Build.VERSION.SDK_INT <= 10) {
//            editText.setInputType(InputType.TYPE_NULL);
//        } else {
//            Class<EditText> cls = EditText.class;
//            Method method;
//            try {
//                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
//                method.setAccessible(true);
//                method.invoke(editText, false);
//            } catch (Exception e) {//TODO: handle exception
//            }
//            try {
//                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
//                method.setAccessible(true);
//                method.invoke(editText, false);
//            } catch (Exception e) {//TODO: handle exception
//            }
//        }
//
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                editText.setSelection(editText.length());
//            }
//        });
//    }
//
//    public void setEditText(EditText editText, int position, String text) {
//        if (position == 9) {
//            if (!TextUtils.isEmpty(editText.getText()))
//                editText.setText(editText.getText().subSequence(0, editText.getText().length() - 1));
//        } else {
//            editText.setText(editText.getText() + text);
//        }
//    }
//
//    public void setEditText(EditText editText, int position, String text, int type) {
//        if (position == 9) {
//            if (!TextUtils.isEmpty(editText.getText()))
//                editText.setText(editText.getText().subSequence(0, editText.getText().length() - 1));
//        } else {
//            editText.setText(editText.getText() + text);
//        }
//    }
//
//
//}