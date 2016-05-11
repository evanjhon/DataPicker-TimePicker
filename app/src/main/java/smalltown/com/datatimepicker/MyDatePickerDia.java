package smalltown.com.datatimepicker;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by cxz on 2015/12/21 0021.
 */
public class MyDatePickerDia extends LinearLayout {
    private Context context;
    private ImageView imgAdd;
    private ImageView imgMinus;
    private TextView tvType;
    private EditText edInput;
    private LinearLayout llInput;
    private OnClickListener onClickListener;
    private View view;

    public MyDatePickerDia(Context context) {
        super(context);
        this.context = context;
        this.onCreat();
    }

    public MyDatePickerDia(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.onCreat();
    }

    public MyDatePickerDia(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.onCreat();
    }

    private void onCreat() {
        view = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_picker, this, true);
        initView();
    }

    private void initView() {
        imgAdd = (ImageView) view.findViewById(R.id.img_add);
        imgMinus = (ImageView) view.findViewById(R.id.img_minus);
        edInput = (EditText) view.findViewById(R.id.ed_input);
        tvType = (TextView) view.findViewById(R.id.tv_type);
        //点击事件
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    String inputStr = edInput.getText().toString();
                    onClickListener.add(inputStr);
                }
            }
        });
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    String inputStr = edInput.getText().toString();
                    onClickListener.minus(inputStr);
                }
            }
        });
        edInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (onClickListener != null) {
                    onClickListener.textChange(s);
                }

            }
        });

    }

    public String getEdInput() {

        return edInput.getText().toString();
    }

    /**
     * 设置EditText显示的内容
     *
     * @param string EditText要显示的值
     */
    public void setEdInput(String string) {

        edInput.setText(string);
    }

    /**
     * 设置显示的类型
     *
     * @param inputType tvType 显示的值 "年","月","日"
     */
    public void setInputType(String inputType) {

        tvType.setText(inputType);
    }

    /**
     * 设置输入的长度
     *
     * @param inputLenth 输入的长度
     */
    public void setInputLenth(int inputLenth) {
        if (inputLenth == 0 || String.valueOf(inputLenth).length() == 0 || "".equals(String.valueOf(inputLenth))) {
            edInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        } else {
            //最大输入长度
            edInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputLenth)});
        }
    }

    public void keyboardUp(boolean keyboard) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (keyboard) {
            //打开软键盘
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            imm.hideSoftInputFromWindow(edInput.getWindowToken(), 0);
        }
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 接口
     * imgAdd  +
     * imgMinus  —
     */
    public interface OnClickListener {
        //加
        void add(String inputStr);

        //减
        void minus(String inputStr);

        //输入的内容的监听
        void textChange(Editable str);
    }

}
