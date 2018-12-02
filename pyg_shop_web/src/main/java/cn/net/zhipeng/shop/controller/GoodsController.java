package cn.net.zhipeng.shop.controller;

import cn.net.zhipeng.entity.Msg;
import cn.net.zhipeng.entity.PageResult;
import cn.net.zhipeng.pojo.Goods;
import cn.net.zhipeng.sellergoods.service.GoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findAll")
	public List<Goods> findAll(){
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping
	public PageResult findPage(int page, int rows){
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@PostMapping
	public Msg add(@RequestBody Goods goods, Authentication authentication){
		System.out.println(ReflectionToStringBuilder.toString(goods, ToStringStyle.MULTI_LINE_STYLE));
		goods.setSellerId(authentication.getName());
		try {
			Goods one = goodsService.findOne(149187842867916L);
			System.out.println(one);
			goodsService.add(goods);
			return Msg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Msg.fail();
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@PutMapping
	public Msg update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return Msg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Msg.fail();
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("{id:\\d+}")
	public Goods findOne(@PathVariable Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping("{ids}")
	public Msg delete(@PathVariable Long [] ids){
		try {
			goodsService.delete(ids);
			return Msg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Msg.fail();
		}
	}
	
		/**
	 * 查询+分页
	 * @param page
	 * @param rows
	 * @return
	 */
	@PostMapping("/search")
	public PageResult search(@RequestBody Goods goods, int page, int rows, Authentication authentication){
		goods.setSellerId(authentication.getName());
		return goodsService.findPage(goods, page, rows);		
	}

	@GetMapping("/updateStatus")
	public Msg updateStatus(Long[] ids, String status){
		try {
			goodsService.updateStatus(ids,status);
			return Msg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Msg.fail();
		}
	}
}
