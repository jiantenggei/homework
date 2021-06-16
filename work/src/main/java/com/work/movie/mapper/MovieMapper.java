package com.work.movie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.movie.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2021-05-25
 */
@Mapper
@Component("MovieMapper")
public interface MovieMapper extends BaseMapper<Movie> {

}
