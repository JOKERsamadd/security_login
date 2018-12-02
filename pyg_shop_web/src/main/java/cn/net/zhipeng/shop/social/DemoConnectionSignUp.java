package cn.net.zhipeng.shop.social;

import cn.net.zhipeng.pojo.Seller;
import cn.net.zhipeng.sellergoods.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    @Reference
    SellerService sellerService;

    @Override
    public String execute(Connection<?> connection) {
        Seller seller = new Seller();
        seller.setCreateTime(new Date());
        seller.setStatus("0"); //0未审核 1已审核 2已驳回
        seller.setNickName(connection.getDisplayName());
        seller.setSellerId("qq_" + UUID.randomUUID().toString().substring(0, 5));
        sellerService.add(seller);
        //根据社交用户信息默认创建用户并返回用户唯一标识
        return seller.getSellerId();
    }

}