package com.alpha.repository.rdb.singleTable.dao;

import com.alpha.repository.entity.Address;

public interface AddressMapper {
    Address getAddressByBacklogId(Integer backlogId);
}