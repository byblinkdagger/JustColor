package com.blink.dagger.justcolor.entity;

import com.litesuits.orm.db.annotation.Default;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by lucky on 2017/7/12.
 */
@Table("color_pool")
public class Colors {
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public int id;

    @NotNull
    public String rgb;

    //是否是热门配色
    public int isPopular;

    @Default("0")
    public int isCollection;

    @Default("1")
    public int isShow;

    /**
     * 色系分类
     * 0 --> 默认
     * 1 -->
     * 2 -->
     * 3 -->
     */
    @Default("0")
    public int colorType;

    public Colors(String rgb, int colorType, int isPopular) {
        this.rgb = rgb;
        this.colorType = colorType;
        this.isPopular = isPopular;
    }
}
