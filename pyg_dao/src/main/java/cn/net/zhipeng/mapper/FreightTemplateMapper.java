package cn.net.zhipeng.mapper;

import cn.net.zhipeng.pojo.FreightTemplate;
import cn.net.zhipeng.pojo.FreightTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FreightTemplateMapper {
    long countByExample(FreightTemplateExample example);

    int deleteByExample(FreightTemplateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FreightTemplate record);

    int insertSelective(FreightTemplate record);

    List<FreightTemplate> selectByExample(FreightTemplateExample example);

    FreightTemplate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FreightTemplate record, @Param("example") FreightTemplateExample example);

    int updateByExample(@Param("record") FreightTemplate record, @Param("example") FreightTemplateExample example);

    int updateByPrimaryKeySelective(FreightTemplate record);

    int updateByPrimaryKey(FreightTemplate record);
}