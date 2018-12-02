package cn.net.zhipeng.sellergoods.service.impl;

import cn.net.zhipeng.entity.PageResult;
import cn.net.zhipeng.mapper.*;
import cn.net.zhipeng.pojo.Goods;
import cn.net.zhipeng.pojo.GoodsDesc;
import cn.net.zhipeng.pojo.GoodsExample;
import cn.net.zhipeng.pojo.Item;
import cn.net.zhipeng.sellergoods.service.GoodsService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SellerMapper sellerMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Goods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    @SuppressWarnings("unchecked")
    public void add(Goods goods) {
//        System.out.println(ReflectionToStringBuilder.toString(goods));
        //新增商品
        goods.setAuditStatus("0"); // 未申请状态
        goodsMapper.insert(goods);

        //新增商品详情
        GoodsDesc goodsDesc = goods.getGoodsDesc();
        goodsDesc.setGoodsId(goods.getId()); //维护一对一关系
        goodsDescMapper.insert(goodsDesc);

        List<Item> itemList = goods.getItemList();
        for (Item dbItem : itemList) {
            //商品名称+所有规格选项
            StringBuilder title = new StringBuilder(goods.getGoodsName());
            Map<String, String> specMap = JSON.parseObject(dbItem.getSpec(), Map.class);

            for (String key : specMap.keySet()) {
                title.append(" ").append(specMap.get(key));
            }
            dbItem.setTitle(title.toString());
            dbItem.setSellPoint(goods.getCaption()); //卖点

            //存一张图片从goodsDesc图片集合中取第一个
            List<Map> images = JSON.parseArray(goodsDesc.getItemImages(), Map.class);
            if (images.size() > 0) {
                dbItem.setImage(images.get(0).get("url").toString());
            }

            dbItem.setCategoryid(goods.getCategory3Id());//保存三级分类id
            dbItem.setCategory(itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName());

            dbItem.setCreateTime(new Date()); //创建时间
            dbItem.setUpdateTime(new Date());
            dbItem.setGoodsId(goods.getId());  //多对一关联
            dbItem.setSellerId(goods.getSellerId()); //商家id
            dbItem.setSeller(sellerMapper.selectByPrimaryKey(goods.getSellerId()).getName());

            dbItem.setBrand(brandMapper.selectByPrimaryKey(goods.getBrandId()).getName()); //设置品牌名称

            itemMapper.insert(dbItem);
        };
    }


    /**
     * 修改
     */
    @Override
    public void update(Goods goods) {
        goodsMapper.updateByPrimaryKey(goods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Goods findOne(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
//            goodsMapper.deleteByPrimaryKey(id);
            Goods goods = goodsMapper.selectByPrimaryKey(id);
            goods.setIsDelete("1");
            goodsMapper.updateByPrimaryKey(goods);
        }
    }


    @Override
    public PageResult findPage(Goods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteIsNull();

        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            //先查后改
            Goods goods = goodsMapper.selectByPrimaryKey(id);
            goods.setAuditStatus(status);
            goodsMapper.updateByPrimaryKey(goods);
        }
    }
}
