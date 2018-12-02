//服务层
app.service('goodsService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../goods/findAll');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../goods?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../goods/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../goods',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.put('../goods',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.delete('../goods/'+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../goods/search?page='+page+"&rows="+rows, searchEntity);
	}
    this.updateStatus = function (ids, status) {
        return $http.get('../goods/updateStatus?ids='+ids+"&status="+status);
    }
});
