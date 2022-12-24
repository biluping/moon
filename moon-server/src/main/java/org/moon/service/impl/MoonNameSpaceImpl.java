package org.moon.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.MoonNameSpaceEntity;
import org.moon.entity.vo.MoonNameSpaceVo;
import org.moon.mapper.MoonNameSpaceMapper;
import org.moon.service.MoonNameSpaceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MoonNameSpaceImpl extends ServiceImpl<MoonNameSpaceMapper, MoonNameSpaceEntity>
    implements MoonNameSpaceService {

    @Override
    public List<MoonNameSpaceVo> listByAppid(String appid) {
        LambdaQueryWrapper<MoonNameSpaceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MoonNameSpaceEntity::getAppid, appid);
        wrapper.orderByAsc(MoonNameSpaceEntity::getCreateTime);
        List<MoonNameSpaceEntity> list = list();
        return Convert.toList(MoonNameSpaceVo.class, list);
    }

    @Override
    public MoonNameSpaceEntity getByAppidAndNameSpace(String appid, String nameSpace) {
        return lambdaQuery().eq(MoonNameSpaceEntity::getAppid, appid)
                .eq(MoonNameSpaceEntity::getName, nameSpace)
                .last("limit 1")
                .one();
    }
}




