package cn.net.zhipeng.sellergoods.service.impl;

import cn.net.zhipeng.entity.PageResult;
import cn.net.zhipeng.mapper.BrandMapper;
import cn.net.zhipeng.pojo.Brand;
import cn.net.zhipeng.pojo.BrandExample;
import cn.net.zhipeng.sellergoods.service.BrandService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @SuppressWarnings("unchecked")
    @Override
    public PageResult findPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize); //当前页，每页记录数

        List<Brand> brands = brandMapper.selectByExample(null);//查询全部不需要参数
        PageInfo pageInfo = new PageInfo(brands);
        return new PageResult(pageInfo.getTotal(), brands);
    }

    @Override
    public void add(Brand brand) {
        brandMapper.insert(brand);
    }

    @Override
    public Brand findOne(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageResult search(int pageNo, int pageSize, Brand brand) {
        PageHelper.startPage(pageNo, pageSize); //当前页，每页记录
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        if (brand != null) {
            if (StringUtils.isNotBlank(brand.getName())) {
                criteria.andNameLike("%" + brand.getName() + "%");
            }
            if (StringUtils.isNotBlank(brand.getFirstChar())) {
                criteria.andFirstCharEqualTo(brand.getFirstChar());
            }
        }
        List<Brand> brands = brandMapper.selectByExample(brandExample);//查询全部不需要参数
        PageInfo pageInfo = new PageInfo(brands);
        return new PageResult(pageInfo.getTotal(), brands);
    }

    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}
