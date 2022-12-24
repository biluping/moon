package org.moon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.moon.entity.MoonNameSpaceEntity;
import org.moon.entity.vo.MoonNameSpaceVo;

import java.util.List;

/**
* @author biluping
* @description 针对表【moon_app】的数据库操作Service
* @createDate 2022-09-12 18:12:45
*/
public interface MoonNameSpaceService extends IService<MoonNameSpaceEntity> {

    List<MoonNameSpaceVo> listByAppid(String appid);

    MoonNameSpaceEntity getByAppidAndNameSpace(String appid, String nameSpace);
}
