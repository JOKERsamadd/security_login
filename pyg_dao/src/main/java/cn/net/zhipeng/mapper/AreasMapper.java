package cn.net.zhipeng.mapper;

import cn.net.zhipeng.pojo.Areas;
import cn.net.zhipeng.pojo.AreasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AreasMapper {
    long countByExample(AreasExample example);

    int deleteByExample(AreasExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Areas record);

    int insertSelective(Areas record);

    List<Areas> selectByExample(AreasExample example);

    Areas selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Areas record, @Param("example") AreasExample example);

    int updateByExample(@Param("record") Areas record, @Param("example") AreasExample example);

    int updateByPrimaryKeySelective(Areas record);

    int updateByPrimaryKey(Areas record);
}