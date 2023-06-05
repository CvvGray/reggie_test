package com.cvv.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cvv.reggie.common.R;
import com.cvv.reggie.entity.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
