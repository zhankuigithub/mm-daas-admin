package com.mm.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * @author:
 * Date: 2023/4/6 11:10
 * Desc:
 */
public abstract class BasePO <T extends Model<?>> extends Model<T> {
    @TableId(
            value = "id",
            type = IdType.AUTO
    )
    private Long id;

    public BasePO() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
