package cn.net.zhipeng.shop.controller;

import cn.net.zhipeng.entity.Msg;
import cn.net.zhipeng.entity.PageResult;
import cn.net.zhipeng.pojo.ItemCat;
import cn.net.zhipeng.sellergoods.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

	@Reference
	private ItemCatService itemCatService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findAll")
	public List<ItemCat> findAll(){
		return itemCatService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping
	public PageResult findPage(int page, int rows){
		return itemCatService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param itemCat
	 * @return
	 */
	@PostMapping
	public Msg add(@RequestBody ItemCat itemCat){
		try {
			itemCatService.add(itemCat);
			return Msg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Msg.fail();
		}
	}
	
	/**
	 * 修改
	 * @param itemCat
	 * @return
	 */
	@PutMapping
	public Msg update(@RequestBody ItemCat itemCat){
		try {
			itemCatService.update(itemCat);
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
	public ItemCat findOne(@PathVariable Long id){
		return itemCatService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping("{ids}")
	public Msg delete(@PathVariable Long [] ids){
		try {
			itemCatService.delete(ids);
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
	public PageResult search(@RequestBody ItemCat itemCat, int page, int rows  ){
		return itemCatService.findPage(itemCat, page, rows);		
	}

	/**
	 * 根据父分类的id获取该分类下的所有列表数据
	 * @param parentId
	 * @return
	 */
	@GetMapping("/findByParentId")
	public List<ItemCat> findByParentId(Long parentId){
		return itemCatService.findByParentId(parentId);
	}
	
}
