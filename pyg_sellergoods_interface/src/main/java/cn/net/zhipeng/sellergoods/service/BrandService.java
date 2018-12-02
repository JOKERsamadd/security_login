package cn.net.zhipeng.sellergoods.service;

import cn.net.zhipeng.entity.Msg;
import cn.net.zhipeng.pojo.Brand;
import cn.net.zhipeng.entity.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService {

    PageResult findPage(int pageNo, int pageSize);

    void add(Brand brand);

    Brand findOne(Long id);

    void update(Brand brand);

    void delete(Long[] ids);

    PageResult search(int pageNo, int pageSize, Brand brand);

    List<Map> selectOptionList();
}