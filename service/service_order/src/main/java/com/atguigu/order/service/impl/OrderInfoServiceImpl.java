package com.atguigu.order.service.impl;

import com.atguigu.ggkt.model.order.OrderDetail;
import com.atguigu.ggkt.model.order.OrderInfo;
import com.atguigu.ggkt.vo.order.OrderInfoQueryVo;
import com.atguigu.order.mapper.OrderInfoMapper;
import com.atguigu.order.service.OrderDetailService;
import com.atguigu.order.service.OrderInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务实现类
 * </p>
 *
 * @author ZRH
 * @since 2022-10-25
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

//    订单详情
    @Autowired
    private OrderDetailService orderDetailService;

//    订单列表
    @Override
    public Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo) {

//      获取查询条件
//        用户id
        Long userId = orderInfoQueryVo.getUserId();
//        交易号
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
//        电话号
        String phone = orderInfoQueryVo.getPhone();
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();
//        判断条件之是否为空
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(orderStatus)) {
            wrapper.eq("order_status",orderStatus);
        }
        if(!StringUtils.isEmpty(userId)) {
            wrapper.eq("user_id",userId);
        }
        if(!StringUtils.isEmpty(outTradeNo)) {
            wrapper.eq("out_trade_no",outTradeNo);
        }
        if(!StringUtils.isEmpty(phone)) {
            wrapper.eq("phone",phone);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
//        调用mapper中的方法进行分页
        Page<OrderInfo> pages = baseMapper.selectPage(pageParam, wrapper);
//        记录数
        long total = pages.getTotal();
        // 总页数
        long pages1 = pages.getPages();
//        订单信息
        List<OrderInfo> records = pages.getRecords();
//        封装详情数据
        records.stream().forEach(item ->{
            this.getOrderDetail(item);
        });
//        所有数据统一封装进行返回
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("pageCount",pages);
        map.put("records",records);
        return map;
    }

//查询订单数据
    private OrderInfo getOrderDetail(OrderInfo orderInfo) {
//        得到订单id
        Long id = orderInfo.getId();
//    查询详情
        OrderDetail orderDetail = orderDetailService.getById(id);
        if(orderDetail!=null){
            String courseName = orderDetail.getCourseName();
            orderInfo.getParam().put("courseName",courseName);
        }
        return orderInfo;
    }
}
