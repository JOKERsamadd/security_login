package cn.net.zhipeng.mapper;

import cn.net.zhipeng.pojo.PayLog;
import cn.net.zhipeng.pojo.PayLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayLogMapper {
    long countByExample(PayLogExample example);

    int deleteByExample(PayLogExample example);

    int deleteByPrimaryKey(String outTradeNo);

    int insert(PayLog record);

    int insertSelective(PayLog record);

    List<PayLog> selectByExample(PayLogExample example);

    PayLog selectByPrimaryKey(String outTradeNo);

    int updateByExampleSelective(@Param("record") PayLog record, @Param("example") PayLogExample example);

    int updateByExample(@Param("record") PayLog record, @Param("example") PayLogExample example);

    int updateByPrimaryKeySelective(PayLog record);

    int updateByPrimaryKey(PayLog record);
}