package org.macula.engine.mybatis.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.macula.engine.mybatis.entity.User;
import org.macula.engine.mybatis.vo.UserVO;

/**
 * <p>
 * <b>UserMapper</b> User Entity Mapper
 * </p>
 *
 */
public interface UserMapper extends BaseMapper<User> {

	List<UserVO> listById(@Param("id") Long id);

}
