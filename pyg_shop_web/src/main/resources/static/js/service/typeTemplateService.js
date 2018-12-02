//服务层
app.service('typeTemplateService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../typeTemplate/findAll');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../typeTemplate?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../typeTemplate/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../typeTemplate',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.put('../typeTemplate',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.delete('../typeTemplate/'+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../typeTemplate/search?page='+page+"&rows="+rows, searchEntity);
	}

	// 根据模板id获取该模板的所有规格和规格选项
    this.findSpecList = function (id) {
		// alert(111);
        return $http.get("../typeTemplate/findSpecList/" + id);
    }
});
