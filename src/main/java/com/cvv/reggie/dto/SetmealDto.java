package com.cvv.reggie.dto;

import com.cvv.reggie.entity.Setmeal;
import com.cvv.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
