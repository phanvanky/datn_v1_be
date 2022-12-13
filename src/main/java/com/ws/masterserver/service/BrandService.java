package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.enum_dto.BrandDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

public interface BrandService {
    ResData<String> create(CurrentUser currentUser, BrandDto dto);

    ResData<String> delete(CurrentUser currentUser, BrandDto dto);

    ResData<String> update(CurrentUser currentUser, BrandDto dto);

    ResData<String> changeStatus(CurrentUser currentUser, String id);

    Object detail(CurrentUser currentUser, String id);

    Object noPage();
}
