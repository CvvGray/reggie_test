package com.cvv.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvv.reggie.entity.AddressBook;
import com.cvv.reggie.mapper.AddressBookMapper;
import com.cvv.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
