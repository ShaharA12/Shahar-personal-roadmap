package test.test.com.myapplication.fragments;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.test.com.myapplication.MainActivity;
import test.test.com.myapplication.R;
import test.test.com.myapplication.utilities.SharedPreferencesUtil;

/**
 * Created by ShaharAlush on 30/01/2017.
 */
public class EditSettingFragment extends BaseFragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gasET)
    EditText gasPrice;
    @Bind(R.id.kTomET)
    EditText kToM;

    public static String Name = "EditSettingFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_fragment_settings, container, false);
        ButterKnife.bind(this, view);
        setToolBar();
        setData();
        return view;
    }

    private void setData() {
        gasPrice.setText(SharedPreferencesUtil.getGasPrice());
        kToM.setText(SharedPreferencesUtil.getKToM());
    }



    private void setToolBar() {
        super.setToolbar((MainActivity) getActivity(),toolbar,getString(R.string.app_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @OnClick(R.id.saveBT)
    public void saveData() {
        float gas = 0;
        float kilometer = 0;
        if (isFloat(String.valueOf(gasPrice.getText())) && isFloat(String.valueOf(kToM.getText()))) {
            gas = Float.parseFloat(String.valueOf(gasPrice.getText()));
            kilometer = Float.parseFloat(String.valueOf(kToM.getText()));
            if (gas > 0 && kilometer > 0) {
                SharedPreferencesUtil.saveFloat(SharedPreferencesUtil.GAS_PRICE, gas);
                SharedPreferencesUtil.saveFloat(SharedPreferencesUtil.KILOMETER, kilometer);
                getActivity().onBackPressed();
            } else {

            }
        } else
            Toast.makeText(getActivity(), "oooops1", Toast.LENGTH_LONG).show();

    }

    private boolean isFloat(String string) {
        final String Digits = "(\\p{Digit}+)";
        final String HexDigits = "(\\p{XDigit}+)";

        // an exponent is 'e' or 'E' followed by an optionally
        // signed decimal integer.
        final String Exp = "[eE][+-]?" + Digits;
        final String fpRegex =
                ("[\\x00-\\x20]*" +  // Optional leading "whitespace"
                        "[+-]?(" + // Optional sign character
                        "NaN|" +           // "NaN" string
                        "Infinity|" +      // "Infinity" string

                        // A decimal floating-point string representing a finite positive
                        // number without a leading sign has at most five basic pieces:
                        // Digits . Digits ExponentPart FloatTypeSuffix
                        //
                        // Since this method allows integer-only strings as input
                        // in addition to strings of floating-point literals, the
                        // two sub-patterns below are simplifications of the grammar
                        // productions from the Java Language Specification, 2nd
                        // edition, section 3.10.2.

                        // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                        "(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +

                        // . Digits ExponentPart_opt FloatTypeSuffix_opt
                        "(\\.(" + Digits + ")(" + Exp + ")?)|" +

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"

        if (Pattern.matches(fpRegex, string))
            return true;
        return false;
    }
}
