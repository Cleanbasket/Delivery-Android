package kr.co.cleanbasket.cleanbasketdelivererandroid.utils;

import android.content.Context;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InputValidationChecker.java
 * CleanBasket Deliverer Android
 *
 * Created by Yongbin Cha on 16. 3. 3..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class InputValidationChecker {

    Context mContext;
    private static InputValidationChecker mInputValidationChecker;

    private InputValidationChecker(Context c) {
        mContext = c;
    }

    public static synchronized InputValidationChecker getInstance(Context c) {
        if (mInputValidationChecker == null) {
            mInputValidationChecker = new InputValidationChecker(c);
        }
        return mInputValidationChecker;
    }

    // EditText이 채워져있는지 확인
    public boolean isFilled(String input, int toastMsgResId) {
        if (input.equals("")) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isFilled(String input, String toastMsgResId) {
        if (input.equals("")) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 복수의 EditText가 채워져있는지 확인
    public boolean isFilledAll(String[] inputs, int toastMsgResId) {
        for (String input : inputs) {
            if (input.equals("")) {
                Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
    public boolean isFilledAll(String[] inputs, String toastMsgResId) {
        for (String input : inputs) {
            if (input.equals("")) {
                Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    // 올바른 이메일 주소 형식인지 확인
    public boolean isEmailAddress(String email, int toastMsgResId) {
        Pattern pattern = Pattern.compile("\\w+[@]\\w+\\.\\w+");
        Matcher match = pattern.matcher(email);
        boolean isEmailAddress = match.find();

        if (!isEmailAddress) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
        }
        return isEmailAddress;
    }
    public boolean isEmailAddress(String email, String toastMsgResId) {
        Pattern pattern = Pattern.compile("\\w+[@]\\w+\\.\\w+");
        Matcher match = pattern.matcher(email);
        boolean isEmailAddress = match.find();

        if (!isEmailAddress) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
        }
        return isEmailAddress;
    }

    // 비밀번호 영문,숫자 조합 확인
    public boolean isPasswordCombinedAlphaAndDigit(String password, int toastMsgResId) {
        Pattern patternAlpha = Pattern.compile("^[\\p{Alpha}[^\\p{Digit}]]*$");
        Matcher matcherAlpha = patternAlpha.matcher(password);

        Pattern patternDigit = Pattern.compile("^[\\p{Digit}[^\\p{Alpha}]]*$");
        Matcher matcherDigit = patternDigit.matcher(password);

        if (!(!matcherAlpha.matches() && !matcherDigit.matches())) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isPasswordCombinedAlphaAndDigit(String password, String toastMsgResId) {
        Pattern patternAlpha = Pattern.compile("^[\\p{Alpha}[^\\p{Digit}]]*$");
        Matcher matcherAlpha = patternAlpha.matcher(password);

        Pattern patternDigit = Pattern.compile("^[\\p{Digit}[^\\p{Alpha}]]*$");
        Matcher matcherDigit = patternDigit.matcher(password);

        if (!(!matcherAlpha.matches() && !matcherDigit.matches())) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 비밀번호 길이 확인(6~10자)
    public boolean isPasswordLengthOK(String password, int toastMsgResId) {
        if (password.length() < 6 || password.length() > 10) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isPasswordLengthOK(String password, String toastMsgResId) {
        if (password.length() < 6 || password.length() > 10) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 모두 동의 확인
    public boolean isContractsAgreeValid(CheckedTextView chkAgreeAll, int toastMsgResId) {
        if(!chkAgreeAll.isChecked()) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isContractsAgreeValid(CheckedTextView chkAgreeAll, String toastMsgResId) {
        if(!chkAgreeAll.isChecked()) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 글자 수 미달 확인
    public boolean isInputLengthEnough(String input, int min_length, int toastMsgResId) {
        if (input.trim().length() < min_length) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isInputLengthEnough(String input, int min_length, String toastMsgResId) {
        if (input.trim().length() < min_length) {
            Toast.makeText(mContext, toastMsgResId, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 글자 수 초과 확인
    public boolean isInputLengthExceed(String input, int max_length, int toastMsgResId) {
        if (input.trim().length() > max_length) {
            Toast.makeText(mContext, mContext.getString(toastMsgResId) + "현재 " + input.length() + "자", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isInputLengthExceed(String input, int max_length, String toastMsgResId) {
        if (input.trim().length() > max_length) {
            Toast.makeText(mContext, toastMsgResId + " 현재 " + input.length() + "자", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 이미지 미입력 확인
    public boolean isImageFilled(ImageView image) {
        if (image.getDrawable() == null) {
            Toast.makeText(mContext, "이미지를 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 로그인 화면에서의 통합체크
    public boolean isLoginInputValid(String email, String password) {
        if (!isFilled(email, "이메일을 입력해주세요.") || !isEmailAddress(email, "잘못된 이메일 형식입니다.")
                || !isFilled(password, "비밀번호를 입력해주세요.")) {
            return false;
        }
        return true;
    }

//	public boolean isStyleUploadInputValid(String input, ImageView image, int[] category_select) {
//		if(!isInputLengthEnough(input, 2, R.string.input_invalid_min_length_2) || !isInputLengthExceed(input, 300, R.string.input_invalid_max_length_300)
//				|| !isImageFilled(image) || !isCategorySelected(category_select)) {
//			return false;
//		}
//		return true;
//	}

    // 회원가입 화면에서의 통합체크
    public boolean isRegisterInputValid(ImageView profil, String email, String password, String name, String birth, String phonenumber) {
        if (!isFilled(email, "이메일을 입력해주세요.")
                || !isEmailAddress(email, "잘못된 이메일 형식입니다.") || !isFilled(password, "비밀번호를 입력해주세요")
                || !isPasswordLengthOK(password, "비밀번호는 6자이상 10자이하 입니다.")
                || !isPasswordCombinedAlphaAndDigit(password, "영문자, 숫자 혼용하여 입력해주세요.")
                || !isFilled(name, "이름을 입력해주세요.") || !isFilled(birth, "생년월일을 입력해주세요") || !isInputLengthEnough(birth, 6, "생년월일을 6자로 입력해주세요.") || !isFilled(phonenumber, "연락처를 입력해주세요")
                || !isInputLengthEnough(phonenumber, 11, "올바른 연락처를 입력해주세요.")) {
            return false;
        }
        return true;
    }

    // 비밀번호찾기 화면에서의 통합체크
//	public boolean isFindPasswordInputValid(String email) {
//		if (!isFilled(email, R.string.input_invalid_null_email) || !isEmailAddress(email, R.string.input_invalid_email)) {
//			return false;
//		}
//		return true;
//	}
}
