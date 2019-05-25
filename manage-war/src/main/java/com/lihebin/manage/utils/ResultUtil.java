package com.lihebin.manage.utils;

import com.lihebin.manage.bean.Code;
import com.lihebin.manage.bean.Result;

/**
 * Created by lihebin on 2019/5/22.
 */
public class ResultUtil {

    /**
     * 成功方法
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(Code.CODE_SUCCESS);
        result.setMsg("SUCCESS");
        if (object != null) {
            result.setData(object);
        }
        return result;
    }


    /**
     * 操作成功不返回消息
     * @return
     */
    public static Result success() {
        return success(null);
    }


    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
