package cn.net.zhipeng.shop.controller;

import cn.net.zhipeng.entity.Msg;
import cn.net.zhipeng.entity.PageResult;
import cn.net.zhipeng.pojo.Seller;
import cn.net.zhipeng.sellergoods.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findAll")
	public List<Seller> findAll(){
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping
	public PageResult findPage(int page, int rows){
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@PostMapping
	public Msg add(@RequestBody Seller seller){
//		通过BCryptPassword进行md5加密
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		seller.setPassword(encoder.encode(seller.getPassword()));

		seller.setCreateTime(new Date());
		seller.setStatus("0");  //0未审核 1已审核 2已驳回
		try {
			sellerService.add(seller);
			return Msg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Msg.fail();
		}
	}
	
	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@PutMapping
	public Msg update(@RequestBody Seller seller){
		try {
			sellerService.update(seller);
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
	@GetMapping("{id}")
	public Seller findOne(@PathVariable String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping("{ids}")
	public Msg delete(@PathVariable String[] ids){
		try {
			sellerService.delete(ids);
			return Msg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Msg.fail();
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@PostMapping("/search")
	public PageResult search(@RequestBody Seller seller, int page, int rows  ){
		return sellerService.findPage(seller, page, rows);		
	}
	
}
