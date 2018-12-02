package cn.net.zhipeng.sellergoods.service.impl;
import java.util.List;
import java.util.Map;


import cn.net.zhipeng.entity.PageResult;
import cn.net.zhipeng.mapper.SpecificationMapper;
import cn.net.zhipeng.mapper.SpecificationOptionMapper;
import cn.net.zhipeng.pojo.Specification;
import cn.net.zhipeng.pojo.SpecificationExample;
import cn.net.zhipeng.pojo.SpecificationOption;
import cn.net.zhipeng.pojo.SpecificationOptionExample;
import cn.net.zhipeng.sellergoods.service.SpecificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private SpecificationMapper specificationMapper;

	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<Specification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Specification> page=   (Page<Specification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		//新增规格
//		Specification spec = specification.getSpecification();
		specificationMapper.insert(specification);
		addOptionBySpecificationId(specification);
	}

	private void addOptionBySpecificationId(Specification specification) {
		//新增所有规格选项
		List<SpecificationOption> optionList = specification.getSpecificationOptionList();
		for (SpecificationOption option : optionList) {
			//需要设置规格的id，维护多对一关系
			option.setSpecId(specification.getId());
			specificationOptionMapper.insert(option);
		}
	}


	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){

//		Specification spec = specification.getSpecification();
		specificationMapper.updateByPrimaryKey(specification);
		//删除该规格id下的所有规格选项
		deleteOptionBySpecificationId(specification.getId());

		//本次传入的规格选项变成新增操作
		//新增所有规格选项
		addOptionBySpecificationId(specification);
	}

	private void deleteOptionBySpecificationId(Long id) {
		//删除该规格id下的所有规格选项
		SpecificationOptionExample example = new SpecificationOptionExample();
		example.createCriteria().andSpecIdEqualTo(id);//查询该规格id下的所有规格选项
		specificationOptionMapper.deleteByExample(example);
	}

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){

		//查询规格,设置规格对象
		Specification spec = specificationMapper.selectByPrimaryKey(id);
		//查询规格选项
		SpecificationOptionExample example = new SpecificationOptionExample();
		example.createCriteria().andSpecIdEqualTo(id);//查询该规格id下的所有规格选项
		List<SpecificationOption> options = specificationOptionMapper.selectByExample(example);
		spec.setSpecificationOptionList(options);

		return spec;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//只是删除规格
		for(Long id:ids){
			specificationMapper.deleteByPrimaryKey(id);

			//并且删除规格选项
			//删除该规格id下的所有规格选项
			deleteOptionBySpecificationId(id);
		}
	}


	@Override
	public PageResult findPage(Specification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		SpecificationExample example=new SpecificationExample();
		SpecificationExample.Criteria criteria = example.createCriteria();

		if(specification!=null){
			if(StringUtils.isNotBlank(specification.getSpecName())){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
		}

		Page<Specification> page= (Page<Specification>)specificationMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public List<Map> selectOptionList() {
		return specificationMapper.selectOptionList();
	}

}
