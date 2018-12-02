package cn.net.zhipeng.shop.controller;

import cn.net.zhipeng.entity.Msg;
import cn.net.zhipeng.entity.PageResult;
import cn.net.zhipeng.pojo.TypeTemplate;
import cn.net.zhipeng.sellergoods.service.TypeTemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Reference
	private TypeTemplateService typeTemplateService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findAll")
	public List<TypeTemplate> findAll(){
		return typeTemplateService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping
	public PageResult findPage(int page, int rows){
		return typeTemplateService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param typeTemplate
	 * @return
	 */
	@PostMapping
	public Msg add(@RequestBody TypeTemplate typeTemplate){
		try {
			typeTemplateService.add(typeTemplate);
			return Msg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Msg.fail();
		}
	}
	
	/**
	 * 修改
	 * @param typeTemplate
	 * @return
	 */
	@PutMapping
	public Msg update(@RequestBody TypeTemplate typeTemplate){
		try {
			typeTemplateService.update(typeTemplate);
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
	public TypeTemplate findOne(@PathVariable Long id){
		return typeTemplateService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping("{ids}")
	public Msg delete(@PathVariable Long [] ids){
		try {
			typeTemplateService.delete(ids);
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
	public PageResult search(@RequestBody TypeTemplate typeTemplate, int page, int rows  ){
		return typeTemplateService.findPage(typeTemplate, page, rows);		
	}

	@GetMapping("/findSpecList/{id:\\d+}")
	public List<Map> findSpecList(@PathVariable Long id) {
		return typeTemplateService.findSpecList(id);
	}
}
