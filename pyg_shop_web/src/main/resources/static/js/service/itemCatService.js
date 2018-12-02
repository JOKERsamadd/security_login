//服务层
app.service('itemCatService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../itemCat/findAll');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../itemCat?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../itemCat/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../itemCat',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.put('../itemCat',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.delete('../itemCat/'+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../itemCat/search?page='+page+"&rows="+rows, searchEntity);
	}

	this.findByParentId = function (parentId) {
		return $http.get('../itemCat/findByParentId?parentId='+parentId);
    }
});
