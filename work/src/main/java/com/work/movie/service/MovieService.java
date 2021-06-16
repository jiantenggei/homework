package com.work.movie.service;

import com.work.movie.entity.Movie;
import com.baomidou.mybatisplus.extension.service.IService;
import com.work.movie.vo.SearchVo;
import com.work.result.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian
 * @since 2021-05-25
 */
public interface MovieService extends IService<Movie> {

    Result getMovies(SearchVo searchVo);
}
