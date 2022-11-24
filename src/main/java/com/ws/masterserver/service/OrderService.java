package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.order.CancelOrder;
import com.ws.masterserver.dto.customer.order.OrderRequest;
import com.ws.masterserver.dto.customer.order.OrderSearch;
import com.ws.masterserver.dto.customer.order.SubmitOrderReceived;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

public interface OrderService {
    Object checkout(CurrentUser currentUser, OrderRequest req);
    ResData<String> cancelOrder(CurrentUser currentUser, CancelOrder dto);
    Object getMyOrders(CurrentUser currentUser);
    Object search(CurrentUser currentUser, OrderSearch req);
    Object countMyOrders(CurrentUser currentUser);
    void submitReceivedOrder(CurrentUser currentUser, SubmitOrderReceived req);
}
