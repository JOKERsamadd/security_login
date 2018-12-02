package cn.net.zhipeng.mapper;

import cn.net.zhipeng.pojo.Cities;
import cn.net.zhipeng.pojo.CitiesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CitiesMapper {
    long countByExample(CitiesExample example);

    int deleteByExample(CitiesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Cities record);

    int insertSelective(Cities record);

    List<Cities> selectByExample(CitiesExample example);

    Cities selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Cities record, @Param("example") CitiesExample example);

    int updateByExample(@Param("record") Cities record, @Param("example") CitiesExample example);

    int updateByPrimaryKeySelective(Cities record);

    int updateByPrimaryKey(Cities record);
}