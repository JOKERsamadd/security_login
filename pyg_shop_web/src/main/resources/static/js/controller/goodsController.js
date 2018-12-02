//控制层
app.controller('goodsController', function ($scope, $controller, goodsService, itemCatService, typeTemplateService,uploadService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        $scope.entity.goodsDesc.introduction = editor.html();

        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                alert(response.message);
                if (response.success) {
                    location.href("goods.html");
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    $scope.init = function () {
        $scope.selectItemCat1List();
        $scope.entity = {goodsDesc:{customAttributeItems:[],itemImages:[],specificationItems:[]}};

        $scope.entity_image = {};
        $scope.findItemCatList();
    }

    //获取顶级分类的itemCat列表数据
    $scope.selectItemCat1List = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1List = response;
            }
        )
    }

    //$watch方法的两个参数，第一个是查看哪个变量发生变量，需要填字符串，参数二中的function有两个参数分别是新值，和旧值
    $scope.$watch('entity.category1Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List = response;
            }
        )
    })

    //三级列表
    $scope.$watch('entity.category2Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List = response;
            }
        )
    })

    //根据三级分类的id获取分类对象将模版id赋值给，封装对象中的spu对象的模版属性
    $scope.$watch('entity.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.typeTemplateId = response.typeId;
            }
        )
    })

    $scope.$watch('entity.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(
            function (response) {
                $scope.typeTemplate = response;
                $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds); //将返回的字符串转json对象

                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems)

            }
        )

        //根据模版id获取该模版的所有规格和规格选项
        typeTemplateService.findSpecList(newValue).success(
            function(response){
                console.log(response);
                $scope.specList = response;
            }
        )
    })

    $scope.uploadFile = function(){
        uploadService.uploadFile().success(
            function (response) {
                if(response.success){
                    $scope.entity_image.url = response.extend.imageUrl;
                }else{
                    alert(response.message);
                }

            }
        )
    }

    //添加图片到图片列表的方法
    $scope.addImage = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.entity_image);
    }

    //移除图片从列表中
    $scope.deleImage = function ($index) {
        $scope.entity.goodsDesc.itemImages.splice($index,1);
    }



//参数1，多选框对象，规格名称，规格选项名称
    $scope.updateSpecAttribute = function($event, name, value) {
        var  object = searchObjectByKey($scope.entity.goodsDesc.specificationItems,name);//参数1.是保存选择后的所有规格选项list集合，name是
        if(object == null){//没找到该规格
            $scope.entity.goodsDesc.specificationItems.push({'attributeName':name,'attributeValue':[value]});
        }else{ //找到该规格
            if($event.target.checked){ //是否勾选
                object.attributeValue.push(value);
            }else{
                //如果是取消勾选，移除掉attributeValue中的内容
                object.attributeValue.splice(object.attributeValue.indexOf(value),1);
                //如果attributeValue中没有规格选项，将{"attributeName":"网络","attributeValue":["移动3G","移动4G"]}从$scope.entity.goodsDesc.specificationItems移除掉
                if(object.attributeValue.length < 1){
                    $scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object),1);
                }
            }
        }
    }

    //创建itemList列表方法
    $scope.createItemList = function() {
        $scope.entity.itemList = [ {spec : {},price : 0,num : 99999,status : '0',isDefault : '0'} ];// 初始
        var items = $scope.entity.goodsDesc.specificationItems; //用户勾选规格&规格选项
        for (var i = 0; i < items.length; i++) {
            $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);
        }
    }


    // 添加列值:参数1是创建的itemList集合（空），参数2是规格名称，参数3是规格选项的集合
    addColumn = function(list, columnName, conlumnValues) {
        var newList = [];// 新的集合
        for (var i = 0; i < list.length; i++) {
            var oldRow = list[i];  //获取出当前行的内容 {spec : {},price : 0,num : 99999,status : '0',isDefault : '0'}
            for (var j = 0; j < conlumnValues.length; j++) {//循环attributeValue数组的内容
                var newRow = JSON.parse(JSON.stringify(oldRow));// 深克隆,根据attributeValue的数量，将对象转字符串，字符串转对象完成深克隆
                newRow.spec[columnName] = conlumnValues[j];//{spec:{"网络制式":"移动4G"},price:'0.01',num:'99999',status:'0',isDefault:'0'}
                newList.push(newRow);
            }
        }
        return newList;
    }

    //通过该方法查询list集合中和规格名称一样的对象
    searchObjectByKey=function(list,value){
        for (var i = 0; i < list.length; i++) {
            if(list[i].attributeName == value){
                return list[i];
            }
        }
        return null;
    }

    $scope.status = ['未申请','审核中','审核通过','审核驳回'];

    $scope.itemCatList = [];
    $scope.findItemCatList = function () {
        itemCatService.findAll().success(
            function (response) {
                for (var i = 0; i < response.length; i++) {
                    $scope.itemCatList[response[i].id] = response[i].name;
                }
            }
        )
    }

    //商家修改商品状态
    $scope.updateStatus = function (status) {
        goodsService.updateStatus($scope.selectIds,status).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();
                } else {
                    alert(response.message);
                }
                $scope.selectIds = [];
            }

        )
    }
    //返回是否勾选
    $scope.checkAttributeValue=function(specName,optionName){
        var items= $scope.entity.goodsDesc.specificationItems;
        var object= searchObjectByKey(items,specName);
        if(object==null){ //找规格
            return false;
        }else{
            if(object.attributeValue.indexOf(optionName)>=0){ //找规格选项
                return true;
            }else{
                return false;
            }
        }
    }

});	
