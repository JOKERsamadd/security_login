package cn.net.zhipeng.sellergoods.service.impl;

import cn.net.zhipeng.entity.PageResult;
import cn.net.zhipeng.mapper.SpecificationOptionMapper;
import cn.net.zhipeng.mapper.TypeTemplateMapper;
import cn.net.zhipeng.pojo.SpecificationOption;
import cn.net.zhipeng.pojo.SpecificationOptionExample;
import cn.net.zhipeng.pojo.TypeTemplate;
import cn.net.zhipeng.pojo.TypeTemplateExample;
import cn.net.zhipeng.sellergoods.service.TypeTemplateService;
import com.alibaba.fastjson.JSON;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TypeTemplateMapper typeTemplateMapper;

	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TypeTemplate> findAll() {
		return typeTemplateMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TypeTemplate> page=   (Page<TypeTemplate>) typeTemplateMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TypeTemplate typeTemplate){
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TypeTemplate findOne(Long id){
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			typeTemplateMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TypeTemplate typeTemplate, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TypeTemplateExample example=new TypeTemplateExample();
		TypeTemplateExample.Criteria criteria = example.createCriteria();
		
		if(typeTemplate!=null){			
						if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
				criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
			if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
				criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
			}
			if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
				criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
			}
			if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
			}
	
		}
		
		Page<TypeTemplate> page= (Page<TypeTemplate>)typeTemplateMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public List<Map> findSpecList(Long id) {
		TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
		List<Map> maps = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);
		//循环所有规格，根据id查出该规格下的所有规格选项
		maps.forEach(map -> {
			SpecificationOptionExample example = new SpecificationOptionExample();
			example.createCriteria().andSpecIdEqualTo(new Long(map.get("id").toString()));
			List<SpecificationOption> options = specificationOptionMapper.selectByExample(example);
			map.put("options", options);
		});
		//[{id:'id',text:'text',options:[{规格选项},{},{}]},{},{}]
		return maps;
	}

}
