package com.lihebin.manage.dao.manage;

import com.lihebin.manage.bean.Code;
import com.lihebin.manage.exception.BackendException;
import com.lihebin.manage.exception.BackendRepeatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by lihebin on 2019/4/15.
 */
@Repository
public class SimpleSnGeneratorDao {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSnGeneratorDao.class);

    @Resource(name = "manageJdbcTemplate")
    JdbcTemplate manageJdbcTemplate;

    private static final Random random = new Random();
    private int retries = 3;

    private String orderSnPrefix = "order";

    private static String[] permTables = {
            "2076943518",
            "5308217469",
            "9382104675",
            "2534918067",
            "4317586902",
            "7941602538",
            "3759410628",
            "0237596841",
            "7014582936",
            "7498235106",
            "7640235891",
            "3142780596",
    };

    /**
     * 商户会员号生成器
     *
     * @return
     * @throws BackendRepeatException
     */
    public String nextOrderSn() throws BackendException {
        return orderSnPrefix + nextSn("table_name_sn_prefix", orderSnPrefix, 10);
    }

    /**
     * 商户号生成器
     *
     * @return
     * @throws BackendRepeatException
     */
    public String nextMerchantSn() throws BackendException {
        return orderSnPrefix + nextSn("table_name_sn_prefix", orderSnPrefix, 10);
    }

    private String nextSn(String tableName, String prefix, int length) throws BackendException {
        return nextSn(tableName, prefix, length, false);
    }

    private String nextSn(String tableName, final String prefix, int length, boolean needShuffle) throws BackendException {
        final String getTicketSql = String.format("replace into %s_%s (stub) values ( ? )", tableName, prefix);
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        for (int i=0; i< retries; ++i) {
            try {
                manageJdbcTemplate.update(
                        con -> {
                            //                PreparedStatement ps = con.prepareStatement("replace into order_ticket (stub) values ( ? )", new String[] {"id"});
                            PreparedStatement ps = con.prepareStatement(getTicketSql, Statement.RETURN_GENERATED_KEYS);
                            StatementCreatorUtils.setParameterValue(ps, 1, SqlTypeValue.TYPE_UNKNOWN, prefix);
                            return ps;
                        },
                        keyHolder);

                if (!keyHolder.getKeyList().isEmpty()) {
                    Iterator<Object> it = keyHolder.getKeyList().get(0).values().iterator();
                    Object key = it.next();
                    logger.debug("next tsn {}", key);
                    if(needShuffle){
                        return shuffle(leftPadding(key+"", '0', length));
                    }else{
                        return leftPadding(key+"", '0', length);
                    }
                }
                throw new BackendRepeatException("序列号产生器无法得到自增长ID");

            } catch (TransientDataAccessException ex) {
                // continue to retry
                logger.warn("failed to generate sn due to transient jdbc error", ex);
                sleepRandom(50);
            }catch(Exception ex) {
                throw new BackendException(Code.CODE_PARAM_ERROR, "序列号产生器异常:" + ex.getMessage());
            }
        }
        throw new BackendException(Code.CODE_PARAM_ERROR, String.format("序列号产生器失败（已经试了%d次）", retries));
    }

    private static void sleepRandom(int upTo) {
        long time = random.nextInt(upTo);
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            logger.info("SimpleSnGenerator{} sleepRandom InterruptedException");
        }
    }

    public static String shuffle(String code) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<code.length(); ++i) {
            int digit = Integer.parseInt(code.substring(i, i+1));
            sb.append(permTables[i%11].charAt(digit));
        }
        return sb.toString();
    }

    /**
     * 添加前置字符串，左填充。
     * @param origin
     * @param padding
     * @param length
     * @return
     */
    public  static String leftPadding(String origin, char padding, int length){
        if(origin == null){
            return null;
        }
        if(origin.length() < length){
            int time = length - origin.length();
            StringBuilder originBuilder = new StringBuilder(origin);
            for (int i = 0; i< time; i++){
                originBuilder.insert(0, padding);
            }
            origin = originBuilder.toString();
        }
        return origin;
    }


}
