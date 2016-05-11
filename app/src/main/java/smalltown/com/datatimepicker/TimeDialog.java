package smalltown.com.datatimepicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import smalltown.com.datatimepicker.utils.WindowUtils;


/**
 * Created by 程小镇 on 2016/4/1.
 */
public class TimeDialog extends Dialog {

    // 结果
    private TextView tvOk, tvCancle;
    private View view;
    private MyDatePickerDia hourStart, minuteStart, hourEnd, minuteEnd;
    private TextView tvDate;
    private OnClickListener onClickListener;
    String format = "%02d";

    public TimeDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_time, null);
        hourStart = (MyDatePickerDia) view.findViewById(R.id.date_hour_start);
        hourEnd = (MyDatePickerDia) view.findViewById(R.id.date_hour_end);
        minuteStart = (MyDatePickerDia) view.findViewById(R.id.date_minute_start);
        minuteEnd = (MyDatePickerDia) view.findViewById(R.id.date_minute_end);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        tvOk = (TextView) view.findViewById(R.id.ok);
        tvOk.setClickable(true);
        tvCancle = (TextView) view.findViewById(R.id.cancel);
        tvCancle.setClickable(true);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    int hs = Integer.parseInt(hourStart.getEdInput());
                    int he = Integer.parseInt(hourEnd.getEdInput());
                    int ms = Integer.parseInt(minuteStart.getEdInput());
                    int me = Integer.parseInt(minuteEnd.getEdInput());
                    String time = String.format(format, hs) + ":" + String.format(format, ms)
                            + "-" + String.format(format, he) + ":" + String.format(format, me);
                    onClickListener.clickOk(time);
                }
                dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    String time = "08:00-18:00";
                    onClickListener.clickCancle(time);
                }
                dismiss();
            }
        });
        setContentView(view);
        resize();
    }

    /**
     * 设置标题
     */
    public void setTittle(String string) {
        tvDate.setText(string);
    }

    /**
     * 不能放到onCreate中，放到onCreate 中再次打开日期不会更新
     */
    @Override
    protected void onStart() {
        super.onStart();
        hourStartSetting();
        hourEndSetting();
        minuteStartSetting();
        minuEndSetting();
    }

    /**
     * 上班时间  -小时
     */
    private void hourStartSetting() {
        hourStart.setInputLenth(2);
        hourStart.setEdInput(String.format(format, 8));
        hourStart.setOnClickListener(new MyDatePickerDia.OnClickListener() {
            @Override
            public void add(String inputStr) {
                hourAdd(true);
            }

            @Override
            public void minus(String inputStr) {
                hourMinus(true);
            }

            @Override
            public void textChange(Editable str) {

            }
        });
    }

    /**
     * 上班时间  -分钟
     */
    private void minuteStartSetting() {
        minuteStart.setInputLenth(2);
        minuteStart.setEdInput(String.format(format, 0));
        minuteStart.setOnClickListener(new MyDatePickerDia.OnClickListener() {
            @Override
            public void add(String inputStr) {
                minuteAdd(true);
            }

            @Override
            public void minus(String inputStr) {
                minuteMinus(true);
            }

            @Override
            public void textChange(Editable str) {

            }
        });
    }

    /**
     * 下班时间  -小时
     */
    private void hourEndSetting() {
        hourEnd.setInputLenth(2);
        hourEnd.setEdInput(String.format(format, 18));
        hourEnd.setOnClickListener(new MyDatePickerDia.OnClickListener() {
            @Override
            public void add(String inputStr) {
                hourAdd(false);
            }

            @Override
            public void minus(String inputStr) {
                hourMinus(false);

            }

            @Override
            public void textChange(Editable str) {

            }
        });
    }

    /**
     * 下班时间  -分钟
     */
    private void minuEndSetting() {
        minuteEnd.setInputLenth(2);
        minuteEnd.setEdInput(String.format(format, 0));
        minuteEnd.setOnClickListener(new MyDatePickerDia.OnClickListener() {
            @Override
            public void add(String inputStr) {
                minuteAdd(false);
            }

            @Override
            public void minus(String inputStr) {
                minuteMinus(false);
            }

            @Override
            public void textChange(Editable str) {

            }
        });

    }

    /**
     * 小时 +
     *
     * @param type 类型 true 开始  false 结束
     */
    private void hourAdd(boolean type) {
        int hour = 0;
        if (type) {
            hour = Integer.parseInt(hourStart.getEdInput());
        } else {
            hour = Integer.parseInt(hourEnd.getEdInput());
        }
        if (hour == 23) {
            hour = 0;
        } else {
            hour++;
        }
        String Hour = String.format(format, hour);

        if (type) {
            hourStart.setEdInput(Hour);
        } else {
            hourEnd.setEdInput(Hour);
        }
    }

    /**
     * 小时 -
     *
     * @param type 类型 true 开始  false 结束
     */
    private void hourMinus(boolean type) {
        int hour = 0;
        if (type) {
            hour = Integer.parseInt(hourStart.getEdInput());
        } else {
            hour = Integer.parseInt(hourEnd.getEdInput());
        }
        if (hour == 0) {
            hour = 23;
        } else {
            hour--;
        }
        String Hour = String.format(format, hour);
        if (type) {
            hourStart.setEdInput(Hour);
            return;
        }
        hourEnd.setEdInput(Hour);
    }

    /**
     * 分钟  +
     *
     * @param type 类型 true 开始  false 结束
     */
    private void minuteAdd(boolean type) {
        int minute = 0;
        if (type) {
            minute = Integer.parseInt(minuteStart.getEdInput());
        } else {
            minute = Integer.parseInt(minuteEnd.getEdInput());
        }
        if (minute == 59) {
            minute = 0;
            if (type) {
                hourAdd(true);
            } else {
                hourAdd(false);
            }
        } else {
            minute++;
        }
        if (type) {
            minuteStart.setEdInput(String.format(format, minute));
            return;
        }
        minuteEnd.setEdInput(String.format(format, minute));

    }

    /**
     * 分钟减
     *
     * @param type 类型 true 开始  false 结束
     */
    private void minuteMinus(boolean type) {
        int minute = 0;
        if (type) {
            minute = Integer.parseInt(minuteStart.getEdInput());
        } else {
            minute = Integer.parseInt(minuteEnd.getEdInput());
        }
        if (minute == 0) {
            minute = 59;
            if (type) {
                hourMinus(true);
            } else {
                hourMinus(false);
            }
        } else {
            minute--;
        }
        if (type) {
            minuteStart.setEdInput(String.format(format, minute));
            return;
        }
        minuteEnd.setEdInput(String.format(format, minute));

    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    /**
     * dialog 的宽高的设置
     */
    private void resize() {
        Window dialogWindow = this.getWindow();
        int width = WindowUtils.getScreenWidth(dialogWindow);
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (width * 0.85); // 宽度设置为屏幕的0.85
        dialogWindow.setAttributes(p);
    }


    /**
     * 接口
     */
    public interface OnClickListener {
        void clickOk(String ok);

        void clickCancle(String cancle);
    }

    /**
     * 设置取消按钮的显示内容
     *
     * @param cancle 显示的内容
     */
    public void setTextCancle(String cancle) {
        tvCancle.setText(cancle);
    }

    /**
     * 设置确定按钮的显示内容
     *
     * @param ok
     */
    public void steTextOk(String ok) {
        tvOk.setText(ok);
    }
}
