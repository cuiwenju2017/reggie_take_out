package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.*;
import com.itheima.reggie.mapper.OrdersMapper;
import com.itheima.reggie.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    private BigDecimal zamount;

    /**
     * 用户下单
     *
     * @param orders
     */
    @Override
    @Transactional
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId = orders.getUserId();
        //查询当前用户购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);
        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            throw new CustomException("购物车为空，不能下单");
        }
        //查询用户数据
        User user = userService.getById(userId);
        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            throw new CustomException("用户地址信息有误，不能下单");
        }
        //向订单表插入数据，一条数据
        long orderId = IdWorker.getId();//订单号

        zamount = new BigDecimal(0);
        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);//订单编号
            orderDetail.setNumber(item.getNumber());//菜品或套餐数量
            orderDetail.setDishFlavor(item.getDishFlavor());//口味
            orderDetail.setDishId(item.getDishId());//菜品id
            orderDetail.setSetmealId(item.getSetmealId());//套餐id
            orderDetail.setName(item.getName());//菜品或套餐名称
            orderDetail.setImage(item.getImage());//菜品或套餐图片
            orderDetail.setAmount(item.getAmount());//单份金额

            BigDecimal number = new BigDecimal(item.getNumber());
            BigDecimal amount = item.getAmount().multiply(number);
            zamount = zamount.add(amount);

            return orderDetail;
        }).collect(Collectors.toList());

        orders.setId(orderId);//订单id
        orders.setOrderTime(LocalDateTime.now());//订单生成时间
        orders.setCheckoutTime(LocalDateTime.now());//订单支付时间
        orders.setStatus(2);//订单状态 1：待付款  2:待派送  3：已派送  4：已完成  5：已取消
        orders.setAmount(zamount);//订单总金额
        orders.setUserId(userId);//用户id
        orders.setNumber(String.valueOf(orderId));//订单号
        orders.setUserName(user.getName());//用户昵称
        orders.setConsignee(addressBook.getConsignee());//收餐人姓名
        orders.setPhone(addressBook.getPhone());//收餐人手机号
        //详细地址
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

        this.save(orders);
        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);
        //下单完成，清空购物车数据
        shoppingCartService.remove(wrapper);
    }
}
